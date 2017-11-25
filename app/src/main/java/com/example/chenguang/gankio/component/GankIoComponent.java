package com.example.chenguang.gankio.component;

import com.example.chenguang.gankio.ui.fragment.GankTypeFragment;
import com.example.chenguang.gankio.ui.fragment.VideoFragment;
import com.example.chenguang.gankio.ui.fragment.WelfareFragment;

import dagger.Component;

/**
 * Created by chenguang on 2017/9/14.
 */
@Component(dependencies = AppComponent.class)
public interface GankIoComponent {

    WelfareFragment inject(WelfareFragment welfareFragment);

    GankTypeFragment inject(GankTypeFragment gankTypeFragment);

    VideoFragment inject(VideoFragment videoFragment);
}
