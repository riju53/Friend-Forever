package com.example.rijunath.friendforever;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.example.rijunath.friendforever.fragments.FragmentAddFriend;


/**
 * Created by RIJU NATH on 4/24/2017.
 */
public class Wel extends AppCompatActivity {
    TabLayout tvl;
    ViewPager vwp;
    /*ListView lv1;*/
    DrawerLayout drawer;
    ImageView iv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wel);

        tvl=(TabLayout)findViewById(R.id.tvl);
        drawer=(DrawerLayout)findViewById(R.id.drawer);
        vwp=(ViewPager)findViewById(R.id.vwp);
        /*lv1=(ListView)findViewById(R.id.lv1);*/
        iv=(ImageView)findViewById(R.id.iv);



        FragmentManager fm=getSupportFragmentManager();
        Viewadp adp=new Viewadp(fm);
        vwp.setAdapter(adp);
        tvl.setupWithViewPager(vwp);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });


    }

    public class Viewadp extends FragmentPagerAdapter {

        public Viewadp(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
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

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Chat";
                case 1:
                    return "Request";
                case 2:
                    return "Add Friend";


                default:
                    return null;

            }


        }

        @Override
        public int getCount() {
            return 3;
        }
    }


}
