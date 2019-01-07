package com.example.zhoukao2_17.regist.model;

import android.os.Handler;

import com.example.zhoukao2_17.inter.Cantant;
import com.example.zhoukao2_17.inter.ICallBack;
import com.example.zhoukao2_17.utils.HttpUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;




public class RegistModel {
    public  void  getRegister(String  url,HashMap<String,String> map, ICallBack callBack, Type type){
        HttpUtils.getInstance().doPost(url,map,callBack,type);
    }

}
