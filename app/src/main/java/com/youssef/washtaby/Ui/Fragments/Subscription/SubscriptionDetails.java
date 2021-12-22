package com.youssef.washtaby.Ui.Fragments.Subscription;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.youssef.washtaby.Data.RetrofitClient;
import com.youssef.washtaby.Models.SupScriptionDetails.Data;
import com.youssef.washtaby.R;
import com.youssef.washtaby.Ui.Login.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubscriptionDetails extends Fragment {
    // SharedPreferences
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String token;
    // Views
    TextView tvHeaderTitle, tvtitle, tvCost, tv_start_date, tv_end_date, tvAdsNumber;
    Button btnPromotion, btnRenewal;
    LottieAnimationView lottieAnimationView;
    LinearLayout linearDataLayour;


    public SubscriptionDetails() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subscription_details, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        editor = preferences.edit();
        token = preferences.getString("token", "");
        if (token.isEmpty() || token == null) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        } else {
            initUi(view);
            handleClicks();
            getData();
        }
    }

    private void handleClicks() {
        btnPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, PackageFragment.newInstance("Promotion"))
                        .addToBackStack(null)
                        .commit();
            }
        });
        btnRenewal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageFragment fragment = new PackageFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, PackageFragment.newInstance("Rnewal"))
                        .addToBackStack(null)
                        .commit();


            }
        });
    }

    private void getData() {
        lottieAnimationView.setVisibility(View.VISIBLE);
        linearDataLayour.setVisibility(View.GONE);
        token = "bearer " + preferences.getString("token", "");
        RetrofitClient.retrofitClient.getSubScriptionDetail(token).enqueue(new Callback<com.youssef.washtaby.Models.SupScriptionDetails.SubscriptionDetails>() {
            @Override
            public void onResponse(Call<com.youssef.washtaby.Models.SupScriptionDetails.SubscriptionDetails> call, Response<com.youssef.washtaby.Models.SupScriptionDetails.SubscriptionDetails> response) {
                if (response.isSuccessful()) {
                    lottieAnimationView.setVisibility(View.GONE);
                    linearDataLayour.setVisibility(View.VISIBLE);
                    Data data = response.body().getData();
                    tvHeaderTitle.setText(data.getPackage().getTitle().toString());
                    tvtitle.setText(data.getPackage().getTitle().toString());
                    tvCost.setText(data.getCost().toString());
                    tv_start_date.setText(data.getCreatedAt());
                    tv_end_date.setText(data.getActiveUntil());
                    tvAdsNumber.setText(data.getNumberOfMyAds() + "/" + data.getPackage().getMaxNumberOfAds());
                    //
                    //     Toast.makeText(getContext(), "Response  succes", Toast.LENGTH_SHORT).show();
                } else {
                    lottieAnimationView.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Response not succes", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<com.youssef.washtaby.Models.SupScriptionDetails.SubscriptionDetails> call, Throwable t) {
                Toast.makeText(getContext(), "onFailure", Toast.LENGTH_SHORT).show();
                lottieAnimationView.setVisibility(View.GONE);
            }
        });
    }

    private void initUi(View view) {
        tvHeaderTitle = view.findViewById(R.id.tv_header_title);
        tvtitle = view.findViewById(R.id.tv_package_title);
        tvCost = view.findViewById(R.id.tv_cost);
        tv_start_date = view.findViewById(R.id.tv_strt_date);
        tv_end_date = view.findViewById(R.id.tv_end_date);
        tvAdsNumber = view.findViewById(R.id.tv_ads_number);
        btnPromotion = view.findViewById(R.id.btn_promotion);
        btnRenewal = view.findViewById(R.id.btn_renewal);
        lottieAnimationView = view.findViewById(R.id.animationView);
        linearDataLayour=view.findViewById(R.id.lldata);
    }

}