package com.myapp.activity;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

import com.alibaba.fastjson.JSONObject;
import com.myapp.activity.R;
import com.myapp.activity.LoginActivity.LoginThread;
import com.myapp.common.HttpUtil;
import com.myweb.domain.User;

public class RegActivity extends ActionBarActivity {

	User user;// 存储用户登录信息

	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg);

		// 注册按钮
		Button regBtn = (Button) findViewById(R.id.reg_regbtn);

		regBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String loginname = ((EditText) findViewById(R.id.reg_loginnametxt))
						.getText().toString();

				String loginpsw = ((EditText) findViewById(R.id.reg_passwordtxt))
						.getText().toString();
				if (loginname == null || loginname.equals("")
						|| loginpsw == null || loginpsw.equals("")) {

					Toast.makeText(RegActivity.this, "用户名,密码不能为空!",
							Toast.LENGTH_SHORT).show();

					return;
				}

				/* 显示ProgressDialog */
				pd = ProgressDialog.show(RegActivity.this, "标题", "加载中，请稍后……");

				Thread regThread = new Thread(new RegThread());

				regThread.start();
			}
		});
	}

	// 注册多线程
	class RegThread implements Runnable {

		public void run() {
			try {

				String loginname = ((EditText) findViewById(R.id.reg_loginnametxt))
						.getText().toString();

				String loginpsw = ((EditText) findViewById(R.id.reg_passwordtxt))
						.getText().toString();

				String emailtxt = ((EditText) findViewById(R.id.reg_emailtxt))
						.getText().toString();

				String teltxt = ((EditText) findViewById(R.id.reg_teltxt))
						.getText().toString();

				User user = new User();

				user.setLoginname(loginname);

				user.setLoginpsw(loginpsw);

				user.setTel(teltxt);

				user.setEmail(emailtxt);

				System.out.println("=============="
						+ JSONObject.toJSONString(user));

				JSONObject paraObj = new JSONObject();

				paraObj.put("action", "reg");

				paraObj.put("user", JSONObject.toJSONString(user));

				String result = HttpUtil.getJsonFromServlet(paraObj.toString(),
						"UserService");

				if (result.equals("")) {

					Message msg = new Message();

					msg.what = 2;// 程序有错误

					handler.sendMessage(msg);
				} else {
					Message msg = new Message();

					msg.what = 3;// 访问成功并有返回值

					msg.obj = result.toString();

					handler.sendMessage(msg);

				}

			} catch (Exception ex) {

				System.out.println("ex: " + ex.getMessage());

				Message msg = new Message();

				msg.what = 1;// 网络异常

				handler.sendMessage(msg);

				Intent intent = new Intent(RegActivity.this, MainActivity.class);
				// 启动Activity
				startActivity(intent);
			}
		}
	}

	// Handler
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			super.handleMessage(msg);

			switch (msg.what) {

			case 1:
				pd.dismiss();// 关闭ProgressDialog
				Toast.makeText(getApplicationContext(), "注册失败,请检查网络是否畅通!",
						Toast.LENGTH_SHORT).show();
				break;
			case 2:
				pd.dismiss();// 关闭ProgressDialog
				Toast.makeText(getApplicationContext(), "注册失败,请联系管理员!",
						Toast.LENGTH_SHORT).show();
				break;

			case 3:
				pd.dismiss();// 关闭ProgressDialog

				String result = (String) msg.obj;
				try {

					Toast.makeText(getApplicationContext(), "注册成功!",
							Toast.LENGTH_SHORT).show();

					Intent intent = new Intent(RegActivity.this,
							LoginActivity.class);
					// 启动Activity
					startActivity(intent);

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
		// getMenuInflater().inflate(R.menu.main, menu);

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
