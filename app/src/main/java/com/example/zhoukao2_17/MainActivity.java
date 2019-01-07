package com.example.zhoukao2_17;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhoukao2_17.bean.LoginBean;
import com.example.zhoukao2_17.login.presenter.LoginPresenter;
import com.example.zhoukao2_17.login.view.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity  implements LoginView{
    @BindView(R.id.ed_phone)
    EditText  edPhone;
    @BindView(R.id.ed_pwd)
    EditText  edPwd;
    @BindView(R.id.btn_login)
    Button  btnLogin;
    private boolean iseye = false;
    private static final String ZEBDS = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";


    private Unbinder bind;
    private LoginPresenter presenter;
    private SharedPreferences sp;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRegister = findViewById(R.id.btn_register);


        bind = ButterKnife.bind(this, this);
        sp = getSharedPreferences("user", MODE_PRIVATE);
        presenter=new LoginPresenter();
        presenter.attach(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
    @OnClick(R.id.ed_pwd)
    public  void  edpwdClick(){

    }
    @OnClick(R.id.ed_phone)
    public  void edphoneClick(){
        String phone = edPhone.getText().toString().trim();
            if (phone == null) {
                Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
        } else {
            boolean mobileNO = isMobileNO(phone);
            if (mobileNO) {
                Toast.makeText(this, "手机号码输入正确", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "号码格式不正确", Toast.LENGTH_SHORT).show();
            }
        }

    }
    @OnClick(R.id.btn_login)
    public  void  onClickListener(){

                String phone = edPhone.getText().toString().trim();
                String pwd = edPwd.getText().toString().trim();
                if (phone!=null&&pwd!=null){
                    presenter.requestData(phone,pwd);
                }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
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
        if (TextUtils.isEmpty(mobileNums)){
            return false;
        } else{
            return mobileNums.matches(ZEBDS);
        }
    }

    @Override
    public void successful(LoginBean data) {
        if (data != null) {
            if (data.getStatus().equals("0000")) {
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("pwd", edPwd.getText().toString().trim());
                edit.putString("phone", edPhone.getText().toString().trim());

                edit.commit();
                String headPic = data.getResult().getHeadPic();
                String nickName = data.getResult().getNickName();
                String phone = data.getResult().getPhone();
                String sessionId = data.getResult().getSessionId();
                int sex = data.getResult().getSex();
                int userId = data.getResult().getUserId();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);

                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public String getUserName() {
        return edPhone.getText().toString().trim();
    }

    @Override
    public String getUserPassWord() {
        return edPwd.getText().toString();
    }

    @Override
    public void failed(Exception e) {
        Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
