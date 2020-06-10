package com.androidproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidproject.R;
import com.androidproject.util.HttpUtils;
import com.androidproject.util.MD5Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editText_accountNumber;
    private EditText editText_password;
    private Button login;
    private RadioButton radioButton;
    private TextView txt_register;
    private boolean rdb_choose;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                if (radioButton.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                    editor.putString("id", String.valueOf(editText_accountNumber.getText()));
                    editor.putString("password", String.valueOf(editText_password.getText()));
                    editor.putBoolean("remember", radioButton.isChecked());
                    editor.apply();
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("id",editText_accountNumber.getText().toString());
                startActivity(intent);
            }
            else{
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText_accountNumber = (EditText) findViewById(R.id.edt_loginID);
        editText_password = (EditText) findViewById(R.id.edt_loginPassword);
        radioButton=(RadioButton)findViewById(R.id.rdb_rememeberPassword);
        txt_register=(TextView)findViewById(R.id.txt_register);
        login = (Button) findViewById(R.id.btn_login);

        //记住账号密码功能
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        String id = pref.getString("id", "");
        String password = pref.getString("password", "");
        boolean remember = pref.getBoolean("remember", true);
        editText_accountNumber.setText(id);
        editText_password.setText(password);
        radioButton.setChecked(remember);
        rdb_choose=radioButton.isChecked();

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rdb_choose){
                    radioButton.setChecked(false);
                    rdb_choose=false;
                }else{
                    radioButton.setChecked(true);
                    rdb_choose=true;
                }
            }
        });

        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*需要传回后台验证的账号密码*/
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient();
                            Response response;
                            MD5Utils md5Utils=new MD5Utils();
                            String password_en= md5Utils.getMD5Code(editText_password.getText().toString());
                            response= HttpUtils.post("http://39.105.80.171:8080/login/user_login?" +
                                    "account=" + editText_accountNumber.getText().toString() +
                                    "&password=" + password_en,null);
                            String jsonData = response.body().string();
                            JSONObject jsonObject = new JSONObject(jsonData);
                            int judge = jsonObject.getInt("code");
                            Message msg = new Message();
                            if(judge==0)
                                msg.what = 0;
                            else
                                msg.what = 1;
                            handler.sendMessage(msg);
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        editText_accountNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (str.indexOf("\r") >= 0 || str.indexOf("\n") >= 0) {//发现输入回车符或换行符
                    editText_accountNumber.setText(str.replace("\r", "").replace("\n", ""));//去掉回车符和换行符
                    editText_password.requestFocus();//让editText2获取焦点
                    editText_password.setSelection(editText_password.getText().length());//若editText2有内容就将光标移动到文本末尾
                }
            }
        });
    }
}
