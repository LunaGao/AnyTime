package com.luna.anytime;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AnyTimeActivity {

	Button loginButton;
	Button registerButton;
	Button forgetPasswordButton;
	EditText userNameEditText;
	EditText userPasswordEditText;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		AVAnalytics.trackAppOpened(getIntent());

    AVService.initPushService(this);

		loginButton = (Button) findViewById(R.id.button_login);
		registerButton = (Button) findViewById(R.id.button_register);
		forgetPasswordButton = (Button) findViewById(R.id.button_forget_password);
		userNameEditText = (EditText) findViewById(R.id.editText_userName);
		userPasswordEditText = (EditText) findViewById(R.id.editText_userPassword);

		if (getUserId() != null) {
			Intent mainIntent = new Intent(activity, MainActivity.class);
			startActivity(mainIntent);
			activity.finish();
		}

		loginButton.setOnClickListener(loginListener);
		registerButton.setOnClickListener(registerListener);
		forgetPasswordButton.setOnClickListener(forgetPasswordListener);
	}

  OnClickListener loginListener = new OnClickListener() {

		@SuppressLint("NewApi")
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void onClick(View arg0) {
      String username = userNameEditText.getText().toString();
      if (username.isEmpty()) {
				showUserNameEmptyError();
				return;
			}
			if (password().isEmpty()) {
				showUserPasswordEmptyError();
				return;
			}
			progressDialogShow();
			AVUser.logInInBackground(username,
          password(),
					new LogInCallback() {
						public void done(AVUser user, AVException e) {
							if (user != null) {
								progressDialogDismiss();
								Intent mainIntent = new Intent(activity,
										MainActivity.class);
								startActivity(mainIntent);
								activity.finish();
							} else {
								progressDialogDismiss();
								showLoginError();
							}
						}
					});
		}

    private String password() {
      return userPasswordEditText.getText().toString();
    }
  };

	OnClickListener forgetPasswordListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			Intent forgetPasswordIntent = new Intent(activity, ForgetPasswordActivity.class);
			startActivity(forgetPasswordIntent);
			activity.finish();
		}
	};
	
	OnClickListener registerListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent registerIntent = new Intent(activity, RegisterActivity.class);
			startActivity(registerIntent);
			activity.finish();
		}
	};

	private void progressDialogDismiss() {
		if (progressDialog != null)
			progressDialog.dismiss();
	}

	private void progressDialogShow() {
		progressDialog = ProgressDialog
				.show(activity,
						activity.getResources().getText(
								R.string.dialog_message_title),
						activity.getResources().getText(
								R.string.dialog_text_wait), true, false);
	}

	private void showLoginError() {
		new AlertDialog.Builder(activity)
				.setTitle(
						activity.getResources().getString(
								R.string.dialog_error_title))
				.setMessage(
						activity.getResources().getString(
								R.string.error_login_error))
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();
	}

	private void showUserPasswordEmptyError() {
		new AlertDialog.Builder(activity)
				.setTitle(
						activity.getResources().getString(
								R.string.dialog_error_title))
				.setMessage(
						activity.getResources().getString(
								R.string.error_register_password_null))
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();
	}

	private void showUserNameEmptyError() {
		new AlertDialog.Builder(activity)
				.setTitle(
						activity.getResources().getString(
								R.string.dialog_error_title))
				.setMessage(
						activity.getResources().getString(
								R.string.error_register_user_name_null))
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();
	}
}
