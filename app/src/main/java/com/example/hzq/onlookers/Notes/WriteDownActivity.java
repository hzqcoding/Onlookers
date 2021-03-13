package com.example.hzq.onlookers.Notes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hzq.onlookers.R;

import java.util.Date;

public class WriteDownActivity extends AppCompatActivity {

    MyDatabaseHelper myDb;
    private Button btnCancel;
    private Button btnSave;
    private EditText titleEditText;
    private EditText contentEditText;
    private TextView timeTextView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_writedown);

        init();
        if(timeTextView.getText().length()==0)
            timeTextView.setText(getTime());
    }

    private void init() {
        myDb = new MyDatabaseHelper(this);//实例化自己创建的数据库类
        //获取控件
        titleEditText = (EditText) findViewById(R.id.et_title);
        contentEditText = (EditText) findViewById(R.id.et_content);
        timeTextView = (TextView) findViewById(R.id.edit_time);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnSave = (Button) findViewById(R.id.btn_save);

        //取消按钮
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                WriteDownActivity.this.finish();//销毁界面
                Intent intent = new Intent(WriteDownActivity.this,NotesActivity.class);
                startActivity(intent);
            }
        });
        //保存按钮
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDb.getWritableDatabase();
                ContentValues values = new ContentValues();
                //获取页面信息
                String title= titleEditText.getText().toString();
                String content=contentEditText.getText().toString();
                String time= timeTextView.getText().toString();

                if("".equals(titleEditText.getText().toString())){//标题为空
                    Toast.makeText(WriteDownActivity.this,"标题不能为空",Toast.LENGTH_LONG).show();
                    return;
                }
                if("".equals(contentEditText.getText().toString())) {//内容为空
                    Toast.makeText(WriteDownActivity.this,"内容不能为空",Toast.LENGTH_LONG).show();
                    return;
                }
                //保存信息到数据库中
                values.put(MyDatabaseHelper.TITLE,title);
                values.put(MyDatabaseHelper.CONTENT,content);
                values.put(MyDatabaseHelper.TIME,time);
                db.insert(MyDatabaseHelper.TABLE,null,values);//添加数据到数据库
                Toast.makeText(WriteDownActivity.this,"保存成功",Toast.LENGTH_LONG).show();
                WriteDownActivity.this.finish();//销毁界面
                Intent intent = new Intent(WriteDownActivity.this,NotesActivity.class);
                startActivity(intent);
                db.close();
            }
        });
    }

    //获取当前时间
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String str = sdf.format(date);
        return str;
    }
}
