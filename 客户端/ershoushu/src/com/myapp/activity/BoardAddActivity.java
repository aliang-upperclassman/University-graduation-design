package com.myapp.activity;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.myapp.common.HttpUtil;
import com.myapp.common.UploadUtil;
import com.myweb.domain.Board;

public class BoardAddActivity extends ActionBarActivity {

	String username;

	EditText contentTxt;

	Button saveBtn;

	Button returnBtn;

	MyApplication myApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_board_add);

		myApp = (MyApplication) getApplication();

		username = myApp.getUser().getLoginname();

		contentTxt = (EditText) findViewById(R.id.addnote_contenttxt);

		returnBtn = (Button) findViewById(R.id.addnote_returnbtn);

		returnBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				contentTxt.setText("");
			}
		});

		// 保存按钮
		saveBtn = (Button) findViewById(R.id.addnote_savebtn);

		saveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (contentTxt.getText().length() < 1) {
					Toast.makeText(getApplicationContext(), "评论内容不能为空!",
							Toast.LENGTH_SHORT).show();
					return;
				}

				Thread saveThread = new Thread(new SaveThread());

				saveThread.start();
			}
		});

	}

	class SaveThread implements Runnable {

		public void run() {
			try {

				String content = contentTxt.getText().toString();

				Board board = new Board();

				board.setContent(content);

				board.setUsername(username);

				JSONObject paraObj = new JSONObject();

				paraObj.put("action", "add");

				paraObj.put("board", JSONObject.toJSONString(board));

				String result = HttpUtil.getJsonFromServlet(paraObj.toString(),
						"BoardService");

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

			case 1:
				Toast.makeText(getApplicationContext(), "保存成功!",
						Toast.LENGTH_SHORT).show();
				finish();
				break;

			case 2:

				Toast.makeText(getApplicationContext(), "保存失败，请检查网络!",
						Toast.LENGTH_SHORT).show();
				break;
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
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
