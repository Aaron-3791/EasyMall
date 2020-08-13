package com.easymall.servlet;

import com.easymall.utils.WebUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
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

//@WebServlet("/RegistServlet")
public class RegistServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //处理乱码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");


        //获取用户发送过来的信息
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");
        String valistr = request.getParameter("valistr");

        //非空校验
        if (WebUtils.isNull(username)){
            request.setAttribute("msg","用户名不能为空！");
            request.getRequestDispatcher("/regist.jsp").forward(request,response);
            return;
        }
        if (WebUtils.isNull(password)){
            request.setAttribute("msg","密码不能为空！");
            request.getRequestDispatcher("/regist.jsp").forward(request,response);
            return;
        }
        if (WebUtils.isNull(password2)){
            request.setAttribute("msg","确认密码不能为空！");
            request.getRequestDispatcher("/regist.jsp").forward(request,response);
            return;
        }
        if (WebUtils.isNull(nickname)){
            request.setAttribute("msg","昵称不能为空！");
            request.getRequestDispatcher("/regist.jsp").forward(request,response);
            return;
        }
        if (WebUtils.isNull(email)){
            request.setAttribute("msg","邮箱地址不能为空！");
            request.getRequestDispatcher("/regist.jsp").forward(request,response);
            return;
        }
        if (WebUtils.isNull(valistr)){
            request.setAttribute("msg","验证码不能为空！");
            request.getRequestDispatcher("/regist.jsp").forward(request,response);
            return;
        }

        //邮箱格式校验
        String mailReg="^\\w+@(\\w+\\.\\w+)+$";
        if(!email.matches(mailReg)){
            request.setAttribute("msg","邮箱格式不正确！");
            request.getRequestDispatcher("/regist.jsp").forward(request,response);
            return;
        }

        //密码一致性校验
        if(!password.equals(password2)){
            request.setAttribute("msg","两次密码不一致！");
            request.getRequestDispatcher("/regist.jsp").forward(request,response);
            return;
        }

        //验证码校验
        //TODO--

        //完成注册
        //检查是否已存在用户
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            conn=WebUtils.getConnection();
            ps=conn.prepareStatement("select 1 from user where username=?");
            ps.setString(1,username);
            rs=ps.executeQuery();
            if(rs.next()){
                request.setAttribute("msg","用户名已经存在！");
                request.getRequestDispatcher("/regist.jsp").forward(request,response);
                return ;
            }

            //检测邮箱是否重复
            conn=WebUtils.getConnection();
            ps=conn.prepareStatement("select 1 from user where email=?");
            ps.setString(1,email);
            rs=ps.executeQuery();
            if(rs.next()){
                request.setAttribute("msg","邮箱已经存在！");
                request.getRequestDispatcher("/regist.jsp").forward(request,response);
                return ;
            }

            //没有重复，进行新增
                ps=conn.prepareStatement("insert into user(username, password, nickname, email) values(?,?,?,?)");
                ps.setString(1,username);
                ps.setString(2,password);
                ps.setString(3,nickname);
                ps.setString(4,email);
                ps.executeUpdate();

        } catch (SQLException e) {
//            e.printStackTrace();
            request.setAttribute("msg",e.getErrorCode()+e.getMessage());
            request.getRequestDispatcher("/regist.jsp").forward(request,response);

        }finally {
            WebUtils.close(conn,ps,rs);
        }

        //注册成功，返回消息
        response.getWriter().write("<h1 align='center'><font color='red'>用户注册成功！3秒后自动跳转到首页......</font></h1>");
        response.setHeader("refresh","3;url=/");
    }
}
