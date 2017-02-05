package com.myapp.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myapp.activity.R;
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

public class NewsListAdapter extends BaseAdapter {

	private List<Map<String, Object>> mData;

	private LayoutInflater mInflater;

	public static Map<Integer, Boolean> isSelected;

	private Context context;// 用于接收传递过来的Context对象

	public NewsListAdapter(Context context, List<Map<String, Object>> mData) {

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

			convertView = mInflater.inflate(R.layout.activity_news_list_item,
					null);

			holder.title = (TextView) convertView
					.findViewById(R.id.newslisttitle);

			holder.id = (TextView) convertView.findViewById(R.id.newslistid);

			holder.id.setVisibility(View.GONE);

			holder.img = (ImageView) convertView
					.findViewById(R.id.newslistimage);

			

			holder.createtime = (TextView) convertView
					.findViewById(R.id.newslistcreatetime);

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		holder.title.setText(mData.get(position).get("title").toString());

		holder.id.setText(mData.get(position).get("id").toString());
 
		String createTime = mData.get(position).get("createTime").toString();

		if (createTime.length() > 0) {

			createTime = createTime.substring(0, createTime.indexOf(" "))
					.replace("-", "");
		}

		holder.createtime.setText(createTime);

		String imgpath = mData.get(position).get("imgpath").toString();

		if (imgpath.length() > 0) {
			// 显示图片的配置
			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.xwzx)
					.showImageOnFail(R.drawable.xwzx).cacheInMemory(true)
					.cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565)
					.build();

			ImageLoader.getInstance()
					.displayImage(imgpath, holder.img, options);
		}

		return convertView;
	}

	public final class ViewHolder {

		public TextView title;

		public TextView id;

		 

		public TextView createtime;

		public ImageView img;
	}

}
