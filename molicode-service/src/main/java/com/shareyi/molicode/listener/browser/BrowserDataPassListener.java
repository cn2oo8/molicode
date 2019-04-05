package com.shareyi.molicode.listener.browser;

import com.shareyi.molicode.common.constants.CommonConstant;
import com.shareyi.molicode.common.gui.BrowserCallbackListener;
import com.shareyi.molicode.common.utils.Profiles;
import com.shareyi.molicode.common.web.CommonResult;
import org.springframework.stereotype.Service;

/**
 * 数据传递listener
 *
 * @author zhangshibin
 * @date 2018/11/1
 */
@Service
public class BrowserDataPassListener implements BrowserCallbackListener{

    @Override
    public CommonResult<Object> handle(String payload) {
        Profiles.getInstance().putCache(CommonConstant.BROWSER_PASS_KEY, payload);
        return CommonResult.create().succeed();
    }

    @Override
    public String getAction() {
        return "dataPass";
    }

}
