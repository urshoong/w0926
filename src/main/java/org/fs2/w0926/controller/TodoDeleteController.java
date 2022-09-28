package org.fs2.w0926.controller;

import org.fs2.w0926.dao.TodoDAO;
import org.fs2.w0926.dto.TodoDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TodoDeleteController", value = "/todo/delete")
public class TodoDeleteController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tno = request.getParameter("tno");

        System.out.println("delete tno : " + tno);

        TodoDTO todoDTO = TodoDTO.builder().tno(Integer.parseInt(tno)).build();

        try {
            int result = TodoDAO.INSTANCE.delete(todoDTO);
            System.out.println("delete resut = " + result);

            request.setAttribute("result", result);

            response.sendRedirect("/todo/list?work=delete&result="+result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
