package com.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidproject.R;

import java.io.File;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText editText_accountNumber;
    private EditText editText_password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        editText_accountNumber = (EditText) findViewById(R.id.account_number);
        editText_password = (EditText) findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        editText_accountNumber.addTextChangedListener(new JumpTextWatcher());
        editText_password.addTextChangedListener(new EditLogin());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                需要传回后台验证的账号密码
//                Log.d("login",editText_accountNumber.getText().toString());
//                Log.d("login",editText_password.getText().toString());
                String accountNumber = editText_accountNumber.getText().toString();
                String password = editText_accountNumber.getText().toString();
                //后应补写跳转代码
            }
        });
    }

    private class JumpTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String str=s.toString();
            if (str.indexOf("\r")>=0 || str.indexOf("\n")>=0){//发现输入回车符或换行符
                editText_accountNumber.setText(str.replace("\r","").replace("\n",""));//去掉回车符和换行符
                editText_password.requestFocus();//让editText2获取焦点
                editText_password.setSelection(editText_password.getText().length());//若editText2有内容就将光标移动到文本末尾
            }

        }
    }

    private class EditLogin implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String str=s.toString();
            if (str.indexOf("\r")>=0 || str.indexOf("\n")>=0){//发现输入回车符或换行符
                editText_accountNumber.setText(str.replace("\r","").replace("\n",""));//去掉回车符和换行符
//                需要传回后台验证的账号密码
//                Log.d("login",editText_accountNumber.getText().toString());
//                Log.d("login",editText_password.getText().toString());
                String accountNumber = editText_accountNumber.getText().toString();
                String password = editText_accountNumber.getText().toString();
                //后应补写跳转代码
            }

        }
    }

}
