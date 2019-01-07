package com.example.zhoukao2_17.regist.view;

import com.example.zhoukao2_17.bean.RegisterBean;


public interface RegistView {
    void successful(RegisterBean data);

    void failed(Exception e);

    String getUserName();

    String getPassWord();

}
