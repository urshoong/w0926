package org.fs2.w0926.controller;

import org.fs2.w0926.dao.TodoDAO;
import org.fs2.w0926.dto.TodoDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "TodoUpdateController", value = "/todo/update")
public class TodoUpdateController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tno = request.getParameter("tno");

        try {
            TodoDTO todoDTO = TodoDAO.INSTANCE.selectOne(Integer.parseInt(tno));

            request.setAttribute("todo", todoDTO);

            request.getRequestDispatcher("/WEB-INF/views/todo/update.jsp").forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tno = request.getParameter("tno");
        String title = request.getParameter("title");
        String memo = request.getParameter("memo");
        String dueDate = request.getParameter("dueDate");
        String complete = request.getParameter("complete");

        System.out.println("complete :::" + complete);

        String[] dates = dueDate.split("-");

        TodoDTO todoDTO = TodoDTO.builder()
                .tno(Integer.parseInt(tno))
                .title(title)
                .memo(memo)
                .dueDate(new Date((Integer.parseInt(dates[0]) - 1900), (Integer.parseInt(dates[1]) - 1), Integer.parseInt(dates[2])).toLocalDate())
                .complete(complete == null ? false : true)
                .build();

        try {
            int result = TodoDAO.INSTANCE.update(todoDTO);

            System.out.println("update result : " + result);

            response.sendRedirect("/todo/list?work=update&result="+result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
