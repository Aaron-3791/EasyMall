package com.easymall.servlet;

import com.easymall.utils.VerifyCode;
import com.easymall.utils.WebUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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

        VerifyCode vc = new VerifyCode();
        vc.drawImage(response.getOutputStream());
        String code = vc.getCode();

        Cookie ck=new Cookie("code",code);
        ck.setMaxAge(60*60*24);
        ck.setPath(request.getContextPath()+"/");
        response.addCookie(ck);



        //--将Code保存在session
        System.out.println(code);


        /*Cookie ct = null;
        Cookie[] cs = request.getCookies();
        if (cs != null) {
            for (Cookie c : cs) {
                System.out.println(c.getName()+" -- "+c.getValue());
                if ("Code".equals(c.getName())) {
                    ct = c;
                }
            }
        }*/

//        System.out.println("Cookie get : "+(ct==null?"":ct.getValue()));

        //禁止缓存
        response.setDateHeader("Expires", -1);
        response.setHeader("Cache-Control", "no-cache");

    }
}
