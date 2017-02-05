package com.myapp.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.myapp.activity.R;
import com.myweb.domain.Board;
import com.myweb.domain.Board1;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Board1ListAdapter extends BaseAdapter {

	private List<Board1> mData;

	private LayoutInflater mInflater;

	private Context context;// ���ڽ��մ��ݹ�����Context����

	public Board1ListAdapter(Context context, List<Board1> mData) {

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

		// convertViewΪnull��ʱ���ʼ��convertView��
		if (convertView == null) {

			holder = new ViewHolder();

			convertView = mInflater.inflate(R.layout.activity_board1_list_item,
					null);

			FinalActivity.initInjectedView(this, convertView);

			holder.boardid = (TextView) convertView.findViewById(R.id.boardid);

			holder.biaoti = (TextView) convertView.findViewById(R.id.biaoti);

			holder.faqiren = (TextView) convertView.findViewById(R.id.faqiren);

		 

			holder.createtime = (TextView) convertView
					.findViewById(R.id.createtime);

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		String createtime1 = (mData.get(position).getCreatetime());

		holder.boardid.setText(String.valueOf(mData.get(position).getId()));

		holder.createtime.setText(createtime1);

		holder.biaoti.setText(mData.get(position).getTitle());

		holder.faqiren.setText("����:" + mData.get(position).getUsername());

		holder.loginname = mData.get(position).getUsername();


		return convertView;
	}

	public final class ViewHolder {

		public String loginname;

		public TextView boardid;

		public TextView biaoti;

		public TextView createtime;

		public TextView faqiren;

	

	}
}
