package com.luna.anytime;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.CountCallback;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DoingDetailActivity extends AnyTimeActivity {

  TextView loadingText;
  TextView atTimeTitleText;
  TextView countText;
  String doingObjectId;
  String doingObjectTitle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_doing_detail);
    this.getActionBar().setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    doingObjectId = intent.getStringExtra("childobj");
    doingObjectTitle = intent.getStringExtra("childtitle");
    loadingText = (TextView) findViewById(R.id.textView_doing_detail_loading);
    atTimeTitleText = (TextView) findViewById(R.id.textView_doing_detail_same_time_title);
    countText = (TextView) findViewById(R.id.textView_doing_detail_count);
    SearchData();
  }

  private void SearchData() {
    CountCallback countCallback = new CountCallback() {
      @Override
      public void done(int count, AVException e) {
        if (e == null) {
          showDetail(count);
        } else {
          loadingText
              .setText(getString(R.string.doing_list_error_loading));
        }
        mHandler.obtainMessage(1).sendToTarget();
      }
    };
    String doingObjectId = this.doingObjectId;
    AVService.countDoing(doingObjectId, countCallback);
  }

  private void showDetail(int count) {
    findViewById(R.id.view_doing_detail_1).setVisibility(View.VISIBLE);
    findViewById(R.id.view_doing_detail_2).setVisibility(View.VISIBLE);
    findViewById(R.id.textView_doing_detail_get_chievement).setVisibility(
        View.VISIBLE);
    loadingText.setVisibility(View.INVISIBLE);
    atTimeTitleText
        .setText(getString(R.string.doing_detail_same_time_title)
            .replace("{0}", doingObjectTitle));
    countText.setText(count + getString(R.string.doing_detail_person));
  }

  private void uploadData(String doingObjectId) {
    String userId = getUserId();
    AVService.createDoing(userId, doingObjectId);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @SuppressLint("HandlerLeak")
  private Handler mHandler = new Handler() {
    public void handleMessage(Message msg) {
      if (1 == msg.what) {
        uploadData(doingObjectId);
        AVService.getAchievement(getUserId());
      }
    }
  };
}
