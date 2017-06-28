package com.example.rijunath.friendforever;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RIJU NATH on 4/27/2017.
 */
public class Request extends Fragment {
    ListView lv1;
    String url = "", urlaccept = "", urldelet = "";
    ArrayList<UserDetail> arrayList = null;
    TextView btn2;
    ImageView cross;
    String Success = "", Message = "";


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab2, container, false);
        lv1 = (ListView) v.findViewById(R.id.lv1);
        btn2 = (TextView) v.findViewById(R.id.btn2);
        url = "http://220.225.80.177/friendcircle/friendcircle.asmx/ShowFriendRequest?email=" + UserPreference.getUserEmail(getActivity());
//        Dataexecute da = new Dataexecute();
//        da.execute("DataExecute");
        new Dataexecute().execute();
        return v;


    }

    public class Dataexecute extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            JSONParser jp = new JSONParser();
            JSONObject jobj = jp.getJsonFromURL(url);
            try {
                JSONArray jarr = jobj.getJSONArray("frndrqstList");
                arrayList = new ArrayList<UserDetail>();
                for (int i = 0; i < jarr.length(); i++) {
                    JSONObject jo = jarr.getJSONObject(i);
                    String Fname = jo.getString("Fname");
                    String Lname = jo.getString("Lname");
                    String requestid = jo.getString("RequestId");
                    // String Online_Offline_Flag=jo.getString("Online_Offline_Flag");
                    UserDetail obj = new UserDetail();
                    obj.setFname(Fname);
                    obj.setLname(Lname);
                    obj.setRequestId(requestid);
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
                lv1.setAdapter(my);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    public class Myadp extends BaseAdapter {

        @Override
        public int getCount() {
            //if(arrayList!=null)
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
            View v = inf.inflate(R.layout.row2, parent, false);
            TextView tv2 = (TextView) v.findViewById(R.id.tv2);
            TextView tv3 = (TextView) v.findViewById(R.id.tv3);
            // ImageView im=(ImageView)v.findViewById(R.id.im);
            TextView btn = (TextView) v.findViewById(R.id.btn2);
            ImageView btnn = (ImageView) v.findViewById(R.id.cross);
            UserDetail obje = new UserDetail();
            obje = arrayList.get(position);
            tv2.setText(obje.getFname());
            tv3.setText(obje.getLname());
            final String rqid = obje.getRequestId();
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    urlaccept = "http://220.225.80.177/friendcircle/friendcircle.asmx/AcceptFriendRequest?RequestId=" + rqid;
                    new AcceptFriendRequest().execute("");
                    //Toast.makeText(getActivity(),rqid,Toast.LENGTH_LONG).show();
                }
            });
            btnn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    urldelet = "http://220.225.80.177/friendcircle/friendcircle.asmx/DeletefriendRequest?fid=" + rqid;
                    new DeletFriendRequest().execute("");
                    /*Toast.makeText(getActivity(),rqid,Toast.LENGTH_LONG).show();*/
                }
            });
            return v;
        }
    }

    public class AcceptFriendRequest extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... params) {
            JSONParser jp = new JSONParser();
            JSONObject jobj = jp.getJsonFromURL(urlaccept);
            try {
                Success = jobj.getString("Sucess");
                Message = jobj.getString("Message");
//

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (Success.equalsIgnoreCase("1")) {
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
                new Dataexecute().execute();
            } else {
                Toast.makeText(getActivity(), "Fail", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class DeletFriendRequest extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... params) {
            JSONParser jp = new JSONParser();
            JSONObject jobj = jp.getJsonFromURL(urldelet);
            try {
                Success = jobj.getString("Sucess");
                Message = jobj.getString("Message");
//

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (Success.equalsIgnoreCase("1")) {
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
                new Dataexecute().execute();
            } else {
                Toast.makeText(getActivity(), "Fail", Toast.LENGTH_LONG).show();
            }
        }
    }

}
