package com.lock;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.app.AppInfo;
import com.app.AppInfoManager;
import com.app.SettingsManager;


import java.io.ByteArrayOutputStream;
import java.util.Calendar;

/**
 * 锁定应用的后台服务
 * 该服务一直在后台运行，不断监测前台的运行状态，立即锁定需要锁定的应用
 */
public class LockService extends Service {
    private static final String ACTION_NEW_APP_UNLOCKED = "LockService.newAppUnlocked";
    private static final String PARAM_APP_NAME = "packagename";
    private Thread thread;
    private WatchThreadRunnable watchThreadRunnable;
    private Handler screenUnlockAllHandler;
    private Runnable clearAppLockedRunnable;
    private SettingsManager settingsManager;
    private AppInfoManager appInfoManager;
    private Calendar calendar;
    private boolean locateServiceIsOk;
    private BroadcastReceiver screenLockReceiver;
    private BroadcastReceiver screenUnLockReceiver;
    private BroadcastReceiver appUnlockReceiver;
    private UnlockedList unlockedList;

    /**
     * 启动应用锁服务
     */
    public static void startBlockService(Context context) {
            Intent intent = new Intent(context, LockService.class);
            context.startService(intent);
    }

    /**
     * 关闭应用锁服务
     */
    public static void stopBlockService(Context context) {
            Intent intent = new Intent(context, LockService.class);
            context.stopService(intent);
    }

    /**
     * 发广播消息告知服务有新的应用已经解除锁定
     *
     * @param packageName 已经解锁的app包名
     */
    public static void broadcastAppUnlocked(Context context, String packageName) {
        // 纪录应用的包名
        Intent intent = new Intent(ACTION_NEW_APP_UNLOCKED);
        intent.putExtra(PARAM_APP_NAME, packageName);

        // 发送本地广播告知服务，有应用已经解锁
        LocalBroadcastManager lm = LocalBroadcastManager.getInstance(context);
        lm.sendBroadcast(intent);
    }

    /**
     * Bind回调,未用
     *
     * @param intent
     * @return 未用
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Serivce的onCreate回调
     */
    @Override
    public void onCreate() {
        // 获取所有的包信息
        appInfoManager = new AppInfoManager(this);
        calendar = Calendar.getInstance();

        settingsManager = SettingsManager.getInstance(this);
        unlockedList = new UnlockedList(this);

        screenUnlockAllHandler = new Handler();
        clearAppLockedRunnable = new Runnable() {
            @Override
            public void run() {
                // 清除所有已经解锁应用的锁定状态
                unlockedList.clear();

                // 简单停止监听线程即可
                suspendThread();
            }
        };

        // 注册监听器
        registerAppUnlockReceiver();
        registerScreenLockReceiver();
        registerScreenUnLockReceiver();

        // 创建监控线程
        createWatchThread();
    }

    /**
     * Service的onStartCommand回调
     *
     * @param intent  未用
     * @param flags   未用
     * @param startId 未用
     * @return 未用
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 启动线程
        startWatchThread();
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    /**
     * Serivce的onDestroy回调
     */
    @Override
    public void onDestroy() {

        // 停止监听器和线程
        unregisterAppUnlockReceiver();
        unregisterScreenLockReceiver();

        stopWatchThread();

        super.onDestroy();
    }

    /**
     * 启动观察线程
     */
    private void startWatchThread() {
        thread.start();
    }

    /**
     * 停止观察线程
     */
    private void suspendThread() {
        watchThreadRunnable.suspend();
    }

    /**
     * 启动观察线程
     */
    private void resumeWatchThread() {
        watchThreadRunnable.resume();
        synchronized (thread) {
            thread.notifyAll();
        }
    }

    /**
     * 停止观察线程
     */
    private void stopWatchThread() {
        watchThreadRunnable.stop();

        // 清除可能的延时操作
        screenUnlockAllHandler.removeCallbacks(clearAppLockedRunnable);
    }

    /**
     * 创建应用锁定观察线程
     */
    private void createWatchThread() {
        watchThreadRunnable = new WatchThreadRunnable();
        thread = new Thread(watchThreadRunnable);
    }

