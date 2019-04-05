package com.shareyi.molicode.service;

import com.shareyi.molicode.builder.BaseBuilder;
import com.shareyi.molicode.common.utils.LogHelper;
import com.shareyi.molicode.common.valid.Validate;
import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.common.web.PageQuery;
import com.shareyi.molicode.domain.BasicDomain;
import com.shareyi.molicode.manager.BaseManager;
import com.shareyi.molicode.validate.BaseValidator;

import java.util.List;

/**
 * 抽象的服务类
 *
 * @author david
 * @date 2018/8/21
 */
public abstract class AbstractService<T extends BasicDomain> implements BaseService<T> {

    @Override
    public CommonResult<T> add(T t) {
        CommonResult<T> result = CommonResult.create();
        String bizName = getBuilder().getBizName();
        try {
            getValidator().addValid(t);
            getBuilder().supplyAddInfo(t);
            getManager().add(t);
            result.succeed().addDefaultModel(t);
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("添加" + bizName + "失败，data={}", t, e);
            result.failed("添加" + bizName + "失败，原因是：" + e.getMessage());
        }
        return result;
    }

    @Override
    public CommonResult<T> update(T t) {
        CommonResult<T> result = CommonResult.create();
        String bizName = getBuilder().getBizName();
        try {
            getValidator().updateValid(t);
            getBuilder().supplyUpdateInfo(t);
            getManager().update(t);
            result.succeed().addDefaultModel(t);
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("修改" + bizName + "失败，data={}", t, e);
            result.failed("修改" + bizName + "失败，原因是：" + e.getMessage());
        }
        return result;
    }

    @Override
    public CommonResult<String> deleteByPk(Long primaryKey) {
        CommonResult<String> result = CommonResult.create();
        String bizName = getBuilder().getBizName();
        try {
            getValidator().deleteValid(primaryKey);
            int count = getManager().deleteByPk(primaryKey);
            if (count > 0) {
                result.succeed();
            } else {
                result.failed("删除数量为0");
            }
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("删除" + bizName + "失败，data={}", primaryKey, e);
            result.failed("删除" + bizName + "失败，原因是：" + e.getMessage());
        }
        return result;
    }

    @Override
    public CommonResult<T> getByPk(Long primaryKey) {
        CommonResult<T> result = CommonResult.create();
        String bizName = getBuilder().getBizName();
        try {
            Validate.notNull(primaryKey, "主键值不能为空");
            T data = getManager().getByPk(primaryKey);
            if (data != null) {
                result.succeed().addDefaultModel(data);
            } else {
                result.failed("未查询到数据");
            }
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("获取" + bizName + "失败，data={}", primaryKey, e);
            result.failed("获取" + bizName + "失败，原因是：" + e.getMessage());
        }
        return result;
    }

    @Override
    public CommonResult<List<T>> queryByPage(PageQuery pageQuery) {
        CommonResult<List<T>> result = CommonResult.create();
        String bizName = getBuilder().getBizName();
        try {
            Long count = getManager().count(pageQuery.getParams());
            pageQuery.setTotalCount(count);
            result.addModel("pageQuery", pageQuery);
            if (count > 0) {
                List<T> dataList = getManager().queryByPage(pageQuery.getParams());
                result.addDefaultModel(dataList);
            }
            result.succeed();
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("分页查询" + bizName + "失败，data={}", pageQuery, e);
            result.failed("分页查询" + bizName + "失败，原因是：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取manager
     *
     * @return
     */
    public abstract BaseManager<T> getManager();

    /**
     * 获取validator
     *
     * @return 返回验证器
     */
    public abstract BaseValidator<T> getValidator();

    /**
     * 获取构建器
     *
     * @return
     */
    public abstract BaseBuilder<T> getBuilder();
}
