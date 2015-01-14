package com.luna.anytime;

import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.luna.anytime.adapter.AnytimeUserResponseListAdapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AboutAppActivity extends AnyTimeActivity {
	Button submitButton;
	EditText submitEditText;
	ListView mUserResponseListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_app);
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		submitButton = (Button) findViewById(R.id.button_about_app_submit_user_input);
		submitEditText = (EditText) findViewById(R.id.editText_about_app_user_input);
		mUserResponseListView = (ListView) findViewById(R.id.listView_user_back);
		submitButton.setOnClickListener(buttonListener);

    FindCallback<AVObject> findCallback=new FindCallback<AVObject>() {
      public void done(List<AVObject> avObjects, AVException e) {
        if (e == null) {
          Message msg = new Message();
          msg.what = 3;
          msg.obj = avObjects;
          mHandler.sendMessage(msg);
        } else {
          showError(activity.getString(R.string.network_error));
        }
      }
    };
		AVQuery<AVObject> query = new AVQuery<AVObject>("SuggestionByUser");
		query.whereEqualTo("UserObjectId", getUserId());
		query.findInBackground(findCallback);
	}

	OnClickListener buttonListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
      SaveCallback saveCallback=new SaveCallback() {
        @Override
        public void done(AVException e) {
          if (e == null) {
            mHandler.obtainMessage(1).sendToTarget();
          } else {
            mHandler.obtainMessage(2).sendToTarget();
          }
        }
      };
      String advice = submitEditText.getText().toString();
      AVService.createAdvice(getUserId(), advice, saveCallback);
		}
	};

  @SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				new AlertDialog.Builder(activity)
						.setTitle(
								activity.getResources().getString(
										R.string.dialog_message_success))
						.setMessage(
								activity.getResources()
										.getString(
												R.string.action_about_app_submit_message_success))
						.setNegativeButton(android.R.string.ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
										submitEditText.setText("");
									}
								}).show();
				break;
			case 2:
				showError(activity
						.getString(R.string.action_about_app_submit_message_error));
				break;
			case 3:
				showResponseList((List<AVObject>) msg.obj);
				break;
			default:
				break;
			}
		}
	};

	private void showResponseList(List<AVObject> responseList) {
		if (responseList != null && responseList.size() != 0) {
			AnytimeUserResponseListAdapter adapter = new AnytimeUserResponseListAdapter(
					responseList, activity);
			mUserResponseListView.setAdapter(adapter);
			mUserResponseListView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
