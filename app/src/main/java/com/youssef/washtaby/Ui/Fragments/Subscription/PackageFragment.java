package com.youssef.washtaby.Ui.Fragments.Subscription;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.youssef.washtaby.Data.RetrofitClient;
import com.youssef.washtaby.Models.PackageModle.Datum;
import com.youssef.washtaby.Models.PackageModle.Feature;
import com.youssef.washtaby.Models.PackageModle.Package;
import com.youssef.washtaby.R;
import com.youssef.washtaby.Ui.adapters.PackageAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class PackageFragment extends Fragment {
    public static final String TAG="Packages_Fragment";
    private static final String ARG_PARAM1 = "param1";
    // Rv
    RecyclerView rvPackages;
    PackageAdapter packageAdapter;

    // Variables
    List<Datum> packageList=new ArrayList<>();
    List<Feature> featuresList=new ArrayList<>();
    String typeOfsubScription;
    // Shared Prefrance
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String token;
   // Views
    LinearLayout linearLayout;
    LottieAnimationView lottieAnimationView;

    public PackageFragment() {
        // Required empty public constructor
    }

    public static PackageFragment newInstance(String typeOfsubScription) {
        PackageFragment fragment = new PackageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, typeOfsubScription);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            typeOfsubScription = getArguments().getString(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_package, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor =preferences.edit();
        token ="bearer "+ preferences.getString("token", "");
        linearLayout.setVisibility(View.GONE);
        lottieAnimationView.setVisibility(View.VISIBLE);
        RetrofitClient.retrofitClient.getAllPackage(token).enqueue(new Callback<Package>() {
            @Override
            public void onResponse(Call<Package> call, Response<Package> response) {
                if(response.isSuccessful()) {
                    lottieAnimationView.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    packageList = response.body().getData();
                    initRecycleView(packageList);
                }else {
                    lottieAnimationView.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Response Not succes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Package> call, Throwable t) {
                Toast.makeText(getContext(), "On Failure", Toast.LENGTH_SHORT).show();
                lottieAnimationView.setVisibility(View.GONE);
            }
        });
    }

    private void initUi(View view) {
        rvPackages=view.findViewById(R.id.rv_packages);
        linearLayout=view.findViewById(R.id.ll_Data);
        lottieAnimationView=view.findViewById(R.id.animationView);


    }

    private void   initRecycleView(List<Datum> list){
        packageAdapter=new PackageAdapter(list);
        rvPackages.setAdapter(packageAdapter);
        packageAdapter.onUserClicklistner(new PackageAdapter.onItemClickListner() {
            @Override
            public void onClickListner(int position) {
                if (list.get(position).getTitle().equalsIgnoreCase("Free") || list.get(position).getTitle().equalsIgnoreCase("مجانية")) {

                    Toast.makeText(getContext(), "لا يمكن تجديد او الاشتراك", Toast.LENGTH_SHORT).show();

                }else {
                    int packageId = packageList.get(position).getId();
                    Log.d(TAG, "onClickListner: " + packageId);
                    Datum datum = packageList.get(position);
                    String packagetittle = datum.getTitle();
                    String monthlycost = datum.getSubscriptionTypes().get(0).getPivot().getCost().toString();
                    String threeMonthes = datum.getSubscriptionTypes().get(1).getPivot().getCost().toString();
                    String yealycost = datum.getSubscriptionTypes().get(2).getPivot().getCost().toString();
                    String packageFeature = packageList.get(position).getFeatures().get(0).getTitle();
                    Log.d(TAG, "onClickListner: " + packageId + "  " + monthlycost + " " + yealycost + " " + threeMonthes + "   " + packageFeature);
                    packageList.get(position);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame, PackageDetailsFragment.newInstance(datum,typeOfsubScription, packagetittle, monthlycost, threeMonthes, yealycost, packageFeature, packageId))
                            .addToBackStack(null)
                            .commit();
                    packageList.get(position).getSubscriptionTypes();
                }
            }
        });


  }
}