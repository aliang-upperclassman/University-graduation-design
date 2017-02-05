package com.myapp.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.myapp.activity.NewsListActivity.NewsListThread;
import com.myapp.adapter.GoodsListAdapter;
import com.myapp.adapter.GoodsListAdapter.ViewHolder;
import com.myapp.common.HttpUtil;
import com.myweb.domain.Goods;
import com.myweb.domain.GoodsType;
import com.myweb.domain.News;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class GoodsListActivity extends ActionBarActivity {

	private ActionBar bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_goods_list);

		bar = getSupportActionBar();

		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Thread goodsTypeThread = new Thread(new GoodsTypeThread());

		goodsTypeThread.start();

	}

	// 获取书籍类型多线程
	class GoodsTypeThread implements Runnable {

		public void run() {
			try {

				JSONObject paraObj = new JSONObject();

				paraObj.put("action", "list");

				String result = HttpUtil.getJsonFromServlet(paraObj.toString(),
						"GoodsTypeService");

				if (!result.equals("")) {

					List<GoodsType> goodsTypeList = JSONObject.parseArray(
							result, GoodsType.class);

					ArrayList<Map<String, Object>> goodstypelistMap = new ArrayList<Map<String, Object>>();

					for (GoodsType goodstype : goodsTypeList) {

						Map<String, Object> map = new HashMap<String, Object>();

						map.put("id", goodstype.getId());

						map.put("typename", goodstype.getTypename());

						goodstypelistMap.add(map);
					}

					Message msg = new Message();

					msg.what = 1;// 访问成功并有返回值

					msg.obj = goodstypelistMap;

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

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			super.handleMessage(msg);

			switch (msg.what) {

			case 0:

				Toast.makeText(getApplicationContext(), "加载失败,请检查网络是否畅通!",
						Toast.LENGTH_SHORT).show();
				break;

			case 1:
				try {
					ArrayList<Map<String, Object>> result = (ArrayList<Map<String, Object>>) msg.obj;

					if (result != null) {

						for (Map<String, Object> map : result) {

							map.get("id");

							map.get("typename");

							Fragment goodslistfragment = GoodsListFragment
									.newInstance(String.valueOf(map.get("id")));

							Tab tab1 = bar
									.newTab()
									.setText(
											String.valueOf(map.get("typename")))
									.setTabListener(
											new TestListener(goodslistfragment));

							bar.addTab(tab1);

						}

						System.out.println(result.toString());
					}
				} catch (Exception ex) {

					System.out.println("ex: " + ex.getMessage());
				}
				break;
			}

		}
	};

	class TestListener implements TabListener {
		// 声明Fragment
		private Fragment fragment;

		// 通过构造引用对应的Fragment
		public TestListener(Fragment fragment) {
			this.fragment = fragment;
		}

		// 实现ActionBar.TabListener接口所要实现的方法
		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			ft.add(R.id.container, fragment, null);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			ft.remove(fragment);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch (id) {

		case R.id.goodssearch:
			SearchDialog dialog = new SearchDialog(this,
					new SearchDialog.OnCustomDialogListener() {

						@Override
						public void back(String goodsname) {
							if (!goodsname.equals("")) {

								Intent intent = new Intent();

								intent.putExtra("goodsname", goodsname);

								intent.setClass(GoodsListActivity.this, SearchGoodsListActivity.class);

								// 启动Activity
								startActivity(intent);
								
							} else {
								Toast.makeText(GoodsListActivity.this,
										"搜索标题不能为空!", Toast.LENGTH_SHORT).show();
							}
						}
					});
			dialog.show();
			break;
		}
		return super.onOptionsItemSelected(item);

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_goods_list,
					container, false);
			return rootView;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

		}

	}

}
