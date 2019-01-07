package com.example.zhoukao2_17.banner;

import com.example.zhoukao2_17.bean.BannerBean;

public interface BannerView {
    void   getBanner(BannerBean bannerBean);
    void   onFailed(Exception e);
}
