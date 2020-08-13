package com.easymall.servlet;

import com.easymall.utils.VerifyCode;
import com.easymall.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("ValidImgServlet")
public class ValidImgServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //处理乱码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        VerifyCode vc=new VerifyCode();
        vc.drawImage(response.getOutputStream());
        String code = vc.getCode();
        //--将Code保存在session
        //request.setAttribute("Code",code);
        System.out.println(code);

        //禁止缓存
        response.setDateHeader("Expires",-1);
        response.setHeader("Cache-Control","no-cache");

    }
}
