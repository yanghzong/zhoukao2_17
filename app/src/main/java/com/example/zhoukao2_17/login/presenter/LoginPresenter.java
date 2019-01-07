package com.example.zhoukao2_17.login.presenter;


import com.example.zhoukao2_17.bean.LoginBean;
import com.example.zhoukao2_17.inter.Cantant;
import com.example.zhoukao2_17.inter.ICallBack;
import com.example.zhoukao2_17.login.model.LoginModel;
import com.example.zhoukao2_17.login.view.LoginView;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class LoginPresenter {
    private LoginView iv;
    private LoginModel model;
    public void attach(LoginView iv){
        this.iv=iv;
        model=new LoginModel();
    }

    public void requestData(String phone,String pwd){
        HashMap<String,String> map =new HashMap<>();
        map.put("phone",phone);
        map.put("pwd",pwd);
        Type type = new TypeToken<LoginBean>(){}.getType();
        model.getLogin(Cantant.LoginUrl,map,new ICallBack() {
            @Override
            public void onSuccess(Object obj) {
                LoginBean login=(LoginBean) obj;
                iv.successful(login);
            }

            @Override
            public void onFailed(Exception e) {
               iv.failed(e);
            }


        },type);

    }



    public void detach(){
        if (iv!=null){
            iv=null;
        }
    }
}
