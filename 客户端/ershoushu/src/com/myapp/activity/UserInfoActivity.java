package com.myapp.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.myapp.common.HttpUtil;
import com.myweb.domain.User;

public class UserInfoActivity extends ActionBarActivity {

	MyApplication myApp;

	TextView loginnameTxt;

	EditText passwordTxt;

	EditText emailtxt;

	EditText reg_teltxt;

	Button regBtn;

	Map<String, Object> userMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_userinfo);

		loginnameTxt = (TextView) findViewById(R.id.reg_loginnametxt);

		passwordTxt = (EditText) findViewById(R.id.reg_passwordtxt);

		emailtxt = (EditText) findViewById(R.id.reg_emailtxt);

		reg_teltxt = (EditText) findViewById(R.id.reg_teltxt);

		regBtn = (Button) findViewById(R.id.reg_regbtn);

		regBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (passwordTxt.getText() == null
						|| passwordTxt.getText().toString().equals("")) {

					Toast.makeText(UserInfoActivity.this, "密码不能为空!",
							Toast.LENGTH_SHORT).show();

					return;
				}

				Thread saveThread = new Thread(new SaveThread());

				saveThread.start();
			}
		});

		Thread userThread = new Thread(new UserInfoThread());

		userThread.start();

		myApp = (MyApplication) getApplication(); // 获得自定义的应用程序MyApp

	}

	class UserInfoThread implements Runnable {

		public void run() {
			try {

				JSONObject paraObj = new JSONObject();

				paraObj.put("userid", String.valueOf(myApp.getUser().getId()));

				paraObj.put("action", "init");

				String result = HttpUtil.getJsonFromServlet(paraObj.toString(),
						"UserService");

				if (!result.equals("")) {

					User user = JSONObject.parseObject(result, User.class);

					Map<String, Object> userMap1 = new HashMap<String, Object>();

					userMap1.put("loginname", user.getLoginname());

					userMap1.put("password", user.getLoginpsw());

					userMap1.put("email", user.getEmail());

					userMap1.put("telphone", user.getTel());

					Message msg = new Message();

					msg.what = 4;// 访问成功并有返回值

					msg.obj = userMap1;

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

	class SaveThread implements Runnable {

		public void run() {
			try {

				String loginpsw = ((EditText) findViewById(R.id.reg_passwordtxt))
						.getText().toString();

				String emailtxt = ((EditText) findViewById(R.id.reg_emailtxt))
						.getText().toString();

				String teltxt = ((EditText) findViewById(R.id.reg_teltxt))
						.getText().toString();

				User user = myApp.getUser();

				user.setLoginpsw(loginpsw);

				user.setTel(teltxt);

				user.setEmail(emailtxt);

				// user.setUsername(username);

				// 密码是否为空
				if (loginpsw == null || loginpsw.equals("")) {

					Message msg = new Message();

					msg.what = 0;

					handler.sendMessage(msg);

					return;
				}

				System.out.println("=============="
						+ JSONObject.toJSONString(user));

				JSONObject paraObj = new JSONObject();

				paraObj.put("action", "update");

				paraObj.put("user", JSONObject.toJSONString(user));

				String result = HttpUtil.getJsonFromServlet(paraObj.toString(),
						"UserService");

				if (result.equals("ok")) {

					Message msg = new Message();

					msg.what = 1;// 成功

					handler.sendMessage(msg);
				}

			} catch (Exception ex) {

				System.out.println("ex: " + ex.getMessage());

				Message msg = new Message();

				msg.what = 2;// 网络异常

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

				Toast.makeText(getApplicationContext(), "密码不能为空!",
						Toast.LENGTH_SHORT).show();
				break;

			case 1:

				Toast.makeText(getApplicationContext(), "修改成功!",
						Toast.LENGTH_SHORT).show();
				break;

			case 2:

				Toast.makeText(getApplicationContext(), "修改失败，请检查网络!",
						Toast.LENGTH_SHORT).show();
				break;
			case 4:

				try {
					userMap = (Map<String, Object>) msg.obj;

					loginnameTxt.setText(userMap.get("loginname").toString());

					passwordTxt.setText(userMap.get("password").toString());

					emailtxt.setText(userMap.get("email").toString());

					reg_teltxt.setText(userMap.get("telphone").toString());

				} catch (Exception ex) {
					System.out.print(ex.getMessage());
				}

			}

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.add_question, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will

		return super.onOptionsItemSelected(item);
	}
}
