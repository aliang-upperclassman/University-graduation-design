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
import com.myweb.dao.Board1Dao;
import com.myweb.dao.BoardDao;


import com.myweb.dao.UserDao;
import com.myweb.domain.Board;
import com.myweb.domain.Board1;
import com.myweb.domain.BoardReply;

import com.myweb.domain.User;

public class Board1Service extends HttpServlet {

	HttpSession _session;

	HttpServletRequest _request;

	HttpServletResponse _response;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		_session = request.getSession();

		_request = request;

		_response = response;

		String action = "";

		action = request.getParameter("action").toString();

		if (action.equals("add")) {

			addBoard();

		} else if (action.equals("deletereply")) {

			deleteReply();

		} else if (action.equals("delete")) {

			deleteBoard();

		}  else if (action.equals("reply")) {

			addReply();

		} else if (action.equals("list")) {

			listBoard();

		} else if (action.equals("view")) {

			viewBoard();

		} else if (action.equals("replylist")) {

			listBoardReply();

		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	public void deleteReply() throws ServletException, IOException {

		String result = "";// 返回结果集

		String replyid = _request.getParameter("replyid").toString();
		try {

			Board1Dao shoucangDAO = new Board1Dao();

			shoucangDAO.delReplyById(replyid);

			result = "ok";

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
	
	public void deleteBoard() throws ServletException, IOException {

		String result = "";// 返回结果集

		String boardid = _request.getParameter("boardid").toString();
		try {

			Board1Dao shoucangDAO = new Board1Dao();

			shoucangDAO.delBoardById(boardid);

			result = "ok";

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

	// 查看
	public void viewBoard() throws ServletException, IOException {

		String result = "";// 返回结果集

		String boardid = _request.getParameter("boardid").toString();

		try {

			Board1Dao boardDao = new Board1Dao();

			Board1 board = new Board1();

			board = boardDao.getBoardById(boardid);

			if (board != null) {

				result = JSONObject.toJSONString(board);
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

	public void listBoard() throws ServletException, IOException {

		String result = "";// 返回结果集

		try {

			Board1Dao boardDao = new Board1Dao();

			List<Map<String, Object>> boardList = new ArrayList<Map<String, Object>>();

			boardList = boardDao.getBoardList();

			if (boardList.size()>0) {

				String json = JSONArray.toJSONString(boardList);

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

	public void listBoardReply() throws ServletException, IOException {

		String result = "";// 返回结果集

		String boardid = _request.getParameter("boardid").toString();

		try {

			Board1Dao boardDao = new Board1Dao();

			List<Map<String, Object>> boardreplyList = new ArrayList<Map<String, Object>>();

			boardreplyList = boardDao.getBoardReplyList(boardid);

			if (boardreplyList.size()>0) {

				String json = JSONArray.toJSONString(boardreplyList);

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

	public void addBoard() throws ServletException, IOException {

		String board = _request.getParameter("board").toString();

		board = new String(board.getBytes("ISO8859-1"), "UTF-8");

		String result = "";// 返回结果集

		try {

			Board1 boardObj = JSONObject.parseObject(board, Board1.class);

			Board1Dao boardDAO = new Board1Dao();

			boardDAO.addBoard(boardObj);

			result = "ok";

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

	 
	public void addReply() throws ServletException, IOException {

		String boardreply = _request.getParameter("boardreply").toString();

		boardreply = new String(boardreply.getBytes("ISO8859-1"), "UTF-8");

		String result = "";// 返回结果集

		try {

			BoardReply boardreplyObj = JSONObject.parseObject(boardreply,
					BoardReply.class);

			Board1Dao boardDAO = new Board1Dao();

			boardDAO.addReply(boardreplyObj);

			result = "ok";

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

}
