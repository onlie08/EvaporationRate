package com.ch.base.base;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.ch.bean.DaoMaster;
import com.ch.bean.DaoSession;
import com.ch.db.DatabaseContext;
import com.ch.evaporationrate.R;
import com.ch.utils.AppPreferences;
import com.ch.utils.ToastHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.leakcanary.LeakCanary;

/**
 * @author RedLi
 * @date
 */

public class BaseApplication extends Application {
    private static DaoSession daoSession;
    public static SharedPreferences sharedPreferences;

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull
                    RefreshLayout layout) {
                //全局设置主题颜色
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
                //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
                return new ClassicsHeader(context);
            }
        });

        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull
                    RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //配置LeakCanary
        setupLeakCanary();
        Logger.addLogAdapter(new AndroidLogAdapter());
        //使用ToastHelper,必须初始化
        ToastHelper.init(this);
        setupDatabase();
        sharedPreferences = getSharedPreferences(AppPreferences.PreferenceKey.SP_NAME_NAME,MODE_PRIVATE);
    }

    private void setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {

        DatabaseContext databaseContext = new DatabaseContext(this);
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(databaseContext, "evaporation.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

//        //创建数据库shop.db"
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "evaporation.db", null);
//        //获取可写数据库
//        SQLiteDatabase db = helper.getWritableDatabase();
//        //获取数据库对象
//        DaoMaster daoMaster = new DaoMaster(db);
//        //获取Dao对象管理者
//        daoSession = daoMaster.newSession();

    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
