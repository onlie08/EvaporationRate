package com.ch.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ch.base.base.BaseApplication;
import com.ch.bean.Parameter;
import com.ch.bean.ParameterDao;
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
    @BindView(R.id.edit_liquid_filling_end_date)
    TextView editLiquidFillingEndDate;
    @BindView(R.id.edit_design_standard)
    EditText editDesignStandard;
    @BindView(R.id.edit_device_madein_company)
    EditText editDeviceMadeinCompany;

    ParameterDao parameterDao;
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
        parameterDao = BaseApplication.getDaoInstant().getParameterDao();
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

    @OnClick({R.id.btn_cancel, R.id.btn_save, R.id.tv_medium_type, R.id.tv_device_type, R.id.tv_test_date, R.id.tv_test_end_date, R.id.tv_madein_date, R.id.edit_liquid_filling_end_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                break;
            case R.id.btn_save:
                break;
            case R.id.tv_medium_type:
                List<String> mediumType = new ArrayList<>();
                mediumType.add("介质类型1");
                mediumType.add("介质类型2");
                SpinnerController spinnerController = new SpinnerController(getActivity());
                spinnerController.showSpinnerDialog(mediumType);
                spinnerController.setListener(new SpinnerController.SpinnerListener() {
                    @Override
                    public void selectResult(String date) {
                        tvMediumType.setText(date);
                    }
                });

                break;
            case R.id.tv_device_type:
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
                break;
            case R.id.tv_madein_date:
                break;
            case R.id.edit_liquid_filling_end_date:
                break;
        }
    }
}
