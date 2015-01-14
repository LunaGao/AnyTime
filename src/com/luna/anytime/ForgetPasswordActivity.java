package com.luna.anytime;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.RequestPasswordResetCallback;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPasswordActivity extends AnyTimeActivity {

	EditText emailText;
	Button findPasswordButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password);
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		emailText = (EditText) findViewById(R.id.editText_forget_password_email);
		findPasswordButton = (Button) findViewById(R.id.button_find_password);
		findPasswordButton.setOnClickListener(findPasswordListener);
	}

	OnClickListener findPasswordListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
      String email = emailText.getText()
          .toString();
      if (email != null) {
        RequestPasswordResetCallback callback=new RequestPasswordResetCallback() {
          public void done(AVException e) {
            if (e == null) {
              Toast.makeText(activity,
                  R.string.forget_password_send_email,
                  Toast.LENGTH_LONG).show();
              Intent LoginIntent = new Intent(activity,
                  LoginActivity.class);
              startActivity(LoginIntent);
              finish();
            } else {
              showError(activity
                  .getString(R.string.forget_password_email_error));
            }
          }
        };
        AVService.requestPasswordReset(email, callback);
      } else {
				showError(activity.getResources().getString(
						R.string.error_register_email_address_null));
			}
		}
	};

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
}
