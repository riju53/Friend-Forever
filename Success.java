package com.example.rijunath.friendforever;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Created by RIJU NATH on 4/14/2017.
 */
public class Success extends AppCompatActivity {
    Button btn;
    TextView tv, tv1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success);
        /*btn = (Button) findViewById(R.id.btn);
        tv=(TextView)findViewById(R.id.tv);*/
        //open this after cheaking
        tv1=(TextView)findViewById(R.id.tv_success);

        final String s=getIntent().getExtras().getString("Name");
        //getIntent().getExtras().getString("Name");
        tv1.setText(s);
        /*Thread Mythread= new Thread(){

            {
                try {
                    sleep(5000);
                    Intent intent3 = new Intent(Success.this, First.class);
                    startActivity(intent3);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };*/
        Thread wait = new Thread()
        {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent3 = new Intent(Success.this, MainActivity.class);
                startActivity(intent3);

            }



            };
        wait.start();


    // Mythread.start();
    }
}

