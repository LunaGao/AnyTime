package com.luna.anytime.adapter;

import java.util.List;

import com.avos.avoscloud.AVObject;
import com.luna.anytime.R;

import android.content.Context;
import android.database.DataSetObserver;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

public class AnytimeUserResponseListAdapter implements ListAdapter {

	private List<AVObject> mResponseList;
	private LayoutInflater mInflater;
	private Context mContext;

	public AnytimeUserResponseListAdapter(List<AVObject> responseList,
			Context context) {
		this.mResponseList = responseList;
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mResponseList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.view_user_response, null);
		TextView response = (TextView) convertView
				.findViewById(R.id.textView_user_response);
		TextView responseTime = (TextView) convertView
				.findViewById(R.id.textView_user_response_time);

		response.setText(mResponseList.get(position)
				.getString("UserSuggestion"));
		responseTime.setText(DateFormat.format("yyyy-MM-dd HH:mm",
				mResponseList.get(position).getCreatedAt()).toString());

		if (mResponseList.get(position).getBoolean("IsResponseToUser")) {
			response.setTextColor(this.mContext.getResources().getColor(
					R.color.BlueColor));
			responseTime.setTextColor(this.mContext.getResources()
					.getColor(R.color.BlueColor));
		}

		return convertView;
	}

	@Override
	public int getViewTypeCount() {
		return getCount();
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		return mResponseList.isEmpty();
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {

	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

}
