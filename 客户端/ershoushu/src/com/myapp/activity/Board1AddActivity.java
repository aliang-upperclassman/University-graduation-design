package com.myapp.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.myapp.common.HttpUtil;
import com.myweb.domain.Board;
import com.myweb.domain.Board1;
import com.myweb.domain.User;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class Board1AddActivity extends FinalActivity {

	@ViewInject(id = R.id.biaoti)
	EditText biaoti;

	@ViewInject(id = R.id.neirong)
	EditText neirong;

	@ViewInject(id = R.id.savebtn, click = "saveBtnClick")
	Button savebtn;

	String questid;

	MyApplication myApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_board1_add);

		myApp = (MyApplication) getApplication();

	}

	public void saveBtnClick(View v) {

		if (biaoti.getText().length() < 1) {

			Toast.makeText(Board1AddActivity.this, "标题不能为空!", Toast.LENGTH_LONG)
					.show();

			return;
		}

		if (neirong.getText().toString().length() < 1) {

			Toast.makeText(Board1AddActivity.this, "内容不能为空!", Toast.LENGTH_LONG)
					.show();

			return;
		}

		Board1 board = new Board1();

		board.setTitle(biaoti.getText().toString());

		board.setContent(neirong.getText().toString());

		board.setUserid(String.valueOf(myApp.getUser().getId()));

		User user = myApp.getUser();

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date date = new Date();

		board.setCreatetime(f.format(date));

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "add");

		ajaxParams.put("board", JSONObject.toJSONString(board));

		try {
			finalHttp.get(HttpUtil.BASE_URL + "Board1Service", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.equals("ok")) {

								Toast.makeText(Board1AddActivity.this, "保存成功!",
										Toast.LENGTH_LONG).show();

								finish();

							} else {

								Toast.makeText(Board1AddActivity.this, "保存失败!",
										Toast.LENGTH_LONG).show();

							}

						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Toast.makeText(getActivity(), "保存成功!", Toast.LENGTH_LONG).show();

	}

	public void returnClick(View v) {

		Board1AddActivity.this.finish();
	}

}