    /**
     * 注册应用解锁的广播事件监听
     * 当用户输入正确的解锁密码时，将发送广播消息。
     */
    private void registerAppUnlockReceiver() {
        // 应用解锁的广播事件监听器
        appUnlockReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 取出解锁的应用包名，添加到临时解锁队列
                String packageName = intent.getStringExtra(PARAM_APP_NAME);
                unlockedList.add(packageName);
            }
        };

        // 注册广播监听器
        IntentFilter intentFilter = new IntentFilter(ACTION_NEW_APP_UNLOCKED);
        LocalBroadcastManager lm = LocalBroadcastManager.getInstance(this);
        lm.registerReceiver(appUnlockReceiver, intentFilter);
    }

    /**
     * 取消应用解锁的插言事件监听
     */
    private void unregisterAppUnlockReceiver() {
        LocalBroadcastManager lm = LocalBroadcastManager.getInstance(this);
        lm.unregisterReceiver(appUnlockReceiver);
    }

    /**
     * 注册锁屏广播事件监听
     * 当锁屏发生时，服务不再需要监听App的锁，减少系统开销
     */
    private void registerScreenLockReceiver() {
        // 锁屏的广播事件监听器
        screenLockReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 首先，检查是否要在锁屏时清除所有的应用锁定状态
                int mode = settingsManager.getScreenUnlockLockMode();
                switch (mode) {
                    case SettingsManager.SCREENLOCK_CLEAN_NO_LOCK:
                        // 不需要做任何处理，这样已经锁的和不锁的状态保持不变
                        break;
                    case SettingsManager.SCREENLOCK_CLEAN_ALL_LOCKED:
                        // 清除所有已经解锁应用的锁定状态，
                        unlockedList.clear();
                        break;
                    case SettingsManager.SCREENLOCK_CLEAN_ALL_LOCKED_AFTER_3MIN:
                        // 启动延时操作
                        screenUnlockAllHandler.postDelayed(clearAppLockedRunnable, 3000 * 60);
                        break;
                }

                // 锁屏后，不需要监听锁，停止监听线程即可
                suspendThread();
            }
        };

        // 注册广播监听器
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(screenLockReceiver, intentFilter);
    }

    /**
     * 注册屏解锁广播事件监听
     * 当屏锁解开时，需发监听广播事件
     */
    private void registerScreenUnLockReceiver() {
        // 锁屏的广播事件监听器
        screenUnLockReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 移除延时清除已解锁的App列表
                screenUnlockAllHandler.removeCallbacks(clearAppLockedRunnable);

                // 开启后，恢复锁屏监听
                resumeWatchThread();
            }
        };

        // 注册广播监听器
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        registerReceiver(screenUnLockReceiver, intentFilter);
    }

    /**
     * 取消屏锁相关的事件监听
     */
    private void unregisterScreenLockReceiver() {
        unregisterReceiver(screenLockReceiver);
        unregisterReceiver(screenUnLockReceiver);
    }

    /**
     * 锁定队列的观察线程处理器
     *
     */
    public class WatchThreadRunnable implements Runnable {
        private String currentLockingPackageName;
        private ComponentName topComponentName;

        private boolean quit = false;
        private boolean wait = false;

        /**
         * 让线程等等
         */
        public void suspend() {
            wait = true;
        }

        /**
         * 恢复线程运行
         */
        public void resume() {
            wait = false;
        }

        /**
         * 停止观察线程
         */
        public void stop() {
            quit = true;
        }

        /**
         * 休眠一会儿
         */
        private void sleep() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * 从已解锁队列中清除已经退出的App
         * 应用退出，不会以某种方式通知到自己，必须主动检查
         */
        void removeExitedApps() {
            if (settingsManager.isAutoLockOnAppQuit()) {
                unlockedList.removeExitedApps();
            }
        }

        /**
         * 判断是否需要锁定
         *
         * @return true 需要锁定; false 不需要锁定
         */
        private boolean ifNeedLock(String packageName) {
            // 如果启动了一次解锁即解锁全部且有任意包已经被解锁
            // 且当前应用并不是自己，则不需要锁定
            if (settingsManager.isUnlockAnyUnlockAllEnabled() &&
                    !packageName.equals(getPackageName())) {
                if (unlockedList.isEmpty() == false) {
                    return false;
                }
            }

            // 其它包，且已经解锁过，则不必再解锁了
            if (unlockedList.contains(packageName)) {
                Log.d("AppLocker", packageName + ":already unlocked");
                return false;
            }

            // 正在锁定的包和要判断的包相同，则不再必重复判断
            // 注：锁定界面的包和被锁应用包的包是不同的
            if (TextUtils.equals(currentLockingPackageName, packageName)) {
                Log.d("AppLocker", packageName + ":already locking");
                return false;
            }

            // 如果是自己，必须锁定，即每次重新打开界面，都会锁定，提高安全度
            // 前面的代码已经包含了判断解锁队列操作，所以没有关系
            if (packageName.equals(getPackageName())) {
                Log.d("AppLocker", "lock myself");
                return true;
            }
            return  true;
        }

        /**
         * 检查是否需要等待
         * 用于配合锁屏操作，一旦锁屏，监控线程进入睡眠状态，可减少功耗
         */
        void checkIfNeedWait() {
            if (wait) {
                synchronized (thread) {
                    try {
                        Log.d("AppLocker", "Thread wait!");
                        thread.wait();
                        Log.d("AppLocker", "Thread wakeup!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * 锁定特定的App
         *
         * @param packageName App的包名
         */
        void lockPackage(String packageName) {
            // 获取App信息
            AppInfo appInfo = appInfoManager.querySimpleAppInfo(packageName);
            String appName = appInfo.getName();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = ((BitmapDrawable) appInfo.getIcon()).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        }

        /**
         * Thread的运行函数
         */
        @Override
        public void run() {
            String mypackageName = getPackageName();
            while (!quit) {
                checkIfNeedWait();
                removeExitedApps();
                // 仅当顶层有应用，才检查是否需要锁定
                topComponentName = appInfoManager.queryTopComponentName();
                String currentPackageName = topComponentName.getPackageName();
                if (currentPackageName != null) {
                    // 将自己从已解锁队列中移除
                    // 主要用于当用户从本应用切换至其它应用时，用于提高安全度
                    if (!currentPackageName.equals(mypackageName)) {
                        unlockedList.remove(getPackageName());
                    }

                    // 检查是否需要锁定
                    if (ifNeedLock(currentPackageName)) {
                        lockPackage(currentPackageName);
                        currentLockingPackageName = currentPackageName;
                        Log.d("AppLocker", "locked:" + currentPackageName);
                    } else {
                        currentLockingPackageName = null;
                    }
                }
                sleep();
            }

        }
    }
}

