package com.example.rijunath.friendforever;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RIJU NATH on 5/12/2017.
 */
public class Nevigation extends Fragment {
    TextView tv1, tv2;
    String url = "", Message = "", Sucess = "";
    ArrayList<UserDetail> arrayList = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.nevigation, container, false);
        tv1 = (TextView) v.findViewById(R.id.tv_logout);
        tv2 = (TextView) v.findViewById(R.id.tv_showdetails);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Email:-" + UserPreference.getUserEmail(getActivity()), Toast.LENGTH_LONG).show();
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(getActivity(),Logout.class);
                startActivity(i);*/
                url = "http://220.225.80.177/friendcircle/friendcircle.asmx/UserLogout?email=" + UserPreference.getUserEmail(getActivity());
                if (Util.isConnected(getActivity())) {
                    new Logout().execute("");
                } else {
                    Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;

    }

    public class Logout extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            JSONParser jp = new JSONParser();
            JSONObject jobj = jp.getJsonFromURL(url);
            try {
                Message = jobj.getString("Message");
                Sucess = jobj.getString("Sucess");
                JSONObject obj = jobj.getJSONObject("UserDetails");
                UserPreference.setUserEmail(getActivity(), obj.getString("Email"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (Sucess.equalsIgnoreCase("1")) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                Toast.makeText(getActivity(), "" + Message, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "" + Message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
