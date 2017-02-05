package com.myapp.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.activity.R;
import com.myapp.adapter.NewsListAdapter.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class NewsreplyListAdapter extends BaseAdapter {
	private List<Map<String, Object>> mData;

	private LayoutInflater mInflater;
 
	private Context context;// 用于接收传递过来的Context对象

	public NewsreplyListAdapter(Context context, List<Map<String, Object>> mData) {

		this.context = context;

		mInflater = LayoutInflater.from(context);

		this.mData = mData;
 

	}

	@Override
	public int getCount() {
		System.out.print("mData.size()=" + mData.size());
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
 
		ViewHolder holder = null;

		// convertView为null的时候初始化convertView。
		if (convertView == null) {

			holder = new ViewHolder();

			convertView = mInflater.inflate(R.layout.activity_newsreply_list_item,
					null);

			holder.yonghuming = (TextView) convertView
					.findViewById(R.id.yonghuming);

			 
			holder.pinglunshijian = (TextView) convertView
					.findViewById(R.id.pinglunshijian);

			holder.neirong = (TextView) convertView
					.findViewById(R.id.neirong);
		
			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		holder.yonghuming.setText(mData.get(position).get("yonghuming").toString());
 
		String createtime = mData.get(position).get("pinglunshijian").toString();

		if (createtime.length() > 0) {

			Date d = new Date(Long.valueOf(createtime));

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

			createtime = sdf.format(d);

			/*
			 * createtime = createtime.substring(0, createtime.indexOf(" "))
			 * .replace("-", "");
			 */
		}

		holder.pinglunshijian.setText(createtime);

		holder.neirong.setText( mData.get(position).get("neirong").toString());

		return convertView;
	}

	public final class ViewHolder {

		public TextView yonghuming;

		public TextView pinglunshijian;

		public TextView neirong;
 

	}
}
