package com.shareyi.molicode.common.web;

import com.shareyi.molicode.common.enums.ResultCodeEnum;

import java.io.Serializable;
import java.util.*;

/**
 * 公用返还结果
 *
 * @param <T>
 * @author david
 * @date 2017-12-12
 */
public class CommonResult<T> implements Serializable {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 默认的modelKey,存储于resultMap中
     */
    public String modelKey = "value";
    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 响应码
     */
    private String returnCode;
    /**
     * 消息
     */
    private String message;
    /**
     * 结果Map
     */
    private Map<String, Object> resultMap;

    public CommonResult() {
        this(false);
    }


    public CommonResult(boolean success) {
        this(success, new HashMap<String, Object>());
    }


    public CommonResult(boolean success, Map<String, Object> resultMap) {
        super();
        this.success = success;
        this.resultMap = resultMap;
    }

    public void addDefaultModel(T value) {
        resultMap.put(modelKey, value);
    }

    @SuppressWarnings("unchecked")
    public T getDefaultModel() {
        return resultMap == null ? null : (T) resultMap.get(modelKey);
    }

    public void addDefaultModel(String key, T value) {
        this.modelKey = key;
        resultMap.put(modelKey, value);
    }

    public void addModel(String key, Object value) {
        resultMap.put(key, value);
    }

    public Object getModel(String key) {
        return resultMap == null ? null : resultMap.get(key);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 设置结果信息
     *
     * @param isSuccess
     * @param message
     */
    public CommonResult setResultInfo(boolean isSuccess, String message) {
        this.success = isSuccess;
        this.message = message;
        return this;
    }

    public CommonResult setResultInfo(boolean success, String message, String returnCode) {
        this.success = success;
        this.message = message;
        this.returnCode = returnCode;
        return this;
    }


    public Map<String, Object> getReturnMap() {
        return getReturnMap(false);
    }


    //获取结果对象Map 用于json转换
    public Map<String, Object> getReturnMap(boolean isNew) {
        Map<String, Object> returnMap = null;
        if (isNew || resultMap == null) {
            returnMap = new HashMap();
            if (resultMap != null) {
                returnMap.putAll(this.resultMap);
            }
        } else {
            returnMap = this.resultMap;
        }
        returnMap.put("success", this.success);
        returnMap.put("returnCode", this.returnCode);
        returnMap.put("message", this.message);
        return returnMap;
    }


    /**
     * 没有MAP的result,节约内存空间
     *
     * @return
     */
    public static CommonResult newSimpleResult() {
        return new CommonResult(false, null);
    }


    public CommonResult<T> failed(String message) {
        return setResultInfo(false, message);
    }

    public CommonResult<T> failed(String message, ResultCodeEnum resultCodeEnum) {
        return setResultInfo(false, message, resultCodeEnum.codeString());
    }

    public CommonResult<T> succeed() {
        return succeed(null);
    }


    public CommonResult<T> succeed(String message) {
        return setResultInfo(true, message);
    }


    /**
     * 获取Raw map数据
     */
    public Map<String, Object> getRawMap() {
        return resultMap;
    }

    /**
     * 合并两个结果
     *
     * @param result
     */
    public void mergeResult(CommonResult result) {
        if (result != null && result.resultMap != null) {
            this.resultMap.putAll(result.resultMap);
        }
    }

    public CommonResult<T> put(String key, Object value) {
        if (key != null && key.length() > 0) {
            this.resultMap.put(key, value);
        }
        return this;
    }

    public static <T> CommonResult<T> create() {
        CommonResult<T> result = new CommonResult<T>();
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CommonResult{");
        sb.append("modelKey='").append(modelKey).append('\'');
        sb.append(", success=").append(success);
        sb.append(", returnCode='").append(returnCode).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", resultMap=").append(resultMap);
        sb.append('}');
        return sb.toString();
    }
}