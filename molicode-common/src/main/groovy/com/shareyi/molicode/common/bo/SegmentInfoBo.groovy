package com.shareyi.molicode.common.bo

import com.google.common.collect.Lists
import com.shareyi.molicode.sdk.dto.ExtAttrDto

/**
 * 片段信息业务对象
 */
class SegmentInfoBo extends ExtAttrDto{

    /**
     * 片段line堆栈
     */
    List<String> segmentLineStack = Lists.newArrayList();

    /**
     * 全部文件的文档内容，指向对象
     */
    transient List<String> lineContentList;
    /**
     * 片段开始的行号
     */
    int startLineIndex;
    /**
     * 片段结束的行号
     */
    int endLineIndex;

    /**
     * 获取原样字符串
     * @param prepend
     * @return
     */
    String getOriginSegmentContent(String prepend) {
        def sb = new StringBuilder();
        for (int index = 0; index < segmentLineStack.size(); index++) {
            String line = segmentLineStack.get(index);
            if (index != 0) {
                sb.append("\n");
            }
            //前置内容
            if (prepend != null && prepend.length() > 0) {
                sb.append(prepend);
            }
            sb.append(line);
        }
        return sb.toString();
    }

    /**
     * 通过行下标获取原文档中的内容
     * @param lineIndex
     * @return
     */
    String getOriginContentByLine(int lineIndex) {
        if (lineIndex >= lineContentList.size()) {
            return null;
        }
        return lineContentList.get(lineIndex);
    }

    /**
     * 通过相对坐标获取内容
     *
     * @param relativeIndex
     * @return
     */
    String getLineByRelativeIndex(int relativeIndex) {
        int lineIndex = 0;
        //不能取当前行
        if (relativeIndex == 0) {
            return null;
        }
        if (relativeIndex < 0) {
            lineIndex = startLineIndex + relativeIndex;
        } else {
            lineIndex = endLineIndex + relativeIndex;
        }

        if (lineIndex >= 0 && lineIndex < lineContentList.size()) {
            return lineContentList.get(lineIndex);
        }
        return null;
    }

    /**
     * 构建
     * @param strings
     * @param i
     * @return
     */
    static SegmentInfoBo create(List<String> lineContentList, int startLineIndex) {
        SegmentInfoBo segmentInfoBo = new SegmentInfoBo();
        segmentInfoBo.lineContentList = lineContentList;
        segmentInfoBo.startLineIndex = startLineIndex;
        return segmentInfoBo
    }


}