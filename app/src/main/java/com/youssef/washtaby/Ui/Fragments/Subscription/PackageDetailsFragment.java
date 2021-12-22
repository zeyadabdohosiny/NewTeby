package com.youssef.washtaby.Ui.Fragments.Subscription;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.youssef.washtaby.Data.RetrofitClient;
import com.youssef.washtaby.Models.PackageModle.Datum;
import com.youssef.washtaby.Models.Subsribe.Subscribe;
import com.youssef.washtaby.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PackageDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PackageDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";
    private static final String ARG_PARAM7 = "param7";
    // Views
    TextView tvPackageTitle, tvMonthlyCost, tvthreeMonthlyCost, tvYearlyCost, tvFeature;
    // TODO: Rename and change types of parameters
    private String typeOfsubScription, packageTittle, monthlycost, threemonthsCost, yearlycost, feature;
    ImageView ivMonthCheck, ivtThreeMonthesCheck, ivYearlyCheack;
    LinearLayout llYear, llMonth, llThreeMonths;
    Button btnSubscribe;
    // Variables
    int packageId, subscriptionTypeId;
    boolean check = true;
    Datum datum;
    // Shared Prefrance
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String token;

    public PackageDetailsFragment() {
        // Required empty public constructor
    }


    public static PackageDetailsFragment newInstance(Datum datum1, String typeOfsubScription, String packageTittle,
                                                     String monthlycost, String threemonthsCost, String yearlycost, String feature, int packageId) {
        PackageDetailsFragment fragment = new PackageDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("Name", datum1);
        args.putString(ARG_PARAM1, typeOfsubScription);
        args.putString(ARG_PARAM2, packageTittle);
        args.putString(ARG_PARAM3, monthlycost);
        args.putString(ARG_PARAM4, threemonthsCost);
        args.putString(ARG_PARAM5, yearlycost);
        args.putString(ARG_PARAM6, feature);
        args.putInt(ARG_PARAM7, packageId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            datum = (Datum) getArguments().getSerializable("Name");
            typeOfsubScription = getArguments().getString(ARG_PARAM1);
            packageTittle = getArguments().getString(ARG_PARAM2);
            monthlycost = getArguments().getString(ARG_PARAM3);
            threemonthsCost = getArguments().getString(ARG_PARAM4);
            yearlycost = getArguments().getString(ARG_PARAM5);
            feature = getArguments().getString(ARG_PARAM6);
            packageId = getArguments().getInt(ARG_PARAM7);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_package_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        initSharedPreferences();
        setData();
        handleClicks();
    }

    private void initSharedPreferences() {
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor =preferences.edit();
        token ="bearer "+ preferences.getString("token", "");
    }

    private void handleClicks() {
        llYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivYearlyCheack.setVisibility(View.VISIBLE);
                ivMonthCheck.setVisibility(View.GONE);
                ivtThreeMonthesCheck.setVisibility(View.GONE);
                packageId = 3;
                check = true;
            }
        });
        llThreeMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivMonthCheck.setVisibility(View.GONE);
                ivYearlyCheack.setVisibility(View.GONE);
                ivtThreeMonthesCheck.setVisibility(View.VISIBLE);
                subscriptionTypeId = 2;

            }
        });
        llMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivMonthCheck.setVisibility(View.VISIBLE);
                ivYearlyCheack.setVisibility(View.GONE);
                ivtThreeMonthesCheck.setVisibility(View.GONE);
                subscriptionTypeId = 1;

            }
        });
        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeOfsubScription.equalsIgnoreCase("promotion")) {
         //           showTost("promotion");
                    RetrofitClient.retrofitClient.subscribe(packageId, subscriptionTypeId,token).enqueue(new Callback<Subscribe>() {
                        @Override
                        public void onResponse(Call<Subscribe> call, Response<Subscribe> response) {
                            if (response.isSuccessful()) {
                         //       showTost("done");
                                String url = response.body().getData();
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .add(R.id.frame, WebVIewFragment.newInstance(url, ""))
                                        .addToBackStack(null)
                                        .commit();


                            } else {
                            //    showTost("notSucces" + response.message());

                            }

                        }

                        @Override
                        public void onFailure(Call<Subscribe> call, Throwable t) {
                            showTost("Faliur");

                        }
                    });
                } else {
                    RetrofitClient.retrofitClient.packageRenew(subscriptionTypeId,token).enqueue(new Callback<Subscribe>() {
                        @Override
                        public void onResponse(Call<Subscribe> call, Response<Subscribe> response) {
                            if (response.isSuccessful()) {
                       //         showTost("done");
                                String url = response.body().getData();
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame, WebVIewFragment.newInstance(url, ""))
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                showTost("notSucces" + response.message());
                            }

                        }

                        @Override
                        public void onFailure(Call<Subscribe> call, Throwable t) {
                            showTost("Faliur");
                        }
                    });


                }

            }
        });

    }

    private void initUi(View view) {
        tvPackageTitle = view.findViewById(R.id.tv_header_title);
        tvMonthlyCost = view.findViewById(R.id.tv_monthly_package);
        tvthreeMonthlyCost = view.findViewById(R.id.tv_three_months_package);
        tvYearlyCost = view.findViewById(R.id.tv_yearly_package);
        tvFeature = view.findViewById(R.id.tv_package_feature);
        ivMonthCheck = view.findViewById(R.id.iv_monthly_check);
        ivtThreeMonthesCheck = view.findViewById(R.id.iv_three_months_check);
        ivYearlyCheack = view.findViewById(R.id.iv_year_check);
        llYear = view.findViewById(R.id.ll_year);
        llMonth = view.findViewById(R.id.llmonth);
        llThreeMonths = view.findViewById(R.id.ll_three_monthes);
        btnSubscribe = view.findViewById(R.id.btn_subscribe);


    }

    public void setData() {
        tvPackageTitle.setText(packageTittle);
        tvMonthlyCost.setText(monthlycost);
        tvthreeMonthlyCost.setText(threemonthsCost);
        tvYearlyCost.setText(yearlycost);
        tvFeature.setText("- " + feature);
        subscriptionTypeId = 3;
    }

    public void showTost(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}