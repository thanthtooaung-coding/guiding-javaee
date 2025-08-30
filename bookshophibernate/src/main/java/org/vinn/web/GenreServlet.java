package org.vinn.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.vinn.dao.GenreDao;
import org.vinn.dao.impl.GenreDaoImpl;
import org.vinn.model.Genre;

import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class GenreServlet extends HttpServlet {
    private GenreDao genreDao;

    public void init() {
        genreDao = new GenreDaoImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/genre-new":
                    showNewForm(request, response);
                    break;
                case "/genre-save":
                    saveToDb(request, response);
                    break;
                case "/genre-edit":
                    showEditForm(request, response);
                    break;
                case "/genre-update":
                    updateToDB(request, response);
                    break;
                case "/genre-list":
                    showGenreList(request, response);
                    break;
                case "/genre-delete":
                    deleteGenreById(request, response);
                    break;
                default:
                    showGenreList(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Genre existingGenre = genreDao.findById(id);
        request.setAttribute("genre", existingGenre);
        RequestDispatcher dispatcher = request.getRequestDispatcher("genre-form.jsp");
        dispatcher.forward(request, response);
    }

    private void updateToDB(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");

        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(name);
        genreDao.update(genre);
        response.sendRedirect(request.getContextPath() + "/genre-list");
    }

    private void deleteGenreById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        genreDao.deleteById(id);
        response.sendRedirect(request.getContextPath() + "/genre-list");
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("genre-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showGenreList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Genre> genreList = genreDao.findAll();
        request.setAttribute("genreList", genreList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("genre-list.jsp");
        dispatcher.forward(request, response);
    }

    private void saveToDb(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        Genre newGenre = new Genre(name);
        genreDao.save(newGenre);
        response.sendRedirect(request.getContextPath() + "/genre-list");
    }
}
