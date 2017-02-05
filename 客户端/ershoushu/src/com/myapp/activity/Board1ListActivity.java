package com.myapp.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.alibaba.fastjson.JSONObject;
import com.myapp.adapter.Board1ListAdapter;
 
import com.myapp.adapter.Board1ListAdapter.ViewHolder;
import com.myapp.common.HttpUtil;
import com.myweb.domain.Board1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class Board1ListActivity extends FinalActivity{

	List<Board1> mListViewData = new ArrayList<Board1>();

	List<Board1> boardlist1;

	@ViewInject(id = R.id.lishilv)
	ListView listView;

	@ViewInject(id = R.id.ll)
	LinearLayout ll;

	@ViewInject(id = R.id.tianjiahuati, click = "addBtnClick")
	Button tianjiahuati;

	private Board1ListAdapter adapter;

	MyApplication myApp;

	private String[] opr = new String[] { "删除" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_board1_list);

		FinalActivity.initInjectedView(this);

		myApp = (MyApplication) getApplication();

		if (myApp.getUser().getLoginname() == null) {
			tianjiahuati.setVisibility(View.GONE);
			ll.setVisibility(View.GONE);
		}

		boardlist1 = new ArrayList<Board1>();

		adapter = new Board1ListAdapter(Board1ListActivity.this, boardlist1);

		listView.setAdapter(adapter);

		if (myApp.getUser().getLoginname() != null) {
			listView.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, final int position, long id) {

					ViewHolder vHollder = (ViewHolder) view.getTag();

					if (myApp.getUser().getLoginname()
							.equals(vHollder.loginname)) {
						showOprDialog(String.valueOf(vHollder.boardid.getText()
								.toString()));
					}

					return true;

				}
			});
		}

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ViewHolder vHollder = (ViewHolder) view.getTag();

				Intent intent = new Intent();

				intent.putExtra("boardid", vHollder.boardid.getText());

				intent.setClass(Board1ListActivity.this,
						BoardReplylListActivity.class);

				startActivity(intent);
			}
		});

		initData();
	}

	private void showOprDialog(String id) {

		final String _id = id;

		AlertDialog.Builder builder = new AlertDialog.Builder(
				Board1ListActivity.this);

		builder.setTitle("请选择操作:");

		builder.setItems(opr, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (which == 0) {

					FinalHttp finalHttp = new FinalHttp();

					AjaxParams ajaxParams = new AjaxParams();

					ajaxParams.put("action", "delete");

					ajaxParams.put("boardid", _id);

					try {
						finalHttp.get(HttpUtil.BASE_URL + "Board1Service",
								ajaxParams, new AjaxCallBack<Object>() {
									@Override
									public void onSuccess(Object t) {

										initData();

										Toast.makeText(Board1ListActivity.this,
												"删除成功!", Toast.LENGTH_LONG)
												.show();
									}
								});

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		});

		builder.create().show();
	}

	public void addBtnClick(View v) {

		Intent intent = new Intent();

		intent.setClass(Board1ListActivity.this, Board1AddActivity.class);

		startActivity(intent);
	}

	public void initData() {

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "list");

		try {
			finalHttp.get(HttpUtil.BASE_URL + "Board1Service", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.length() > 0) {

								List<Board1> boardList = new ArrayList<Board1>();

								boardList = JSONObject.parseArray(result,
										Board1.class);// 获取商品集合

								boardlist1.clear();

								if (boardList.size() > 0) {

									boardlist1.addAll(boardList);

								}
								adapter.notifyDataSetChanged();

							} else {
								boardlist1.clear();

								adapter.notifyDataSetChanged();
							}

						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
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

				initData();

				break;
			}
		};
	};

}
