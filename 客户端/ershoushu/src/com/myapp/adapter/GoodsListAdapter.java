package com.myapp.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.myapp.activity.R;
import com.myapp.adapter.NewsListAdapter.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GoodsListAdapter extends BaseAdapter {

	private List<Map<String, Object>> mData;

	private LayoutInflater mInflater;

	public static Map<Integer, Boolean> isSelected;

	private Context context;// 用于接收传递过来的Context对象

	public GoodsListAdapter(Context context, List<Map<String, Object>> mData) {

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

		ViewHolder holder = null;// 数据项

		holder = new ViewHolder();

		convertView = LayoutInflater.from(context).inflate(
				R.layout.activity_goods_list_item, null);

		holder.name = (TextView) convertView.findViewById(R.id.goodslistname);

		holder.chuanjianren = (TextView) convertView
				.findViewById(R.id.chuangjianren);

		holder.id = (TextView) convertView.findViewById(R.id.goodslistid);

		holder.id.setVisibility(View.GONE);

		holder.createtime = (TextView) convertView
				.findViewById(R.id.goodslistcreatetime);

		convertView.setTag(holder);

		holder.name.setText(mData.get(position).get("name").toString());

		holder.id.setText(mData.get(position).get("id").toString());

		holder.chuanjianren.setText(mData.get(position).get("createusername")
				.toString());

		String createtime = mData.get(position).get("createtime").toString();

		if (createtime.length() > 0) {

			Date d = new Date(Long.valueOf(createtime));

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			createtime = sdf.format(d);

		}

		holder.createtime.setText(createtime);

		return convertView;

	}

	public final class ViewHolder {

		public TextView id;

		public TextView name;

		public TextView chuanjianren;

		public TextView createtime;

	}
}
