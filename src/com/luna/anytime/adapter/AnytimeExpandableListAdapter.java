package com.luna.anytime.adapter;

import java.util.ArrayList;

import com.luna.anytime.R;
import com.luna.anytime.data.DoingListData;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class AnytimeExpandableListAdapter extends BaseExpandableListAdapter {
	
	Activity ExpandableList;
	// 父列表数据
	private ArrayList<DoingListData> groups;
	
	public AnytimeExpandableListAdapter(Activity _activity) {
		super();
		ExpandableList = _activity;
		groups = new ArrayList<DoingListData>();
	}
	
	public void AddGroupTitle(DoingListData title) {
		groups.add(title);
	}
	
	public void setGroups(ArrayList<DoingListData> theGrouops) {
		groups = theGrouops;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return groups.get(groupPosition).doingListData.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(ExpandableList);  
		convertView = inflater.inflate(R.layout.view_doing_list_child, null);
		TextView textView = (TextView)convertView.findViewById(R.id.textView_group_title);
		textView.setText(getChild(groupPosition, childPosition).toString());
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return groups.get(groupPosition).doingListData.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groups.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(ExpandableList);  
		convertView = inflater.inflate(R.layout.view_doing_list_group, null);
		TextView textView = (TextView)convertView.findViewById(R.id.textView_group_title);
		textView.setText(getGroup(groupPosition).toString());
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}
}
