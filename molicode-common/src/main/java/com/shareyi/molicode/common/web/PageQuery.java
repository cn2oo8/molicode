/**
 *
 */
package com.shareyi.molicode.common.web;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * 分页查询数据对象
 *
 * @author david
 * @date 2017-12-12
 */
public class PageQuery {

    public static final String CURRENT_PAGE = "page";
    public static final String DIRECTION_ASC = "ASC";
    public static final String DIRECTION_DESC = "DESC";

    /**
     * 当前页码
     */
    private int currentPageNo;
    /**
     * 每页条数
     */
    private int pageSize = 20;
    /**
     * 数据库起始index
     */
    private int startIndex;
    /**
     * 记录总数
     */
    private long totalCount;
    /**
     * 页码总数
     */
    private int pageCount;
    /**
     * 排序字段
     */
    private String orderBy;
    /**
     * 排序
     */
    private String direction;

    /**
     * 防止对象被json化或者序列化
     */
    private transient Map<String, Object> params = new HashMap<String, Object>();


    public PageQuery() {
        this(20);
    }

    public PageQuery(int pageSize) {
        this.initPageQuery(pageSize, null, null);
    }

    public PageQuery(HttpServletRequest request, int pageSize) {
        this.initPageQuery(pageSize, request, null);
    }

    public PageQuery(int pageSize, Map<String, Object> params) {
        this.initPageQuery(pageSize, null, params);
    }


    private void initPageQuery(int pageSize, HttpServletRequest request, Map<String, Object> params) {
        if (params == null) {
            if (request == null) {
                ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
                request = requestAttributes == null ? null : requestAttributes.getRequest();
            }
            this.params = getParameterNameValuesMap(request);
        } else {
            this.params = params;
        }
        this.setPageSize(pageSize);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        if (params != null) {
            params.put("pageSize", pageSize);
        }
    }

    public int getStartIndex() {
        startIndex = (getCurrentPageNo() - 1) * pageSize;
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
        this.init();
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }


    public int getCurrentPageNo() {
        if (currentPageNo != 0) {
            return currentPageNo;
        }
        String cpn = String.valueOf(this.params.get(PageQuery.CURRENT_PAGE));
        if (StringUtils.isBlank(cpn) || !StringUtils.isNumeric(cpn) || cpn.length() > 11) {
            cpn = "1";
        }
        currentPageNo = Integer.parseInt(cpn);
        if (currentPageNo > pageCount) currentPageNo = pageCount;
        if (currentPageNo < 1) currentPageNo = 1;
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }


    private void init() {
        pageCount = (int) totalCount / pageSize;
        if (totalCount % pageSize > 0 || pageCount == 0) {
            pageCount++;
        }
        // 把与分页相关的数据放入Map用于查询
        params.put("startIndex", getStartIndex());
        params.put("startRow", getStartIndex());
        params.put("pageSize", getPageSize());
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }


    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        params.put("order_by", this.getOrderBy());
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
        params.put("sort_type", this.getDirection());
    }

    /**
     * 从request中获取所有请求参数
     *
     * @param request
     * @return
     */
    public static Map<String, Object> getParameterNameValuesMap(HttpServletRequest request) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        if (request == null) {
            return paramsMap;
        }
        Enumeration<String> names = (Enumeration<String>) request.getParameterNames();
        StringBuilder pvalues = null;
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String[] values = request.getParameterValues(name);
            if (values != null) {
                if (values.length == 1) {
                    paramsMap.put(name, values[0]);
                    continue;
                }
                for (String value : values) {
                    pvalues = new StringBuilder();
                    pvalues.append(value);
                    pvalues.append(",");
                }
                pvalues.deleteCharAt(pvalues.lastIndexOf(","));
                paramsMap.put(name, pvalues.toString());
            }
        }
        return paramsMap;
    }

    /**
     * 添加查询参数
     *
     * @param name
     * @param value
     */
    public void addQueryParam(String name, Object value) {
        params.put(name, value);
    }


    /**
     * 创建一个PageQuery对象
     *
     * @param readRequest 是否自动读取request里的数据
     * @return
     */
    public static PageQuery create(boolean readRequest) {
        if (readRequest) {
            return new PageQuery();
        } else {
            return new PageQuery(20, new HashMap<String, Object>());
        }
    }

    public PageQuery put(String key, Object value) {
        this.addQueryParam(key, value);
        return this;
    }

    /**
     * 判断入参中bKey如果为boolean类型时，返回是否为false,如果bKey中的值不是boolean类型，始终返回true
     *
     * @param bKey
     * @return 返回true表示:bKey中的值是false或者不是boolean类型的值;
     * 返回false表示：bKey中的值是true;
     */
    public boolean isNot(String bKey) {
        Object obj = this.params.get(bKey);
        if (obj != null) {
            if (obj instanceof Boolean) {
                return !((Boolean) obj);
            }
        }
        return true;
    }

    /**
     * 判断入参中key的值与obj是否相等
     *
     * @param key
     * @param obj
     * @return
     */
    public boolean paramEquals(String key, Object obj) {
        Object val = params.get(key);
        if (val != null && obj != null) {
            return val.equals(obj) || val.toString().equals(obj.toString());
        }
        return false;
    }

    /**
     * 是否包含某个参数
     *
     * @param key
     * @return
     */
    public boolean containsParam(String key) {
        return params == null ? false : params.containsKey(key);
    }

    /**
     * 获取字符串的参数值
     *
     * @param key
     * @return
     */
    public String getParamString(String key) {
        return params == null ? null : (String) params.get(key);
    }

    /**
     * 获取参数值
     *
     * @param key
     * @return
     */
    public Object getParamObject(String key) {
        return params == null ? null : params.get(key);
    }
}
