package com.example.wegz.demo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button registerrequest = (Button)findViewById(R.id.button3);
        final EditText username =(EditText)findViewById(R.id.editText3);
        final EditText password =(EditText)findViewById(R.id.editText4);
        registerrequest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(view.getId()==R.id.button3){
                    RegisterRequest(username.getText().toString(), password.getText().toString());
                }
            }
        });
    }

    public void RegisterRequest(final String accountNumber, final String password) {
        //请求地址
        String url = "http://119.23.55.74:8080/MyFirstWebAPP/RegisterServlet";    //注①
        String tag = "Register";    //注②

        //取得请求队列
        RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue2.cancelAll(tag);

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");  //注③
                            String result = jsonObject.getString("Register");  //注④
                            if (result.equals("success")) {  //注⑤
                                Toast.makeText(Register.this,"Register successful!",Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Register.this,"Register failed!",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(Register.this,"No network connection!",Toast.LENGTH_SHORT).show();
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Register.this,"Try again!",Toast.LENGTH_SHORT).show();
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
        requestQueue2.add(request);
    }
}
