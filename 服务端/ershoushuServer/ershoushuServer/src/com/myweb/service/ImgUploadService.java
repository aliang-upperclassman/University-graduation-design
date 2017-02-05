package com.myweb.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.jspsmart.upload.SmartUpload;
import com.myweb.dao.UserDao;
import com.myweb.domain.User;

public class ImgUploadService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String result = "";// 返回结果集

		response.setCharacterEncoding("GBK");	 
 
	    SmartUpload mySmartUpload = new SmartUpload();

	       // 初始化
	     String myFileName = String
		 .valueOf(new Date().getTime())
		 + ".jpg";
	     
	       mySmartUpload.initialize(getServletConfig(),request,response);

	       long file_size_max = 4000000;
	       
	       String saveurl = request
			.getRealPath("/") + "upload/"; // 应保证在根目录中有此目录的存在

	       try {

	           mySmartUpload.setAllowedFilesList("jpg");

	           // 上载文件
	           mySmartUpload.upload();

	       } catch (Exception e) {

	    	   result="只能上传jpg格式的图片!";

	       } 

	       try {

	           com.jspsmart.upload.File myFile = mySmartUpload.getFiles().getFile(0);

	           if (myFile.isMissing()) {

	        	   result="请选择图片!";

	           } else {

	              //ext = myFile.getFileExt(); // 取得后缀名

	              int file_size = myFile.getSize(); // 取得文件的大小	              

	              if (file_size < file_size_max) {
	            	  	               
	                  myFile.saveAs(saveurl+myFileName, mySmartUpload.SAVE_PHYSICAL);
	                  
	                  result="filename=upload/"+myFileName;
	              }
	           }
	      }
		  catch (Exception e) {

			 System.out.print(e.getMessage());

		} finally {
			/* 返回数据 */
			response.setCharacterEncoding("UTF-8");

			response.setHeader("content-type", "text/html;charset=UTF-8");

			System.out.println("返回报文:" + result);

			PrintWriter pw = response.getWriter();

			pw.write(result);

			pw.flush();

			pw.close();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
