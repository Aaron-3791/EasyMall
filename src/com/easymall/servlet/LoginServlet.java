package com.easymall.servlet;

import com.easymall.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//@WebServlet("LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //处理乱码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //获取参数
        String username=request.getParameter("username");
        String remname=request.getParameter("remname");
        String password=request.getParameter("password");

        //判断处理
        if("true".equals(remname)){
            Cookie ck=new Cookie("username",username);
            ck.setPath(request.getContextPath()+"/");
            ck.setMaxAge(60*60*24*30);
            response.addCookie(ck);
        }else
        {
            Cookie ck=new Cookie("username","");
            ck.setPath(request.getContextPath()+"/");
            ck.setMaxAge(0);
            response.addCookie(ck);
        }
        //登录验证
        //连接
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            conn= WebUtils.getConnection();
            //查询
            ps=conn.prepareStatement("select 1 from user where username=? and password=?");
            ps.setString(1,username);
            ps.setString(2,password);
            rs = ps.executeQuery();
            //判断
            if(rs.next()){
                response.getWriter().write("<h1 align='center'> <font color='red'> 登录成功，3秒后跳回首页......</font></h1>");
                response.setHeader("refresh","3;url="+request.getContextPath()+"/");

            }else
            {
                /*response.getWriter().write("<h1 align='center'><font color='red'>登录失败：用户名和密码不正确！</font></h1>");

                response.sendRedirect(request.getContextPath()+"/login.jsp");*/
                request.setAttribute("msg","登录失败：用户名和密码不正确！");
                request.getRequestDispatcher(request.getContextPath()+"/login.jsp").forward(request,response);
                return;
            }
            //关闭连接
        } catch (SQLException e) {
            response.getWriter().write("<h1 align='center'><font color='red'>登录异常："+e.getMessage()+"</font></h1>");
        }finally {
            WebUtils.close(conn,ps,rs);
        }

    }
}
