package com.example.rijunath.friendforever;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by RIJU NATH on 5/20/2017.
 */
public class Signup extends AppCompatActivity {
    private EditText eid, pas, first, last, adrs;
    private TextView signup1;
    private String url = "";
    private String Message = "";
    private String Sucess = "", fname = "", lname = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initUIComponent();
    }

    private void initUIComponent() {
        eid = (EditText) findViewById(R.id.signIn_edt_email);
        pas = (EditText) findViewById(R.id.signIn_edt_password);
        first = (EditText) findViewById(R.id.signIn_edt_fname);
        last = (EditText) findViewById(R.id.signIn_edt_lname);
        adrs = (EditText) findViewById((R.id.signIn_edt_address));
        signup1 = (TextView) findViewById(R.id.signup1);

        signup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpButtonEvent();
            }
        });
    }


    private void signUpButtonEvent() {
        String email = eid.getText().toString();
        String pwd = pas.getText().toString();
        fname = first.getText().toString();
        lname = last.getText().toString();
        String address = adrs.getText().toString();

        if (email.equals("")) {
            eid.setError("Enter a Email Id");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            pas.setError("Enter Password");
            return;
        }
        if (fname.equals("")) {
            first.setError("Enter Your First Name");
            return;
        }
        if (lname.equals("")) {
            last.setError("Enter Your Last Name");
            return;
        }
        if (address.equals("")) {
            adrs.setError("Enter Your Address");
            return;
        }
        url = "http://220.225.80.177//friendcircle/friendcircle.asmx/Registration?email=" + email + "&pwd=" + pwd + "&fname=" + fname + "&lname=" + lname + "&address=" + address;
        new Sign().execute("");
    }

    public class Sign extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            JSONParser jp = new JSONParser();
            JSONObject jobj = jp.getJsonFromURL(url);
            try {
                Message = jobj.getString("Message");
                Sucess = jobj.getString("Sucess");
                JSONObject obj = jobj.getJSONObject("UserDetails");
                UserPreference.setUserEmail(Signup.this, obj.getString("Email"));
                AppData.fname = obj.getString("Fname");
                AppData.lname = obj.getString("lname");
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
                Toast.makeText(Signup.this, "" + Message, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Signup.this, Success.class);
                // String s1=AppData.;
                i.putExtra("Name", fname + " " + lname);
                startActivity(i);
            } else {
                Toast.makeText(Signup.this, "" + Message, Toast.LENGTH_SHORT).show();
            }

            /*Intent intent4=new Intent(Signup.this,Success.class);
            startActivity(intent4);*/
        }
    }

}
