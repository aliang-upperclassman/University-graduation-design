package com.myweb.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.myweb.domain.Board;
 
import com.myweb.utils.DBManager;

public class BoardDao {

	// 添加留言板
	public void addBoard(Board board) {
	 
		DBManager db = new DBManager();

		db.getConnection();

		String sql = "insert into BOARD (USERNAME,CONTENT) values (?, ?)";

		List<Object> params = new ArrayList<Object>(); 

		params.add(board.getUsername());

		params.add(board.getContent());
	 	
		try {
			boolean flag = db.updateByPreparedStatement(sql, params);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		 
	}

	// 查询所有留言板
	public List<Map<String, Object>> getBoardList() {

		DBManager db = new DBManager();

		db.getConnection();

		String sql2 = "select * from Board  order by CREATETIME desc";

		List<Map<String, Object>> list = null;

		try {
			list = db.findModeResult(sql2, null);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return list;

	}
	
	 
	public Board getBoardById(String id) {

		Board board = null;

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "select *  from Board where id=(?)";

		List<Object> list = new ArrayList<Object>();

		list.add(id);

		try {
			board = (Board) db.findSimpleRefResult(sql, list,
					Board.class);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return board;
	}

	// 删除留言
	public void delBoardById(String id) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "delete from BOARD where id=(?)";

		List<Object> list = new ArrayList<Object>();

		list.add(id);

		try {
			boolean flag = db.updateByPreparedStatement(sql, list);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		
	}
	
	 
	public boolean updateBoard(Board board) {

		boolean flag=false;
		
		DBManager db = new DBManager();

		db.getConnection();

		/*String sql = "update Board  set  TYPENAME=?  where id="+String.valueOf(bookType.getId());

		List<Object> params = new ArrayList<Object>();
        
		params.add(bookType.getTypename());		 
		 
	 	
		try {
		    flag = db.updateByPreparedStatement(sql, params);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}*/
		return flag;
	}
}
