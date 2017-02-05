package com.myweb.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myweb.dao.NewsDao;
import com.myweb.dao.NoteDao;
import com.myweb.domain.News;
import com.myweb.domain.Note;

public class NoteService extends HttpServlet {

	HttpSession _session;

	HttpServletRequest _request;

	HttpServletResponse _response;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		_session = request.getSession();

		_request = request;

		_response = response;

		String action = "";

		try {
			/* 读取数据 */
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(ServletInputStream) _request.getInputStream(), "utf-8"));

			StringBuffer sb = new StringBuffer("");

			String temp;

			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}

			br.close();
 
			if (!sb.toString().equals("")) {

				JSONObject jsonObj = JSONObject.parseObject(sb.toString());

				action = jsonObj.getString("action");

				if (action.equals("list")) {

					listNews(jsonObj.getString("searchtitle"));

				} else if (action.equals("view")) {

					viewNote(jsonObj.getString("id"));

				} else if (action.equals("add")) {

					addNote(jsonObj.getString("note"));

				} else if (action.equals("delete")) {

					deleteNote(jsonObj.getString("ids"));

				} else if (action.equals("update")) {

					updateNote(jsonObj.getString("note"));

				}
			}
		} catch (Exception e) {

			System.out.print(e.getMessage());

		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	// 笔记列表
	public void listNews(String searchtitle) throws ServletException, IOException {

		String result = "";// 返回结果集

		try {

			NoteDao noteDao = new NoteDao();

			List<Map<String, Object>> noteList = new ArrayList<Map<String, Object>>();

			noteList = noteDao.getNoteList(searchtitle);

			if (noteList != null) {

				String json = JSONArray.toJSONString(noteList);

				result = json.toString();

			}
		} catch (Exception e) {

			System.out.print(e.getMessage());

		} finally {
			/* 返回数据 */
			_response.setCharacterEncoding("UTF-8");

			_response.setHeader("content-type", "text/html;charset=UTF-8");

		 
			PrintWriter pw = _response.getWriter();

			pw.write(result);

			pw.flush();

			pw.close();
		}

	}

	// 查看笔记
	public void viewNote(String id) throws ServletException, IOException {

		String result = "";// 返回结果集

		try {

			NoteDao noteDAO = new NoteDao();

			Note note = new Note();

			note = noteDAO.getNotesById(id);

			if (note != null) {
				result = JSONObject.toJSONString(note);
			}

		} catch (Exception e) {

			System.out.print(e.getMessage());

		} finally {
			/* 返回数据 */
			_response.setCharacterEncoding("UTF-8");

			_response.setHeader("content-type", "text/html;charset=UTF-8");

			System.out.println("返回报文:" + result);

			PrintWriter pw = _response.getWriter();

			pw.write(result);

			pw.flush();

			pw.close();
		}
	}

	// 添加笔记
	public void addNote(String note) throws ServletException, IOException {

		String result = "";// 返回结果集

		try {

			Note noteObj = JSONObject.parseObject(note, Note.class);

			NoteDao noteDAO = new NoteDao();

			noteDAO.addNote(noteObj);

			result = "ok";

		} catch (Exception e) {

			System.out.print(e.getMessage());

		} finally {
			/* 返回数据 */
			_response.setCharacterEncoding("UTF-8");

			_response.setHeader("content-type", "text/html;charset=UTF-8");

			System.out.println("返回报文:" + result);

			PrintWriter pw = _response.getWriter();

			pw.write(result);

			pw.flush();

			pw.close();
		}
	}

	// 修改笔记
	public void updateNote(String note) throws ServletException, IOException {

		String result = "";// 返回结果集

		try {

			Note noteObj = JSONObject.parseObject(note, Note.class);

			NoteDao noteDAO = new NoteDao();

			noteDAO.updateNote(noteObj);

			result = "ok";

		} catch (Exception e) {

			System.out.print(e.getMessage());

		} finally {
			/* 返回数据 */
			_response.setCharacterEncoding("UTF-8");

			_response.setHeader("content-type", "text/html;charset=UTF-8");

			System.out.println("返回报文:" + result);

			PrintWriter pw = _response.getWriter();

			pw.write(result);

			pw.flush();

			pw.close();
		}
	}

	// 删除笔记
	public void deleteNote(String ids) throws ServletException, IOException {

		String result = "";// 返回结果集

		String[] _ids = ids.toString().split(",");

		NoteDao noteDAO = new NoteDao();

		try {
			for (int i = 0; i < _ids.length; i++) {
				noteDAO.delNewsById(_ids[i]);
			}

			result = "ok";

		} catch (Exception e) {

			System.out.print(e.getMessage());

		} finally {
			/* 返回数据 */
			_response.setCharacterEncoding("UTF-8");

			_response.setHeader("content-type", "text/html;charset=UTF-8");

			System.out.println("返回报文:" + result);

			PrintWriter pw = _response.getWriter();

			pw.write(result);

			pw.flush();

			pw.close();
		}
	}
}
