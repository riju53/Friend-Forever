package com.example.rijunath.friendforever;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.builder.DiffResult;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText et1, et2;
    TextView login, sign_up, forgot, share;
    String url = "";
    String com = "Hi Use Friend Forever App";
    String Message;
    String Sucess;
    View ViewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        LayoutInflater layoutInflater=getLayoutInflater();
        ViewLayout=layoutInflater.inflate(R.layout.custom_toast,(ViewGroup)findViewById(R.id.custom_layout));

        if (UserPreference.isUserAlreadyLogin(this) && !TextUtils.isEmpty(UserPreference.getUserEmail(this))) {
            startActivity(new Intent(this, DashboardActivity.class));
            overridePendingTransition(0, 0);
            finish();
            return;
        }


        et1 = (EditText) findViewById(R.id.signIn_edt_email);
        et2 = (EditText) findViewById(R.id.signIn_edt_password);
        login = (TextView) findViewById(R.id.login);
        sign_up = (TextView) findViewById(R.id.sign_up);
        forgot = (TextView) findViewById(R.id.forgot);
        /*share=(TextView)findViewById(R.id.share);*/
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Signup.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et1.getText().toString();
                String pwd = et2.getText().toString();
                if (email.equals("")) {
                    et1.setError("Enter Mail Id");
                    return;
                }
                if (pwd.equals("")) {
                    et2.setError("Enter Password");
                    return;
                }

                url = "http://220.225.80.177/friendcircle/friendcircle.asmx/UserLogin?email=" + email + "&pwd=" + pwd;
                if (Util.isConnected(MainActivity.this)) {
                    new Log().execute("");
                } else {
                    /*Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_LONG).show();*/
                    Toast toast1=Toast.makeText(MainActivity.this,"Toast:Gravity.TOP",Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER,0,0);
                    toast1.setView(ViewLayout);
                    toast1.show();
                }


            }
        });

    }

    public void btnClick(View v) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        /*uriStrinh="http://www.google.com";*/

        i.putExtra(Intent.EXTRA_TEXT, "Good Morning" + com);
        i.setPackage("com.facebook.katana");
        startActivity(Intent.createChooser(i, "Dialog title text"));

        /*startActivity(i);*/
    }

    public class Log extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            JSONParser jp = new JSONParser();
            JSONObject jobj = jp.getJsonFromURL(url);
            try {
                Message = jobj.getString("Message");
                Sucess = jobj.getString("Sucess");
                JSONObject obj = jobj.getJSONObject("UserDetails");
                UserPreference.setUserEmail(MainActivity.this, obj.getString("Email"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (Sucess.equalsIgnoreCase("1")) {
                Toast.makeText(MainActivity.this, "" + Message, Toast.LENGTH_SHORT).show();
                UserPreference.setUserLogin(MainActivity.this, true);
                startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                overridePendingTransition(0, 0);
                finish();

            } else {
                Toast.makeText(MainActivity.this, "" + Message, Toast.LENGTH_SHORT).show();
            }
        }
    }


}
