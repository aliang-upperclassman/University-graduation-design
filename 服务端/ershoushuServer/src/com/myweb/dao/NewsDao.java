package com.myweb.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.myweb.domain.Admin;
import com.myweb.domain.News;
import com.myweb.utils.DBManager;

public class NewsDao {
	// 添加新闻
	public int addNews(News news) {

		int id = 0;

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "insert into NEWS (ID, TITLE,IMGPATH,CONTENT,CREATEUSER,CREATETIME) values (?, ?, ?, ?, ?, ?)";

		List<Object> params = new ArrayList<Object>();

		id = db.getMaxId("NEWS");

		params.add(id);

		params.add(news.getTitle());

		params.add(news.getImgpath());

		params.add(news.getContent());

		params.add(news.getCreateuser());

		params.add(news.getCreatetime());

		try {
			boolean flag = db.updateByPreparedStatement(sql, params);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return id;
	}

	// 修改新闻
	public boolean updateNews(News news) {

		boolean flag=false;
		
		DBManager db = new DBManager();

		db.getConnection();

		String sql = "update NEWS  set  TITLE=?,IMGPATH=?,CONTENT=? where id="+String.valueOf(news.getId());

		List<Object> params = new ArrayList<Object>();
        
		params.add(news.getTitle());
		 
		params.add(news.getImgpath());
		
		params.add(news.getContent());

		try {
		    flag = db.updateByPreparedStatement(sql, params);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return flag;
	}

	
	// 查询所有新闻公告
	public List<Map<String, Object>> getNewsList() {

		DBManager db = new DBManager();

		db.getConnection();

		String sql2 = "select * from NEWS  order by CREATETIME desc";

		List<Map<String, Object>> list = null;

		try {
			list = db.findModeResult(sql2, null);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return list;

	}

	// 删除新闻
	public void delNewsById(String id) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "delete from News where id=(?)";

		List<Object> list = new ArrayList<Object>();

		list.add(id);

		try {
			boolean flag = db.updateByPreparedStatement(sql, list);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	// 查询新闻
	public News getNewsById(String id) {

		News news = null;
		
		DBManager db = new DBManager();

		db.getConnection();

		String sql = "select *  from News where id=(?)";

		List<Object> list = new ArrayList<Object>();

		list.add(id);

		try {
			news = (News) db.findSimpleRefResult(sql, list, News.class);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return news;
	}
}
