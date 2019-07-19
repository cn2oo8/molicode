package com.shareyi.molicode.common.lock;


import com.google.common.base.Function;
import com.shareyi.molicode.common.utils.MyLists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 批量锁定结果对象
 *
 * @author david
 * @date 2017/6/13
 */
public class LockListResult {

    /**
     * uuid,整个批次保持一致
     */
    private final String uuid;
    /**
     * 锁定的列表
     */
    private List<String> lockedList;
    /**
     * 未锁定的列表
     */
    private List<String> unlockedList;

    /**
     * 默认构建
     *
     * @param uuid
     */
    public LockListResult(String uuid) {
        this.uuid = uuid;
        this.lockedList = new ArrayList();
        this.unlockedList = new ArrayList();
    }

    /**
     * 创建一个对象
     *
     * @param uuid
     * @return
     */
    public static LockListResult create(String uuid) {
        return new LockListResult(uuid);
    }


    public List<String> getLockedList() {
        return lockedList;
    }


    public List<String> getUnlockedList() {
        return unlockedList;
    }

    public String getUuid() {
        return uuid;
    }

    /**
     * 追加已锁定数据
     *
     * @param item
     */
    public void appendLock(String item) {
        lockedList.add(item);
    }

    /**
     * 追加未锁定的数据
     *
     * @param item
     */
    public void appendUnLock(String item) {
        unlockedList.add(item);
    }

    /**
     * 是否已经全部锁定
     *
     * @return
     */
    public boolean isAllLocked() {
        return CollectionUtils.isEmpty(unlockedList);
    }

    /**
     * 获取redis解锁用的map参数
     *
     * @return
     */
    public Map<String, String> getUnlockParamMap() {
        return MyLists.transformToMap(lockedList, new Function<String, String>() {
            @Override
            public String apply(String lockKey) {
                return lockKey;
            }
        }, new Function<String, String>() {
            @Override
            public String apply(String lockKey) {
                return uuid;
            }
        });
    }

    /**
     * 是否有锁定了的数据
     *
     * @return
     */
    public boolean hasLocked() {
        return CollectionUtils.isNotEmpty(lockedList);
    }
}
