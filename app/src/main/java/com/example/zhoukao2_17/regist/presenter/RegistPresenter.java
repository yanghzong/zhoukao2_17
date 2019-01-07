package com.example.zhoukao2_17.regist.presenter;

import com.example.zhoukao2_17.bean.LoginBean;
import com.example.zhoukao2_17.bean.RegisterBean;
import com.example.zhoukao2_17.inter.Cantant;
import com.example.zhoukao2_17.inter.ICallBack;
import com.example.zhoukao2_17.regist.model.RegistModel;
import com.example.zhoukao2_17.regist.view.RegistView;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;


public class RegistPresenter {
    private RegistView rv;
    private RegistModel model;
    public void attach(RegistView rv){
        this.rv=rv;
        model=new RegistModel();
    }

    public void getData(String phone,String pwd){
        HashMap<String,String> map =new HashMap<>();
        map.put("phone",phone);
        map.put("pwd",pwd);
        Type type = new TypeToken<LoginBean>(){}.getType();
        model.getRegister(Cantant.RegisterUrl,map,new ICallBack() {
            @Override
            public void onSuccess(Object obj) {
               RegisterBean registerBean= (RegisterBean) obj;
                rv.successful(registerBean);
            }

            @Override
            public void onFailed(Exception e) {
            rv.failed(e);
            }


        },type);
    }

    public void detach(){
        if (rv!=null){
            rv=null;
        }
    }
}
