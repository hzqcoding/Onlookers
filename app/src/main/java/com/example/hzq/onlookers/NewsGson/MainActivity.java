package com.example.hzq.onlookers.NewsGson;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hzq.onlookers.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView lvNews;
    //声明Adapter作为listview的填充
    private NewsAdapter adapter;
    private List<Data> dataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        lvNews = (ListView)findViewById(R.id.lvNews);
        dataList = new ArrayList<Data>();
        adapter = new NewsAdapter(this, dataList);
        lvNews.setAdapter(adapter);
        lvNews.setOnItemClickListener(this);
        sendRequestWithOKHttp();
    }

    private void sendRequestWithOKHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient实例
                    Request request = new Request.Builder()//创建Request对象
                            .url("http://v.juhe.cn/toutiao/index?type=top&key=468a538795ca302846f994e7559df8a7")
                            .build();
                    Response response = client.newCall(request).execute();//创建Call对象，并调用execute()方法
                    String responseData = response.body().string();//获取响应
                    parseJsonWithGson(responseData);//调用Gson方法显示响应内容
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //使用GSON方式解析JSON
    private void parseJsonWithGson(String jsonData){
        Gson gson = new Gson();//创建映射类
        News news = gson.fromJson(jsonData, News.class);//映射为类
        List<Data> list = news.getResult().getData();//访问列表元素的属性
        //获取url地址中的数据
        for (int i=0; i<list.size(); i++){
            String uniquekey = list.get(i).getUniqueKey();
            String title = list.get(i).getTitle();
            String date = list.get(i).getDate();
            String category = list.get(i).getCategory();
            String author_name = list.get(i).getAuthorName();
            String content_url = list.get(i).getUrl();
            String pic_url = list.get(i).getThumbnail_pic_s();
            dataList.add(new Data(uniquekey, title, date, category, author_name, content_url, pic_url));
        }
        //子线程更新Adapter(
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Data data = dataList.get(position);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("content_url", data.getUrl());//传递url
        startActivity(intent);
    }
}
