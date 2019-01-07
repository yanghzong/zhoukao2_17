package com.example.zhoukao2_17;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhoukao2_17.bean.RegisterBean;
import com.example.zhoukao2_17.regist.presenter.RegistPresenter;
import com.example.zhoukao2_17.regist.view.RegistView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RegisterActivity extends AppCompatActivity implements RegistView {

    private  RegistPresenter presenter;
    private static final String ZEBDS = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";

    private Unbinder bind;
    private Button btnRegister1;
    private TextView tvLogin1;
    private EditText edRgPwd;
    private EditText edRgphone1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edRgphone1 = findViewById(R.id.ed_rgphone);
        edRgPwd = findViewById(R.id.ed_rgpwd);
        tvLogin1 = findViewById(R.id.tv_login);
        btnRegister1 = findViewById(R.id.btn_register);
        presenter=new RegistPresenter();
        presenter.attach(this);
        tvLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btnRegister1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edRgphone1.getText().toString().trim();
                String pwd = edRgPwd.getText().toString().trim();
                if (phone!=null&&pwd!=null){
                    presenter.getData(phone,pwd);
                }
            }
        });
        edRgphone1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String phone = edRgphone1.getText().toString().trim();
                if (hasFocus) {
                    if (phone == null) {
                        Toast.makeText(RegisterActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    boolean mobileNO = isMobileNO(phone);
                    if (mobileNO) {
                        Toast.makeText(RegisterActivity.this, "手机号码输入正确", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "手机号码输入不正确", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        edRgPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Toast.makeText(RegisterActivity.this, "\"密码最佳长度6~12位\"", Toast.LENGTH_SHORT).show();
                } else {
                    String pwd = edRgPwd.getText().toString().trim();
                    if (pwd.length() > 5 && pwd.length() < 11) {
                        Toast.makeText(RegisterActivity.this, "密码长度适中", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "密码过长或果断", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




    }

/*
    @OnClick({R.id.tv_login,R.id.btn_login})
    public   void  onClickListener(View view){
        switch (view.getId()){
            case R.id.tv_login:

                break;
            case R.id.btn_register:

                break;
        }*/


    @Override
    public void successful(RegisterBean data) {
        if (data!=null){
            if (data.getStatus().equals("1001")){
                Toast.makeText(this, "已经被注册过了", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void failed(Exception e) {
        Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getUserName() {
        return edRgphone1.getText().toString().trim();
    }

    @Override
    public String getPassWord() {
        return edRgPwd.getText().toString();
    }
    public static boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(ZEBDS);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
