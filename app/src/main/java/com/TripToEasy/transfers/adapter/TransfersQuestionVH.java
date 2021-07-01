package com.TripToEasy.transfers.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.TripToEasy.R;


/**
 * Created by ptblr-1195 on 5/6/17.
 */

public class TransfersQuestionVH extends RecyclerView.ViewHolder {

    public TextView question;
    public EditText answer;
    Activity context = null;

    public TransfersQuestionVH(View itemView,
                               final Activity context) {
        super(itemView);
        this.context = context;
        question = (TextView) itemView.findViewById(R.id.tv_question);
        answer = (EditText) itemView.findViewById(R.id.et_answer);


    }

}
