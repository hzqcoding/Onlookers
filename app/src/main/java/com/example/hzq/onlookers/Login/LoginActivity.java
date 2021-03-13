package com.example.hzq.onlookers.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hzq.onlookers.FunctionActivity;
import com.example.hzq.onlookers.GuideActivity;
import com.example.hzq.onlookers.R;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText account,password;
    private Button login,back;
    private CheckBox re_password;
    private String uaccount,psw,spsw;//账号，密码的控件的获取值
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        init();
    }
    private void init() {
        login=(Button)findViewById(R.id.login);//登录按钮
        back=(Button)findViewById(R.id.back);//返回按钮
        //账号，密码，记住密码的控件
        account=(EditText)findViewById(R.id.account);
        password=(EditText)findViewById(R.id.password);
        re_password=(CheckBox) findViewById(R.id.re_password);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor =  pref.edit();
        isRemember();//调用方法isRemember判断用户上一次登录是否选择了记住密码选项
        //返回键
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
                Intent a=new Intent(LoginActivity.this, GuideActivity.class);//登录成功后进入主页
                startActivity(a);
            }
        });
        //登录键
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始登录，获取账号和密码
                uaccount=account.getText().toString().trim();
                psw=password.getText().toString().trim();
                String md5Psw= MD5Utils.md5(psw);//对当前用户输入的密码进行MD5加密再进行比对判断
                spsw=readPsw(uaccount);// 定义方法 readPsw为了读取用户名，得到密码

                if (re_password.isChecked()) {//选择了记住密码选项，保存登录信息
                    editor.putString("account", account.getText().toString().trim());
                    editor.putString("password", password.getText().toString().trim());
                    editor.putBoolean("remember_pass", true);
                } else {
                    editor.putBoolean("remember_pass", false);
                    editor.clear();
                }
                editor.apply();


                if(TextUtils.isEmpty(uaccount)){//账号输入框为空
                    Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){//密码输入框为空
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(md5Psw.equals(spsw)){// 判断，输入的密码加密后，是否与保存在SharedPreferences中一致
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();//一致登录成功
                    saveLoginStatus(true, uaccount);//保存登录状态
                    LoginActivity.this.finish();//销毁登录界面
                    Intent b=new Intent(LoginActivity.this, FunctionActivity.class);//登录成功后进入主页
                    startActivity(b);
                    return;
                }else if((spsw!=null&&!TextUtils.isEmpty(spsw)&&!md5Psw.equals(spsw))){
                    Toast.makeText(LoginActivity.this, "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(LoginActivity.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //定义方法isRemember判断用户上一次登录是否选择了记住密码选项
    private void isRemember() {
        Boolean isRemember = pref.getBoolean("remember_pass",false);
        if(isRemember){//读取存储的用户名和密码
            //将数据显示在界面上
            account.setText(pref.getString("account", ""));
            password.setText(pref.getString("password", ""));
            re_password.setChecked(true);
        }else {
            account.setText(pref.getString("account", ""));
        }
    }

    // 定义方法 readPsw为了读取用户名，得到密码
    private String readPsw(String uaccount){
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);//MODE_PRIVATE表示可以继续写入
        return sp.getString(uaccount ,"");
    }

    //保存登录状态
    private void saveLoginStatus(boolean status,String uaccount){
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);//loginInfo表示文件名
        SharedPreferences.Editor editor=sp.edit();//获取编辑器
        editor.putBoolean("isLogin", status);//存入boolean类型的登录状态
        editor.putString("loginAccount", uaccount);//存入登录状态时的用户名
        editor.commit();//提交修改
    }
}
