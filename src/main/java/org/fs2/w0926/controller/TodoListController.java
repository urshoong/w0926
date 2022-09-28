package org.fs2.w0926.controller;

import org.fs2.w0926.dao.TodoDAO;
import org.fs2.w0926.dto.TodoDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "TodoListController", value = "/todo/list")
public class TodoListController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            int page = Integer.parseInt( request.getParameter("page") == null ? "1" : request.getParameter("page") );
            int size = 10;

            List<TodoDTO> data = TodoDAO.INSTANCE.list(page,size);

            int paging = TodoDAO.INSTANCE.page();

            String work = request.getParameter("work");
            String result = request.getParameter("result");

            //jsp에 보내줌
            request.setAttribute("page", page);
            request.setAttribute("paging", ((paging / size) + 1));
            request.setAttribute("list", data);
            request.setAttribute("work", work);
            request.setAttribute("result", result);
            request.getRequestDispatcher("/WEB-INF/views/todo/list.jsp").forward(request,response);
        }
        catch(Exception e ){
            throw new RuntimeException(e);
        }

    }


}
