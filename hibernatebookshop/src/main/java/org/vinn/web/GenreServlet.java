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
import java.util.ArrayList;
import java.util.List;


@WebServlet("/")
public class GenreServlet extends HttpServlet {
    private GenreDao genreDao;

    public void init() {
        genreDao = new GenreDaoImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String action = request.getServletPath();

//        System.out.println("Action"+action);
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
                case "/book-new":
                    System.out.println("To Do");
                    break;
                case "/book-edit":
                    System.out.println("To Do");
                    break;
                case "/book-list":
                    System.out.println("To Do");
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error." + e.getMessage());
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        Genre genre = genreDao.findById(id);
//        GenreResponseDto genreResponseDto = GenreMapper.ToResponseDto(genre);
        request.setAttribute("genre", genre);
        RequestDispatcher dispatcher = request.getRequestDispatcher("genre-form.jsp");
        dispatcher.forward(request, response);
    }

    private void updateToDB(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        genreDao.update(id, new Genre(id, name));
        response.sendRedirect(request.getContextPath() + "/genre-list");
    }

    private void deleteGenreById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
//        Genre genre = genreDao.findById(id);
        genreDao.deleteById(id);
        response.sendRedirect(request.getContextPath() + "/genre-list");
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("genre-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showGenreList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Genre> genreList = genreDao.findAll();

//        List<GenreResponseDto> genreResponseDtoList = new ArrayList<>();
//        for (Genre genre: genreList) {
//            genreResponseDtoList.add(
//                    GenreMapper.ToResponseDto(genre)
//            );
//        }
        request.setAttribute("genreList", genreList);
        request.setAttribute("greet", "Hello");
        RequestDispatcher dispatcher = request.getRequestDispatcher("genre-list.jsp");
        dispatcher.forward(request, response);
    }

    private void saveToDb(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String name = request.getParameter("name");
        genreDao.save(new Genre(name));
//        genreDao.save(
//                new GenreRequestDto(
//                        name
//                )
//        );

//        RequestDispatcher dispatcher = request.getRequestDispatcher("genre-list.jsp");
//        dispatcher.forward(request, response);
        response.sendRedirect(request.getContextPath() + "/genre-list");
    }
}
