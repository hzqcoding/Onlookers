package com.example.hzq.onlookers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hzq.onlookers.Login.LoginActivity;
import com.example.hzq.onlookers.Login.RegisterActivity;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_guide);
        //获取控件
        Button login = (Button) findViewById(R.id.b_login);
        Button registration = (Button) findViewById(R.id.b_registration);
        //登录按钮
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent a=new Intent(GuideActivity.this,LoginActivity.class);//跳转到第二个界面
                startActivity(a);
            }
        });
        //注册按钮
        registration.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent b=new Intent(GuideActivity.this,RegisterActivity.class);//跳转到第二个界面
                startActivity(b);
            }
        });
    }
}
