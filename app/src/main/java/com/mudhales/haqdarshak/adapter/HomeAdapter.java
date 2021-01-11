package com.mudhales.haqdarshak.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mudhales.haqdarshak.R;
import com.mudhales.haqdarshak.data.Article;
import com.mudhales.haqdarshak.ui.HomeFragment;
import com.mudhales.haqdarshak.ui.NewsDetailsFragment;

import java.io.Serializable;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<Article> mCountryDescList;
    private Context mContext;



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDescription;
        private ImageView ivImage;

        private ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            ivImage = itemView.findViewById(R.id.iv_image);

        }
    }

    public HomeAdapter(Context context, List<Article> countryDescList) {
        mCountryDescList = countryDescList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.row_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView);
        postView.setTag(viewHolder);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,
                                 final int position) {
        Article mCountryDesc = mCountryDescList.get(position);
        Glide.with(mContext).load(mCountryDesc.getUrlToImage()).placeholder(R.drawable.no_images_availale).error(R.drawable.no_images_availale).into(holder.ivImage); // Set image url to view by SM
        holder.tvTitle.setText(checkNotNull(mCountryDesc.getTitle())); // Set title to view by SM
        holder.tvDescription.setText(checkNotNull(mCountryDesc.getDescription())); // Set description to view by SM

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try {
                   Fragment fragment =new NewsDetailsFragment();
                   Bundle bundle=new Bundle();
                   bundle.putSerializable("data", mCountryDesc);
                   fragment.setArguments(bundle);
                   ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction()
                           .replace(R.id.screenContainer, fragment)
                           .addToBackStack("NewsDetailsFragment")
                           .commit();
               }catch (Exception e){
                   e.printStackTrace();
               }
            }
        });
    }

    private String checkNotNull(String strValue) {
        return TextUtils.isEmpty(strValue)?"N/A":strValue;
    }

    @Override
    public int getItemCount() {
        return mCountryDescList.size();
    }

    // To refresh the list by SM 201912111540
    public void refreshList(List<Article> countryDescList) {
        this.mCountryDescList = countryDescList;
        this.notifyDataSetChanged();
    }

}

