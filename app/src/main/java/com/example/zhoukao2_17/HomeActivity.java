package com.example.zhoukao2_17;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zhoukao2_17.banner.BannerPresenter;
import com.example.zhoukao2_17.banner.BannerView;
import com.example.zhoukao2_17.bean.BannerBean;
import com.example.zhoukao2_17.inter.Cantant;
import com.stx.xhb.xbanner.XBanner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements BannerView {
    @BindView(R.id.banner)
    XBanner banner;
    private BannerPresenter bannerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this,this);
        bannerPresenter = new BannerPresenter();
        bannerPresenter.attach(this);
        bannerPresenter.Banner(Cantant.BannerUrl,BannerBean.class);
    }

    @Override
    public void getBanner(BannerBean bannerBean) {
        if(bannerBean instanceof BannerBean)
        {
            BannerBean banner1= (BannerBean) bannerBean;
            banner.setData(banner1.getResult(),null);
            banner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    BannerBean.ResultBean bean= (BannerBean.ResultBean) model;
                    Glide.with(HomeActivity.this).load(bean.getImageUrl()).into((ImageView) view);
                    banner.setPageChangeDuration(1000);
                }
            });

        }


    }

    @Override
    public void onFailed(Exception e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bannerPresenter.detach();
    }
}
