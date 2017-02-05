package com.myapp.activity;

import java.util.HashMap;
import java.util.List;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.myapp.activity.NoteAddActivity.InitSelThread;
import com.myapp.activity.NoteAddActivity.SaveThread;
import com.myapp.activity.NoteDetailActivity.NoteDetailThread;
import com.myapp.common.HttpUtil;
import com.myweb.domain.Goods;
import com.myweb.domain.GoodsType;
import com.myweb.domain.Note;

public class NoteEditActivity extends ActionBarActivity {

	private String[] goodstypeListString;

	private ArrayAdapter<String> adapter;

	private Spinner goodsadd_goodstype;

	EditText titleTxt;

	EditText contentTxt;

	Button saveBtn;

	Button returnBtn;

	List<GoodsType> goodsListType;

	private MyApplication myapp;

	private String id;

	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_note_edit);

		/* ��ʾProgressDialog */
		pd = ProgressDialog.show(NoteEditActivity.this, "����", "�����У����Ժ󡭡�");

		Intent intent = getIntent();

		id = intent.getStringExtra("id");

		goodsadd_goodstype = (Spinner) this
				.findViewById(R.id.goodsadd_goodstype);

		titleTxt = (EditText) findViewById(R.id.addnote_titletxt);

		contentTxt = (EditText) findViewById(R.id.addnote_contenttxt);

		returnBtn = (Button) findViewById(R.id.addnote_returnbtn);

		returnBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				titleTxt.setText("");

				contentTxt.setText("");
			}
		});

		Thread initSelThread = new Thread(new InitSelThread());

		initSelThread.start();
		// ���水ť
		saveBtn = (Button) findViewById(R.id.addnote_savebtn);

		saveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Thread saveThread = new Thread(new SaveThread());

				saveThread.start();
			}
		});

	}

	// ��ȡ���
	class InitSelThread implements Runnable {

		public void run() {
			try {

				JSONObject paraObj = new JSONObject();

				paraObj.put("action", "list");

				String result = HttpUtil.getJsonFromServlet(paraObj.toString(),
						"GoodsTypeService");

				if (!result.equals("")) {

					Message msg = new Message();

					msg.what = 3;// ���ʳɹ����з���ֵ

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

	class initData implements Runnable {
		public void run() {
			try {

				JSONObject paraObj = new JSONObject();

				paraObj.put("id", id);

				paraObj.put("action", "view");

				String result = HttpUtil.getJsonFromServlet(paraObj.toString(),
						"GoodsService");

				if (!result.equals("")) {

					Message msg = new Message();

					msg.what = 4;// ���ʳɹ����з���ֵ

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

	// ���� �߳�
	class SaveThread implements Runnable {

		public void run() {
			try {

				String title = titleTxt.getText().toString();

				String content = contentTxt.getText().toString();

				Goods goods = new Goods();

				goods.setId(Integer.parseInt(id));

				goods.setName(title);

				goods.setDescription(content);

				if (goodsadd_goodstype.getSelectedItem().toString() != "") {
					for (GoodsType gt : goodsListType) {
						if (goodsadd_goodstype.getSelectedItem().toString()
								.equals(gt.getTypename())) {
							goods.setTypeid(String.valueOf(gt.getId()));
						}
					}

				}

				myapp = (MyApplication) getApplication();

				goods.setCreateuser(String.valueOf(myapp.getUser().getId()));

				JSONObject paraObj = new JSONObject();

				paraObj.put("action", "editsave");

				paraObj.put("goods", JSONObject.toJSONString(goods));

				String result = HttpUtil.getJsonFromServlet(paraObj.toString(),
						"GoodsService");

				if (result.equals("ok")) {

					Message msg = new Message();

					msg.what = 1;// �ɹ�

					handler.sendMessage(msg);
				}

			} catch (Exception ex) {

				System.out.println("ex: " + ex.getMessage());

				Message msg = new Message();

				msg.what = 2;// �����쳣

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
				pd.dismiss();// �ر�ProgressDialog
				Toast.makeText(getApplicationContext(), "�޸ĳɹ�!",
						Toast.LENGTH_SHORT).show();
				titleTxt.setText("");

				contentTxt.setText("");

				break;

			case 2:

				pd.dismiss();// �ر�ProgressDialog
				Toast.makeText(getApplicationContext(), "�޸�ʧ�ܣ���������!",
						Toast.LENGTH_SHORT).show();
				break;

			case 3:

				goodsListType = JSONObject.parseArray(msg.obj.toString(),
						GoodsType.class);

				Thread initData1 = new Thread(new initData());

				initData1.start();

				break;

			case 4:

				try {

					Goods good = JSONObject.parseObject(msg.obj.toString(),
							Goods.class);

					titleTxt.setText(good.getName());

					String createTime = good.getCreatetime();

					if (!createTime.equals("")) {
						createTime = createTime.substring(0,
								createTime.indexOf(" "));
					}

					contentTxt.setText(good.getDescription());

					int Position = 0;

					if (goodsListType.size() > 0) {
						goodstypeListString = new String[goodsListType.size()];
					}
					for (int i = 0; i < goodsListType.size(); i++) {
						goodstypeListString[i] = String.valueOf(goodsListType
								.get(i).getTypename());
						if (goodstypeListString[i].equals(good.getTypename())) {
							Position = i;
						}
					}

					adapter = new ArrayAdapter<String>(NoteEditActivity.this,
							android.R.layout.simple_spinner_item,
							goodstypeListString);

					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

					goodsadd_goodstype.setAdapter(adapter);

					goodsadd_goodstype.setVisibility(View.VISIBLE);

					goodsadd_goodstype.setSelection(Position, true);

				} catch (Exception ex) {
					System.out.print(ex.getMessage());
				}
				pd.dismiss();// �ر�ProgressDialog
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
