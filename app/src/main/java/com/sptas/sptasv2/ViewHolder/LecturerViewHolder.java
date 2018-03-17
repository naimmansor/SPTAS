package com.sptas.sptasv2.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sptas.sptasv2.R;

/**
 * Created by Na'im Mansor on 05-Mar-18.
 */

public class LecturerViewHolder extends RecyclerView.ViewHolder {

    public TextView txt_name, txt_email, txt_phone;


    public LecturerViewHolder(View itemView) {
        super(itemView);

        txt_name = (TextView) itemView.findViewById(R.id.txt_name);
        txt_email = (TextView) itemView.findViewById(R.id.txt_email);
        txt_phone = (TextView) itemView.findViewById(R.id.txt_phone);

    }
}
