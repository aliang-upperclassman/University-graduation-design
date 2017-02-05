package com.myapp.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.myapp.adapter.BoardReplyListAdapter;
import com.myapp.adapter.BoardReplyListAdapter.ViewHolder;
import com.myapp.common.HttpUtil;
import com.myweb.domain.Board;
import com.myweb.domain.Board1;
import com.myweb.domain.BoardReply;
import com.myweb.domain.User;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class BoardReplylListActivity extends FinalActivity {

	@ViewInject(id = R.id.biaoti)
	TextView biaoti;

	@ViewInject(id = R.id.neirong)
	TextView neirong;

	@ViewInject(id = R.id.shuoshuo)
	LinearLayout shuoshuo;

	@ViewInject(id = R.id.faqiren)
	TextView faqiren;

	@ViewInject(id = R.id.lishilv)
	ListView listView;

	String boardid;

	List<BoardReply> mListViewData = new ArrayList<BoardReply>();

	List<BoardReply> boardreplylist1;

	private BoardReplyListAdapter adapter;

	MyApplication myApp;

	private String[] opr = new String[] { "删除" };

	String loginname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_board_reply_list);

		myApp = (MyApplication) getApplication();

		boardreplylist1 = new ArrayList<BoardReply>();

		adapter = new BoardReplyListAdapter(this, boardreplylist1);

		listView.setAdapter(adapter);

		if (myApp.getUser().getLoginname() != null) {
			listView.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, final int position, long id) {

					ViewHolder vHollder = (ViewHolder) view.getTag();

					if (myApp.getUser().getLoginname().equals(loginname)) {
						showOprDialog(String.valueOf(vHollder.replyid.getText()
								.toString()));
					}

					return true;

				}
			});
		}  
		Intent intent = getIntent();

		boardid = intent.getStringExtra("boardid");

		initData1();

	}

	private void showOprDialog(String id) {

		final String _id = id;

		AlertDialog.Builder builder = new AlertDialog.Builder(
				BoardReplylListActivity.this);

		builder.setTitle("请选择操作:");

		builder.setItems(opr, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (which == 0) {

					FinalHttp finalHttp = new FinalHttp();

					AjaxParams ajaxParams = new AjaxParams();

					ajaxParams.put("action", "deletereply");

					ajaxParams.put("replyid", _id);

					try {
						finalHttp.get(HttpUtil.BASE_URL + "Board1Service",
								ajaxParams, new AjaxCallBack<Object>() {
									@Override
									public void onSuccess(Object t) {

										initData1();

										Toast.makeText(
												BoardReplylListActivity.this,
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

	public void initData1() {

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "view");

		ajaxParams.put("boardid", boardid);

		try {
			finalHttp.get(HttpUtil.BASE_URL + "Board1Service", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.length() > 0) {

								Board1 board = JSONObject.parseObject(result,
										Board1.class);

								biaoti.setText(board.getTitle());

								faqiren.setText(board.getUsername());

								loginname = board.getUsername();

								neirong.setText(board.getContent());

							 

							}
							initData2();

						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void initData2() {

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "replylist");

		ajaxParams.put("boardid", boardid);

		try {
			finalHttp.get(HttpUtil.BASE_URL + "Board1Service", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.length() > 0) {

								List<BoardReply> boardreplyList = new ArrayList<BoardReply>();

								boardreplyList = JSONObject.parseArray(result,
										BoardReply.class);

								boardreplylist1.clear();

								if (boardreplyList.size() > 0) {

									boardreplylist1.addAll(boardreplyList);

								}
								adapter.notifyDataSetChanged();

							} else {
								boardreplylist1.clear();

								adapter.notifyDataSetChanged();
							}

						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

 

	public void replyClick() {

		ReplyDialog dialog = new ReplyDialog(this,
				new ReplyDialog.OnCustomDialogListener() {

					@Override
					public void back(String goodsname) {
						if (!goodsname.equals("")) {

							saveReply(goodsname);

						} else {
							Toast.makeText(BoardReplylListActivity.this,
									"回复内容不能为空!", Toast.LENGTH_SHORT).show();
						}
					}
				});
		dialog.show();
	}

	public void saveReply(String _neirong) {

		BoardReply boardReply = new BoardReply();

		boardReply.setBoardid(boardid);

		boardReply.setReplycontent(_neirong);

		boardReply.setReplyuserid(String.valueOf(myApp.getUser().getId()));

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date date = new Date();

		boardReply.setReplytime(f.format(date));

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "reply");

		ajaxParams.put("boardreply", JSONObject.toJSONString(boardReply));

		try {
			finalHttp.get(HttpUtil.BASE_URL + "Board1Service", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.equals("ok")) {

								Toast.makeText(BoardReplylListActivity.this,
										"回复成功!", Toast.LENGTH_LONG).show();

								initData2();

							} else {

								Toast.makeText(BoardReplylListActivity.this,
										"保存失败!", Toast.LENGTH_LONG).show();

							}

						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Toast.makeText(getActivity(), "保存成功!", Toast.LENGTH_LONG).show();

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
				initData1();

				break;
			}
		};
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		if (myApp.getUser().getLoginname() != null) {

			getMenuInflater().inflate(R.menu.note_list_menu, menu);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		if (id == R.id.noteadd) {

			replyClick();

			return true;

		}

		return super.onOptionsItemSelected(item);
	}

}
