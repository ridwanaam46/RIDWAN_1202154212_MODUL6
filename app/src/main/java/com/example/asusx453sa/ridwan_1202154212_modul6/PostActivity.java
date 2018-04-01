package com.example.asusx453sa.ridwan_1202154212_modul6;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PostActivity extends RecyclerView.Adapter<PostActivity.ViewHolder> {
    Context context;
    List<Post> postList;

    public PostActivity(Context context, List<Post> postList){
        this.context = context;
        this.postList = postList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvusername;
        public TextView tvtitle;
        public TextView tvdesc;
        public ImageView imageView;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            tvusername = itemView.findViewById(R.id.username_post);
            tvtitle = itemView.findViewById(R.id.title_post);
            tvdesc = itemView.findViewById(R.id.desc_post);
            imageView = itemView.findViewById(R.id.image_post);
            cardView = itemView.findViewById(R.id.cv_post);
        }
    }

    @NonNull
    @Override
    public PostActivity.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Post post = postList.get(position);
        holder.tvusername.setText(post.getUsernamePost());

        Glide.with(context).load(post.getImagePost()).into(holder.imageView);

        holder.tvtitle.setText(post.getTitlePost());
        holder.tvdesc.setText(post.getDescPost());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id", post.getId());
                intent.putExtra("Username", post.getUsernamePost());
                intent.putExtra("image", post.getImagePost());
                intent.putExtra("Title", post.getTitlePost());
                intent.putExtra("Description", post.getDescPost());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


}
