package com.myapp.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ReplyDialog extends Dialog {
	// 定义回调事件，用于dialog的点击事件
	public interface OnCustomDialogListener {

		public void back(String name);
	}

	private Button qdBtn;

	private Button qxBtn;

	private OnCustomDialogListener customDialogListener;

	EditText goodsnameET;

	public ReplyDialog(Context context,
			OnCustomDialogListener customDialogListener) {

		super(context, R.style.FullHeightDialog);

		this.customDialogListener = customDialogListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_reply_dialog);

		goodsnameET = (EditText) findViewById(R.id.search_goodsname);

		qdBtn = (Button) findViewById(R.id.qdButton);

		qdBtn.setOnClickListener(clickListener);

		Button qxBtn = (Button) findViewById(R.id.qxButton);

		qxBtn.setOnClickListener(clickListener1);
	}

	private View.OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {	

			if (goodsnameET.getText()!=null&&!goodsnameET.getText().toString().equals("")) {
				
				customDialogListener.back(String.valueOf(goodsnameET.getText()));
				
				ReplyDialog.this.dismiss();
			}
			else
			{
				return;
			}

		}
	};

	private View.OnClickListener clickListener1 = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			ReplyDialog.this.dismiss();
		}
	};
}
