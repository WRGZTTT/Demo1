package com.example.wegz.demo1;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.service.voice.VoiceInteractionSession;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextLinks;
import android.view.textclassifier.TextSelection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wegz.demo1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginrequest = (Button)findViewById(R.id.button);
        Button registerrequest = (Button)findViewById(R.id.button2);
        final EditText username =(EditText)findViewById(R.id.editText);
        final EditText password =(EditText)findViewById(R.id.editText2);


        loginrequest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(view.getId()==R.id.button){
                    LoginRequest(username.getText().toString(),password.getText().toString());
                }
            }
        });
        registerrequest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(v.getId()==R.id.button2){
                    Intent intent = new Intent(Login.this, Register.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void LoginRequest(final String accountNumber, final String password) {
        SharedPreferences mySharedPreferences= getSharedPreferences("User",
                MODE_MULTI_PROCESS);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("Username",accountNumber);
        editor.commit();
        //请求地址
        String url = "http://119.23.55.74:8080/MyFirstWebAPP/LoginServlet";    //注①
        String tag = "Login";    //注②

        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");  //注③
                            String result = jsonObject.getString("Result");  //注④
                            if (result.equals("success")) {  //注⑤
                                String data = accountNumber;
                                Intent intent = new Intent(Login.this, FirstActivity.class);
                                intent.putExtra("extra_username",data);
                                startActivity(intent);
                                Intent intent1 = new Intent(Login.this,LongRunningService.class);
                                startService(intent1);
                                Toast.makeText(Login.this,"登陆成功!",Toast.LENGTH_SHORT).show();



                            } else {
                                Toast.makeText(Login.this,"登陆失败!",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(Login.this,"没有网络连接!",Toast.LENGTH_SHORT).show();
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this,"Try again!",Toast.LENGTH_SHORT).show();
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AccountNumber", accountNumber);  //注⑥
                params.put("Password", password);
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }


}
