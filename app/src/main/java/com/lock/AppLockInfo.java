package com.lock;


import com.app.AppInfo;

/**
 * 配置锁定的App基本信息
 */
public class AppLockInfo {
    private long id;
    private AppInfo appInfo;
    private boolean enable;
    private boolean selected;


    /**
     * 获取模式ID
     *
     * @return ID
     */
    public long getId() {
        return id;
    }

    /**
     * 设置模式ID
     *
     * @param id 模式ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * 获取App信息
     *
     * @return App信息
     */
    public AppInfo getAppInfo() {
        return appInfo;
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
     * 获取应用的包名
     */
    public String getPackageName() {
        return getAppInfo().getPackageName();
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
}
