package com.blogspot.huyhungdinh.robochat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by HUNGDH on 12/3/2015.
 */
public class LoginActivity extends Activity {

    private EditText etUsername;
    private Button btnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText)findViewById(R.id.et_username);
        btnJoin = (Button)findViewById(R.id.btn_join);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                new Join().execute(username);
            }
        });
    }

    private class Join extends AsyncTask<String, Void, Void> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(LoginActivity.this, "", getResources().getString(R.string.login));
            dialog.setCancelable(true);
        }

        @Override
        protected Void doInBackground(String... params) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            String username = params[0];
            Bundle data = new Bundle();
            data.putString("username", username);
            intent.putExtras(data);
            startActivity(intent);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dialog != null) {
                dialog.dismiss();
            }
            finish();
        }
    }
}
