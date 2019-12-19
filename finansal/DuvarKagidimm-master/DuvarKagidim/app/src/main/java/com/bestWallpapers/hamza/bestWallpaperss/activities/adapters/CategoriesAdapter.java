package com.bestWallpapers.hamza.bestWallpaperss.activities.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bestWallpapers.hamza.bestWallpaperss.R;
import com.bestWallpapers.hamza.bestWallpaperss.activities.activities.DuvarKagidiActivity;
import com.bestWallpapers.hamza.bestWallpaperss.activities.models.Category;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {

    private Context context;
    private List<Category> categoryList;

    public CategoriesAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_categories, viewGroup, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
        Category c = categoryList.get(i);
        categoryViewHolder.textView.setText(c.name);
        Glide.with(context)
                .load(c.thumb)
                .into(categoryViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView;
        ImageView imageView;


        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_view_cat_name);
            imageView = itemView.findViewById(R.id.image_view);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int p = getAdapterPosition();
            Category c = categoryList.get(p);

            Intent intent = new Intent(context, DuvarKagidiActivity.class);
            intent.putExtra("category", c.name);
            context.startActivity(intent);
        }
    }
}
