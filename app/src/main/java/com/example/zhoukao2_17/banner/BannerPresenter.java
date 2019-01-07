package com.example.zhoukao2_17.banner;

import com.example.zhoukao2_17.bean.BannerBean;
import com.example.zhoukao2_17.inter.ICallBack;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class BannerPresenter {

    private BannerModel model;
    private BannerView bv;

    public  void  attach(BannerView  bv){
        this.bv=bv;
        model=new BannerModel();
    }

    public  void  Banner(String url, Class<BannerBean> bannerBeanClass){
        Type type=new TypeToken<BannerBean>(){}.getType();
        model.getBanner(url, new ICallBack() {
            @Override
            public void onSuccess(Object obj) {
                bv.getBanner((BannerBean) obj);
            }

            @Override
            public void onFailed(Exception e) {
               bv.onFailed(e);
            }
        },type);
    }
    public  void detach(){
        if (bv!=null){
            bv=null;
        }
        if (model!=null){
            bv=null;
        }
    }
}
