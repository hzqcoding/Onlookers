package com.example.hzq.onlookers.Notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.hzq.onlookers.R;

import java.util.ArrayList;
import java.util.List;


public class NotesActivity extends AppCompatActivity {

    private List<Notes> notesList = new ArrayList<Notes>();//创建自定义好的适配器Artist
    private MyDatabaseHelper myDb;
    private Button btnadd;
    private ListView lv_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_notes);
        myDb = new MyDatabaseHelper(this);//实例化自己创建的数据库类
        //获取控件
        btnadd = (Button) findViewById(R.id.btn_add);
        lv_note = (ListView) findViewById(R.id.lv_note);
        //初始化函数
        init();
    }
    public void init(){
        List<Notes> notesList = new ArrayList<>();//创建Values类型的list保存数据库中的数据
        SQLiteDatabase db = myDb.getReadableDatabase();//获得可读SQLiteDatabase对象

        //查询数据库中的数据
        Cursor cursor = db.query(MyDatabaseHelper.TABLE,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            Notes notes;
            while (!cursor.isAfterLast()){
                //实例化values对象
                notes = new Notes();
                //把数据库中的一个表中的数据赋值给notes
                notes.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.ID))));
                notes.setTitle(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.TITLE)));
                notes.setContent(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.CONTENT)));
                notes.setTime(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.TIME)));
                //将notes对象存入list对象数组中
                notesList.add(notes);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        //设置list组件adapter
        final NotesAdapter myBaseAdapter = new NotesAdapter(this,R.layout.notes_item,notesList);
        lv_note.setAdapter(myBaseAdapter);

        //+按钮
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotesActivity.this.finish();
                Intent a = new Intent(NotesActivity.this, WriteDownActivity.class);
                startActivity(a);
            }
        });

        //单击查询
        lv_note.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent b = new Intent(NotesActivity.this,BriefActivity.class);
                Notes values = (Notes) lv_note.getItemAtPosition(position);
                //传递数据
                b.putExtra(MyDatabaseHelper.TITLE,values.getTitle().trim());
                b.putExtra(MyDatabaseHelper.CONTENT,values.getContent().trim());
                b.putExtra(MyDatabaseHelper.TIME,values.getTime().trim());
                b.putExtra(MyDatabaseHelper.ID,values.getId().toString().trim());
                NotesActivity.this.finish();//销毁界面
                startActivity(b);
            }
        });


        //长按删除
        lv_note.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Notes values = (Notes) lv_note.getItemAtPosition(position);
                //弹出提示框
                new AlertDialog.Builder(NotesActivity.this)
                        .setTitle("提示框")
                        .setMessage("是否删除?")
                        .setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SQLiteDatabase db = myDb.getWritableDatabase();
                                        //删除数据库中相应数据
                                        db.delete(MyDatabaseHelper.TABLE,MyDatabaseHelper.ID+"=?",new String[]{String.valueOf(values.getId())});
                                        db.close();
                                        myBaseAdapter.removeItem(position);
                                        lv_note.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                myBaseAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
                                })
                        .setNegativeButton("no",null).show();//不作处理
                return true;
            }
        });
    }
}