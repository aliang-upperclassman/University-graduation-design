package com.myapp.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.myapp.activity.LoginActivity.LoginThread;
import com.myapp.adapter.NewsListAdapter;
import com.myapp.adapter.NewsListAdapter.ViewHolder;
import com.myapp.common.HttpUtil;
import com.myweb.domain.News;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class NewsListActivity extends ActionBarActivity {

	private TextView t1;

	private TextView t2;

	private TextView t3;

	private ProgressDialog pd;

	ListView listView;

	List<Map<String, Object>> newslist1;

	private NewsListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_news_list);

		/* ��ʾProgressDialog */
		pd = ProgressDialog.show(NewsListActivity.this, "����", "�����У����Ժ󡭡�");

		listView = (ListView) findViewById(R.id.news_lv);

		newslist1 = new ArrayList<Map<String, Object>>();

		adapter = new NewsListAdapter(this, newslist1);

		listView.setAdapter(adapter);

		// listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				ViewHolder vHollder = (ViewHolder) view.getTag();

				// Toast.makeText(getApplicationContext(),
				// vHollder.title.getText(), Toast.LENGTH_SHORT).show();

				Intent intent = new Intent();

				intent.putExtra("id", vHollder.id.getText());

				intent.setClass(NewsListActivity.this, NewsDetailActivity.class);

				// ����Activity
				startActivity(intent);
			}
		});

		Thread newsListThread = new Thread(new NewsListThread());

		newsListThread.start();
	}

	class NewsListThread implements Runnable {

		public void run() {
			try {

				JSONObject paraObj = new JSONObject();

				paraObj.put("action", "list");

				String result = HttpUtil.getJsonFromServlet(paraObj.toString(),
						"NewsService");

				if (!result.equals("")) {

					List<News> newsList = JSONObject.parseArray(result,
							News.class);

					ArrayList<Map<String, Object>> newslistMap = new ArrayList<Map<String, Object>>();

					for (News news : newsList) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", news.getId());

						String _title = news.getTitle();

						if (_title.length() > 16) {
							_title = _title.substring(0, 16) + "...";
						}
						map.put("title", _title);

						map.put("createTime", news.getCreatetime());

						if (news.getImgpath().length() > 0) {
							map.put("imgpath",
									HttpUtil.BASE_URL + news.getImgpath());
						} else {
							map.put("imgpath", "");
						}

						map.put("author", news.getCreateuser());

						newslistMap.add(map);
					}

					Message msg = new Message();

					msg.what = 1;// ���ʳɹ����з���ֵ

					msg.obj = newslistMap;

					handler.sendMessage(msg);

				}

			} catch (Exception ex) {

				System.out.println("ex: " + ex.getMessage());

				Message msg = new Message();

				msg.what = 0;// �����쳣

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
				pd.dismiss();// �ر�ProgressDialog

				Toast.makeText(getApplicationContext(), "��ѯʧ��,���������Ƿ�ͨ!",
						Toast.LENGTH_SHORT).show();
				break;

			case 1:
				pd.dismiss();// �ر�ProgressDialog

				try {
					ArrayList<Map<String, Object>> result = (ArrayList<Map<String, Object>>) msg.obj;

					if (result != null) {

						newslist1.clear();

						newslist1.addAll(result);

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

		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.news_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
