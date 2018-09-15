package com.example.wegz.demo1;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener{
    private String addrs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Button button2 = (Button) findViewById(R.id.buttonfj);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
        Button button3 = (Button) findViewById(R.id.buttonwzxx);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Button button4 = (Button) findViewById(R.id.buttonjjzs);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, FourthActivity.class);
                startActivity(intent);
            }
        });
        Button button5 = (Button) findViewById(R.id.buttonyhxx);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (FirstActivity.this,Login.class);
                startActivity(intent);
            }
        });

        SharedPreferences mySharedPreferences= getSharedPreferences("test",
                MODE_MULTI_PROCESS);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        addrs = mySharedPreferences.getString("addrs","读取失败");

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                Toast.makeText(this,"you clicked 用户名",Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this,"you clicked 注销",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FirstActivity.this, Login.class);
                startActivity(intent);
                break;
            case R.id.heart_item:
                Toast.makeText(this,"you clicked 心肺复苏",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(FirstActivity.this, FifthActivity.class);
                startActivity(intent1);
                break;
            default:
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonyjjy:
                AlertDialog.Builder dialog = new AlertDialog.Builder(FirstActivity.this);
                dialog.setTitle("请确认是否拨打120急救电话");
                dialog.setMessage(addrs);
                dialog.setCancelable(false);
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        final String username = intent.getStringExtra("extra_username");
                        HelpRequest(username);
                        Intent intent1 = new Intent(FirstActivity.this, SecondActivity.class);
                        startActivity(intent1);
                        Intent intentPhone = new Intent();
                        intentPhone.setAction(Intent.ACTION_DIAL);
                        intentPhone.setData(Uri.parse("tel:120"));
                        startActivity(intentPhone);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })    ;
                dialog.show();
                break;
            default:
                break;

        }
    }
    public void HelpRequest(final String username){
        String url = "http://119.23.55.74:8080/MyFirstWebAPP/HelpServlet";    //注①
        String tag = "Help";    //注②

        //取得请求队列
        RequestQueue requestQueue3 = Volley.newRequestQueue(getApplicationContext());

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue3.cancelAll(tag);

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");  //注③
                            String result = jsonObject.getString("Help");  //注④
                            if (result.equals("success")) {  //注⑤
                                Toast.makeText(FirstActivity.this,"求救成功!",Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(FirstActivity.this,"求救失败!",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(FirstActivity.this,"No network connection!",Toast.LENGTH_SHORT).show();
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FirstActivity.this,"Try again!",Toast.LENGTH_SHORT).show();
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Username",username);  //注⑥
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue3.add(request);
    }

    /**
     * 获取广播数据
     *
     * @author jiqinlin
     *
     */

}

