package org.vinn.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.vinn.dao.AccountDao;
import org.vinn.dao.AccountTransactionDao;
import org.vinn.dao.UserDao;
import org.vinn.dao.impl.AccountDaoImpl;
import org.vinn.dao.impl.AccountTransactionDaoImpl;
import org.vinn.dao.impl.UserDaoImpl;
import org.vinn.model.Account;
import org.vinn.model.AccountTransaction;
import org.vinn.model.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/banking")
public class BankingServlet extends HttpServlet {

    private UserDao userDao; // Dependency Injection
    public AccountDao accountDao;
    private AccountTransactionDao accountTransactionDao;

    public void init() {
        userDao = new UserDaoImpl(); // Initializing Dependency Injection
        accountDao = new AccountDaoImpl();
        accountTransactionDao = new AccountTransactionDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        /*if (!action.equals("login") && !action.equals("register")) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                response.sendRedirect("banking?action=login");
                return;
            }
        }*/
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        if (!isLoggedIn) {
            final Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("username")) {
                        final String username = cookie.getValue();
                        try {
                            Optional<User> userOptional = userDao.findByUsername(username);
                            if (userOptional.isPresent()) {
                                session = request.getSession();
                                session.setAttribute("user", userOptional.get());
                                isLoggedIn = true;
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }

        String action = request.getParameter("action"); // banking?action=.....
        if (action == null) {
            action = "dashboard";
        }
        if (action.equals("login") || action.equals("register")) {
            try {
                if ("login".equals(action)) {
                    request.getRequestDispatcher("leogin.jsp").forward(request, response);
                } else { // register
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            return;
        }

        if (!isLoggedIn) {
            response.sendRedirect("banking?action=login");
            return;
        }

        try {
            switch (action) {
                case "dashboard":
                    showDashboard(request, response);
                    break;
                case "transfer":
                    showTransferPage(request, response);
                    break;
                case "accountDetails":
                    showAccountDetails(request, response);
                    break;
                case "createAccount":
                    request.getRequestDispatcher("create-account.jsp").forward(request, response);
                    break;
                case "logout":
                    handleLogout(request, response);
                    break;
                default:
                   request.getRequestDispatcher("home.jsp").forward(request, response);
                   break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // clear
        }
        Cookie userCookie = new Cookie("username", null);
        userCookie.setMaxAge(0);

        Cookie lastLoginCookie = new Cookie("lastLogin", null);
        lastLoginCookie.setMaxAge(0);

        response.addCookie(userCookie);
        response.addCookie(lastLoginCookie);
        response.sendRedirect("index.jsp");
    }

    private void showTransferPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        List<Account> accounts = accountDao.findAccountsByUser(user);
        request.setAttribute("accounts", accounts);
        request.getRequestDispatcher("transfer.jsp").forward(request, response);
    }

    private void showAccountDetails(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String accountNumber = request.getParameter("accountNumber");
        User user = (User) request.getSession().getAttribute("user");
        String sessionUsername = user.getUsername();
        Optional<Account> accountOptional = accountDao.findByAccountNumber(accountNumber);
        if (accountOptional.isPresent() && accountOptional.get().getUser().getUsername().equals(sessionUsername)) {
            Account account = accountOptional.get();
            request.setAttribute("account", account);
            List<AccountTransaction> transactions = accountTransactionDao.findTransactionsByAccount(account);
            request.setAttribute("transactions", transactions);
            request.getRequestDispatcher("account-details.jsp").forward(request, response);
        } else {
            response.sendRedirect("banking?action=dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "home";
        }
        if (!action.equals("login") && !action.equals("register")) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                response.sendRedirect("banking?action=login");
                return;
            }
        }
        try {
            switch (action) {
                case "login":
                    handleLogin(request, response);
                    break;
                case "register":
                    handleRegistration(request, response);
                    break;
                case "transfer":
                    handleTransfer(request, response);
                    break;
                case "createAccount":
                    handleCreateAccount(request, response);
                    break;
                default:
                    request.getRequestDispatcher("home.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleCreateAccount(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        String accountName = request.getParameter("accountName");
        String accountType = request.getParameter("accountType");
        BigDecimal initialDeposit = new BigDecimal(request.getParameter("initialDeposit"));
        final String uniqueAccountNumber = UUID.randomUUID().toString().substring(0, 10);

        accountDao.saveAccount(
                new Account(
                        uniqueAccountNumber,
                        initialDeposit,
                        user,
                        accountName,
                        accountType
                )
        );
        response.sendRedirect("banking?action=dashboard");
    }

    private void handleRegistration(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("Registering user: " + username);
        System.out.println("Password: " + password);
        User user = new User(
                username,
                password
        );

        userDao.saveUser(
                user
        );

        final String uniqueAccountNumber = UUID.randomUUID().toString().substring(0, 10);
        accountDao.saveAccount(
                new Account(
                        uniqueAccountNumber,
                        new BigDecimal(10000),
                        user,
                        "Primary Account",
                        "Savings"
                )
        );

        response.sendRedirect("banking?action=login");
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");
        Optional<User> userOptional = userDao.findByUsername(username);

        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", userOptional.get());

            if ("true".equals(rememberMe)) {
                final Cookie userCookie = new Cookie(
                        "username", username
                );
                userCookie.setMaxAge(7 * 24 * 60 * 60); // Exp time 7 days in seconds
                response.addCookie(userCookie); // Add Cookie to client's browser
            }
            final Cookie lastLoginCookie = new Cookie(
                    "lastLogin",
                    String.valueOf(System.currentTimeMillis())
            );
            lastLoginCookie.setMaxAge(365 * 24 * 60 * 60); // Exp time 1 year in seconds
            response.addCookie(lastLoginCookie); // Add Cookie to client's browser

//            session.setAttribute("accounts", userOptional.get().getAccounts().iterator().next());
            response.sendRedirect("banking?action=dashboard");
        } else {
            request.setAttribute("error", "Invalid username or password");
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
                    request.setAttribute("lastLoginTime", lastLoginTime);
                }
            }
        }

        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    private void handleTransfer(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Class Casting
        // int numOne = Integer.parseInt("123");
        // int numTwo = (Integer) "123";
        /*Account sourceAccount = (Account) session.getAttribute("account");*/
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        String sourceAccountNumber = request.getParameter("sourceAccountNumber");
        Optional<Account> sourceAccountOptional = accountDao.findByAccountNumber(sourceAccountNumber);
        Account sourceAccount;
        if (sourceAccountOptional.isEmpty()) {
            request.setAttribute("error", "Invalid account number");
            List<Account> accounts = accountDao.findAccountsByUser(user);
            request.setAttribute("accounts", accounts);
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
            return;
        } else {
            sourceAccount = sourceAccountOptional.get();
            System.out.println("ID"+ sourceAccount.getUser().getId());
        }


        if (!sourceAccount.getUser().getId().equals(user.getId())) {
            request.setAttribute("error", "Invalid source account");
            List<Account> accounts = accountDao.findAccountsByUser(user);
            request.setAttribute("accounts", accounts);
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
            return;
        }

        BigDecimal amount = new BigDecimal(request.getParameter("amount"));

        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            request.setAttribute("error", "Insufficient balance");
            List<Account> accounts = accountDao.findAccountsByUser(user);
            request.setAttribute("accounts", accounts);
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
            return;
        }

        String targetAccountNumber = request.getParameter("targetAccountNumber");
        Optional<Account> targetAccountOptional = accountDao.findByAccountNumber(targetAccountNumber);

        if (targetAccountOptional.isEmpty()) {
            request.setAttribute("error", "Invalid account number");
            List<Account> accounts = accountDao.findAccountsByUser(user);
            request.setAttribute("accounts", accounts);
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
            return;
        }

        if (sourceAccount.getAccountNumber().equals(targetAccountNumber)) {
            request.setAttribute("error", "You cannot transfer money to the same account");
            List<Account> accounts = accountDao.findAccountsByUser(user);
            request.setAttribute("accounts", accounts);
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
            return;
        }

        AccountTransaction accountTransaction = new AccountTransaction(
                "Transfer",
                LocalDate.now(),
                amount,
                sourceAccount,
                targetAccountOptional.get()
        );

        accountTransactionDao.saveTransaction(accountTransaction);

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        targetAccountOptional.get().setBalance(targetAccountOptional.get().getBalance().add(amount));
        accountDao.updateAccount(sourceAccount);
        accountDao.updateAccount(targetAccountOptional.get());
        response.sendRedirect("banking?action=dashboard");
    }
}
