package com.shareyi.molicode.common.vo.code

/**
 * 列信息
 * @author david
 * @since 2012/12/12
 */
class ColumnVo implements Serializable {

    private static final long serialVersionUID = 121487859643283967L;
    /**
     * 数据名称，一般可作为java 变量名
     */
    String dataName;
    /**
     * 数据库列名称 如： user_name
     */
    String columnName;
    /**
     * 页面标签，如：text, select等
     */
    String jspTag;
    /**
     * 中文名称，或者成为别称
     */
    String cnname;
    /**
     * 列类型，如 VARCHAR, BIGINT
     */
    String columnType;
    /**
     * 字典项名称
     */
    String dictName;
    /**
     * 备注
     */
    String comment;
    /**
     * 是否可为空
     */
    Boolean canBeNull = false;
    /**
     * 是否为只读
     */
    Boolean readonly = false;
    /**
     * 是否为主键
     */
    Boolean isPK = false;
    /**
     * 最大长度
     */
    Integer length;

    void setLength(String length) {
        if (length != null) {
            try {
                this.length = Integer.parseInt(length);
            } catch (Exception e) {
            }
        }
    }


}
