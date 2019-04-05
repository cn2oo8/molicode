package com.shareyi.molicode.common.vo.replace;


/**
 * 文件目录接触 结果记录类
 *
 * Created by david on 2016/1/1.
 */
public class TouchFileResult {

    public TouchFile touchFile;
    public int touchState=0;  //触摸状态，0.初始化，1.触摸到底部，2.触摸失败
    public int dirLevel;  //匹配上的目录的起始层级深度（相对值）

    public String getCurDirName(int curDirIndex) {
        int relativeIndex=this.getRelativeIndex(curDirIndex);

        //超过了index就返回null
        if(relativeIndex>=touchFile.getOriginPathSize()){
            return null;
        }
        return touchFile.getOriginPathList().get(relativeIndex);
    }

    /**
     * 触摸到下一个文件夹名称成功
     */
    public void touchCurSucess(int curDirIndex) {
        if(this.getRelativeIndex(curDirIndex)+1==touchFile.getOriginPathSize()){
            touchState=1;
        }
    }


    /**
     * 是否触摸到底部
     * @return
     */
    public boolean isTouchEnd(int curDirIndex){
        return this.getRelativeIndex(curDirIndex)+1==touchFile.getOriginPathSize();
    }



    /**
     * 计算文件是否被touch到
     * @param fileName
     * @param touchFile
     * @return
     */
    public static TouchFileResult calcTouchFileResult(String fileName, TouchFile touchFile,int curDirLevel) {
        if(fileName.equals(touchFile.getOriginPathList().get(0))){
            TouchFileResult fileResult=new TouchFileResult();
            fileResult.touchFile=touchFile;
            fileResult.touchCurSucess(curDirLevel);
            return fileResult;
        }
        return null;
    }


    /**
     * 计算相对查找到匹配起始目录的 index
     * @param curDirIndex
     * @return
     */
    public int getRelativeIndex(int curDirIndex){
        return curDirIndex-dirLevel;
    }

    public DirTrans getCurrentLevelDirTrans(int level) {
        return touchFile.getDirTransByIndex(level-dirLevel);
    }
}
