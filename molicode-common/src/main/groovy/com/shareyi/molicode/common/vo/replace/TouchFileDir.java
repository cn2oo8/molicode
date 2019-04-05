package com.shareyi.molicode.common.vo.replace;

import com.shareyi.molicode.common.enums.DirTransType;
import com.shareyi.molicode.common.enums.TouchFileType;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 探访文件夹相关信息
 * Created by david on 2016/1/4.
 */
public class TouchFileDir {

    private String relPath;
    private String absPath;
    private String name;
    private TouchFileType touchFileType;
    public int level;


    public TouchFileResult touchFileResult;



    public TouchFileDir parentTouchDir;

    public List<TouchFileDir> childTouchDirList;


    public List<TouchFileResult> tempSusTouchResultList;



    public String getRelPath() {
        return relPath;
    }

    public void setRelPath(String relPath) {
        this.relPath = relPath;
    }

    public String getAbsPath() {
        return absPath;
    }

    public void setAbsPath(String absPath) {
        this.absPath = absPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TouchFileType getTouchFileType() {
        return touchFileType;
    }

    public void setTouchFileType(TouchFileType touchFileType) {
        this.touchFileType = touchFileType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public TouchFileDir getParentTouchDir() {
        return parentTouchDir;
    }


    public TouchFileResult getTouchFileResult() {
        return touchFileResult;
    }

    public void setParentTouchDir(TouchFileDir parentTouchDir) {
        this.parentTouchDir = parentTouchDir;
        parentTouchDir.addChildDir(this);
    }



    /**
     * 添加子目录
     * @param touchFileDir
     */
    private void addChildDir(TouchFileDir touchFileDir) {
        if(childTouchDirList==null){
            childTouchDirList=new ArrayList<TouchFileDir>();
        }
        childTouchDirList.add(touchFileDir);
    }

    public void clearSusTouchResultList() {
        this.tempSusTouchResultList=null;
    }


    public void clearSusTouchResultList(TouchFileResult touchFileResult) {
        if(this.level>touchFileResult.dirLevel){
            parentTouchDir.clearSusTouchResultList(touchFileResult);
        }
        this.tempSusTouchResultList=null;
    }


    /**
     * 设置本路径下的匹配的替换内容，并且将父级别的一并设置
     * @param touchFileResult
     */
    public boolean setTouchFileResult(TouchFileResult touchFileResult) {
        //如果本级别的深度更高，应该要往父级别的目录继续设置,并且需要父级别的设置成功，才能设置子级别的
        DirTrans dirTrans=touchFileResult.getCurrentLevelDirTrans(this.level);

        if(this.touchFileResult!=null && dirTrans.getDirTransType()!=DirTransType.NONE){
            return false;
        }

        if(this.level>touchFileResult.dirLevel){
            if(parentTouchDir.setTouchFileResult(touchFileResult)){
                //如果当前目录为 定位目录，不做任何处理，则无需设置result
                if(dirTrans.getDirTransType()!=DirTransType.NONE){
                    this.touchFileResult=touchFileResult;
                }
                return true;
            }
        }else{
            //如果当前目录为 定位目录，不做任何处理，则无需设置result
            if(dirTrans.getDirTransType()!=DirTransType.NONE){
                this.touchFileResult=touchFileResult;
            }
            return true;
        }
        return false;
    }

    /**
     * 清除子目录中未被touch到的目录，垃圾目录
     *
     * @return  返回本目录 或者 子目录是否被touch到
     */
    public boolean clearNotTouchedSubDir() {
        boolean hasTouched=(this.touchFileResult!=null);
        if(CollectionUtils.isNotEmpty(childTouchDirList)){
            TouchFileDir touchFileDir=null;
            for(Iterator<TouchFileDir> it=childTouchDirList.iterator(); it.hasNext();){
                touchFileDir=it.next();
                if(touchFileDir.clearNotTouchedSubDir()){
                    hasTouched=true;
                }else{ //如果子目录
                    it.remove();
                }
            }
        }
        return hasTouched;
    }

    public TouchFileDir getChildTouchFileDirByDirName(String childDirName) {
        if(CollectionUtils.isNotEmpty(childTouchDirList)){
            for(TouchFileDir touchFileDir:childTouchDirList){
                if(childDirName.equals(touchFileDir.getName())){
                    return touchFileDir;
                }
            }


        }
        return null;
    }

    public String getChangedDirName() {
        DirTrans dirTrans=touchFileResult.getCurrentLevelDirTrans(this.level);
        if(dirTrans!=null){
            DirTransType dirTransType=dirTrans.getDirTransType();
            if(dirTransType==DirTransType.NAME_CHANGE){
                return dirTrans.getTargetDirName();
            }else if(dirTransType==DirTransType.SHORTEN_DIR){
                return null;
            }else if(dirTransType==DirTransType.EXTEND_DIR){
                return dirTrans.getTargetDirListStr();
            }
        }
        return this.name;
    }
}
