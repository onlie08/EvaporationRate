package com.ch.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ch.base.base.BaseApplication;
import com.ch.bean.Parameter;
import com.ch.db.DbManage;
import com.ch.evaporationrate.R;
import com.ch.utils.ToastHelper;
import com.ch.view.DateChooseController;
import com.ch.view.SpinnerController;
import com.deadline.statebutton.StateButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ParameterFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.btn_cancel)
    StateButton btnCancel;
    @BindView(R.id.btn_save)
    StateButton btnSave;
    @BindView(R.id.edit_checkout_company)
    EditText editCheckoutCompany;
    @BindView(R.id.edit_use_device_company)
    EditText editUseDeviceCompany;
    @BindView(R.id.edit_test_address)
    EditText editTestAddress;
    @BindView(R.id.edit_device_id)
    EditText editDeviceId;
    @BindView(R.id.tv_medium_type)
    TextView tvMediumType;
    @BindView(R.id.edit_device_name)
    EditText editDeviceName;
    @BindView(R.id.tv_device_type)
    TextView tvDeviceType;
    @BindView(R.id.edit_fullness_rate)
    EditText editFullnessRate;
    @BindView(R.id.edit_measurement_volume)
    EditText editMeasurementVolume;
    @BindView(R.id.edit_effective_volume)
    EditText editEffectiveVolume;
    @BindView(R.id.edit_license_no)
    EditText editLicenseNo;
    @BindView(R.id.tv_test_date)
    TextView tvTestDate;
    @BindView(R.id.tv_test_end_date)
    TextView tvTestEndDate;
    @BindView(R.id.edit_qualification_rate)
    EditText editQualificationRate;
    @BindView(R.id.tv_madein_date)
    TextView tvMadeinDate;
    @BindView(R.id.tv_liquid_filling_end_date)
    TextView tvLiquidFillingEndDate;

    @BindView(R.id.edit_design_standard)
    EditText editDesignStandard;
    @BindView(R.id.edit_device_madein_company)
    EditText editDeviceMadeinCompany;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_parameter, container, false);
        unbinder = ButterKnife.bind(this, root);
        initView();
        initData();
        return root;
    }

    private void initView() {

    }

    private void initData() {
        Parameter parameter = DbManage.getInstance().getParamter();
        if(null == parameter){
            return;
        }
        editCheckoutCompany.setText(parameter.getCheckoutCompany());
        editUseDeviceCompany.setText(parameter.getUseDeviceCompany());
        editTestAddress.setText(parameter.getTestAddress());
        editDeviceMadeinCompany.setText(parameter.getDeviceMadeinCompany());
        editDeviceId.setText(parameter.getDeviceId());
        tvMediumType.setText(parameter.getMediumType());
        tvTestDate.setText(parameter.getTestDate());
        editDeviceName.setText(parameter.getDeviceName());
        tvTestEndDate.setText(parameter.getTestEndDate());
        tvDeviceType.setText(parameter.getDeviceType());
        editQualificationRate.setText(parameter.getQualificationRate());
        editFullnessRate.setText(parameter.getFullnessRate());
        tvMadeinDate.setText(parameter.getMadeinDate());
        editMeasurementVolume.setText(parameter.getMeasurementVolume());
        tvLiquidFillingEndDate.setText(parameter.getLiquidFillingEndDate());
        editEffectiveVolume.setText(parameter.getEffectiveVolume());
        editDesignStandard.setText(parameter.getDesignStandard());
        editLicenseNo.setText(parameter.getLicenseNo());


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

    @OnClick({R.id.btn_cancel, R.id.btn_save, R.id.tv_medium_type, R.id.tv_device_type, R.id.tv_test_date, R.id.tv_test_end_date, R.id.tv_madein_date, R.id.tv_liquid_filling_end_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                break;
            case R.id.btn_save:
                if(checkInputLegal()){
                    saveDateToDb();
                }
                break;
            case R.id.tv_medium_type:
                List<String> mediumType = new ArrayList<>();
                mediumType.add("LN2");
                mediumType.add("LNG");
                SpinnerController spinnerMedium = new SpinnerController(getActivity());
                spinnerMedium.showSpinnerDialog(mediumType);
                spinnerMedium.setListener(new SpinnerController.SpinnerListener() {
                    @Override
                    public void selectResult(String date) {
                        tvMediumType.setText(date);
                    }
                });

                break;
            case R.id.tv_device_type:
                List<String> devieceType = new ArrayList<>();
                devieceType.add("储罐");
                devieceType.add("气瓶");
                devieceType.add("槽车");
                SpinnerController spinnerDevice = new SpinnerController(getActivity());
                spinnerDevice.showSpinnerDialog(devieceType);
                spinnerDevice.setListener(new SpinnerController.SpinnerListener() {
                    @Override
                    public void selectResult(String date) {
                        tvDeviceType.setText(date);
                    }
                });
                break;
            case R.id.tv_test_date:
                DateChooseController startDate = new DateChooseController(getActivity());
                startDate.showChooseDateDialog();
                startDate.setListener(new DateChooseController.DateChooseListener() {
                    @Override
                    public void dateResult(final String date1) {
                        DateChooseController endDate = new DateChooseController(getActivity());
                        endDate.showChooseDateDialog();
                        endDate.setListener(new DateChooseController.DateChooseListener() {
                            @Override
                            public void dateResult(String date2) {
                                tvTestDate.setText(date1 + "——" + date2);
                            }
                        });
                    }
                });
                break;
            case R.id.tv_test_end_date:
                DateChooseController testEndDate = new DateChooseController(getActivity());
                testEndDate.showChooseDateDialog();
                testEndDate.setListener(new DateChooseController.DateChooseListener() {
                    @Override
                    public void dateResult(final String date1) {
                        DateChooseController endDate = new DateChooseController(getActivity());
                        endDate.showChooseDateDialog();
                        endDate.setListener(new DateChooseController.DateChooseListener() {
                            @Override
                            public void dateResult(String date2) {
                                tvTestEndDate.setText(date1 + "——" + date2);
                            }
                        });
                    }
                });
                break;
            case R.id.tv_madein_date:
                DateChooseController madeinDate = new DateChooseController(getActivity());
                madeinDate.showChooseDateDialog();
                madeinDate.setListener(new DateChooseController.DateChooseListener() {
                    @Override
                    public void dateResult(final String date1) {
                        DateChooseController endDate = new DateChooseController(getActivity());
                        endDate.showChooseDateDialog();
                        endDate.setListener(new DateChooseController.DateChooseListener() {
                            @Override
                            public void dateResult(String date2) {
                                tvMadeinDate.setText(date1 + "——" + date2);
                            }
                        });
                    }
                });
                break;
            case R.id.tv_liquid_filling_end_date:
                DateChooseController fillingDate = new DateChooseController(getActivity());
                fillingDate.showChooseDateDialog();
                fillingDate.setListener(new DateChooseController.DateChooseListener() {
                    @Override
                    public void dateResult(final String date1) {
                        DateChooseController endDate = new DateChooseController(getActivity());
                        endDate.showChooseDateDialog();
                        endDate.setListener(new DateChooseController.DateChooseListener() {
                            @Override
                            public void dateResult(String date2) {
                                tvLiquidFillingEndDate.setText(date1 + "——" + date2);
                            }
                        });
                    }
                });
                break;
        }
    }

    private void saveDateToDb() {
        Parameter parameter = new Parameter();
        parameter.setCheckoutCompany(editCheckoutCompany.getText().toString().trim());
        parameter.setUseDeviceCompany(editUseDeviceCompany.getText().toString().trim());
        parameter.setTestAddress(editTestAddress.getText().toString().trim());
        parameter.setDeviceMadeinCompany(editDeviceMadeinCompany.getText().toString().trim());
        parameter.setDeviceId(editDeviceId.getText().toString().trim());
        parameter.setMediumType(tvMediumType.getText().toString().trim());
        parameter.setTestDate(tvTestDate.getText().toString().trim());
        parameter.setDeviceName(editDeviceName.getText().toString().trim());
        parameter.setTestEndDate(tvTestEndDate.getText().toString().trim());
        parameter.setDeviceType(tvDeviceType.getText().toString().trim());
        parameter.setQualificationRate(editQualificationRate.getText().toString().trim());
        parameter.setFullnessRate(editFullnessRate.getText().toString().trim());
        parameter.setMadeinDate(tvMadeinDate.getText().toString().trim());
        parameter.setMeasurementVolume(editMeasurementVolume.getText().toString().trim());
        parameter.setLiquidFillingEndDate(tvLiquidFillingEndDate.getText().toString().trim());
        parameter.setEffectiveVolume(editEffectiveVolume.getText().toString().trim());
        parameter.setDesignStandard(editDesignStandard.getText().toString().trim());
        parameter.setLicenseNo(editLicenseNo.getText().toString().trim());

        DbManage.getInstance().saveParamter(parameter);
        ToastHelper.showToast("保存成功");
    }

    private boolean checkInputLegal(){
        if(TextUtils.isEmpty(tvMediumType.getText().toString().trim())){
            ToastHelper.showToast("试验介质不能为空");
            return false;
        }
        if(TextUtils.isEmpty(editQualificationRate.getText().toString().trim())){
            ToastHelper.showToast("NER合格值不能为空");
            return false;
        }
        if(TextUtils.isEmpty(editEffectiveVolume.getText().toString().trim())){
            ToastHelper.showToast("有效容积不能为空");
            return false;
        }
        if(TextUtils.isEmpty(editDeviceId.getText().toString().trim())){
            ToastHelper.showToast("容器编号不能为空");
            return false;
        }
//        if(TextUtils.isEmpty(editCheckoutCompany.getText().toString().trim()) || TextUtils.isEmpty(editUseDeviceCompany.getText().toString().trim()) ||TextUtils.isEmpty(editTestAddress.getText().toString().trim()) ||TextUtils.isEmpty(editDeviceMadeinCompany.getText().toString().trim()) ||
//                TextUtils.isEmpty(editDeviceId.getText().toString().trim()) ||TextUtils.isEmpty(tvMediumType.getText().toString().trim()) ||TextUtils.isEmpty(tvTestDate.getText().toString().trim()) ||TextUtils.isEmpty(editDeviceName.getText().toString().trim()) ||
//                TextUtils.isEmpty(tvTestEndDate.getText().toString().trim()) ||TextUtils.isEmpty(tvDeviceType.getText().toString().trim()) ||TextUtils.isEmpty(editQualificationRate.getText().toString().trim()) ||TextUtils.isEmpty(editFullnessRate.getText().toString().trim()) ||
//                TextUtils.isEmpty(tvMadeinDate.getText().toString().trim()) ||TextUtils.isEmpty(editMeasurementVolume.getText().toString().trim()) ||TextUtils.isEmpty(tvLiquidFillingEndDate.getText().toString().trim()) ||TextUtils.isEmpty(editEffectiveVolume.getText().toString().trim()) ||
//                TextUtils.isEmpty(editDesignStandard.getText().toString().trim()) ||TextUtils.isEmpty(editLicenseNo.getText().toString().trim())){
//            return false;
//        }
        return true;
    }
}
