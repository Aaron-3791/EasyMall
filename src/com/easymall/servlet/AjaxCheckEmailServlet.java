package com.easymall.servlet;

import com.easymall.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//@WebServlet("AjaxCheckEmailServlet")
public class AjaxCheckEmailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //处理乱码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String email = request.getParameter("email");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = WebUtils.getConnection();
            ps = conn.prepareStatement("select 1 from user where email=?");
            ps.setString(1,email);

            rs = ps.executeQuery();
            if (rs.next()) {
                response.getWriter().write("该邮箱已经被使用！~~~");
            } else {
                response.getWriter().write("恭喜，该邮箱可以被使用！~~~");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            WebUtils.close(conn, ps, rs);
        }

    }
}
