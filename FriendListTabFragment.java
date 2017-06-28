package com.example.rijunath.friendforever;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RIJU NATH on 4/27/2017.
 */
public class FriendListTabFragment extends Fragment {
    ImageView iv;
    DrawerLayout drawer;
    ImageView cross;
    String Success="",Message="",urldelet="";

    private ListView friendCircleListView;
    ArrayList<UserDetail> arrayList = null;
    private FriendCircleAdapter friendCircleAdapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_allfriends, container, false);
        initUIComponent(rootView);
        dataLoad();
        return rootView;
    }


    private void initUIComponent(View rootView) {
        friendCircleListView = (ListView) rootView.findViewById(R.id.lv2);
        friendCircleAdapter = new FriendCircleAdapter(getActivity(), new ArrayList<UserDetail>());
        friendCircleListView.setAdapter(friendCircleAdapter);
    }

    private void dataLoad() {
        LoadFriendCircleAsyncTask mLoadFriendCircleAsyncTask = new LoadFriendCircleAsyncTask(getActivity(), new FriendCircleResponseListener() {

            @Override
            public void success(ArrayList<UserDetail> listFrnd) {
                friendCircleAdapter.refreshDataSet(listFrnd);
            }

            @Override
            public void fail() {

            }
        });
        mLoadFriendCircleAsyncTask.execute("DataExecute");
    }
    /*--------------------------------------------------------------------------*/
   /* public class DeletFriendRequest extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... params) {
            JSONParser jp = new JSONParser();
            JSONObject jobj = jp.getJsonFromURL(urldelet);
            try {
                Success=jobj.getString("Sucess");
                Message=jobj.getString("Message");
//

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (Success.equalsIgnoreCase("1")){
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
                *//*new Dataexecute().execute();*//*

            }
            else {
                Toast.makeText(getActivity(),"Fail",Toast.LENGTH_LONG).show();
            }
        }
    }*/
}