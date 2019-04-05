package com.shareyi.molicode.common.vo.replace;

import com.shareyi.molicode.common.enums.DirTransType;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidon 2016/1/5.
 */
public class DirTrans {

    private String originDirName;
    private String targetDirName;
    private List<String> targetDirList;
    private DirTransType dirTransType;


    public String getOriginDirName() {
        return originDirName;
    }

    public void setOriginDirName(String originDirName) {
        this.originDirName = originDirName;
    }

    public String getTargetDirName() {
        return targetDirName;
    }

    public void setTargetDirName(String targetDirName) {
        this.targetDirName = targetDirName;
    }

    public List<String> getTargetDirList() {
        return targetDirList;
    }

    public void setTargetDirList(List<String> targetDirList) {
        this.targetDirList = targetDirList;
    }

    public DirTransType getDirTransType() {
        return dirTransType;
    }

    public void setDirTransType(DirTransType dirTransType) {
        this.dirTransType = dirTransType;
    }

    public void addTargetDir(String targetDir) {
        if (targetDirList == null) {
            targetDirList = new ArrayList<String>();
        }
        targetDirList.add(targetDir);
    }


    @Override
    public String toString() {
        return "{originDirName:" + originDirName + ",targetDirName:" + targetDirName + ",targetDirList:" + targetDirList + ",dirTransType:" + dirTransType + "}";
    }


    /**
     * 获取目标路径的拼接版本数据
     *
     * @return
     */
    public String getTargetDirListStr() {
        if (targetDirList != null) {
            return StringUtils.join(targetDirList, "/");
        }
        return null;
    }
}
