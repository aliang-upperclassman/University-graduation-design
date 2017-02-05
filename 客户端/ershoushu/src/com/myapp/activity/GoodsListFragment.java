package com.myapp.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.myapp.adapter.GoodsListAdapter;
import com.myapp.adapter.GoodsListAdapter.ViewHolder;
import com.myapp.common.HttpUtil;
import com.myweb.domain.Goods;
import com.myweb.domain.GoodsType;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class GoodsListFragment extends Fragment {

	ListView goodsListView;

	GoodsListAdapter goodsListAdapter;

	private String _goodstypeid;

	private ProgressDialog pd;

	List<Map<String, Object>> goodsList; // ��Ʒ�б�

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		_goodstypeid = getArguments().getString("goodstypeid");

	}

	public static final GoodsListFragment newInstance(String goodstypeid) {
		GoodsListFragment f = new GoodsListFragment();

		Bundle bdl = new Bundle(2);

		bdl.putString("goodstypeid", goodstypeid);

		f.setArguments(bdl);

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_goods_list, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		/* ��ʾProgressDialog */
		pd = ProgressDialog.show(getActivity(), "����", "�����У����Ժ󡭡�");

		ListView bookListView = (ListView) getActivity().findViewById(
				R.id.goodslist_lv);

		goodsList = new ArrayList<Map<String, Object>>();

		goodsListAdapter = new GoodsListAdapter(getActivity(), goodsList);

		bookListView.setAdapter(goodsListAdapter);

		bookListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				ViewHolder vHollder = (ViewHolder) view.getTag();

				// Toast.makeText(getApplicationContext(),
				// vHollder.name.getText(), Toast.LENGTH_SHORT).show();

				if (vHollder.id != null) {// ֻ����������

					Intent intent = new Intent();

					intent.putExtra("id", vHollder.id.getText());

					intent.setClass(getActivity(), GoodsDetailActivity.class);

					// ����Activity
					startActivity(intent);
				}
			}
		});

		Thread goodsListThread = new Thread(new GoodsListThread());

		goodsListThread.start();

	}

	class GoodsListThread implements Runnable {

		public void run() {
			try {

				JSONObject paraObj = new JSONObject();

				paraObj.put("action", "list");

				paraObj.put("goodstype", _goodstypeid);

				String result = HttpUtil.getJsonFromServlet(paraObj.toString(),
						"GoodsService");

				List<Goods> goodsList1 = new ArrayList<Goods>();

				if (!result.equals("")) {

					goodsList1 = JSONObject.parseArray(result, Goods.class);// ��ȡ��Ʒ����
				}

				ArrayList<Map<String, Object>> goodslistMap1 = new ArrayList<Map<String, Object>>();

				for (Goods goods : goodsList1) {

					Map<String, Object> map1 = new HashMap<String, Object>();

					map1.put("id", goods.getId());

					map1.put("name", goods.getName());

					map1.put("createtime", goods.getCreatetime());

					map1.put("createusername", goods.getCreateusername());

					goodslistMap1.add(map1);

				}

				Message msg = new Message();

				msg.what = 1;// ���ʳɹ����з���ֵ

				List para = new ArrayList();

				para.add(goodslistMap1);

				msg.obj = para;

				handler.sendMessage(msg);

			} catch (Exception ex) {

				System.out.println("ex: " + ex.getMessage());

				Message msg = new Message();

				msg.what = 0;// �����쳣

				handler.sendMessage(msg);

			}
		}
	}

	// Handler
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			super.handleMessage(msg);

			switch (msg.what) {

			case 0:
				pd.dismiss();// �ر�ProgressDialog

				Toast.makeText(getActivity().getApplicationContext(),
						"��ѯʧ��,���������Ƿ�ͨ!", Toast.LENGTH_SHORT).show();
				break;

			case 1:
				pd.dismiss();// �ر�ProgressDialog

				try {

					List paraList = (ArrayList) msg.obj;

					ArrayList<Map<String, Object>> result = (ArrayList<Map<String, Object>>) paraList
							.get(0);

					List<String> result1 = (ArrayList) paraList.get(0);

					if (result1 != null) {

						goodsList.addAll(result);

						goodsListAdapter.notifyDataSetChanged();

						System.out.println(result.toString());
					}
				} catch (Exception ex) {

					System.out.println("ex: " + ex.getMessage());
				}
				break;
			}

		}
	};
}
