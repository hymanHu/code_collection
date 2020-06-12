package com.thornBird.modules.gifCaptcha;

import java.awt.Font;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thornBird.common.gifCaptcha.GifCaptcha;

@Controller
public class GifCaptchaController {

	/**
	 * 生成动态验证码
	 * $("#codePic").bind("click", function() {
	 * 	$("#codePic").attr("src", "/getGifCode?flag=" + Math.random());
	 * });
	 */
	@RequestMapping(value="/getGifCode",method=RequestMethod.GET)
	public void getGifCode(HttpServletResponse response, HttpServletRequest request){
		// 设定response相关信息
		response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/gif");
        
        GifCaptcha gifCaptcha = new GifCaptcha(146, 33, new Font("宋体", Font.BOLD, 20), 100);
        try {
        	//存入Session
        	HttpSession session = request.getSession(true);
        	session.setAttribute("captchaWord", gifCaptcha.getWord());
        	
        	// 输出gif图片
            gifCaptcha.out(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
