package com.example.zhoukao2_17.login.view;


import com.example.zhoukao2_17.bean.LoginBean;

public interface LoginView {
    void successful(LoginBean data);
    String getUserName();
    String getUserPassWord();
    void failed(Exception e);
}
