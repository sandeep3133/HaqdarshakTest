package com.mudhales.haqdarshak.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mudhales.haqdarshak.R;
import com.mudhales.haqdarshak.data.Article;
import com.mudhales.haqdarshak.utils.Utils;


public class NewsDetailsFragment extends Fragment {
  private Article article;
    View view;
    private TextView tvTitle, tvDescription;
    private ImageView ivImage;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            article = (Article) getArguments().getSerializable("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news_details, container, false);
        setUpUi();
        return view;
    }

    private void setUpUi() {
        tvTitle = view.findViewById(R.id.tv_title);
        tvDescription = view.findViewById(R.id.tv_description);
        ivImage = view.findViewById(R.id.iv_image);
        setData();
    }

    private void setData() {
        if (article!=null){
            Glide.with(getActivity()).load(article.getUrlToImage()).placeholder(R.drawable.no_images_availale).error(R.drawable.no_images_availale).into(ivImage); // Set image url to view by SM
            tvTitle.setText(Utils.checkNotNull(article.getTitle())); // Set title to view by SM
            tvDescription.setText(Utils.checkNotNull(article.getDescription()));
        }
    }
}