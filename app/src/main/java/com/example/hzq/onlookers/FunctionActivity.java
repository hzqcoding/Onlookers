package com.example.hzq.onlookers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hzq.onlookers.NewsGson.MainActivity;
import com.example.hzq.onlookers.Notes.NotesActivity;

public class FunctionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_function);
        //获取控件
        Button vnews = (Button) findViewById(R.id.v_news);
        Button wbook = (Button) findViewById(R.id.w_note);
        Button fexit = (Button) findViewById(R.id.f_exit);

        //了解世界按钮
        vnews.setOnClickListener(new View.OnClickListener(){//监听登录点击事件
            public void onClick(View v){
                Intent a=new Intent(FunctionActivity.this,MainActivity.class);//跳转到第二个界面
                startActivity(a);
            }
        });
        //记录见解按钮
        wbook.setOnClickListener(new View.OnClickListener(){//监听登录点击事件
            public void onClick(View v){
                Intent b=new Intent(FunctionActivity.this,NotesActivity.class);//跳转到第二个界面
                startActivity(b);
            }
        });
        //退出程序按钮
        fexit.setOnClickListener(new View.OnClickListener(){//监听登录点击事件
            public void onClick(View v){
                ActivityCollector.finishAll();//销毁所有活动，退出程序
            }
        });
    }
}
