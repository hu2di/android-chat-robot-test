package com.blogspot.huyhungdinh.robochat;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HUNGDH on 12/17/2015.
 */
public class ListViewChatAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    ArrayList<Message> chatMessageList;

    public ListViewChatAdapter(Context context, ArrayList<Message> chatMessageList) {
        this.chatMessageList = chatMessageList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return chatMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return chatMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = chatMessageList.get(position);
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.lv_chat_layout, null);
        }
        TextView tv_message = (TextView) vi.findViewById(R.id.tv_message);
        tv_message.setText(message.getUsername() + ": " + message.getMessage());
        LinearLayout layout = (LinearLayout) vi.findViewById(R.id.ll_chat_bubble);
        LinearLayout parent_layout = (LinearLayout) vi.findViewById(R.id.ll_chat_parent);

        // if message is mine then align to right
        if (message.isMine()) {
            layout.setBackgroundResource(R.drawable.bg_green);
            parent_layout.setGravity(Gravity.RIGHT);
        }
        // If not mine then align to left
        else {
            layout.setBackgroundResource(R.drawable.bg_white);
            parent_layout.setGravity(Gravity.LEFT);
        }
        tv_message.setTextColor(Color.BLACK);
        return vi;
    }

    public void add(Message message) {
        chatMessageList.add(message);
    }
}
