package com.szzaq.jointdefence;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.szzaq.jointdefence.adapter.ActivityMessageAdapter;
import com.szzaq.jointdefence.sqlite.MessageDBManager;

/**
 * 消息列表
 */
public class MessageActivity extends Activity implements OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        App.getInstance().addActivity(this);
        mMessageDBManager = new MessageDBManager(this);

        ListView message_listview = (ListView) this.findViewById(R.id.message_listview);

        findViewById(R.id.message_back).setOnClickListener(this);
        //message_listview.setAdapter(new ActivityMessageAdapter(null, this));
           message_listview.setAdapter(new ActivityMessageAdapter(mMessageDBManager.query(), this));
        //message_listview.setOnItemClickListener(new Message_Item_OnClick());
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }


    private MessageDBManager mMessageDBManager;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMessageDBManager.closeDB();
    }
}
