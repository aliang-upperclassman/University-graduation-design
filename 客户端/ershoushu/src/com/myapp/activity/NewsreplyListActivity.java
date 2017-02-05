package com.myapp.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.myapp.adapter.NewsreplyListAdapter;
import com.myapp.common.HttpUtil;
import com.myweb.domain.Newsreply;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class NewsreplyListActivity extends ActionBarActivity {

	ListView listView;

	List<Map<String, Object>> boardlist1;

	private NewsreplyListAdapter adapter;

	String newsid;

	MyApplication myApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_newsreply_list);

		myApp = (MyApplication) getApplication();

		Intent intent = getIntent();

		newsid = intent.getStringExtra("id");

		listView = (ListView) findViewById(R.id.note_lv);

		boardlist1 = new ArrayList<Map<String, Object>>();

		adapter = new NewsreplyListAdapter(this, boardlist1);

		listView.setAdapter(adapter);

		Thread boardListThread = new Thread(new BoardListThread());

		boardListThread.start();
	}

	class BoardListThread implements Runnable {

		public void run() {

			try {

				JSONObject paraObj = new JSONObject();

				paraObj.put("action", "list");

				paraObj.put("newsid", newsid);

				String result = HttpUtil.getJsonFromServlet(paraObj.toString(),
						"NewsreplyService");

				if (!result.equals("")) {

					List<Newsreply> boardList = JSONObject.parseArray(result,
							Newsreply.class);

					ArrayList<Map<String, Object>> boardlistMap = new ArrayList<Map<String, Object>>();

					for (Newsreply board : boardList) {

						Map<String, Object> map = new HashMap<String, Object>();

						map.put("yonghuming", board.getUsername());

						map.put("pinglunshijian", board.getCreatetime());

						map.put("neirong", board.getContent());

						boardlistMap.add(map);
					}

					Message msg = new Message();

					msg.what = 1;// 访问成功并有返回值

					msg.obj = boardlistMap;

					handler.sendMessage(msg);

				}

			} catch (Exception ex) {

				System.out.println("ex: " + ex.getMessage());

				Message msg = new Message();

				msg.what = 0;// 网络异常

				handler.sendMessage(msg);

			}
		}
	}

	// Handler
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			super.handleMessage(msg);

			switch (msg.what) {

			case 0:

				Toast.makeText(getApplicationContext(), "查询失败,请检查网络是否畅通!",
						Toast.LENGTH_SHORT).show();
				break;

			case 1:

				try {
					ArrayList<Map<String, Object>> result = (ArrayList<Map<String, Object>>) msg.obj;

					if (result != null) {

						boardlist1.clear();

						boardlist1.addAll(result);

						adapter.notifyDataSetChanged();

						System.out.println(result.toString());
					}
				} catch (Exception ex) {

					System.out.println("ex: " + ex.getMessage());
				}

				break;

			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		if (myApp.getUser().getLoginname() != null) {

			getMenuInflater().inflate(R.menu.note_list_menu, menu);
		}
		// Inflate the menu; this adds items to the action bar if it is present.

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		if (id == R.id.noteadd) {

			Intent intent = new Intent();

			intent.setClass(NewsreplyListActivity.this, NewsreplyAddActivity.class);

			intent.putExtra("newsid", newsid);
			// 启动Activity
			startActivity(intent);

			// NoteListActivity.this.finish();

			return true;

		}

		return super.onOptionsItemSelected(item);
	}

	// 子页面关闭时调用
	@Override
	protected void onResume() {

		super.onResume();

		handler1.post(runnable);
	}

	private Runnable runnable = new Runnable() {

		public void run() {
			// 做操作
			handler1.sendEmptyMessage(1);
		}
	};

	private Handler handler1 = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {

			case 1:
				// 更新UI
				Thread boardListThread = new Thread(new BoardListThread());

				boardListThread.start();

				break;
			}
		};
	};

}
