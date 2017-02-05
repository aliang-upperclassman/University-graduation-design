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

public class NoteListAdapter extends BaseAdapter {
	private List<Map<String, Object>> mData;

	private LayoutInflater mInflater;

	private Context context;// 用于接收传递过来的Context对象

	public NoteListAdapter(Context context, List<Map<String, Object>> mData) {

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

			convertView = mInflater.inflate(R.layout.activity_note_list_item,
					null);

			holder.title = (TextView) convertView
					.findViewById(R.id.notelisttitle);

			holder.id = (TextView) convertView.findViewById(R.id.notelistid);

			holder.id.setVisibility(View.GONE);

			holder.createtime = (TextView) convertView
					.findViewById(R.id.notelistcreatetime);
			
			holder.goodstype = (TextView) convertView
					.findViewById(R.id.goodstype);

			holder.state = (TextView) convertView.findViewById(R.id.TextView01);

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		holder.title.setText(mData.get(position).get("NAME").toString());
		
		holder.goodstype.setText(mData.get(position).get("GOODSTYPENAME").toString());

		holder.id.setText(mData.get(position).get("ID").toString());

		String createtime = mData.get(position).get("CREATETIME").toString();

		if (createtime.length() > 0) {

			Date d = new Date(Long.valueOf(createtime));

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			createtime = sdf.format(d);

			/*
			 * createtime = createtime.substring(0, createtime.indexOf(" "))
			 * .replace("-", "");
			 */
		}

		holder.createtime.setText(createtime);

		if(mData.get(position).get("STATE").toString().equals("0"))
		{
		  holder.state.setText("未审核");
		}
		else
		{
			holder.state.setText("已审核");
		}

		return convertView;
	}

	public final class ViewHolder {

		public TextView title;

		public TextView id;

		public TextView createtime;

		public TextView state;
		
		public TextView goodstype;

	}
}
