/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.validate.provide;

import com.shareyi.molicode.common.enums.OwnerTypeEnum;
import com.shareyi.molicode.common.enums.ResultCodeEnum;
import com.shareyi.molicode.common.exception.ExceptionMaker;
import com.shareyi.molicode.common.valid.Validate;
import com.shareyi.molicode.domain.conf.AcProject;
import com.shareyi.molicode.domain.conf.CommonExtInfo;
import com.shareyi.molicode.manager.conf.AcProjectManager;
import com.shareyi.molicode.manager.conf.CommonExtInfoManager;
import com.shareyi.molicode.validate.AbstractValidator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Objects;

import static com.shareyi.molicode.common.valid.Validate.notNull;


/**
 * 项目 SDK 验证 类
 * @author david
 * @date 2018-08-22
 */
@Service("acProjectValidator")
public class AcProjectValidator extends AbstractValidator<AcProject> {

    @Resource
    CommonExtInfoManager commonExtInfoManager;

    @Resource
    AcProjectManager acProjectManager;

   @Override
   public void addValid(AcProject obj) {
       notNull(obj, "入参对象不能为空.");
       notNull(obj.getName(), "name不能为空.");
   }

   @Override
   public void updateValid(AcProject obj) {
       notNull(obj, "入参对象不能为空.");
       notNull(obj.getId(), "主键ID不能为空.");
   }


   @Override
   public void queryValid(AcProject obj) {
       notNull(obj, "入参对象不能为空.");
       notNull(obj.getProjectKey(), "projectKey不能为空.");
   }

    @Override
    public void deleteValid(Long primaryKey) {
        Validate.notNull(primaryKey, "主键值不能为空");

        CommonExtInfo queryBean = new CommonExtInfo();
        queryBean.setOwnerType(OwnerTypeEnum.SYSTEM.getCode());
        queryBean.setOwnerCode("admin");
        queryBean.setExtKey("defaultProjectKey");
        CommonExtInfo defProjectKey = commonExtInfoManager.getByOwnerAndKey(queryBean);
        if(defProjectKey == null){
            return;
        }

       AcProject acProject = acProjectManager.getByPk(primaryKey);
        if(acProject == null){
          throw ExceptionMaker.buildException("数据不存在", ResultCodeEnum.DATA_NOT_EXIST);
        }

        if(Objects.equals(defProjectKey.getExtValue(), acProject.getProjectKey())){
            throw ExceptionMaker.buildException("不能删除默认项目！", ResultCodeEnum.FAILURE);
        }
    }

    public void copyValidate(AcProject acProject) {
        notNull(acProject, "入参对象不能为空.");
        notNull(acProject.getName(), "name不能为空.");
        notNull(acProject.getProjectKey(), "projectKey不能为空.");
    }
}
