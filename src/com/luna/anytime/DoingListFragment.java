package com.luna.anytime;

import java.util.ArrayList;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.luna.anytime.adapter.AnytimeExpandableListAdapter;
import com.luna.anytime.data.DoingListData;

import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class DoingListFragment extends Fragment {

	ExpandableListView expandableListView;
	TextView messageText;
	AnytimeExpandableListAdapter adapter;
	Activity activity;
	// 列表数据
	private ArrayList<DoingListData> mDoingListData;

	public DoingListFragment(Activity _activity) {
		activity = _activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main_doing_list,
				container, false);
		mDoingListData = new ArrayList<DoingListData>();
		expandableListView = (ExpandableListView) rootView
				.findViewById(R.id.expandableListView_doing_list);
		adapter = new AnytimeExpandableListAdapter(activity);
		expandableListView.setAdapter(adapter);
		expandableListView.setOnChildClickListener(listener);
		messageText = (TextView) rootView
				.findViewById(R.id.textView_loading_wait);
		LoadingData();
		return rootView;
	}

	private OnChildClickListener listener = new OnChildClickListener() {
		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			// TODO Auto-generated method stub
			Intent intentDoingListDetail = new Intent(activity,
					DoingDetailActivity.class);
			intentDoingListDetail.putExtra("childobj",
					mDoingListData.get(groupPosition).doingListData
							.get(childPosition).objectId);
			intentDoingListDetail.putExtra(
					"childtitle",
					mDoingListData.get(groupPosition).doingListData.get(
							childPosition).toString());
			startActivity(intentDoingListDetail);
			return true;
		}
	};

	private void LoadingData() {
    FindCallback<AVObject> findCallback = new FindCallback<AVObject>() {
      @Override
      public void done(List<AVObject> avObjects, AVException e) {
        if (e == null) {
          for (AVObject avo : avObjects) {
            mDoingListData.add(new DoingListData(avo.getObjectId(),
                avo.getString("GroupName")));
          }
          mHandler.obtainMessage(1).sendToTarget();
          adapter.notifyDataSetChanged();
        } else {
          ERROR();
        }
      }
    };
    AVService.findDoingListGroup(findCallback);
	}

  private void ERROR() {
		messageText.setText(getString(R.string.doing_list_error_loading));
		expandableListView.setVisibility(View.INVISIBLE);
	}

	private void SetChildrenList(final String groupObjectId) {
    FindCallback<AVObject> findCallback=new FindCallback<AVObject>() {
      @Override
      public void done(List<AVObject> avObjects, AVException e) {
        if (e == null) {
          ArrayList<DoingListData> childrenList = new ArrayList<DoingListData>();
          for (AVObject avo : avObjects) {
            childrenList.add(new DoingListData(avo.getObjectId(),
                avo.getString("ChildName")));
          }
          for (DoingListData dld : mDoingListData) {
            if (dld.objectId.equals(groupObjectId)) {
              dld.doingListData = childrenList;
            }
          }
          adapter.setGroups(mDoingListData);
          expandableListView.setVisibility(View.VISIBLE);
          messageText.setVisibility(View.INVISIBLE);
          adapter.notifyDataSetChanged();
        } else {
          ERROR();
        }
      }
    };
    AVService.findChildrenList(groupObjectId, findCallback);
	}

  @SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (1 == msg.what) {
				for (DoingListData dld : mDoingListData) {
					SetChildrenList(dld.objectId);
				}
			}
		}
	};
}
