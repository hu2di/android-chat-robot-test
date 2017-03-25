package com.blogspot.huyhungdinh.robochat;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Socket mSocket;
    //private String url = "http://10.0.3.2:3000/";
    //private String url = "http://192.168.10.119:3000/";
    private String url = "http://188.166.251.166:3000/";

    {
        try {
            mSocket = IO.socket(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String strJson = (String) args[0];
                    try {
                        JSONObject jsonObject = new JSONObject(strJson);
                        String username = jsonObject.getString("username");
                        String msg = jsonObject.getString("message");
                        Message message = new Message(username, msg, false);
                        //Add the message to view
                        addMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private String username = "Anonymous";

    private ListView lvMessage;
    private ListViewChatAdapter adapter;
    private ArrayList<Message> arrMessage = new ArrayList<Message>();

    private EditText mInputMessageView;
    private ImageButton btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();

        try {
            Bundle data = getIntent().getExtras();
            username = data.getString("username");
            actionBar.setTitle(getResources().getString(R.string.app_name) + " - " + username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mSocket.on("new message", onNewMessage);
        mSocket.connect();

        lvMessage = (ListView) findViewById(R.id.lv_message);
        adapter = new ListViewChatAdapter(MainActivity.this, arrMessage);
        lvMessage.setAdapter(adapter);

        mInputMessageView = (EditText) findViewById(R.id.et_message);
        mInputMessageView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    attemptSend();
                }
                return false;
            }
        });

        btnSend = (ImageButton) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSend();
            }
        });
    }

    private void attemptSend() {
        String msg = mInputMessageView.getText().toString().trim();
        if (!TextUtils.isEmpty(msg)) {
            mInputMessageView.setText("");
            Message message = new Message(username, msg, true);
            String strJson = message.toString();
            addMessage(message);
            mSocket.emit("new message", strJson);
        }
    }

    private void addMessage(Message message) {
        arrMessage.add(message);
        adapter.notifyDataSetChanged();
        lvMessage.setSelection(lvMessage.getAdapter().getCount() - 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off("new message", onNewMessage);
    }
}
