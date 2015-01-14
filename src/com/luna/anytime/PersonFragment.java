package com.luna.anytime;

import com.avos.avoscloud.AVUser;

import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PersonFragment extends Fragment {

	TextView nameTextView;
	TextView registerTimeTextView;

	public PersonFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main_person,
				container, false);

		nameTextView = (TextView) rootView.findViewById(R.id.textView_userName);
		registerTimeTextView = (TextView) rootView
				.findViewById(R.id.textView_register_time);

		AVUser currentUser = AVUser.getCurrentUser();
		nameTextView.setText(currentUser.getUsername());
		String date = DateFormat.format("yyyy-MM-dd HH:mm",
				currentUser.getCreatedAt()).toString();
		registerTimeTextView.setText(getString(R.string.person_register_time)
				.replace("{0}", date));
		return rootView;
	}
}
