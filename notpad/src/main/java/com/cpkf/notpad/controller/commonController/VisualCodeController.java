package com.cpkf.notpad.controller.commonController;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**  
 * Filename:    VisualCodeController.java
 * Description: 验证码控制器
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   May 30, 2011 4:00:17 PM
 * modified:    
 */
@Controller
public class VisualCodeController {
	@RequestMapping(value="visualCode.do",method={RequestMethod.GET,RequestMethod.POST})
	public void getVisualCode(HttpServletRequest request,HttpServletResponse response){
		//设定图片长宽
		int width = 68;
		int height = 22;
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		//得到缓冲区Graphics对象
		Graphics graphics = img.getGraphics();
		//画背景,颜色浅
		graphics.setColor(getRandColor(200, 250));
		graphics.fillRect(0, 0, width, height);
		//画边框
		graphics.setColor(getRandColor(0, 255));
		graphics.drawRect(0, 0, width - 1, height - 1);
		//随机产生两条干扰线
		graphics.setColor(getRandColor(160, 200));
		Random random = new Random();
		for(int i = 0;i < 2;i ++){
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int x1 = random.nextInt(width);
			int y1 = random.nextInt(height);
			graphics.drawLine(x, y, x1, y1);
		}
		//随机产生几个点
		graphics.setColor(getRandColor(160, 200));
		for(int i = 0;i < 10;i ++){
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			graphics.drawLine(x, y, x, y);
		}
		//随机产生四个字符，并设置其颜色、字体、大小以及位置
		Font font = new Font("Times New Roman",Font.ITALIC | Font.BOLD, 22);
		StringBuffer sb = new StringBuffer();
		char[] ch = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		int index,length = ch.length;
		for(int i = 0;i < 4;i ++){
			index = random.nextInt(length);
			graphics.setColor(getRandColor(20, 130));
			graphics.setFont(font);
			graphics.drawString("" + ch[index], (i * 15) + 3, 18);
			sb.append(ch[index]);
		}
		//字符串放入session
		request.getSession().setAttribute("checkCode", sb.toString());
		//生成图片
		graphics.dispose();
		//禁止图像缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		//创建二进制输出流并输出
		ServletOutputStream sos = null;
		try {
			sos = response.getOutputStream();
			ImageIO.write(img, "jpeg", sos);
			sos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(sos != null){
				try {
					sos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public Color getRandColor(int lower,int upper){
		Random random = new Random();
		lower = lower > 255 ? 255 : lower;
		lower = lower < 1 ? 1 : lower;
		upper = upper > 255 ? 255 : upper;
		upper = upper < 1 ? 1 : upper;
		if(lower > upper){
			int temp = upper;
			upper = lower;
			lower = temp;
		}
		int r = lower + random.nextInt(upper - lower);
		int g = lower + random.nextInt(upper - lower);
		int b = lower + random.nextInt(upper - lower);;
		return new Color(r, g, b);
	}
}
