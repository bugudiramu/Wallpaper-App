package com.example.wallapp;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallapp.models.WallPaper;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class WallPaperAdapter extends RecyclerView.Adapter<WallPaperAdapter.ImageViewHolder> {
    private Context context;
    private List<WallPaper> mWallpapers;
    int pos;

    public WallPaperAdapter(Context context, List<WallPaper> wallPapers) {
        this.context = context;
        this.mWallpapers = wallPapers;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item, parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view, context, mWallpapers);
        return imageViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
        final WallPaper wallPaper = mWallpapers.get(position);
        pos = position;
        // * Set the values to fields
        String smallImgUrl = wallPaper.getSmallImageUrl();
        String avatarName = wallPaper.getAvatarName();
        String avatarUrl = wallPaper.getAvatarImageUrl();

        Picasso.with(context).load(smallImgUrl).into(holder.mWallpaperImage);
        Picasso.with(context).load(avatarUrl).fit().into(holder.mAvatarImage);
//        holder.mAvatarName.setText(avatarName);

    }

    @Override
    public int getItemCount() {
        return mWallpapers.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mAvatarName;
        ImageView mWallpaperImage, mAvatarImage;
        List<WallPaper> wallPapers;
        Context context;

        public ImageViewHolder(@NonNull View itemView, Context context, List<WallPaper> wallPapers) {
            super(itemView);
            this.context = context;
            this.wallPapers = wallPapers;
//            mAvatarName = itemView.findViewById(R.id.avatar_name);
            mWallpaperImage = itemView.findViewById(R.id.wall_image);
            mAvatarImage = itemView.findViewById(R.id.avatar_image);
            itemView.setOnClickListener(this);
        }


        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, WallpaperDetail.class);
            intent.putExtra("image", wallPapers.get(getAdapterPosition()).getSmallImageUrl());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }

    }
}
