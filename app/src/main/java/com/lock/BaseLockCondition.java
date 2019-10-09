package com.lock;

import android.content.ContentValues;

import java.io.Serializable;

/**
 * 基本锁定配置
 *
 */
public abstract class BaseLockCondition implements Cloneable, Serializable {
    private int id;
    private boolean enable;
    private boolean selected;

    /**
     * 复制锁定信息
     *
     * @param lockConfig 锁定信息
     */
    public void copy(BaseLockCondition lockConfig) {
        this.enable = lockConfig.enable;
        this.selected = lockConfig.selected;
    }

    /**
     * 克隆接口
     *
     * @return
     */
    @Override
    public Object clone() {
        Object object = null;

        try {
            object = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 判断两个对像否匹配
     *
     * @return true/false
     */
    public boolean isMatch(BaseLockCondition condition) {
        return condition.getId() == getId();
    }

    /**
     * 获取模式ID
     *
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * 设置模式ID
     *
     * @param id 模式ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 是否被选中
     *
     * @return true/fase
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * 设置是否被选中
     *
     * @param selected 是否被选中
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * 是否使能锁定
     *
     * @return true/false
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * 设置是否使能锁定
     *
     * @param enable true/false
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     * 获取对像名称
     * 该名称要求与数据库中表名相同
     *
     * @return 对像名称
     */
    abstract public String getName();


}
