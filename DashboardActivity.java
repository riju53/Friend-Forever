package com.example.rijunath.friendforever;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rijunath.friendforever.fragments.FragmentAddFriend;
import com.example.rijunath.friendforever.fragments.FragmentAllFriends;

import org.apache.commons.lang3.builder.DiffResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
    String url = "", Email = "", Pwd = "", Fname = "", Lname = "", Address = "", Sucess = "", Message = "";


    private Context context;
    private Toolbar toolbar;
    private PagerSlidingTabStrip tab_layout;
    private ViewPager frg_pager;
    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        context = DashboardActivity.this;
        initComponent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*int id = item.getItemId();
        if (id == R.id.action_more) {

            return true;
        }
        return super.onOptionsItemSelected(item);*/
        switch (item.getItemId()) {
            case R.id.action_more:
                showLogoutDialog();
                return true;
            case R.id.action_facebook:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, "Good Morning");
                i.setPackage("com.facebook.katana");
                startActivity(Intent.createChooser(i, "Dialog title text"));
                return true;
            case R.id.action_userlist:
                url = "http://220.225.80.177/friendcircle/friendcircle.asmx/SearchAllUser?email=" + UserPreference.getUserEmail(context);
                new UserList().execute("");
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolBra);
        tab_layout = (PagerSlidingTabStrip) findViewById(R.id.tab_layout);
        frg_pager = (ViewPager) findViewById(R.id.frg_pager);

        setSupportActionBar(toolbar);

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        frg_pager.setAdapter(adapter);
        tab_layout.setIndicatorHeight(6);
        tab_layout.setDrawDivider(false);
        tab_layout.setDrawUnderlineColor(false);
        tab_layout.setIndicatorColorResource(R.color.colorPrimaryDark);
        tab_layout.setTextColorResource(R.color.md_falcon_500_50);
        tab_layout.setViewPager(frg_pager);
    }

    private void showLogoutDialog() {
        AlertDialog.Builder ald = new AlertDialog.Builder(DashboardActivity.this);
        ald.setMessage("Do You Want Logout");
        ald.setTitle("Exit");
        ald.setCancelable(false);
        ald.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserPreference.setUserLogin(context, false);
                UserPreference.setUserEmail(context, "");
                startActivity(new Intent(context, MainActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
        ald.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ald.show();
    }

    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        private final String[] tabName = {"Friend List", "Request", "Add Friend"};

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabName[position];
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return 3;
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FriendListTabFragment();
                case 1:
                    return new Request();
                case 2:
                    return new FragmentAddFriend();
                default:
                    return null;
            }
        }
    }

    /*public class Logout extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            JSONParser jp=new JSONParser();
            JSONObject jobj=jp.getJsonFromURL(url);
            try {
                Message=jobj.getString("Message");
                Sucess=jobj.getString("Sucess");
                JSONObject obj=jobj.getJSONObject("UserDetails");
                AppData.useremail = obj.getString("Email");
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
                Intent i=new Intent(DashboardActivity.this,MainActivity.class);
                startActivity(i);
                Toast.makeText(DashboardActivity.this, "" + Message, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DashboardActivity.this, "" + Message, Toast.LENGTH_SHORT).show();
            }
        }
    }*/
    public class UserList extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            JSONParser jp = new JSONParser();
            JSONObject jobj = jp.getJsonFromURL(url);
            try {
                JSONArray jarr = jobj.getJSONArray("UserList");

                for (int i = 0; i < jarr.length(); i++) {
                    JSONObject obj = jarr.getJSONObject(i);
                    Email = obj.getString("Email");
                    Pwd = obj.getString("Pwd");
                    Fname = obj.getString("Fname");
                    Lname = obj.getString("Lname");
                    Address = obj.getString("Address");
                    Sucess = jobj.getString("Sucess");
                }


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
                Toast.makeText(DashboardActivity.this, "Name:-" + Fname + " " + Lname, Toast.LENGTH_LONG).show();
                /*Toast.makeText(DashboardActivity.this, "" + Message, Toast.LENGTH_SHORT).show();*/
            } else {
                Toast.makeText(DashboardActivity.this, "Fail" + Message, Toast.LENGTH_SHORT).show();

            }
        }
    }
}
