package org.vinn;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/")
public class StudentServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // how to prepare data for Student List page
        List<Student> students = new ArrayList<>();
        students.add(new Student("Vincent", 20));
        students.add(new Student("Vincent2", 21));
        students.add(new Student("Vincent3", 22));

        for (Student student : students) {
            System.out.println(student.getName() + " " + student.getAge());
        }
        request.setAttribute("studentList", students);
        RequestDispatcher dispatcher = request.getRequestDispatcher("StudentList.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {

    }
}
