package com.sptas.sptasv2.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sptas.sptasv2.Interface.ItemClickListener;
import com.sptas.sptasv2.R;

public class ChapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_name, txt_no;

    private ItemClickListener itemClickListener;

    public ChapterViewHolder(View itemView) {
        super(itemView);

        txt_name = itemView.findViewById(R.id.txt_name);
        txt_no = itemView.findViewById(R.id.txt_no);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
