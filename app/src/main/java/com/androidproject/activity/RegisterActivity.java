package com.androidproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidproject.R;
import com.androidproject.util.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText account;
    private EditText name;
    private EditText password;
    private EditText password2;
    private Button register;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1)
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        account = (EditText) findViewById(R.id.edtxt_register_id);
        name = (EditText) findViewById(R.id.edtxt_register_name);
        password = (EditText) findViewById(R.id.edtxt_register_password1);
        password2 = (EditText) findViewById(R.id.edtxt_register_password2);
        register = (Button) findViewById(R.id.btn_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals(password2.getText().toString())) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    OkHttpClient client = new OkHttpClient();
                                    Response response;
                                    response= HttpUtils.post("http://39.105.80.171:8080/api/v1/user/adduser?" +
                                            "account=" + account.getText().toString() +
                                            "&name=" + name.getText().toString() +
                                            "&password=" + password.getText().toString(),null);
                                    String jsonData = response.body().string();
                                    Message msg = new Message();
                                    if(jsonData.equals("1"))
                                        msg.what = 1;
                                    else
                                        msg.what = 0;
                                    handler.sendMessage(msg);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                } else
                    Toast.makeText(RegisterActivity.this, "两次密码输入不一致，请重新确认", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
