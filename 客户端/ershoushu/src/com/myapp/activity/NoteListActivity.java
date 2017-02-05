package com.myapp.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.myapp.activity.NewsListActivity.NewsListThread;
import com.myapp.adapter.GoodsListAdapter;
import com.myapp.adapter.NewsListAdapter;
import com.myapp.adapter.NoteListAdapter.ViewHolder;
import com.myapp.adapter.NoteListAdapter;
import com.myapp.common.HttpUtil;
import com.myweb.domain.Goods;
import com.myweb.domain.GoodsType;
import com.myweb.domain.News;
import com.myweb.domain.Note;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class NoteListActivity extends ActionBarActivity {

	private TextView t1;

	private TextView t2;

	private TextView t3;

	List<Integer> listItemID = new ArrayList<Integer>();

	private ProgressDialog pd;

	ListView listView;

	List<Map<String, Object>> notelist1;

	private NoteListAdapter adapter;

	private ImageView ivDeleteText;

	private EditText etSearch;

	private Button searchBtn;

	private MyApplication myapp;

	private String[] opr = new String[] { "修改", "删除" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_note_list);

		/* 显示ProgressDialog */
		pd = ProgressDialog.show(NoteListActivity.this, "标题", "加载中，请稍后……");

		listView = (ListView) findViewById(R.id.note_lv);

		notelist1 = new ArrayList<Map<String, Object>>();

		adapter = new NoteListAdapter(this, notelist1);

		listView.setAdapter(adapter);

		// listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		// 查看详情事件
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				ViewHolder vHollder = (ViewHolder) view.getTag();

				// Toast.makeText(getApplicationContext(),
				// vHollder.title.getText(), Toast.LENGTH_SHORT).show();

				Intent intent = new Intent();

				intent.putExtra("id", vHollder.id.getText());

				intent.setClass(NoteListActivity.this, NoteDetailActivity.class);

				// 启动Activity
				startActivity(intent);
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {

				ViewHolder vHollder = (ViewHolder) view.getTag();

				// Toast.makeText(getApplicationContext(),
				// vHollder.title.getText(), Toast.LENGTH_SHORT).show();

				showOprDialog(vHollder.id.getText().toString());

				return true;

			}
		});

		Thread newsListThread = new Thread(new NoteListThread("list"));

		newsListThread.start();
	}

	private void showOprDialog(String id) {

		final String _id = id;

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("请选择操作:");

		builder.setItems(opr, new DialogInterface.OnClickListener()

		{
			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (which == 0) {
					Intent intent = new Intent();

					intent.putExtra("id", _id);

					intent.setClass(NoteListActivity.this,
							NoteEditActivity.class);

					// 启动Activity
					startActivity(intent);

					// NoteListActivity.this.finish();

				} else if (which == 1) {

					Thread deleteThread = new Thread(new DeleteThread(_id));

					deleteThread.start();
				}

			}
		});

		builder.create().show();
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
					Thread newsListThread = new Thread(new NoteListThread("list"));

					newsListThread.start();

					break;
				}
			};
		};
		
	class NoteListThread implements Runnable {

		private String _action = "";

		public NoteListThread(String action) {
			this._action = action;
		}

		public void run() {

			try {

				JSONObject paraObj = new JSONObject();

				paraObj.put("action", "myList");

				myapp = (MyApplication) getApplication();

				paraObj.put("userid", String.valueOf(myapp.getUser().getId()));

				String result = HttpUtil.getJsonFromServlet(paraObj.toString(),
						"GoodsService");

				if (!result.equals("")) {

					Message msg = new Message();

					msg.what = 1;// 访问成功并有返回值

					msg.obj = result;

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
				pd.dismiss();// 关闭ProgressDialog

				Toast.makeText(getApplicationContext(), "查询失败,请检查网络是否畅通!",
						Toast.LENGTH_SHORT).show();
				break;

			case 1:

				try {

					List<Map<String, Object>> listMap = JSON.parseObject(
							msg.obj.toString(),
							new TypeReference<List<Map<String, Object>>>() {
							});

					if (listMap != null) {

						notelist1.clear();

						notelist1.addAll(listMap);

						adapter.notifyDataSetChanged();

					}
				} catch (Exception ex) {

					System.out.println("ex: " + ex.getMessage());
				}

				pd.dismiss();// 关闭ProgressDialog

				break;
			case 2:

				Toast.makeText(getApplicationContext(), "操作成功!",
						Toast.LENGTH_SHORT).show();

				// adapter.isSelected.clear();

				Thread newsListThread = new Thread(new NoteListThread("list"));

				newsListThread.start();

				break;
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.note_list_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		if (id == R.id.noteadd) {

			Intent intent = new Intent();

			intent.setClass(NoteListActivity.this, NoteAddActivity.class);

			// 启动Activity
			startActivity(intent);

			// NoteListActivity.this.finish();

			return true;

		}
		return super.onOptionsItemSelected(item);
	}

	// 注册多线程
	class DeleteThread implements Runnable {

		private String _ids; // 定义需要传值进来的参数

		public DeleteThread(String ids) {

			this._ids = ids;

		}

		public void run() {
			try {

				JSONObject paraObj = new JSONObject();

				paraObj.put("action", "delete");

				paraObj.put("ids", _ids);

				String result = HttpUtil.getJsonFromServlet(paraObj.toString(),
						"GoodsService");

				// String result ="ok";

				if (result.equals("ok")) {

					Message msg = new Message();

					msg.what = 2;// 成功

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

}
