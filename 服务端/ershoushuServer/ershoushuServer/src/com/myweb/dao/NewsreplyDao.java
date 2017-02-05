package com.myweb.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.myweb.domain.Newsreply;
 
import com.myweb.utils.DBManager;

public class NewsreplyDao {

	// 添加留言
	public void addNewsreply(Newsreply newsreply) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "insert into Newsreply (NEWSID,USERNAME,STATE,CONTENT) values (?, ?, ?,?)";

		List<Object> params = new ArrayList<Object>();

		params.add(newsreply.getNewsid());

		params.add(newsreply.getUsername());

		params.add(newsreply.getState());

		params.add(newsreply.getContent());

		try {
			boolean flag = db.updateByPreparedStatement(sql, params);

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public List<Map<String, Object>> getNewsreplyListByNewsId(String newsid) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql2 = "select * from Newsreply where newsid='" + newsid
				+ "' order by CREATETIME desc";

		List<Map<String, Object>> list = null;

		try {
			list = db.findModeResult(sql2, null);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return list;

	}
	

	public List<Map<String, Object>> getNewsreplyListByNewsIdService(String newsid) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql2 = "select * from Newsreply where newsid='" + newsid
				+ "' and state='已审核' order by CREATETIME desc";

		List<Map<String, Object>> list = null;

		try {
			list = db.findModeResult(sql2, null);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return list;

	}

	public Newsreply getNewsreplyById(String id) {

		Newsreply newsreply = null;

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "select *  from Newsreply where id=(?)";

		List<Object> list = new ArrayList<Object>();

		list.add(id);

		try {
			newsreply = (Newsreply) db.findSimpleRefResult(sql, list, Newsreply.class);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return newsreply;
	}

	// 删除留言
	public void delNewsreplyById(String id) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "delete from Newsreply where id=(?)";

		List<Object> list = new ArrayList<Object>();

		list.add(id);

		try {
			boolean flag = db.updateByPreparedStatement(sql, list);

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public boolean updateNewsreply(Newsreply newsreply) {

		boolean flag = false;

		DBManager db = new DBManager();

		db.getConnection();

		/*
		 * String sql =
		 * "update Newsreply  set  TYPENAME=?  where id="+String.valueOf
		 * (bookType.getId());
		 * 
		 * List<Object> params = new ArrayList<Object>();
		 * 
		 * params.add(bookType.getTypename());
		 * 
		 * 
		 * try { flag = db.updateByPreparedStatement(sql, params);
		 * 
		 * System.out.println(flag);
		 * 
		 * } catch (SQLException e) {
		 * 
		 * e.printStackTrace(); }
		 */
		return flag;
	}

	public boolean shengpi(String newsreplyid, String state) {

		boolean flag = false;

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "update Newsreply  set  state=?  where id=" + newsreplyid;

		List<Object> params = new ArrayList<Object>();

		params.add(state);

		try {
			flag = db.updateByPreparedStatement(sql, params);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return flag;
	}
}
