package org.vinn.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
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

        HttpSession session = request.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        if (!isLoggedIn) {
            final Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("username")) {
                        final String username = cookie.getValue();
                        Optional<User> userOptional = userDao.findByUsername(username);
                        if (userOptional.isPresent()) {
                            session = request.getSession();
                            session.setAttribute("user", userOptional.get());
                            isLoggedIn = true;
                        }
                        break;
                    }
                }
            }
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "dashboard";
        }

        // Publicly accessible actions
        if ("login".equals(action) || "register".equals(action)) {
            try {
                if ("login".equals(action)) {
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else { // register
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }
            } catch (Exception e) {
                handleException(response, e);
            }
            return;
        }

        if (!isLoggedIn) {
            response.sendRedirect("banking?action=login");
            return;
        }

        // Actions requiring login
        try {
            switch (action) {
                case "dashboard":
                    showDashboard(request, response);
                    break;
                case "transfer":
                    showTransferPage(request, response);
                    break;
                case "profile":
                    request.getRequestDispatcher("profile.jsp").forward(request, response);
                    break;
                case "createAccount":
                    request.getRequestDispatcher("create-account.jsp").forward(request, response);
                    break;
                case "accountDetails":
                    showAccountDetails(request, response);
                    break;
                case "logout":
                    logout(request, response);
                    break;
                default:
                    response.sendRedirect("banking?action=dashboard");
            }
        } catch (Exception e) {
            handleException(response, e);
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
                case "updateProfile":
                    handleProfileUpdate(request, response);
                    break;
                case "createAccount":
                    handleCreateAccount(request, response);
                    break;
                case "changeTheme":
                    handleChangeTheme(request, response);
                    break;
                default:
                    response.sendRedirect("index.jsp");
            }
        } catch (Exception e) {
            handleException(response, e);
        }
    }

    private void handleChangeTheme(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String theme = request.getParameter("theme");
        if (theme != null && !theme.isEmpty()) {
            Cookie themeCookie = new Cookie("theme", theme);
            themeCookie.setMaxAge(365 * 24 * 60 * 60);
            response.addCookie(themeCookie);
        }
        response.sendRedirect("banking?action=dashboard");
    }

    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
    }

    private void showTransferPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        List<Account> accounts = accountDao.findAccountsByUser(user);
        request.setAttribute("accounts", accounts);
        request.getRequestDispatcher("transfer.jsp").forward(request, response);
    }

    private void showAccountDetails(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String accountNumber = request.getParameter("accountNumber");
        User user = (User) request.getSession().getAttribute("user");

        Optional<Account> accountOpt = accountDao.findByAccountNumber(accountNumber);
        if (accountOpt.isPresent() && accountOpt.get().getUser().getId().equals(user.getId())) {
            Account account = accountOpt.get();
            List<Transaction> transactions = transactionDao.findTransactionsByAccount(account);
            request.setAttribute("account", account);
            request.setAttribute("transactions", transactions);
            request.getRequestDispatcher("account-details.jsp").forward(request, response);
        } else {
            response.sendRedirect("banking?action=dashboard");
        }
    }

    private void handleCreateAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        String accountName = request.getParameter("accountName");
        String accountType = request.getParameter("accountType");
        BigDecimal initialDeposit = new BigDecimal(request.getParameter("initialDeposit"));

        Account account = new Account();
        account.setUser(user);
        account.setAccountName(accountName);
        account.setAccountType(accountType);
        account.setBalance(initialDeposit);
        account.setAccountNumber(UUID.randomUUID().toString().substring(0, 10));
        accountDao.saveAccount(account);

        response.sendRedirect("banking?action=dashboard");
    }

    private void handleProfileUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setEmail(request.getParameter("email"));

        userDao.updateUser(user);
        session.setAttribute("user", user);

        request.setAttribute("message", "Profile updated successfully!");
        request.getRequestDispatcher("profile.jsp").forward(request, response);
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

        // Create a default checking account
        Account account = new Account();
        account.setUser(user);
        account.setBalance(new BigDecimal("1000.00")); // Initial deposit
        account.setAccountNumber(UUID.randomUUID().toString().substring(0, 10));
        account.setAccountName("Primary Checking");
        account.setAccountType("Checking");
        accountDao.saveAccount(account);

        response.sendRedirect("banking?action=login");
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        Optional<User> userOptional = userDao.findByUsername(username);

        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", userOptional.get());
            if ("true".equals(rememberMe)) {
                final Cookie userCookie = new Cookie("username", username);
                userCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days (in seconds)
                response.addCookie(userCookie);
            }

            Cookie lastLoginCookie = new Cookie("lastLogin", String.valueOf(System.currentTimeMillis()));
            lastLoginCookie.setMaxAge(365 * 24 * 60 * 60); // 1 year (in seconds)
            response.addCookie(lastLoginCookie);
            response.sendRedirect("banking?action=dashboard");
        } else {
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        List<Account> accounts = accountDao.findAccountsByUser(user);
        request.setAttribute("accounts", accounts);

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("lastLogin")) {
                    long lastLoginTime = Long.parseLong(cookie.getValue());
                    request.setAttribute("lastLoginTime", new java.util.Date(lastLoginTime));
                }
            }
        }

        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    private void handleTransfer(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String sourceAccountNumber = request.getParameter("sourceAccountNumber");
        String targetAccountNumber = request.getParameter("targetAccountNumber");
        BigDecimal amount = new BigDecimal(request.getParameter("amount"));

        Optional<Account> sourceAccountOpt = accountDao.findByAccountNumber(sourceAccountNumber);
        Optional<Account> targetAccountOpt = accountDao.findByAccountNumber(targetAccountNumber);

        User user = (User) request.getSession().getAttribute("user");
        List<Account> userAccounts = accountDao.findAccountsByUser(user);
        request.setAttribute("accounts", userAccounts);


        if (!sourceAccountOpt.isPresent()) {
            request.setAttribute("error", "Source account not found.");
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
            return;
        }

        // Security check: Ensure the source account belongs to the logged-in user
        if (sourceAccountOpt.get().getUser().getId() != user.getId()){
            request.setAttribute("error", "Invalid source account.");
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
            return;
        }


        if (!targetAccountOpt.isPresent()) {
            request.setAttribute("error", "Target account not found.");
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
            return;
        }

        if (sourceAccountNumber.equals(targetAccountNumber)) {
            request.setAttribute("error", "You cannot transfer money to the same account.");
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
            return;
        }

        Account sourceAccount = sourceAccountOpt.get();
        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            request.setAttribute("error", "Insufficient balance.");
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
            return;
        }

        Account targetAccount = targetAccountOpt.get();

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
        Cookie userCookie = new Cookie("username", null);
        userCookie.setMaxAge(0);
        response.addCookie(userCookie);
        response.sendRedirect("index.jsp");
    }
}
