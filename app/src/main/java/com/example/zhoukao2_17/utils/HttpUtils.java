package com.example.zhoukao2_17.utils;

import android.os.Handler;

import com.example.zhoukao2_17.inter.ICallBack;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpUtils {

    private static final MediaType Json =MediaType.parse("application/json; charset=utf-8");
    private static volatile HttpUtils  instance;
    private OkHttpClient client;
    private Handler handler=new Handler();

    private  HttpUtils (){
        client=new OkHttpClient();
    }

    public static  HttpUtils getInstance(){
        if (instance==null){
            synchronized (HttpUtils.class){
                if (instance==null){
                    instance=new HttpUtils();
                }
            }
        }
        return instance;
    }

    public static void enqueuePost(String loginUrl, Map<String,String> map, Callback callback, Type type) {
    }

    public  void  get(String  url, final ICallBack callBack, final Type type){
        Request request=new Request
                .Builder()
                .url(url)
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson=new Gson();
                final Object o = gson.fromJson(string, type);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(o);
                    }
                });

            }
        });

    }

    public void doPost(String url, HashMap<String, String> map, final ICallBack callBack, final Type type) {
        //创建FormBody的对象,把表单添加到formBody中
        FormBody.Builder builder = new FormBody.Builder();
        if (map != null) {
            for (String key : map.keySet()) {
                builder.add(key, map.get(key));
            }
        }
        FormBody formBody = builder.build();
        //创建Request对象
        Request request = new Request.Builder().post(formBody).url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                if ( callBack!= null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onFailed(e);
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response != null && response.isSuccessful()) {
                        final String json = response.body().string();
                        Gson gson = new Gson();
                        final Object o = gson.fromJson(json, type);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (callBack != null) {
                                    callBack.onSuccess(o);
                                }
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (callBack == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onFailed(new Exception("网络异常"));
                        }
                    });

                }
            }
        });

    }


}
