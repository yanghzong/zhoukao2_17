package com.example.zhoukao2_17.login.model;

import android.os.Handler;

import com.example.zhoukao2_17.inter.Cantant;
import com.example.zhoukao2_17.inter.ICallBack;
import com.example.zhoukao2_17.utils.HttpUtils;

import java.lang.reflect.Type;
import java.util.HashMap;


public class LoginModel {
    public  void  getLogin(String  url,HashMap<String,String> map, ICallBack callBack, Type type){
        HttpUtils.getInstance().doPost(url,map,callBack,type);
    }

}
