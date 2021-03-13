package com.example.hzq.onlookers.Notes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hzq.onlookers.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BriefActivity extends AppCompatActivity {
    private Button btnSave;
    private Button btnCancel;
    private TextView showTime;
    private EditText showContent;
    private EditText showTitle;
    private Notes notes;
    MyDatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_brief);
        init();
    }

    public void init() {
        myDb = new MyDatabaseHelper(this);//实例化自己创建的数据库类
        //获取控件
        btnCancel = (Button) findViewById(R.id.show_cancel);
        btnSave = (Button) findViewById(R.id.show_save);
        showTime = (TextView) findViewById(R.id.show_time);
        showTitle = (EditText) findViewById(R.id.show_title);
        showContent = (EditText) findViewById(R.id.show_content);

        Intent intent = this.getIntent();//读取上一个页面传递的信息
        if (intent != null) {//内容不为空
            notes = new Notes();
            //获取所有信息
            notes.setTime(intent.getStringExtra(MyDatabaseHelper.TIME));
            notes.setTitle(intent.getStringExtra(MyDatabaseHelper.TITLE));
            notes.setContent(intent.getStringExtra(MyDatabaseHelper.CONTENT));
            notes.setId(Integer.valueOf(intent.getStringExtra(MyDatabaseHelper.ID)));
            //展示所有信息
            showTime.setText(notes.getTime());
            showTitle.setText(notes.getTitle());
            showContent.setText(notes.getContent());
        }

        //保存按钮
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDb.getWritableDatabase();
                ContentValues values = new ContentValues();
                //获取页面信息
                String content = showContent.getText().toString();
                String title = showTitle.getText().toString();
                //保存信息到数据库中
                values.put(MyDatabaseHelper.TIME, getTime());
                values.put(MyDatabaseHelper.TITLE,title);
                values.put(MyDatabaseHelper.CONTENT,content);
                //更新数据库
                db.update(MyDatabaseHelper.TABLE,values,MyDatabaseHelper.ID+"=?",new String[]{notes.getId().toString()});
                Toast.makeText(BriefActivity.this,"修改成功",Toast.LENGTH_LONG).show();
                db.close();
                BriefActivity.this.finish();//销毁界面
                Intent intent = new Intent(BriefActivity.this,NotesActivity.class);//跳转页面
                startActivity(intent);
            }
        });
        //取消按钮
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取页面信息
                final String content = showContent.getText().toString();
                final String title = showTitle.getText().toString();
                //弹出提示框
                new AlertDialog.Builder(BriefActivity.this)
                        .setTitle("提示框")
                        .setMessage("是否保存当前内容?")
                        .setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SQLiteDatabase db = myDb.getWritableDatabase();
                                        ContentValues values = new ContentValues();
                                        //保存信息到数据库中
                                        values.put(MyDatabaseHelper.TIME, getTime());
                                        values.put(MyDatabaseHelper.TITLE,title);
                                        values.put(MyDatabaseHelper.CONTENT,content);
                                        //更新数据库
                                        db.update(MyDatabaseHelper.TABLE,values,MyDatabaseHelper.ID+"=?",new String[]{notes.getId().toString()});
                                        Toast.makeText(BriefActivity.this,"修改成功",Toast.LENGTH_LONG).show();
                                        BriefActivity.this.finish();//销毁界面
                                        Intent intent = new Intent(BriefActivity.this,NotesActivity.class);
                                        startActivity(intent);
                                        db.close();
                                    }
                                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        BriefActivity.this.finish();//销毁界面
                                        Intent intent = new Intent(BriefActivity.this,NotesActivity.class);
                                        startActivity(intent);
                                    }
                                }).show();
            }
        });
    }
    //获取实时时间
    String getTime() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//展示时间格式
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
