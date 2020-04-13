package com.androidproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidproject.R;

public class PersonalInformationActivity extends AppCompatActivity {

    private EditText editText_userName;
    private EditText editText_userNumber;
    private EditText editText_postNumber;
    private Button button_keepInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        editText_userName = (EditText) findViewById(R.id.userName);
        editText_userNumber = (EditText) findViewById(R.id.userNumber);
        editText_postNumber = (EditText) findViewById(R.id.postNumber);
        button_keepInfor = (Button) findViewById(R.id.keepInfor);

        //获取信息

        button_keepInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*上传信息*/
                String userName=editText_userName.getText().toString();

                String message="";
                Toast.makeText(PersonalInformationActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
