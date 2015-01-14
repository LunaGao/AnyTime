package com.luna.anytime;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SignUpCallback;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AnyTimeActivity {

	Button registerButton;
	EditText userName;
	EditText userEmail;
	EditText userPassword;
	EditText userPasswordAgain;
	private ProgressDialog progressDialog;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		this.getActionBar().setDisplayHomeAsUpEnabled(true);

		registerButton = (Button) findViewById(R.id.button_i_need_register);
		userName = (EditText) findViewById(R.id.editText_register_userName);
		userEmail = (EditText) findViewById(R.id.editText_register_email);
		userPassword = (EditText) findViewById(R.id.editText_register_userPassword);
		userPasswordAgain = (EditText) findViewById(R.id.editText_register_userPassword_again);

		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (userPassword.getText().toString()
						.equals(userPasswordAgain.getText().toString())) {
					if (!userPassword.getText().toString().isEmpty()) {
						if (!userName.getText().toString().isEmpty()) {
							if (!userEmail.getText().toString().isEmpty()) {
								progressDialogShow();
								register();
							} else {
								showError(activity
										.getString(R.string.error_register_email_address_null));
							}
						} else {
							showError(activity
									.getString(R.string.error_register_user_name_null));
						}
					} else {
						showError(activity
								.getString(R.string.error_register_password_null));
					}
				} else {
					showError(activity
							.getString(R.string.error_register_password_not_equals));
				}
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			Intent LoginIntent = new Intent(this, LoginActivity.class);
			startActivity(LoginIntent);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void register() {
    SignUpCallback signUpCallback = new SignUpCallback() {
      public void done(AVException e) {
        progressDialogDismiss();
        if (e == null) {
          showRegisterSuccess();
          Intent mainIntent = new Intent(activity, MainActivity.class);
          startActivity(mainIntent);
          activity.finish();
        } else {
          switch (e.getCode()) {
            case 202:
              showError(activity
                  .getString(R.string.error_register_user_name_repeat));
              break;
            case 203:
              showError(activity
                  .getString(R.string.error_register_email_repeat));
              break;
            default:
              showError(activity
                  .getString(R.string.network_error));
              break;
          }
        }
      }
    };
    String username = userName.getText().toString();
    String password = userPassword.getText().toString();
    String email = userEmail.getText().toString();

    AVService.signUp(username, password, email, signUpCallback);
	}

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

	private void showRegisterSuccess() {
		new AlertDialog.Builder(activity)
				.setTitle(
						activity.getResources().getString(
								R.string.dialog_message_title))
				.setMessage(
						activity.getResources().getString(
								R.string.success_register_success))
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();
	}
}
