package com.shareyi.molicode.common.vo.replace;

import com.shareyi.molicode.common.enums.DirTransType;
import com.shareyi.molicode.common.enums.TouchFileType;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件目录接触记录类
 * Created by david on 2016/1/1.
 */
public class TouchFile {

    /**
     * 表达式
     */
    private String expression;


    private String originExpression;

    private String targetExpression;

    List<String> originPathList;

    List<String> targetPathList;


    List<DirTrans> dirTransList;


    private TouchFileType touchFileType;


    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getOriginExpression() {
        return originExpression;
    }

    public void setOriginExpression(String originExpression) {
        this.originExpression = originExpression;
    }

    public String getTargetExpression() {
        return targetExpression;
    }

    public void setTargetExpression(String targetExpression) {
        this.targetExpression = targetExpression;
    }

    public List<String> getOriginPathList() {
        return originPathList;
    }

    public void setOriginPathList(List<String> originPathList) {
        this.originPathList = originPathList;
    }

    public List<String> getTargetPathList() {
        return targetPathList;
    }

    public void setTargetPathList(List<String> targetPathList) {
        this.targetPathList = targetPathList;
    }

    public TouchFileType getTouchFileType() {
        return touchFileType;
    }

    public void setTouchFileType(TouchFileType touchFileType) {
        this.touchFileType = touchFileType;
    }


    public int getOriginPathSize() {
        return originPathList.size();
    }


    public static TouchFile parseExpression(String expression) {
        expression = StringUtils.trim(expression);
        if (StringUtils.isEmpty(expression)) {
            return null;
        }

        String[] expSplit = expression.split("=");
        if (expSplit.length != 2) {
            return null;
        }

        TouchFile touchFile = new TouchFile();
        touchFile.setExpression(expression);
        touchFile.setOriginExpression(expSplit[0]);
        touchFile.setTargetExpression(expSplit[1]);

        String[] orgPath = touchFile.getOriginExpression().split("/");
        String[] tarPath = touchFile.getTargetExpression().split("/");

        touchFile.setOriginPathList(parseStrArr2List(orgPath));
        touchFile.setTargetPathList(parseStrArr2List(tarPath));
        //如果为空，直接返回nuLl
        if (touchFile.getOriginPathList().isEmpty() || touchFile.getTargetPathList().isEmpty()) {
            return null;
        }

        if (orgPath.length < tarPath.length) {
            touchFile.setTouchFileType(TouchFileType.EXTEND_DIR);
        } else if (orgPath.length > tarPath.length) {
            touchFile.setTouchFileType(TouchFileType.SHORTEN_DIR);
        } else {
            touchFile.setTouchFileType(TouchFileType.EXCHANGE_DIR);
        }
        touchFile.parseDirTrans();

        return touchFile;
    }


