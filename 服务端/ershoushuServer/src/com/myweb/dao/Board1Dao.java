package com.myweb.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.myweb.domain.Admin;
import com.myweb.domain.Board;
import com.myweb.domain.Board1;
import com.myweb.domain.BoardReply;

import com.myweb.utils.DBManager;

public class Board1Dao {

	public void addBoard(Board1 board) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "insert into BOARD1 ( USERID,CREATETIME,TITLE,CONTENT,IMGPATH,TICKETNUM) values (?, ?, ?, ?, ?, ?)";

		List<Object> params = new ArrayList<Object>();

		params.add(board.getUserid());

		params.add(board.getCreatetime());

		params.add(board.getTitle());

		params.add(board.getContent());
		
		params.add(board.getImgpath());

		params.add(board.getTicketnum());

		try {
			boolean flag = db.updateByPreparedStatement(sql, params);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void addReply(BoardReply boardreply) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "insert into boardreply ( BOARDID,REPLYUSERID,REPLYTIME,REPLYCONTENT) values (?, ?, ?, ?)";

		List<Object> params = new ArrayList<Object>();

		params.add(boardreply.getBoardid());

		params.add(boardreply.getReplyuserid());

		params.add(boardreply.getReplytime());

		params.add(boardreply.getReplycontent());

		try {
			boolean flag = db.updateByPreparedStatement(sql, params);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public List<Map<String, Object>> getBoardList() {

		DBManager db = new DBManager();

		db.getConnection();

		String sql2 = "select t.ID,t.USERID,t1.LOGINNAME as USERNAME,t.TITLE,t.CONTENT,t.IMGPATH,t.TICKETNUM,t.CREATETIME from board1 t,user t1 where t.USERID=t1.id order by t.CREATETIME desc";

		List<Map<String, Object>> list = null;

		try {
			list = db.findModeResult(sql2, null);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return list;

	}

	public List<Map<String, Object>> getBoardReplyList(String boardid) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql2 = "select t.REPLYID,t.BOARDID,t.REPLYUSERID,t1.LOGINNAME as REPLYUSERNAME,t.REPLYCONTENT,t.REPLYTIME from boardreply t,user t1 where t.REPLYUSERID=t1.ID and t.boardid='"
				+ boardid + "' order by t.REPLYTIME desc";

		List<Map<String, Object>> list = null;

		try {
			list = db.findModeResult(sql2, null);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return list;

	}

	public void delBoardById(String id) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "delete from boardreply where BOARDID=" + id;
 

		String sql2 = "delete from board1 where id=" + id;

		List<Object> list = new ArrayList<Object>();

		try {
			db.updateByPreparedStatement(sql, list);
  
			db.updateByPreparedStatement(sql2, list);

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void delReplyById(String id) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "delete from boardreply where REPLYID=" + id;

		List<Object> list = new ArrayList<Object>();

		try {
			db.updateByPreparedStatement(sql, list);

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}


	public Board1 getBoardById(String boardid) {

		Board1 board = null;

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "select t.ID,t.USERID,t1.LOGINNAME as USERNAME,t.TITLE,t.CONTENT,t.IMGPATH,t.TICKETNUM,t.CREATETIME from board1 t,user t1 where t.USERID=t1.ID and t.ID='"
				+ boardid + "'";

		try {
			board = (Board1) db.findSimpleRefResult(sql, null, Board1.class);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return board;
	}

}
