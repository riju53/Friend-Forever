package com.example.rijunath.friendforever;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.DiffResult;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private static final String baseUrl = "http://220.225.80.177/friendcircle/friendcircle.asmx/";
    private long SyncTime = 2 * 1000;
    private ImageView btnSend;
    private ChatHistoryAdapter chatHistoryAdapter;
    private String chatHistoryUrl;
    private String chatSendUrl;
    private Context context;
    private EditText edtChat;
    private ListView listChat;
    private String senderEmail;
    private Toolbar toolbar;
    private String userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        this.context = this;
        initUIComponent();
        loadBundleData();
        loadChat();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.handleChatLoad.removeCallbacks(this.chatLoadSync);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.handleChatLoad.removeCallbacks(this.chatLoadSync);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Init UI Component
     */
    private void initUIComponent() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edtChat = (EditText) findViewById(R.id.chat_text);
        listChat = (ListView) findViewById(R.id.listview);
        btnSend = (ImageView) findViewById(R.id.btn);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendChat();
            }
        });
        chatHistoryAdapter = new ChatHistoryAdapter(context, new ArrayList<Add_Setget>());
        listChat.setAdapter(this.chatHistoryAdapter);
    }

    private void loadBundleData() {
        userEmail = UserPreference.getUserEmail(context);
        senderEmail = getIntent().getExtras().getString("frnd");
        getSupportActionBar().setTitle(senderEmail);
        chatHistoryUrl = "http://220.225.80.177/friendcircle/friendcircle.asmx/ShowChatHistory?loginEmail=" + userEmail + "&friendEmail=" + senderEmail;
        chatSendUrl = "http://220.225.80.177/friendcircle/friendcircle.asmx/SendMessage?toEmail=" + senderEmail + "&fromEmail=" + userEmail + "&msgbody=";
    }

    private void sendChat() {
        if (TextUtils.isEmpty(edtChat.getText().toString().trim())) {
            return;
        }
        if (Util.isConnected(context)) {
            String msgEmoji = edtChat.getText().toString().trim().replaceAll(StringUtils.SPACE, "%20");
            StringEscapeUtils.unescapeJava(msgEmoji);
            String conCatUrl =  chatSendUrl + msgEmoji;
            hideKeyBoard(edtChat);
            edtChat.setText("");
            sendMessageTask(conCatUrl);
        } else {
            Toast.makeText(this, "PLEASE CONNECT TO THE INTERNET ", Toast.LENGTH_SHORT).show();
        }
    }

    private void hideKeyBoard(View v) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

    private void sendMessageTask(final String chatSendUrl) {
        AsyncTask<String, String, String> sendTask = new AsyncTask<String, String, String>() {
            private boolean postSuccess;

            @Override
            protected String doInBackground(String... strings) {
                try {
                    JSONObject jobj = new JSONParser().getJsonFromURL(chatSendUrl);
                    if (jobj.has("Sucess") && jobj.getString("Sucess").equalsIgnoreCase("1")) {
                        postSuccess = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (postSuccess) {
                    startChatSync();
                }
            }
        };

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            sendTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    private void startChatSync() {
        handleChatLoad.removeCallbacks(chatLoadSync);
        handleChatLoad.postDelayed(chatLoadSync, SyncTime);
    }

    private Handler handleChatLoad = new Handler();
    private Runnable chatLoadSync = new Runnable() {
        @Override
        public void run() {
            loadChat();
        }
    };

    private void loadChat() {
        AsyncTask<String, String, String> historyLoadTask = new AsyncTask<String, String, String>() {
            private ArrayList<Add_Setget> arrayList;

            @Override
            protected String doInBackground(String... strings) {
                try {
                    JSONObject jobj = new JSONParser().getJsonFromURL(chatHistoryUrl);
                    if (jobj.has("Sucess") && jobj.getString("Sucess").equalsIgnoreCase("1")) {
                        JSONArray jarr = jobj.getJSONArray("MessageList");
                        if (jarr != null && jarr.length() >= 1) {
                            arrayList = new ArrayList();
                            for (int i = 0; i < jarr.length(); i++) {
                                JSONObject obj = jarr.getJSONObject(i);
                                Add_Setget as = new Add_Setget();
                                as.setMsgId(obj.getString("MsgId"));
                                as.setTime(obj.getString("ToEmail"));
                                as.setFromEmail(obj.getString("FromEmail"));
                                as.setMsgBody(obj.getString("MsgBody"));
                                as.setAttachmentPath(obj.getString("AttachmentPath"));
                                as.setReadUnreadFlag(obj.getString("ReadUnreadFlag"));
                                as.setTime(obj.getString("Time"));
                                as.setIncomingOutGoing(obj.getString("IncomingOutGoing"));
                                arrayList.add(as);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (arrayList != null && arrayList.size() >= 1) {
                    chatHistoryAdapter.refreshChat(arrayList);
                }
                startChatSync();
            }
        };

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            historyLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    public class ChatHistoryAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private ArrayList<Add_Setget> listChat;

        public ChatHistoryAdapter(Context context, ArrayList<Add_Setget> listChat) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
            this.listChat = listChat;
        }

        private void refreshChat(ArrayList<Add_Setget> rfListChat) {
            if (rfListChat.size() > listChat.size()) {
                this.listChat.clear();
                this.listChat.addAll(rfListChat);
                notifyDataSetChanged();
            }
        }

        public int getCount() {
            return (listChat == null) ? 0 : this.listChat.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = this.inflater.inflate(R.layout.chat_body, null);
                viewHolder.chatReceiver = (TextView) convertView.findViewById(R.id.chat_receiver);
                viewHolder.chatSender = (TextView) convertView.findViewById(R.id.chat_sender);
                viewHolder.layoutSender = (RelativeLayout) convertView.findViewById(R.id.layout_sender);
                viewHolder.layoutReceiver = (RelativeLayout) convertView.findViewById(R.id.layout_receiver);
                /*viewHolder.layoutReceiverTime=(TextView)convertView.findViewById(R.id.receiver_time);
                viewHolder.layoutSenderTime=(TextView)convertView.findViewById(R.id.sender_time);*/
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Add_Setget setget = listChat.get(position);
            if (setget.getFromEmail() != null && setget.getFromEmail().equalsIgnoreCase(userEmail)) {
                viewHolder.layoutSender.setVisibility(View.VISIBLE);
                viewHolder.layoutReceiver.setVisibility(View.GONE);
                viewHolder.chatSender.setText(setget.getMsgBody());
                /*viewHolder.layoutSenderTime.setText(setget.getTime());*/

            } else {
                viewHolder.layoutSender.setVisibility(View.GONE);
                viewHolder.layoutReceiver.setVisibility(View.VISIBLE);
                viewHolder.chatReceiver.setText(setget.getMsgBody());
                /*viewHolder.layoutReceiverTime.setText(setget.getTime());*/
            }
            return convertView;
        }


        private class ViewHolder {
            TextView chatReceiver;
            TextView chatSender;
            /*TextView layoutReceiverTime;
            TextView layoutSenderTime;*/
            RelativeLayout layoutReceiver;
            RelativeLayout layoutSender;
        }

    }
}
