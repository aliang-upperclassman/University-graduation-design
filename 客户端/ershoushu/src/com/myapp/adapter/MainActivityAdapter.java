package com.myapp.adapter;

import com.myapp.activity.R;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivityAdapter extends BaseAdapter {

	private static final String TAG = "MainGridViewAdapter";

	private String[] names = { "系统公告", "书籍列表", "我的书籍","评论管理", "学霸笔记","个人信息","安全退出" };
	private int[] icons = { R.drawable.xinwenzixun, R.drawable.shuji,
			R.drawable.wodefb, R.drawable.liuyanban,  R.drawable.dingdan,R.drawable.gerenxinxi,R.drawable.anquantuichu };
	private Context context;
	LayoutInflater infalter;

	public MainActivityAdapter(Context context) {
		this.context = context;
		// 方法1 通过系统的service 获取到 试图填充器
		// infalter = (LayoutInflater)
		// context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 方法2 通过layoutinflater的静态方法获取到 视图填充器
		infalter = LayoutInflater.from(context);
	}

	// 返回gridview里面有多少个条目
	@Override
	public int getCount() {
		return names.length;
	}

	// 返回某个position对应的id
	@Override
	public Object getItem(int position) {
		return position;
	}

	// 返回某个position对应的id
	@Override
	public long getItemId(int position) {
		return position;
	}

	// 返回某个位置对应的视图
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i(TAG, "GETVIEW " + position);
		// 把一个布局文件转换成视图
		View view = infalter.inflate(R.layout.activity_main_item, null);
		ImageView iv = (ImageView) view.findViewById(R.id.main_gv_iv);
		TextView tv = (TextView) view.findViewById(R.id.main_gv_tv);
		// 设置每一个item的名字和图标
				iv.setImageResource(icons[position]);

				tv.setText(names[position]);

				tv.setTextColor(Color.RED);
				
				tv.getPaint().setFakeBoldText(true); 

				return view;
	}

}
