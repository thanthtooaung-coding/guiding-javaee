package org.vinn.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.vinn.dao.AccountDao;
import org.vinn.dao.TransactionDao;
import org.vinn.dao.UserDao;
import org.vinn.dao.impl.AccountDaoImpl;
import org.vinn.dao.impl.TransactionDaoImpl;
import org.vinn.dao.impl.UserDaoImpl;
import org.vinn.model.Account;
import org.vinn.model.Transaction;
import org.vinn.model.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/banking")
public class BankingServlet extends HttpServlet {

    private UserDao userDao;
    private AccountDao accountDao;
    private TransactionDao transactionDao;

    public void init() {
        userDao = new UserDaoImpl();
        accountDao = new AccountDaoImpl();
        transactionDao = new TransactionDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "dashboard";
        }
        try {
            switch (action) {
                case "login":
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    break;
                case "register":
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                    break;
                case "dashboard":
                    showDashboard(request, response);
                    break;
                case "transfer":
                    request.getRequestDispatcher("transfer.jsp").forward(request, response);
                    break;
                case "logout":
                    logout(request, response);
                    break;
                default:
                    response.sendRedirect("index.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        try {
            switch (action) {
                case "register":
                    handleRegistration(request, response);
                    break;
                case "login":
                    handleLogin(request, response);
                    break;
                case "transfer":
                    handleTransfer(request, response);
                    break;
                default:
                    response.sendRedirect("index.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }
    }

    private void handleRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (userDao.findByUsername(username).isPresent()) {
            request.setAttribute("error", "Username already exists.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // Should be hashed
        userDao.saveUser(user);


        Account account = new Account();
        account.setUser(user);
        account.setBalance(new BigDecimal("1000.00")); // Initial deposit
        account.setAccountNumber(UUID.randomUUID().toString().substring(0, 10));
        accountDao.saveAccount(account);

        response.sendRedirect("banking?action=login");
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Optional<User> userOptional = userDao.findByUsername(username);

        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", userOptional.get());
            session.setAttribute("account", userOptional.get().getAccounts().iterator().next());
            response.sendRedirect("banking?action=dashboard");
        } else {
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        Account account = (Account) session.getAttribute("account");
        if (account != null) {
            List<Transaction> transactions = transactionDao.findTransactionsByAccount(account);
            request.setAttribute("transactions", transactions);
        }
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    private void handleTransfer(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        Account sourceAccount = (Account) session.getAttribute("account");
        String targetAccountNumber = request.getParameter("targetAccountNumber");
        BigDecimal amount = new BigDecimal(request.getParameter("amount"));

        Optional<Account> targetAccountOptional = accountDao.findByAccountNumber(targetAccountNumber);

        if (!targetAccountOptional.isPresent()) {
            request.setAttribute("error", "Target account not found.");
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
            return;
        }

        Account targetAccount = targetAccountOptional.get();

        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            request.setAttribute("error", "Insufficient balance.");
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
            return;
        }

        // Perform transaction
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        targetAccount.setBalance(targetAccount.getBalance().add(amount));

        accountDao.updateAccount(sourceAccount);
        accountDao.updateAccount(targetAccount);

        Transaction transaction = new Transaction();
        transaction.setSourceAccount(sourceAccount);
        transaction.setTargetAccount(targetAccount);
        transaction.setAmount(amount);
        transaction.setTransactionDate(new Date());

        transactionDao.saveTransaction(transaction);

        response.sendRedirect("banking?action=dashboard");
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("index.jsp");
    }
}
