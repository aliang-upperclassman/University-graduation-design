package com.myapp.activity;

import com.myapp.adapter.MainActivityAdapter;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	GridView maingv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取到GridView
		maingv = (GridView) findViewById(R.id.gv_all);
		// 给gridview设置数据适配器
		maingv.setAdapter(new MainActivityAdapter(this));
		// 点击事件
		maingv.setOnItemClickListener(new MainItemClickListener());

	}

	private class MainItemClickListener implements OnItemClickListener {
		/**
		 * @param parent
		 *            代表当前的gridview
		 * @param view
		 *            代表点击的item
		 * @param position
		 *            当前点击的item在适配中的位置
		 * @param id
		 *            当前点击的item在哪一行
		 */
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (position) {

			case 0:

				Intent intent = new Intent(MainActivity.this,
						NewsListActivity.class);
				// 启动Activity
				startActivity(intent);

				break;
			case 1:

				Intent intent1 = new Intent(MainActivity.this,
						GoodsListActivity.class);
				// 启动Activity
				startActivity(intent1);

				break;

			case 2:

				Intent intent2 = new Intent(MainActivity.this,
						NoteListActivity.class);
				// 启动Activity
				startActivity(intent2);

				break;
				
			case 3:

				Intent intent12 = new Intent(MainActivity.this,
						BoardListActivity.class);
				// 启动Activity
				startActivity(intent12);

				break;
				
			case 4:

				Intent intent121 = new Intent(MainActivity.this,
						Board1ListActivity.class);
				// 启动Activity
				startActivity(intent121);

				break;

			case 5:

				Intent intent3 = new Intent(MainActivity.this,
						UserInfoActivity.class);
				// 启动Activity
				startActivity(intent3);

				break;

			case 6:

				MainActivity.this.finish();

				break;

			}
		}
	}

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
