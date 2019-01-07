package com.example.zhoukao2_17.banner;

import com.example.zhoukao2_17.inter.ICallBack;
import com.example.zhoukao2_17.utils.HttpUtils;

import java.lang.reflect.Type;

public class BannerModel {
    public  void  getBanner(String  url, ICallBack callBack, Type type){
        HttpUtils.getInstance().get(url,callBack,type);
    }

}
