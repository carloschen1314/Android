package com.androidproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.androidproject.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editText_accountNumber;
    private EditText editText_password;
    private Button login;
    private RadioButton radioButton;
    private TextView txt_register;
    private boolean rdb_choose;

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
                String accountNumber = editText_accountNumber.getText().toString();
                String password = editText_accountNumber.getText().toString();

                if (radioButton.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                    editor.putString("id", String.valueOf(editText_accountNumber.getText()));
                    editor.putString("password", String.valueOf(editText_password.getText()));
                    editor.putBoolean("remember", radioButton.isChecked());
                    editor.apply();
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("id",accountNumber);
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
