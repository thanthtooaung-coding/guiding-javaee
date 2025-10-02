package org.vinn.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.vinn.dto.CategoryDto;
import org.vinn.dto.ProductDto;
import org.vinn.service.CategoryService;
import org.vinn.service.ProductService;
import org.vinn.service.impl.ProductServiceImpl;
import org.vinn.service.impl.CategoryServiceImpl;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/mini-shop")
public class MiniShopServlet extends HttpServlet {

    private CategoryService categoryService;
    private ProductService productService;

    public void init() {

        categoryService = new CategoryServiceImpl();
        productService = new ProductServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        try {
            switch (action) {
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
                case "category-create":
                    categoryService.create(
                            request.getParameter("name")
                    );
                    break;
                case "category-edit":
                    handleEdit(request, response);
                    break;
                    /* Product */
                case "product-create":
                    handleProductCreate(request, response);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    private void handleProductCreate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");
        Long categoryId = Long.valueOf(request.getParameter("categoryId"));
        String description = request.getParameter("description");
        String imageUrl = request.getParameter("imageUrl");
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(request.getParameter("price")));

        productService.create(
                new ProductDto().initialize(
                        name,
                        description,
                        imageUrl,
                        price,
                        new CategoryDto(
                                categoryId, ""
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
