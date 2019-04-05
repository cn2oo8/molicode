package com.shareyi.molicode.web.base;

import javax.servlet.http.HttpServletRequest;

import com.shareyi.molicode.common.web.CommonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;

/**
 * 基础controller，其它controller可以继承本controller
 *
 * @author david
 * @date 2018-08-04
 */
public class BaseController {

    /**
     * 默认的pageSize
     */
    public static final int DEFAULT_PAGE_SIZE = 10;
    /**
     * 最大的pageSize
     */
    public static final int MAX_PAGE_SIZE = 100;


    public void toVm(CommonResult result, ModelMap context) {
        this.toVm(result, context, null);
    }

    public void toVm(CommonResult result, ModelMap context, HttpServletRequest request) {
        context.putAll(result.getReturnMap());
    }

    /**
     * 获取页面传递的pageSize
     * @param request
     * @return
     */
    public int getPageSize(HttpServletRequest request) {
        return getPageSize(request, DEFAULT_PAGE_SIZE, MAX_PAGE_SIZE);
    }

    /**
     * 获取页面传递的pageSize
     * @param request
     * @param defaultPageSize
     * @param max
     * @return
     */
    public int getPageSize(HttpServletRequest request, int defaultPageSize, int max) {
        String pageSizeStr = request.getParameter("pageSize");
        int pageSize = defaultPageSize;
        if (StringUtils.isNumeric(pageSizeStr)) {
            try {
                pageSize = Integer.valueOf(pageSizeStr);
            } catch (Exception e) {
            }
            //不能超过最大值
            pageSize = pageSize > max ? max : pageSize;
        }
        return pageSize;
    }
}
