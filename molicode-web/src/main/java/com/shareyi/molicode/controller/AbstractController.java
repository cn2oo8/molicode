package com.shareyi.molicode.controller;

import com.shareyi.molicode.common.web.PageQuery;
import com.shareyi.molicode.domain.BasicDomain;
import com.shareyi.molicode.service.BaseService;
import com.shareyi.molicode.web.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 抽象的基础controller
 *
 * @author david
 * @date 2018/8/21
 */
public abstract class AbstractController<T extends BasicDomain> extends BaseController {


    @RequestMapping(value="add",method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map add(T t){
      return  getService().add(t).getReturnMap();
    }

    @RequestMapping(value="update",method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map update(T t){
        return  getService().update(t).getReturnMap();
    }

    @RequestMapping(value="delete",method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map delete(Long primaryKey){
        return  getService().deleteByPk(primaryKey).getReturnMap();
    }

    @RequestMapping(value="getByPk",method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map getByPk(Long primaryKey){
        return  getService().getByPk(primaryKey).getReturnMap();
    }

    @RequestMapping(value="list",method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map queryByPage(HttpServletRequest request){
        PageQuery pageQuery =new PageQuery(request, this.getPageSize(request));
        return  getService().queryByPage(pageQuery).getReturnMap();
    }

    public abstract BaseService getService();

}
