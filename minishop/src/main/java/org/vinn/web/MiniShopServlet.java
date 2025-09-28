package org.vinn.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.vinn.service.CategoryService;
import org.vinn.service.impl.CategoryServiceImpl;

@WebServlet("/mini-shop")
public class MiniShopServlet extends HttpServlet {

    private CategoryService categoryService;

    public void init() {
        categoryService = new CategoryServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "category-create":
                    request.getRequestDispatcher("category-create.jsp").forward(request, response);
                    break;
                case "category-list":
                    showCategoryList(request, response);
                    break;
                case "category-delete":
                    handleDelete(request, response);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
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
                case "category-list":
                    System.out.println("Show List");
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
