package com.myapp.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.myapp.activity.LoginActivity.LoginThread;
import com.myapp.activity.NewsDetailActivity.NewsDetailThread;
import com.myapp.common.HttpUtil;
import com.myweb.domain.Goods;
import com.myweb.domain.Note;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GoodsDetailActivity extends ActionBarActivity {

	private ProgressDialog pd;

	private ImageView goodsdetail_img;

	private TextView goodsdetail_name;

	private TextView chuangjianren;

	private TextView goodsdetail_typename;

	private TextView goodsdetail_createtime;

	private TextView goodsdetail_description;

	private String goodsid;// ��ƷID

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_goods_detail);

		/* ��ʾProgressDialog */

		pd = ProgressDialog.show(GoodsDetailActivity.this, "����", "�����У����Ժ󡭡�");

		goodsdetail_img = (ImageView) findViewById(R.id.goodsdetail_img);

		goodsdetail_name = (TextView) findViewById(R.id.goodsdetail_name);

		chuangjianren = (TextView) findViewById(R.id.faburen);

		goodsdetail_typename = (TextView) findViewById(R.id.goodsdetail_typename);

		goodsdetail_createtime = (TextView) findViewById(R.id.goodsdetail_createtime);

		goodsdetail_description = (TextView) findViewById(R.id.goodsdetail_description);

		Intent intent = getIntent();

		goodsid = intent.getStringExtra("id");

		Thread goodsDetailThread = new Thread(new GoodsDetailThread());

		goodsDetailThread.start();

	}

	// ��ȡ��Ʒ�б���߳�
	class GoodsDetailThread implements Runnable {

		public void run() {
			try {

				JSONObject paraObj = new JSONObject();

				paraObj.put("id", goodsid);

				paraObj.put("action", "view");

				String result = HttpUtil.getJsonFromServlet(paraObj.toString(),
						"GoodsService");

				if (!result.equals("")) {

					Goods goods = JSONObject.parseObject(result, Goods.class);

					Map<String, Object> goodsMap1 = new HashMap<String, Object>();

					goodsMap1.put("name", goods.getName());

					goodsMap1.put("createusername", goods.getCreateusername());

					goodsMap1.put("typename", goods.getTypename());

					goodsMap1.put("createtime", goods.getCreatetime());

					goodsMap1.put("description", goods.getDescription());

					Message msg = new Message();

					msg.what = 1;// ���ʳɹ����з���ֵ

					msg.obj = goodsMap1;

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

	/**
	 * ��ȡ����ͼƬ��Դ
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getHttpBitmap(String url) {
		URL myFileURL;
		Bitmap bitmap = null;
		try {
			myFileURL = new URL(url);
			// �������
			HttpURLConnection conn = (HttpURLConnection) myFileURL
					.openConnection();
			// ���ó�ʱʱ��Ϊ6000���룬conn.setConnectionTiem(0);��ʾû��ʱ������
			conn.setConnectTimeout(6000);
			// �������û��������
			conn.setDoInput(true);
			// ��ʹ�û���
			conn.setUseCaches(false);
			// �����п��ޣ�û��Ӱ��
			// conn.connect();
			// �õ�������
			InputStream is = conn.getInputStream();
			// �����õ�ͼƬ
			bitmap = BitmapFactory.decodeStream(is);
			// �ر�������
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;

	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			super.handleMessage(msg);

			switch (msg.what) {

			case 0:
				pd.dismiss();// �ر�ProgressDialog

				Toast.makeText(getApplicationContext(), "����ʧ��,���������Ƿ�ͨ!",
						Toast.LENGTH_SHORT).show();

			case 1:
				pd.dismiss();// �ر�ProgressDialog

				try {

					Map<String, Object> goodsMap = new HashMap<String, Object>();

					goodsMap = (Map<String, Object>) msg.obj;

					if (goodsMap.get("img") != null) {

						goodsdetail_img.setImageBitmap((Bitmap) goodsMap
								.get("img"));

					}
					goodsdetail_name.setText(goodsMap.get("name").toString());

					chuangjianren.setText(goodsMap.get("createusername")
							.toString());

					goodsdetail_typename.setText(goodsMap.get("typename")
							.toString());

					String createtime = goodsMap.get("createtime").toString();

					createtime = createtime.substring(0,
							createtime.indexOf(" "));

					goodsdetail_createtime.setText(createtime);

					goodsdetail_description.setText(goodsMap.get("description")
							.toString());

					goodsdetail_description
							.setMovementMethod(ScrollingMovementMethod
									.getInstance());
				} catch (Exception ex) {
					System.out.print(ex.getMessage());
				}

				break;

			}

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.news_detail, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.goodspingjia) {
			Intent intent = new Intent(GoodsDetailActivity.this,
					NewsreplyListActivity.class);
			
			intent.putExtra("id", goodsid);

			// ����Activity
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
