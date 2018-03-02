package com.sptas.sptasv2.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sptas.sptasv2.R;

/**
 * Created by Na'im Mansor on 02-Mar-18.
 */

public class StudentViewHolder extends RecyclerView.ViewHolder {

    public TextView txt_name, txt_email, txt_phone, txt_class, txt_sv, txt_score;


    public StudentViewHolder(View itemView) {
        super(itemView);

        txt_score = (TextView) itemView.findViewById(R.id.txt_score);
        txt_name = (TextView) itemView.findViewById(R.id.txt_name);
        txt_email = (TextView) itemView.findViewById(R.id.txt_email);
        txt_phone = (TextView) itemView.findViewById(R.id.txt_phone);
        txt_class = (TextView) itemView.findViewById(R.id.txt_class);
        txt_sv = (TextView) itemView.findViewById(R.id.txt_sv);

    }
}
