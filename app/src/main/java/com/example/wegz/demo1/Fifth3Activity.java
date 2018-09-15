package com.example.wegz.demo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Fifth3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth3);
        Button button1 = (Button) findViewById(R.id.back3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Fifth3Activity.this,Fifth2Activity.class);
                startActivity(intent);
            }
        });
        Button button2 = (Button) findViewById(R.id.huxi2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Fifth3Activity.this,Fifth4Activity.class);
                startActivity(intent);
            }
        });
        Button button3 = (Button) findViewById(R.id.end1);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Fifth3Activity.this,Fifth5Activity.class);
                startActivity(intent);
            }
        });
    }
}
