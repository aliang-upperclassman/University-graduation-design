package com.myapp.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.myapp.activity.R;
import com.myweb.domain.BoardReply;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BoardReplyListAdapter extends BaseAdapter {

	private List<BoardReply> mData;

	private LayoutInflater mInflater;

	private Context context;// 用于接收传递过来的Context对象

	public BoardReplyListAdapter(Context context, List<BoardReply> mData) {

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

			convertView = mInflater.inflate(
					R.layout.activity_board_reply_list_item, null);

			FinalActivity.initInjectedView(this, convertView);

			holder.replyid = (TextView) convertView.findViewById(R.id.replyid);

			holder.username = (TextView) convertView
					.findViewById(R.id.username);

			holder.neirong = (TextView) convertView.findViewById(R.id.neirong);

			holder.createtime = (TextView) convertView
					.findViewById(R.id.createtime);

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		holder.replyid
				.setText(String.valueOf(mData.get(position).getReplyid()));

		String createtime1 = (mData.get(position).getReplytime());

		holder.createtime.setText(createtime1);

		holder.username.setText(mData.get(position).getReplyusername());

		holder.neirong.setText("    " + mData.get(position).getReplycontent());

		return convertView;
	}

	public final class ViewHolder {

		public TextView replyid;

		public TextView username;

		public TextView createtime;

		public TextView neirong;
	}
}