    /**
     * 获取dirTrans
     */
    public void parseDirTrans() {
        dirTransList = new ArrayList<DirTrans>(originPathList.size());


        for (int i = 0; i < originPathList.size(); i++) {
            String originName = originPathList.get(i);

            //如果还没有超过目标文件夹的size
            if (i < targetPathList.size()) {


                String targetName = targetPathList.get(i);
                DirTrans dirTrans = new DirTrans();
                dirTrans.setOriginDirName(originPathList.get(i));

                //如果已经到达末尾
                if (originPathList.size() == i + 1) {
                    //如果目标path比原始的长，表示要做延长处理
                    if (targetPathList.size() > originPathList.size()) {
                        dirTrans.setDirTransType(DirTransType.EXTEND_DIR);
                        dirTrans.setTargetDirList(targetPathList.subList(i, targetPathList.size()));
                        dirTransList.add(dirTrans);
                        continue;
                    }
                }

                dirTrans.setTargetDirName(targetPathList.get(i));
                if (originName.equals(targetName)) {
                    dirTrans.setDirTransType(DirTransType.NONE);
                } else {
                    dirTrans.setDirTransType(DirTransType.NAME_CHANGE);
                }

                dirTransList.add(dirTrans);

            } else {  //前面全部匹配上，后续为空，表明是删除后面的目录移动到上级目录功能
                DirTrans dirTrans = new DirTrans();
                dirTrans.setOriginDirName(originPathList.get(i));
                dirTrans.setDirTransType(DirTransType.SHORTEN_DIR);
                dirTransList.add(dirTrans);
            }
        }

       /* if(originPathList.size()==targetPathList.size()){
            for(int i=0;i<originPathList.size();i++){
                String originName=originPathList.get(i);
                String targetName=targetPathList.get(i);

                DirTrans dirTrans=new DirTrans();
                dirTrans.setOriginDirName(originPathList.get(i));
                if(originName.equals(targetName)){
                    dirTrans.setDirTransType(DirTransType.NONE);
                }else{
                    dirTrans.setDirTransType(DirTransType.NAME_CHANGE);
                }
                dirTrans.setTargetDirName(targetPathList.get(i));
                dirTransList.add(dirTrans);
            }
            return;
        }*/

       /* for(int i=0;i<originPathList.size();i++){
            String originName=originPathList.get(i);

            //如果还没有超过目标文件夹的size
            if(i<targetPathList.size()){
                String targetName=targetPathList.get(i);
                if(originName.equals(targetName)){
                    //如果最后一个也相等，通过前面一个判断可以得出 源目录比目标目录短，则需要做扩展处理
                    if(i==originPathList.size()-1){
                        doTransPathChange(i);
                        break;
                    }
                    DirTrans dirTrans=new DirTrans();
                    dirTrans.setOriginDirName(originPathList.get(i));
                    dirTrans.setDirTransType(DirTransType.NONE);
                    dirTrans.setTargetDirName(targetPathList.get(i));
                    dirTransList.add(dirTrans);
                }else{
                    doTransPathChange(i);
                    break;
                }
            }else{  //前面全部匹配上，后续为空，表明是删除后面的目录移动到上级目录功能
                DirTrans dirTrans=new DirTrans();
                dirTrans.setOriginDirName(originPathList.get(i));
                dirTrans.setDirTransType(DirTransType.SHORTEN_DIR);
                dirTransList.add(dirTrans);
            }
        }*/
    }


    /**
     * 处理文件路径开始变得不同的数据，
     * 第一版本有从不同的位置，做文件内容聚合后，延长文件夹的算法；
     * 现在该成按位置匹配，直接文件匹配即可，如果要恢复，需要放开上面的注释
     *
     * @param index
     */
    private void doTransPathChange(int index) {

        //从不同的地方开始，所有的目录都是先缩短为1个如， com/jd/ka=com/shareyi/myprj/soso    [jd/ka] 缩为一个目录，再扩展成，shareyi/myprj/soso
        //以上等价于， ka目录下文件先移到 jd，然后删除ka,  在进行转换  jd=shareyi/myprj/soso

        if (index == (targetPathList.size() - 1)) {  //即只剩下一个，直接重命名本目录即可
            DirTrans dirTrans = new DirTrans();
            dirTrans.setOriginDirName(originPathList.get(index));
            dirTrans.setTargetDirName(targetPathList.get(index));
            dirTrans.setDirTransType(DirTransType.NAME_CHANGE);
            dirTransList.add(dirTrans);
        } else {
            DirTrans dirTrans = new DirTrans();
            dirTrans.setOriginDirName(originPathList.get(index));
            dirTrans.setDirTransType(DirTransType.EXTEND_DIR);
            for (int tarIndex = index; tarIndex < targetPathList.size(); tarIndex++) {
                dirTrans.addTargetDir(targetPathList.get(tarIndex));
            }
            dirTransList.add(dirTrans);
        }

        //把上一个index先给累加了
        index++;
        for (; index < originPathList.size(); index++) {
            DirTrans dirTrans = new DirTrans();
            dirTrans.setOriginDirName(originPathList.get(index));
            dirTrans.setDirTransType(DirTransType.SHORTEN_DIR);
            dirTransList.add(dirTrans);
        }
    }


    public static List<String> parseStrArr2List(String[] strArr) {
        List<String> list = new ArrayList<String>(strArr.length);
        for (int i = 0; i < strArr.length; i++) {
            if (StringUtils.isNotEmpty(strArr[i])) {
                list.add(strArr[i]);
            }
        }
        return list;
    }

    public List<DirTrans> getDirTransList() {
        return dirTransList;
    }


    /**
     * 通过index获取目录转换结果
     *
     * @param index
     * @return
     */
    public DirTrans getDirTransByIndex(int index) {
        return dirTransList.get(index);
    }


    /**
     * 获取指定index的转换类型
     *
     * @param index
     * @return
     */
    public DirTransType getDirTransTypeByIndex(int index) {
        return dirTransList.get(index).getDirTransType();
    }

}
