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

	private String goodsid;// 商品ID

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_goods_detail);

		/* 显示ProgressDialog */

		pd = ProgressDialog.show(GoodsDetailActivity.this, "标题", "加载中，请稍后……");

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

	// 获取商品列表多线程
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

					msg.what = 1;// 访问成功并有返回值

					msg.obj = goodsMap1;

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

	/**
	 * 获取网落图片资源
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getHttpBitmap(String url) {
		URL myFileURL;
		Bitmap bitmap = null;
		try {
			myFileURL = new URL(url);
			// 获得连接
			HttpURLConnection conn = (HttpURLConnection) myFileURL
					.openConnection();
			// 设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
			conn.setConnectTimeout(6000);
			// 连接设置获得数据流
			conn.setDoInput(true);
			// 不使用缓存
			conn.setUseCaches(false);
			// 这句可有可无，没有影响
			// conn.connect();
			// 得到数据流
			InputStream is = conn.getInputStream();
			// 解析得到图片
			bitmap = BitmapFactory.decodeStream(is);
			// 关闭数据流
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
				pd.dismiss();// 关闭ProgressDialog

				Toast.makeText(getApplicationContext(), "加载失败,请检查网络是否畅通!",
						Toast.LENGTH_SHORT).show();

			case 1:
				pd.dismiss();// 关闭ProgressDialog

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

			// 启动Activity
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
