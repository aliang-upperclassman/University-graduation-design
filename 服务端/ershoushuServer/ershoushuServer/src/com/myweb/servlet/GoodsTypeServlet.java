package com.myweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myweb.dao.GoodsTypeDao;
import com.myweb.domain.Admin;

import com.myweb.domain.GoodsType;
import com.myweb.domain.User;

public class GoodsTypeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

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

			addGoodsType();

		} else if (action.equals("list")) {

			listGoodsType();

		} else if (action.equals("delete")) {

			delGoodsType();

		} else if (action.equals("edit")) {

			editGoodsType();

		} else if (action.equals("editSave")) {

			editSaveGoodsType();

		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	// 添加类型
	public void addGoodsType() throws ServletException, IOException {

		String typename = _request.getParameter("typename");

		GoodsType goodsType = new GoodsType();

		goodsType.setTypename(typename);

		GoodsTypeDao dao = new GoodsTypeDao();

		try {
			dao.addGoodsType(goodsType);

			_request.setAttribute("alertNote", "1");

		} catch (Exception ex) {

			_request.setAttribute("alertNote", "0");
		}
		_request.getRequestDispatcher("/admin/goodsType_Add.jsp").forward(
				_request, _response);

	}

	// 编辑类型
	public void editGoodsType() throws ServletException, IOException {

		String id = _request.getParameter("id");

		GoodsTypeDao dao = new GoodsTypeDao();

		GoodsType goodsType = null;

		try {
			goodsType = dao.getGoodsTypeById(id);

			_request.setAttribute("goodsType", goodsType);

		} catch (Exception ex) {
			_request.setAttribute("alertNote", "0");
		}
		_request.getRequestDispatcher("admin/goodsType_Edit.jsp").forward(
				_request, _response);

	}

	// 保存编辑类型
	public void editSaveGoodsType() throws ServletException, IOException {

		String id = _request.getParameter("id");

		String typename = _request.getParameter("typename");

		GoodsTypeDao dao = new GoodsTypeDao();

		GoodsType bookType = null;

		try {
			bookType = dao.getGoodsTypeById(id);

			bookType.setTypename(typename);

			boolean flag = dao.updateGoodsType(bookType);

			_request.setAttribute("goodsType", bookType);

			if (flag) {

				_request.setAttribute("alertNote", "1");

			} else {

				_request.setAttribute("alertNote", "0");

			}
		} catch (Exception ex) {
			_request.setAttribute("alertNote", "0");
		}

		_request.getRequestDispatcher("/admin/goodsType_Edit.jsp").forward(
				_request, _response);

	}

	// 类型列表
	public void listGoodsType() throws ServletException, IOException {
		GoodsTypeDao dao = new GoodsTypeDao();
		try {

			List<Map<String, Object>> list = dao.getGoodsTypeList();

			_request.setAttribute("goodsTypeList", list);

		} catch (Exception ex) {

			ex.printStackTrace();
		}

		_request.getRequestDispatcher("/admin/goodsType_List.jsp").forward(
				_request, _response);
	}

	// 删除类型
	public void delGoodsType() throws ServletException, IOException {

		String id = _request.getParameter("id");

		GoodsTypeDao dao = new GoodsTypeDao();
		try {
			dao.delGoodsTypeById(id);

			_request.setAttribute("alertNote", "1");

		} catch (Exception ex) {

			_request.setAttribute("alertNote", "0");

			ex.printStackTrace();
		}

		_request.getRequestDispatcher("GoodsTypeServlet?action=list").forward(
				_request, _response);
	}

}
