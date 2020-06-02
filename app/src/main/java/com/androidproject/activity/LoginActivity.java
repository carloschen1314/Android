package com.androidproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidproject.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editText_accountNumber;
    private EditText editText_password;
    private Button login;
    private TextView txt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText_accountNumber = (EditText) findViewById(R.id.edt_loginID);
        editText_password = (EditText) findViewById(R.id.edt_loginPassword);
        login = (Button) findViewById(R.id.btn_login);
        txt_register=(TextView)findViewById(R.id.txt_register);

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
                String accountNumber = editText_accountNumber.getText().toString();
                String password = editText_accountNumber.getText().toString();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
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
