package com.example.hzq.onlookers.Login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hzq.onlookers.R;


public class RegisterActivity extends AppCompatActivity {

    private EditText inaccount,inpassword,sedpassword;
    private Button register,tv_back;
    private String uaccount,psw,pswAgain;//账号，密码，再次输入的密码的控件的获取值
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        init();
    }

    private void init() {
        //从layout_register.xml 页面中获取对应的UI控件
        register=(Button)findViewById(R.id.register);//注册按钮
        tv_back=(Button)findViewById(R.id.tv_back);//返回按钮
        //用户名，密码，再次输入的密码的控件
        inaccount=(EditText)findViewById(R.id.inaccount);
        inpassword=(EditText)findViewById(R.id.inpassword);
        sedpassword=(EditText)findViewById(R.id.sedpassword);
        //返回键
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });
        //注册按钮
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取账号，密码和再次输入密码框的内容
                uaccount=inaccount.getText().toString().trim();
                psw=inpassword.getText().toString().trim();
                pswAgain=sedpassword.getText().toString().trim();
                //判断输入框内容
                if(TextUtils.isEmpty(uaccount)){
                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(pswAgain)){
                    Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!psw.equals(pswAgain)){
                    Toast.makeText(RegisterActivity.this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();
                    return;
                 //从SharedPreferences中读取输入的账号，判断SharedPreferences中是否有此用户名
                }else if(isExistUserName(uaccount)){
                    Toast.makeText(RegisterActivity.this, "此账户名已经存在", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    saveRegisterInfo(uaccount, psw);//保存账号和密码到SharedPreferences中
                    RegisterActivity.this.finish();//销毁界面
                }
            }
        });
    }

     //从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名
    private boolean isExistUserName(String uaccount){
        boolean has_account=false;
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);//获取密码
        String spsw=sp.getString(uaccount, "");//传入账号获取密码
        //如果密码不为空则确实保存过这个用户名
        if(!TextUtils.isEmpty(spsw)) {
            has_account=true;
        }
        return has_account;
    }


    //保存账号和密码到SharedPreferences中
    private void saveRegisterInfo(String uaccount,String psw){
        String md5Psw = MD5Utils.md5(psw);//把密码用MD5加密
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);//loginInfo表示文件名
        SharedPreferences.Editor editor=sp.edit();//获取编辑器
        //以用户名为key，密码为value保存在SharedPreferences中
        editor.putString(uaccount, md5Psw);
        editor.commit();//提交修改
    }
}
