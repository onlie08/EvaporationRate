package com.ch.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ch.base.base.BaseApplication;
import com.ch.bean.Parameter;
import com.ch.bean.ParameterDao;
import com.ch.evaporationrate.R;
import com.ch.service.DataService;
import com.ch.service.bean.BeanOperaParam;
import com.ch.service.bean.BeanRTData;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProcessFragment extends Fragment {
    Unbinder unbinder;
    private Context mCtx; //xxg
    private ParameterDao parameterDao;
    private Parameter parameter;

    private DataService mDataService;//xxg
    private ServiceConnection connection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("Data service", "---service connected");

            mDataService = ((DataService.LocalBinder)iBinder).getService();

            mDataService.addOnDataCallback(new DataService.OnDataCallback() {
                @Override
                public void revRealTimeData(BeanRTData data) {

                    Log.d("Data service", "---activity get data:"+data.toString());
                }

                @Override
                public void revCalResult(Float testEvaR, Float staticEvaR) {
                    Log.d("Data service", "---calculate data:"+testEvaR+"--"+staticEvaR);
                }

                @Override
                public void revAcqTimeData(BeanRTData data) {
                    Log.d("Data service", "---revAcqTimeData data:"+data.toString());
                }

                @Override
                public void experimentOver(BeanRTData data, Float testEvaR, Float staticEvaR) {

                }
            });
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("Data service", "---service disconnected");
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //服务初始化时启动 //xxg
        mCtx = this.getActivity();
        Intent intent = new Intent(mCtx,DataService.class);
        ((Activity)mCtx).bindService(intent,connection,Context.BIND_AUTO_CREATE);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_process, container, false);
        unbinder = ButterKnife.bind(this, root);
        root.findViewById(R.id.btn_test_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mDataService!=null)
                {
                    /*
                     *      recPeroid   - 时间间隔范围(单位秒,30分钟)
                     *      mediumtype  - 介质类型（1-LN2,2-LNG）
                     *      expperiod   - 实验周期（单位秒 ，24小时）
                     *      lng         - LNG 计算参数
                     *      ln2         - LN2 计算参数
                     *      validV      - 有效容积（来自试验参数）单位L
                     */
                    long recPeroid = 3*60l;
                    int  mediumtype = 1;
                    long expperiod = 6*60l; //6分钟便于测试 ,应该一次实验室24小时
                    BeanOperaParam ln2 = new BeanOperaParam(1.2555f,808.61f,1f,1f);
                    BeanOperaParam lng = new BeanOperaParam(0.676f,422.53f,1f,1f);
                    float validV = 40.5f;

                    mDataService.startAcqData(recPeroid,mediumtype,expperiod,lng,ln2,validV);
                }

                Toast.makeText(mCtx,"test",Toast.LENGTH_SHORT).show();

            }
        });
        initView();
        initData();
        return root;
    }

    private void initView() {

    }

    private void initData() {
        parameterDao = BaseApplication.getDaoInstant().getParameterDao();
        List<Parameter> parameters =  parameterDao.loadAll();
        if(parameters.isEmpty()){
            return;
        }
        parameter = parameters.get(0);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
