package com.example.rijunath.friendforever.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rijunath.friendforever.Add_Setget;
import com.example.rijunath.friendforever.AppData;
import com.example.rijunath.friendforever.JSONParser;
import com.example.rijunath.friendforever.R;
import com.example.rijunath.friendforever.UserPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RIJU NATH on 4/27/2017.
 */
public class FragmentAddFriend extends Fragment {

    ListView lv3;
   /* EditText search;
    TextView se;*/
    String success = "", Message = "";
    String url = "", urlsend = "",urlsearch="";

    String et;
    ArrayList<Add_Setget> arrayList = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab3, container, false);
        lv3 = (ListView) v.findViewById(R.id.lv3);
        /*search=(EditText)v.findViewById(R.id.search);*/
        /*se=(TextView)v.findViewById(R.id.se);
        se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et= search.getText().toString();
                url = "http://220.225.80.177/friendcircle/friendcircle.asmx/SearchAllUser?email="+et;
                Dataexecute ob = new Dataexecute();
                ob.execute();
            }
        });*/
        url = "http://220.225.80.177/friendcircle/friendcircle.asmx/ShowAllUser?email=" + UserPreference.getUserEmail(getActivity());

//        Dataexecute da = new Dataexecute();
//        da.execute("DataExecute");
        new Dataexecute().execute("");

        return v;


    }

    public class Dataexecute extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            JSONParser jp = new JSONParser();
            JSONObject jobj = jp.getJsonFromURL(url);
            try {
                JSONArray jarr = jobj.getJSONArray("UserList");
                arrayList = new ArrayList<Add_Setget>();
                for (int i = 0; i < jarr.length(); i++) {
                    JSONObject jo = jarr.getJSONObject(i);
                    String Fname = jo.getString("Fname");
                    String Lname = jo.getString("Lname");
                    String Email = jo.getString("Email");
                    //String Online_Offline_Flag=jo.getString("Online_Offline_Flag");
                    Add_Setget obj = new Add_Setget();
                    obj.setFname(Fname);
                    obj.setLname(Lname);
                    obj.setEmail(Email);

                    // obj.setOnline_Offline_Flag(Online_Offline_Flag);
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

            Myadp my = new Myadp();
            if (arrayList != null)
                lv3.setAdapter(my);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    public class Myadp extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inf = getActivity().getLayoutInflater();
            View v = inf.inflate(R.layout.row3, parent, false);
            TextView tv2 = (TextView) v.findViewById(R.id.tv2);
            TextView tv3 = (TextView) v.findViewById(R.id.tv3);
            /*TextView tv4 = (TextView) v.findViewById(R.id.tv4);*/
            TextView btn3 = (TextView) v.findViewById(R.id.btn3);
            Add_Setget obje = new Add_Setget();
            obje = arrayList.get(position);
            /*tv4.setText(obje.getEmail());*/
            tv2.setText(obje.getFname());
            tv3.setText(obje.getLname());
            final String pos = obje.getEmail();

            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    urlsend = "http://220.225.80.177/friendcircle/friendcircle.asmx/SendFriendRequest?toEmail=" + pos + "&fromEmail=" + UserPreference.getUserEmail(getActivity());
                    ;
                    new Sendreqst().execute("");
                }
            });

            return v;
        }
    }

    public class Sendreqst extends AsyncTask<String, String, String> {
        String msg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... params) {
            JSONParser jp = new JSONParser();
            JSONObject job = jp.getJsonFromURL(urlsend);
            try {
                msg = job.getString("Message");
                success = job.getString("Sucess");


            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getActivity(), " " + msg, Toast.LENGTH_LONG).show();
            new Dataexecute().execute("");
        }
    }

}
