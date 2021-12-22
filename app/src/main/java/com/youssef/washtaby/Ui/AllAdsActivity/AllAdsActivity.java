package com.youssef.washtaby.Ui.AllAdsActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.youssef.washtaby.Data.RetrofitClient;
import com.youssef.washtaby.Models.Ad.ActionResponse;
import com.youssef.washtaby.Models.AllAds.Datum;
import com.youssef.washtaby.R;
import com.youssef.washtaby.Ui.Ad.AdActivity;
import com.youssef.washtaby.Ui.Ad.EditAdActivity;
import com.youssef.washtaby.Ui.Dialogs.FilterDialog.FilterDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllAdsActivity extends AppCompatActivity implements FilterDialog.onUserItemClickListner {
    public static final String TAG="All_Ads_Activity";
    RecyclerView recyclerView;
    TextView tvTitle, tvNoData;
    LottieAnimationView lottieAnimationView;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    private String token;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    int next, firstPage, lastPage;
    private LinearLayoutManager linearLayoutManager;
    private boolean loading = true;
    private boolean isGetData;
    private Integer categoryId,idOfArea;
    List<Datum> lstAds;
    com.youssef.washtaby.Models.AllAds.AdsAdapter adapter;
    //
    ArrayAdapter<CharSequence> sortingArrayAdapter;
    String[] sortingAdsArray;
    AutoCompleteTextView acSorting;
    TextView acFilter;
    String sortType ;
    boolean useFilter=true;
    ArrayList<Integer> adsIdList=new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
        initUi();
        handleClicks();
        String categoryTitle = getIntent().getStringExtra("category_name");
        tvTitle.setText(categoryTitle);
        InitRecycler();
        getData(20, firstPage,sortType,categoryId,idOfArea);
        setData();
        if (categoryId == 0) {
            categoryId = null;
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { //check for scroll down
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    Log.e("pastVisiblesItems", Integer.toString(pastVisiblesItems));
                    Log.e("visibleItemCount", Integer.toString(visibleItemCount));
                    Log.e("totalItemCount", Integer.toString(totalItemCount));

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            Log.e("paginate" , "yes");
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            // Do pagination.. i.e. fetch new data
                            if (next <= lastPage) {
                                if (isGetData) {
                                    getData(20, next,sortType,categoryId,idOfArea);
                                }
                                if (next == lastPage) {
                                    next++;
                                }
                            }
                            loading = true;
                        }
                    }
                }
            }
        });

    }

    private void initUi() {
        tvTitle = findViewById(R.id.tv_title);
        tvNoData = findViewById(R.id.tv_no_data);
        acSorting = findViewById(R.id.ac_sorting_ads);
        acFilter= findViewById(R.id.ac_filter_ads);


    }


    private void setData() {
        preferences = PreferenceManager.getDefaultSharedPreferences(AllAdsActivity.this);
        editor = preferences.edit();
        token = preferences.getString("token", "");
        categoryId = getIntent().getIntExtra("category_id", 0);
        //
        sortingAdsArray = getResources().getStringArray(R.array.sorting_array);
        sortingArrayAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.sorting_array, android.R.layout.simple_spinner_dropdown_item);
        acSorting.setAdapter(sortingArrayAdapter);

    }

    private void handleClicks() {
        acSorting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    sortType="highest";
                }

                else if(position==1){
                    sortType="lowest";
                }
                else if(position==2){
                    sortType="oldest";
                }
                else if(position==3){
                    sortType="latest";
                }
                getData(20,firstPage,sortType,categoryId,idOfArea);

            }
        });
      acFilter.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
           //   Toast.makeText(getApplicationContext(), "awad", Toast.LENGTH_SHORT).show();
              FilterDialog filterDialogSheet=new FilterDialog();
              filterDialogSheet.show(getSupportFragmentManager(),"sd");

          }
      });

    }


     void  getData(int paginate, int page,String sortType, Integer catid,Integer areaid) {
        isGetData = false;
        tvNoData.setVisibility(View.GONE);
//        recyclerView.setVisibility(View.GONE);
        lottieAnimationView.setVisibility(View.VISIBLE);


        RetrofitClient.getInstance().GetallAds(paginate, catid, page, sortType,areaid).enqueue(new Callback<com.youssef.washtaby.Models.AllAds.AdsResponse>() {
            @Override
            public void onResponse(Call<com.youssef.washtaby.Models.AllAds.AdsResponse> call, Response<com.youssef.washtaby.Models.AllAds.AdsResponse> response) {
                lottieAnimationView.setVisibility(View.GONE);
                Log.d(TAG, ""+sortType);
                String res = "";
                try {
                    res = new Gson().toJson(response.body());
                } catch (Exception e) {

                }

                if (response.isSuccessful()) {
                    Log.d(TAG,"ResponseSucsess");
                    Log.d(TAG, ""+sortType);
                    if (response.body() != null) {
                        if(catid!=null || areaid!=null){ // Check If user Use Filter Or not
                                    Log.d("Zoksh","Clear The list"+catid);
                                    lstAds.clear(); // Delete the Last ads Before Do the Filter


                        }else {
                            Log.d("Zoksh","Clear The list"+catid);
                        }

                        Log.d(TAG, ""+sortType);
                        isGetData = true;
                        lastPage = response.body().getData().getLastPage();
                        if (lastPage > 1 && next < lastPage ) {
                            next++;
                            Log.e("next" , Integer.toString(next));
                        }
                        Log.e("response size" , Integer.toString(response.body().getData().getData().size()));

                        lstAds.addAll(response.body().getData().getData());

                        if (lstAds.size() > 0) {
//                            recyclerView.setVisibility(View.VISIBLE);
                            adapter.notifyDataSetChanged();
                            Log.e("adapter size" , Integer.toString(adapter.getItemCount()));

                            adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent=new Intent(AllAdsActivity.this, AdActivity.class);
                                    intent.putExtra("ad_id", lstAds.get(position).getId());
                                    for (int i=0; i<lstAds.size() ; i++){
                                        adsIdList.add(lstAds.get(i).getId());
                                    }
                                    intent.putIntegerArrayListExtra("ads_list",adsIdList);
                                    intent.putExtra("next_page",next);
                                    intent.putExtra("last_page",lastPage);
                                    startActivity(intent);
                                }
                            });
                            adapter.setOnEditButtonClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                                    PopupMenu popup = new PopupMenu(AllAdsActivity.this, view);
                                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                        public boolean onMenuItemClick(MenuItem item) {
                                            switch (item.getItemId()) {
                                                case R.id.i_edit: {
                                                    startActivity(new Intent(AllAdsActivity.this, EditAdActivity.class).putExtra("ad_id", lstAds.get(position).getId()));
                                                    return true;
                                                }
                                                case R.id.i_delete: {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(AllAdsActivity.this);
                                                    builder.setMessage(getString(R.string.delete_question));
                                                    builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            deleteAd(lstAds.get(position).getId());
                                                        }
                                                    });
                                                    builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    });
                                                    builder.show();
                                                    return true;
                                                }
                                                default: return false;
                                            }
                                        }
                                    });
                                    popup.show();
                                }
                            });
                        } else {
                            tvNoData.setVisibility(View.VISIBLE);
                        }
                    } else {
                        tvNoData.setVisibility(View.VISIBLE);
                  //      Toast.makeText(AllAdsActivity.this, response.message()+"", Toast.LENGTH_SHORT).show();
                        Log.d("Awad", res);
                    }
                } else {
                    tvNoData.setVisibility(View.VISIBLE);
           //         Toast.makeText(AllAdsActivity.this, response.message()+"", Toast.LENGTH_SHORT).show();
                    Log.d("Awad", res);
                }
            }

            @Override
            public void onFailure(Call<com.youssef.washtaby.Models.AllAds.AdsResponse> call, Throwable t) {
                lottieAnimationView.setVisibility(View.GONE);
                tvNoData.setVisibility(View.VISIBLE);
                Toast.makeText(AllAdsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void InitRecycler() {
        firstPage = 1;
        next = 1;
        recyclerView = findViewById(R.id.ItemsRecycler);
        lottieAnimationView = findViewById(R.id.animationView);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        linearLayoutManager = new LinearLayoutManager(AllAdsActivity.this , LinearLayoutManager.VERTICAL , false);
        recyclerView.setLayoutManager(linearLayoutManager);
        lstAds = new ArrayList<>();
        adapter = new com.youssef.washtaby.Models.AllAds.AdsAdapter(lstAds, AllAdsActivity.this);
        recyclerView.setAdapter(adapter);
    }

    public void deleteAd(int ad_id) {
        lottieAnimationView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        RetrofitClient.getInstance().deleteAd(ad_id , "Bearer " + token).enqueue(new Callback<ActionResponse>() {
            @Override
            public void onResponse(Call<ActionResponse> call, Response<ActionResponse> response) {
                lottieAnimationView.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().isSuccess()) {
                  //          Toast.makeText(AllAdsActivity.this, getString(R.string.deleted_successfully), Toast.LENGTH_SHORT).show();
                            lstAds.clear();
                            getData(20 , 1,sortType,categoryId,idOfArea);

                        }
                    } else {
                        Toast.makeText(AllAdsActivity.this, response.toString() + "", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AllAdsActivity.this, response.toString() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ActionResponse> call, Throwable t) {
                lottieAnimationView.setVisibility(View.GONE);
                Toast.makeText(AllAdsActivity.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(Integer catId,Integer areaId) {
        Log.d("zoksh", "onClick: "+catId);
        getData(20, firstPage,sortType,catId,areaId);
    }
}