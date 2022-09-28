package org.fs2.w0926.controller;

import org.fs2.w0926.dao.TodoDAO;
import org.fs2.w0926.dto.TodoDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TodoDetailController", value = "/todo/detail")
public class TodoDetailController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tno = request.getParameter("tno");

        try {
            TodoDTO todoDTO = TodoDAO.INSTANCE.selectOne(Integer.parseInt(tno));

            request.setAttribute("todo", todoDTO);

            request.getRequestDispatcher("/WEB-INF/views/todo/detail.jsp").forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
