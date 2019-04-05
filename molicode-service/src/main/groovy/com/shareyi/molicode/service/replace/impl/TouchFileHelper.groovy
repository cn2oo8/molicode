package com.shareyi.molicode.service.replace.impl;

import com.shareyi.molicode.common.vo.replace.TouchFile;
import com.shareyi.molicode.common.vo.replace.TouchFileDir;
import com.shareyi.molicode.common.vo.replace.TouchFileResult;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件触摸帮助类
 * Created by david on 2016/1/4.
 */
public class TouchFileHelper {

    private List<TouchFile> touchFileList;



    /**
     * 解析路径替换的表述式,并获取文件触摸帮助类
     * @param dirReplaceExp
     * @return
     */
    public static TouchFileHelper getTouchFileHelper(String dirReplaceExp){
        if(dirReplaceExp==null || (dirReplaceExp=dirReplaceExp.trim()).length()==0){
            return null;
        }
        TouchFileHelper touchFileHelper=new TouchFileHelper();
        List<TouchFile> touchFileList=new ArrayList<TouchFile>();
        String[] touchFileExps=dirReplaceExp.split("\n");
        for(String touchFileExp :touchFileExps){
            TouchFile touchFile=TouchFile.parseExpression(touchFileExp);
            if(touchFile!=null){
                touchFileList.add(touchFile);
            }
        }
        if(touchFileList.isEmpty()){
            return null;
        }

        touchFileHelper.touchFileList=touchFileList;
        return touchFileHelper;
    }


    /**
     * 执行touch处理，并返回TouchFileDir
     * @param fileName
     * @param parentTouchDir
     * @return
     */
    public TouchFileDir doTouchFile(String fileName, TouchFileDir parentTouchDir) {

        TouchFileDir curTouchFileDir=new TouchFileDir();
        curTouchFileDir.setName(fileName);
        curTouchFileDir.setLevel(parentTouchDir.level + 1);
        curTouchFileDir.setParentTouchDir(parentTouchDir);
        //parentTouchDir.add


        List<TouchFileResult> parentSusList=parentTouchDir.tempSusTouchResultList;

        //为了子目录之间的数据隔开，必须新建一个List来装填
        List<TouchFileResult> curSusList=new ArrayList<TouchFileResult>();
        if(CollectionUtils.isNotEmpty(parentSusList)){
            for(TouchFileResult touchFileResult:parentSusList){
                String nextName=touchFileResult.getCurDirName(curTouchFileDir.getLevel());
                if(fileName.equals(nextName)){
                    touchFileResult.touchCurSucess(curTouchFileDir.getLevel());
                    //如果触摸到，且已经到达末尾，则认为已经完成，而且一个目录里面只能有一个规则,则清空父级别的数据
                    if(touchFileResult.isTouchEnd(curTouchFileDir.getLevel())){
                        curTouchFileDir.setTouchFileResult(touchFileResult);
                       // parentTouchDir.clearSusTouchResultList(touchFileResult);
                       // return curTouchFileDir;
                    }else{ //本目录匹配上，但是依然没有匹配完全,需要继续进行处理
                        curSusList.add(touchFileResult);
                    }
                }//没匹配上的从本目录起丢弃
                /*else{
                    touchFileResult.touchFailed();
                }*/
            }
        }


        for(TouchFile touchFile:touchFileList){
            TouchFileResult touchFileResult=TouchFileResult.calcTouchFileResult(fileName, touchFile,curTouchFileDir.level);
           if(touchFileResult!=null){
               touchFileResult.dirLevel=curTouchFileDir.level;
               if(touchFileResult.isTouchEnd(curTouchFileDir.getLevel())){
                   //如果触摸到，且已经到达末尾，则认为已经完成，而且一个目录里面只能有一个规则,这里允许嵌套，不会对其他目录造成影响
                   curTouchFileDir.setTouchFileResult(touchFileResult);
                  // parentTouchDir.clearSusTouchResultList();
                   break;  //找到一个即可，无需继续查找
               }else{
                   curSusList.add(touchFileResult);
               }
           }
        }

        //如果本目录下的检测不为空，设置进去，交给子目录来匹配
        if(CollectionUtils.isNotEmpty(curSusList)){
            curTouchFileDir.tempSusTouchResultList=curSusList;
        }

        return curTouchFileDir;
    }






}
