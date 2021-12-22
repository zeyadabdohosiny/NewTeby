package com.youssef.washtaby.Ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.youssef.washtaby.Models.FeatureModle.Datum;
import com.youssef.washtaby.Models.PackageModle.Feature;
import com.youssef.washtaby.R;

import java.util.ArrayList;
import java.util.List;

public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.FeatureViewHolder> {
     List<Datum> featureList=new ArrayList<>();
     int cheackedpostion =-1; // -1 no defult Selection  0 1st item Selected
     public static final String TAG="Feature_Adapter";

     int itemPosition;

    public FeatureAdapter(List<Datum> featureList) {
        this.featureList = featureList;
    }

    public class FeatureViewHolder extends RecyclerView.ViewHolder{
      TextView tvFeature;
      ImageView ivFeature;
        public FeatureViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFeature=itemView.findViewById(R.id.tv_feature_item);
            ivFeature=itemView.findViewById(R.id.iv_feature_item);
            itemPosition=getAdapterPosition();
            Log.d(TAG, ""+itemPosition);

        }
        void bind (Datum datum){
            if (cheackedpostion==-1){
                Log.d(TAG, "Cheack for no Click");
                ivFeature.setVisibility(View.GONE);
                //      cheackedpostion=itemPosition;
            }else{
                if(cheackedpostion==getAdapterPosition()){
                    ivFeature.setVisibility(View.VISIBLE);
                }else {
                    ivFeature.setVisibility(View.GONE);
                }
            }
            tvFeature.setText(datum.getTitle().toString());
            ivFeature.setImageResource(R.drawable.ic_baseline_check_circle_24);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ivFeature.setVisibility(View.VISIBLE);
                    if(cheackedpostion!=getAdapterPosition()) {
                        notifyItemChanged(cheackedpostion);
                        cheackedpostion = getAdapterPosition();
                        Log.d(TAG, "if");
                    }
                }
            });

        }
    }
    @NonNull
    @Override
    public FeatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.features_item_view,parent,false);
        FeatureViewHolder viewHolder=new FeatureViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeatureViewHolder holder, int position) {
        holder.bind(featureList.get(position));
        Datum feature=featureList.get(position);

    }

    @Override
    public int getItemCount() {
        return featureList.size();
    }
    public int  getfeatureId(){
        if(cheackedpostion!=-1)
        {
            return featureList.get(cheackedpostion).getId();
        }
        return 0;
    }

}
