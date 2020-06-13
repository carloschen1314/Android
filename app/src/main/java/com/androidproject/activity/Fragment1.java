package com.androidproject.activity;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidproject.R;
import com.androidproject.util.APP;
import com.androidproject.util.DetectionService;
import com.androidproject.util.FormatTools;
import com.androidproject.util.HttpUtils;
import com.androidproject.util.MD5Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class Fragment1 extends Fragment {
    private TextView textView;
    private Button button;
    private List<APP> appList = new ArrayList<>();
    private String appPacket="com.example.androidproject";

    private boolean lockFlag=false;
    private boolean flag=true;
    private Date date_begin;
    private Date date_end;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment1,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button=(Button)getActivity().findViewById(R.id.btn);
        MainActivity mainActivity=(MainActivity) getActivity();
        final String id_send=mainActivity.id;
        appList = LitePal.where("user like ?", mainActivity.id).find(APP.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                运行锁定程序
                if(!lockFlag){
                    flag=true;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while(true){
                                if(flag){
                                    if(!isForegroundPkgViaDetectionService(appPacket)){
                                        appPacket=DetectionService.foregroundPackageName;
                                        appList = LitePal.where("user like ?", id_send).find(APP.class);
                                        for(int i=0;i<appList.size();i++){
                                            APP app=appList.get(i);
                                            if(isForegroundPkgViaDetectionService(app.getAddress())){
                                                Log.d("Fragment","运行锁定程序");
                                                //进行界面遮挡设置
                                                Intent intent=new Intent(getActivity(),LockUIActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                }else
                                    break;
                            }
                        }
                    }).start();
                    button.setText("停止锁定");
                    lockFlag=true;
//                    date_begin=new Date();
                }else{
                    flag=false;
                    button.setText("运行锁定程序");
                    lockFlag=false;
//                    date_end=new Date();
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            OkHttpClient client = new OkHttpClient();
//                            Response response;
//                            try {
//                                response= HttpUtils.post("http://39.105.80.171:8080/applock/add_applock?" +
//                                        "phoneMeid=" + id_send +
//                                        "&appName=" + "com.example.androidproject"+
//                                        "&startDate=" + date_begin+
//                                        "&endDate=" + date_end,null);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }).start();
                }
            }
        });

//        getActivity().stopService(new Intent(getActivity(),DetectionService.class));
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true){
//                    if(!isAccessibilitySettingsOn(getActivity())){
//                        getActivity().startService(new Intent(getActivity(),DetectionService.class));
//                        break;
//                    }
//                }
//            }
//        }).start();
        anyMethod();
    }

    public static boolean isForegroundPkgViaDetectionService(String packageName) {
        Log.d("test",DetectionService.foregroundPackageName);
        return packageName.equals(DetectionService.foregroundPackageName);
    }

    final static String TAG = "AccessibilityUtil";

    // 此方法用来判断当前应用的辅助功能服务是否开启
    public static boolean isAccessibilitySettingsOn(Context context) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            Log.i(TAG, e.getMessage());
        }

        if (accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                return services.toLowerCase().contains(context.getPackageName().toLowerCase());
            }
        }

        return false;
    }

    private void anyMethod() {
        // 判断辅助功能是否开启
        if (!isAccessibilitySettingsOn(getContext())) {
            // 引导至辅助功能设置页面
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        } else {
            // 执行辅助功能服务相关操作
        }
    }

}
