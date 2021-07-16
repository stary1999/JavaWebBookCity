package com.atguigu.web;

import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.service.impl.UserServiceImpl;
import com.atguigu.utils.WebUtils;
import com.google.gson.Gson;
import jdk.nashorn.internal.parser.Token;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 * @ClassName UserServlet
 * @Description: TODO
 * @Author stary
 * @Date 2021/5/7 19:40
 * @Version 1.0
 **/
public class UserServlet extends BaseServlet {

    private UserService userService=new UserServiceImpl();


    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = WebUtils.copyParamToBean(req.getParameterMap(), new User());
        User loginUser = userService.login(user);
        if(loginUser==null)
        {
            req.setAttribute("msg","用户名或密码错误！");
            req.setAttribute("username",user.getUsername());
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);

        }
        else {
            //登录成功
            //保存用户的登录信息到session域中
            req.getSession().setAttribute("user",loginUser);

            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req,resp);
        }


    }
    //注销
    protected void loginOut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //销毁session
        req.getSession().invalidate();
        //重定向
        resp.sendRedirect(req.getContextPath());


    }

        protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数
        req.setCharacterEncoding("UTF-8");



        //获取并删除验证码

        String token=(String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);
        String code = req.getParameter("code");
            System.out.println("code="+code);
            System.out.println("token="+token);

        User user = WebUtils.copyParamToBean(req.getParameterMap(),new User());



        //检查验证码
        if(token!=null&& token.equalsIgnoreCase(code)){
            //验证码正确，检查用户名是否可用
            //可用：保存数据
            //  不可用：返回注册界面
            //检查用户名是否可用
            if(userService.existsUsername(user.getUsername())){
                System.out.println("用户名["+user.getUsername()+"]已存在");

                req.setAttribute("msg","用户名已存在！");
                req.setAttribute("username",user.getUsername());
                req.setAttribute("email",user.getEmail());

                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);

            }
            else {
                userService.registUser(user);

                //创建cookie;

                Cookie cookieUsername=new Cookie("username",user.getUsername());
                resp.addCookie(cookieUsername);
                Cookie cookiePassword=new Cookie("password",user.getPassword());
                resp.addCookie(cookiePassword);
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req,resp);

            }

        }
        else {
            //跳回注册界面
            req.setAttribute("msg","验证码错误");
            req.setAttribute("username",user.getUsername());
            req.setAttribute("email",user.getEmail());

            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);

        }

    }

    public void createCookie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //创建cookie对象
        Cookie cookie1=new Cookie("key1","values1");
        resp.addCookie(cookie1);

        Cookie cookie2=new Cookie("key2","values2");
        resp.addCookie(cookie2);

        Cookie cookie3=new Cookie("key3","values3");
        resp.addCookie(cookie3);


        resp.getWriter().write("Cookie创建成功");

    }

    public void getCookie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //创建cookie对象
        Cookie cookie1=new Cookie("key1","values1");
        resp.addCookie(cookie1);

        Cookie cookie2=new Cookie("key2","values2");
        resp.addCookie(cookie2);

        Cookie cookie3=new Cookie("key3","values3");
        resp.addCookie(cookie3);


        resp.getWriter().write("Cookie创建成功");

    }

    public void ajaxExistsUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        boolean exitsUsername = userService.existsUsername(username);
        Map<String,Object> resultMap=new HashMap<String, Object>();
        resultMap.put("exitsUsername",exitsUsername);
        Gson gson =new Gson();
        String json = gson.toJson(resultMap);
        resp.getWriter().write(json);

    }


}
