package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello Spring Boot.";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String getData() {
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) {
        //获取请求
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        //消息头
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ": " + value);
        }
        System.out.println(request.getParameter("code"));

        //给浏览器返回响应数据
        response.setContentType("text/html;charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.write("<h1>牛客网</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    /**
     * Get请求
     */
    // /student?current=1&limit=2
    // 看参数可以理解怎么把url后面带的参数给获取到，以及做更详细的说明
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        System.out.println(current);
        System.out.println(limit);
        return "some students.";
    }
    // /student/123
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) {
        System.out.println(id);
        return "a student.";
    }

    /**
     * POST请求
     */
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    // 响应HTML数据
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name", "张三");
        modelAndView.addObject("age", "30");
        // 设置模板目录
        modelAndView.setViewName("/demo/view");
        return modelAndView;
    }

    // 这个方法返回html的话，最后就需要return路径
    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model) {
        model.addAttribute("name", "中南大学");
        model.addAttribute("age", 21);
        return "/demo/view";
    }

    // 相应JSON数据(异步请求)
    // Java对象-->Json对象-->JS对象 跨语言转换
    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody // 加上返回一个Body 否则就返回了一个html
    public Map<String, Object> getEmp() {
        Map<String, Object> map = new HashMap<>();
        map.put("name","张三");
        map.put("salary",8000);
        map.put("age",30);
        return map;
    }

    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody // 加上返回一个Body 否则就返回了一个html
    public List<Map<String, Object>> getEmps() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("salary", 8000);
        map.put("age", 30);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "李四");
        map.put("salary", 10000);
        map.put("age", 31);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "王二");
        map.put("salary", 12000);
        map.put("age", 28);
        list.add(map);

        return list;
    }

    @RequestMapping(path = "/cookie/set", method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse httpServletResponse) {
        // 创建对象
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
        // 指定路径，设置cookie有效范围,只有这个范围发送的时候才会发送Cookie
        cookie.setPath("/community");
        // 设置cookie生存时间，以秒为单位
        cookie.setMaxAge(60 * 10);
        // 发送cookie
        httpServletResponse.addCookie(cookie);
        return "set cookie";
    }

    // 查看Cookie,通过@CookieValue注解，可以在服务端得到Cookie，通过里面的变量值来得到某一个Cookie的Value值，不然那得到是头携带的所有的Cookie
    @RequestMapping(path = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code") String code) {
        System.out.println(code);
        return "get cookie";
    }

    // Session示例
    @RequestMapping(path = "/session/set", method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession httpSession) {
        httpSession.setAttribute("id",1);
        httpSession.setAttribute("name","Test");
        return "set Session";
    }
    // 从session中取值
    @RequestMapping(path = "/session/get", method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession httpSession) {
        System.out.println(httpSession.getAttribute("id"));
        System.out.println(httpSession.getAttribute("name"));
        return "get Session";
    }
    //Session可以存任意的数据，而Cookie一般只能存String

    // Ajax实例
    @RequestMapping(path = "/ajax", method = RequestMethod.POST)
    // 异步请求是不返回网页的，所以是返回body
    @ResponseBody
    public String testAjax(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return CommunityUtil.getJSONString(0, "ok");
    }
}
