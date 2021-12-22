package com.youssef.washtaby.Ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.youssef.washtaby.Models.PackageModle.Datum;
import com.youssef.washtaby.Models.PackageModle.SubscriptionType;
import com.youssef.washtaby.R;

import java.util.ArrayList;
import java.util.List;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.PackageViewHolder> {
    List<com.youssef.washtaby.Models.PackageModle.Datum> pacList = new ArrayList<>();

    onItemClickListner mListner;

    public PackageAdapter(List<Datum> pacList) {
        this.pacList = pacList;
    }

    public void onUserClicklistner (onItemClickListner listner){
        this.mListner=listner;
    }

    public interface onItemClickListner{
    void onClickListner(int position);
    }

    public class PackageViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvAdscanAdded, tvMonthlyCost, tvThreeMonthsCost, tvYearlyCost;
        RelativeLayout relativeLayout;

        public PackageViewHolder(@NonNull View itemView,onItemClickListner listner) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tv_package_title);
            tvAdscanAdded=itemView.findViewById(R.id.tv_ads_can_added);
            tvYearlyCost=itemView.findViewById(R.id.tv_yearly_package_item);
            tvThreeMonthsCost=itemView.findViewById(R.id.tv_three_months_package_item);
            tvMonthlyCost=itemView.findViewById(R.id.tv_monthly_package_item);
            relativeLayout=itemView.findViewById(R.id.rl_subscription_types);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.onClickListner(getAdapterPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public PackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.package_item, parent, false);
        PackageViewHolder viewHolder = new PackageViewHolder(view,mListner);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PackageViewHolder holder, int position) {
        Datum datum=pacList.get(position);
        holder.tvTitle.setText(datum.getTitle().toString());
        holder.tvAdscanAdded.setText(datum.getDescription());
        Log.d("zox", "onBindViewHolder: "+datum.getSubscriptionTypes().size());
        if(datum.getSubscriptionTypes().size()!=1) {
            holder.tvMonthlyCost.setText(datum.getSubscriptionTypes().get(0).getCostPerMonth().toString());
            holder.tvThreeMonthsCost.setText(datum.getSubscriptionTypes().get(1).getPivot().getCost().toString());
            holder.tvYearlyCost.setText(datum.getSubscriptionTypes().get(2).getPivot().getCost().toString());
        }else {
         holder.relativeLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return pacList.size();
    }
}
