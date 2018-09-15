package com.example.wegz.demo1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Fifth1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth1);
        Button button1 = (Button) findViewById(R.id.next2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Fifth1Activity.this,Fifth2Activity.class);
                startActivity(intent);
            }
        });
        Button button2 = (Button) findViewById(R.id.back1);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Fifth1Activity.this,FifthActivity.class);
                startActivity(intent);
            }
        });
    }
}
