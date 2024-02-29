package com.johnny.web.controller;

import com.johnny.web.service.ImageCode;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

@RestController
public class LoginController {
    @RequestMapping("/login")
    public String login() {
        return "login";
    }


    @RequestMapping(value = "/images/imageCode")
    public String imageCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        OutputStream os = response.getOutputStream();
        Map<String, Object> map = ImageCode.getImageCode(60, 20);

        String simpleCaptcha = "simpleCaptcha";
        request.getSession().setAttribute(simpleCaptcha, map.get("strEnsure").toString().toLowerCase());
        request.getSession().setAttribute("codeTime", System.currentTimeMillis());

        try {
            ImageIO.write((BufferedImage) map.get("image"), "JPEG", os);
        } catch (IOException e) {
            return "";
        }
        return null;
    }

    @RequestMapping(value = "/checkCode")
    public String checkCode(HttpServletRequest request, HttpSession session) {
        String checkCode = request.getParameter("checkCode");
        Object cko = session.getAttribute("simpleCaptcha"); //验证码对象
        if (cko == null) {
            request.setAttribute("errorMsg", "验证码已失效，请重新输入！");
            return "验证码已失效，请重新输入！";
        }

        String captcha = cko.toString();
        Date now = new Date();
        long codeTime = Long.parseLong(session.getAttribute("codeTime") + "");
        if (StringUtils.isEmpty(checkCode) || !(checkCode.equalsIgnoreCase(captcha))) {
            request.setAttribute("errorMsg", "验证码错误！");
            return "验证码错误！";
        } else if ((now.getTime() - codeTime) / 1000 / 60 > 5) {//验证码有效时长为5分钟
            request.setAttribute("errorMsg", "验证码已失效，请重新输入！");
            return "验证码已失效，请重新输入！";
        } else {
            //TODO
            session.removeAttribute("simpleCaptcha");
            return "1";
        }
    }
}