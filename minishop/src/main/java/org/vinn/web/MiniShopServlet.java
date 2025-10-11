package org.vinn.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.vinn.dto.CategoryDto;
import org.vinn.dto.ProductDto;
import org.vinn.model.User;
import org.vinn.service.CategoryService;
import org.vinn.service.ProductService;
import org.vinn.service.UserService;
import org.vinn.service.impl.ProductServiceImpl;
import org.vinn.service.impl.CategoryServiceImpl;
import org.vinn.service.impl.UserServiceImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@WebServlet("/mini-shop")
public class MiniShopServlet extends HttpServlet {

    private CategoryService categoryService;
    private ProductService productService;
    private UserService userService;

    public void init() {

        categoryService = new CategoryServiceImpl();
        productService = new ProductServiceImpl();
        userService = new UserServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "login":
                    showLoginFrom(request,response);
                    break;
                case "register":
                    showRegisterFrom(request,response);
                    break;
                case "dashboard":
                    showDashboard(request, response);
                    break;
                /* Category */
                case "category-create":
                    request.getRequestDispatcher("category-create.jsp").forward(request, response);
                    break;
                case "category-list":
                    showCategoryList(request, response);
                    break;
                case "category-edit":
                    showEditForm(request, response);
                    break;
                case "category-delete":
                    handleDelete(request, response);
                    break;
                    /* Product */
                case "product-create":
                    showProductCreateForm(request, response);
                    break;
                case "product-list":
                    showProductList(request, response);
                    break;
                case "product-edit":
                    showProductEditForm(request, response);
                    break;
                case "product-delete":
                    handleProductDelete(request, response);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("minishop-username")) {
                    request.setAttribute("username", cookie.getValue());
                }
            }
        }
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    private void showRegisterFrom(HttpServletRequest request, HttpServletResponse response)throws Exception {
        request.getRequestDispatcher("register.jsp").forward(request,response);
    }

    private void showLoginFrom(HttpServletRequest request, HttpServletResponse response) throws Exception{
        request.getRequestDispatcher("login.jsp").forward(request,response);
    }

    private void showProductCreateForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("categories", categoryService.retrieveAll());
        // for (type var: array)
        for (CategoryDto category: categoryService.retrieveAll()) {
            System.out.println(category.getName());
        }
        request.getRequestDispatcher("product-create.jsp").forward(request, response);
    }

    private void handleProductDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = Long.valueOf(request.getParameter("id"));
        productService.delete(id);
        response.sendRedirect("mini-shop?action=product-list");
    }

    private void showProductEditForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = Long.valueOf(request.getParameter("id"));
        request.setAttribute("product", productService.retrieveOne(id));
        request.getRequestDispatcher("product-edit.jsp").forward(request, response);
    }

    private void showProductList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("products", productService.retrieveAll());
        request.getRequestDispatcher("product-list.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        category
        Long id = Long.valueOf(request.getParameter("id"));
        request.setAttribute("category", categoryService.retrieveOne(id));
        request.getRequestDispatcher("category-edit.jsp").forward(request, response);
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = Long.valueOf(request.getParameter("id"));
        categoryService.delete(id);
        response.sendRedirect("mini-shop?action=category-list");
    }

    private void showCategoryList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("categories", categoryService.retrieveAll());
        request.getRequestDispatcher("category-list.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "login":
                    handleLogin(request, response);
                    break;
                case "register":
                    handleRegistration(request, response);
                    break;
                case "category-create":
                    handleCategoryCreate(request, response);
                    break;
                case "category-edit":
                    handleEdit(request, response);
                    break;
                    /* Product */
                case "product-create":
                    handleProductCreate(request, response);
                    break;
                case "product-edit":
                    handleProductEdit(request, response);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    private void handleCategoryCreate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cookie[] cookies = request.getCookies();
        String username = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("minishop-username")) {
                username = cookie.getValue();
            }
        }
        if (username == null) {
            response.sendRedirect("mini-shop?action=login");
        }
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new Exception("User not found"));
        categoryService.create(
                request.getParameter("name"), user.getId()
        );
    }

    private void handleRegistration(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("Registering user: " + username);
        System.out.println("Password: " + password);
        userService.create(username, password);
        response.sendRedirect("mini-shop?action=login");
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username"); // Tester123

        /*
        Random username with Array = [ "3", "2", "1", "T", "e", "s", "t", "e", "r"];
        Random username with String = "321Tester";
        What are number? -> 321
        What are String? -> Tester
         */

        String password = request.getParameter("password");
        Optional<User> userOptional = userService.findByUsername(username);

        if(userOptional.isPresent() && userOptional.get().getPassword().equals(password)){
            final Cookie userCookie = new Cookie("minishop-username", username);
            userCookie.setMaxAge(60 * 60 * 24 * 365);
            response.addCookie(userCookie);

            response.sendRedirect("mini-shop?action=dashboard");
        } else {
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void handleProductEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setId(Long.valueOf(request.getParameter("id")));
        productDto.setName(request.getParameter("name"));
        productDto.setPrice(BigDecimal.valueOf(Double.parseDouble(request.getParameter("price"))));
        productDto.setDescription(request.getParameter("description"));
        productDto.setImageUrl(request.getParameter("imageUrl"));

        productService.edit(productDto);

        response.sendRedirect("mini-shop?action=product-list");
    }

    private void handleProductCreate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");
        Long categoryId = Long.valueOf(request.getParameter("categoryId"));
        String description = request.getParameter("description");
        String imageUrl = request.getParameter("imageUrl");
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(request.getParameter("price")));

        System.out.println("Category Id: " + categoryId);
        productService.create(
                new ProductDto().initialize(
                        name,
                        description,
                        imageUrl,
                        price,
                        new CategoryDto(
                                categoryId, "", 0L
                        )
                )
        );
    }

    private void handleEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = Long.valueOf(request.getParameter("id"));
        String name = request.getParameter("name");
        categoryService.edit(id, name);
        response.sendRedirect("mini-shop?action=category-list");
    }
}
