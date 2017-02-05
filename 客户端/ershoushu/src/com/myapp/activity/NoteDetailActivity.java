package com.myapp.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.myapp.activity.NewsDetailActivity.NewsDetailThread;
import com.myapp.common.HttpUtil;
import com.myweb.domain.Goods;
import com.myweb.domain.News;
import com.myweb.domain.Note;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.Html.ImageGetter;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class NoteDetailActivity extends ActionBarActivity {

	private ProgressDialog pd;

	private TextView tv1;

	private TextView tv2;

	private TextView tv3;

	private TextView tv4;

	Map<String, Object> noteMap;

	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_note_detail);

		/* ��ʾProgressDialog */
		pd = ProgressDialog.show(NoteDetailActivity.this, "����", "�����У����Ժ󡭡�");

		tv1 = (TextView) findViewById(R.id.noteviewtitle);

		tv2 = (TextView) findViewById(R.id.noteviewcreatetime);

		tv4 = (TextView) findViewById(R.id.xinxizhonglei);
		
		tv3 = (TextView) findViewById(R.id.noteviewcontent);

		tv3.setMovementMethod(ScrollingMovementMethod.getInstance());

		noteMap = new HashMap<String, Object>();

		Intent intent = getIntent();

		id = intent.getStringExtra("id");

		Thread noteDetailThread = new Thread(new NoteDetailThread());

		noteDetailThread.start();

	}

	class NoteDetailThread implements Runnable {

		public void run() {
			try {

				JSONObject paraObj = new JSONObject();

				paraObj.put("id", id);

				paraObj.put("action", "view");

				String result = HttpUtil.getJsonFromServlet(paraObj.toString(),
						"GoodsService");

				if (!result.equals("")) {

					Message msg = new Message();

					msg.what = 1;// ���ʳɹ����з���ֵ

					msg.obj = result;

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

					Goods good = JSONObject.parseObject(msg.obj.toString(),
							Goods.class);

					tv1.setText(good.getName());

					String createTime = good.getCreatetime();

					if (!createTime.equals("")) {
						createTime = createTime.substring(0,
								createTime.indexOf(" "));
					}

					tv2.setText("����ʱ��:" + createTime);

					tv3.setText(good.getDescription());
					
					tv4.setText("���:" + good.getTypename());

				} catch (Exception ex) {
					System.out.print(ex.getMessage());
				}
			}

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.news_detail, menu);
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
