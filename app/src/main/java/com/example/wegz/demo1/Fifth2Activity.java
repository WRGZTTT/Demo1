package com.example.wegz.demo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Fifth2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth2);
        Button button1 = (Button) findViewById(R.id.back2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Fifth2Activity.this,Fifth1Activity.class);
                startActivity(intent);
            }
        });
        Button button2 = (Button) findViewById(R.id.anya1);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Fifth2Activity.this,Fifth3Activity.class);
                startActivity(intent);
            }
        });
        Button button3 = (Button) findViewById(R.id.huxi1);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Fifth2Activity.this,Fifth4Activity.class);
                startActivity(intent);
            }
        });
    }
}
