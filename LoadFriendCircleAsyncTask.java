package com.example.rijunath.friendforever;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RIJU NATH on 5/12/2017.
 */
public class LoadFriendCircleAsyncTask extends AsyncTask<String, String, String> {

    private Context context;
    private ProgressDialog progressDialog;
    private FriendCircleResponseListener listener;
    private ArrayList<UserDetail> arrayList = new ArrayList<UserDetail>();

    public LoadFriendCircleAsyncTask(Context context, FriendCircleResponseListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        JSONParser jp = new JSONParser();
        JSONObject jobj = jp.getJsonFromURL("http://220.225.80.177/friendcircle/friendcircle.asmx/ShowFriendList?email=" + UserPreference.getUserEmail(context));
        try {
            JSONArray jarr = jobj.getJSONArray("UserList");
            for (int i = 0; i < jarr.length(); i++) {
                JSONObject jo = jarr.getJSONObject(i);
                UserDetail obj = new UserDetail();
                obj.setEmail(jo.getString("Email"));//Online_Offline_Flag
                obj.setFname(jo.getString("Fname"));
                obj.setLname(jo.getString("Lname"));

                arrayList.add(obj);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (listener != null) {
            listener.success(arrayList);
        }
    }
}
