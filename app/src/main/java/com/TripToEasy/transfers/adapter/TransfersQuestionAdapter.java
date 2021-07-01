package com.TripToEasy.transfers.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.TripToEasy.R;
import com.TripToEasy.model.parsingModel.transfers.TransfersQuestionsInfo;

import java.util.List;

/**
 * Created by ptblr-1195 on 5/6/17.
 */

public class TransfersQuestionAdapter extends RecyclerView.Adapter<TransfersQuestionVH> {
    String[] time_slot_arr={"00:00","00:30","01:00","01:30","02:00","02:30","03:00","03:30",
            "04:00","04:30","05:00","05:30","06:00","06:30","07:00","07:30","08:00","08:30",
            "09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30",
            "14:00","14:30","15:00","15:30","16:00","16:30","17:00","27:30","18:00","18:30",
            "19:00","19:30","20:00","20:30","21:00","21:30","22:00","22:30","23:00","23:30",
    };
    Activity activity;
    TransfersQuestionVH transfersQuestionVH;
    List<TransfersQuestionsInfo> transfersQuesInfos;
    TransfersQuestionVH viewHolder;

    public TransfersQuestionAdapter(Activity activity,
                                    List<TransfersQuestionsInfo> transfersQuesInfos) {
        this.activity = activity;
        this.transfersQuesInfos = transfersQuesInfos;
    }

    @Override
    public TransfersQuestionVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.transfers_question_item,parent,false);
        transfersQuestionVH = new TransfersQuestionVH(view,activity);
        return transfersQuestionVH;
    }

    @Override
    public void onBindViewHolder(final TransfersQuestionVH holder, final int position) {

        viewHolder = holder;

        if(transfersQuesInfos.get(position).getQuestion() != null){
            holder.question.setText(transfersQuesInfos.get(position).getQuestion());
        }
        if(transfersQuesInfos.get(position).getSubTitle() != null){
            holder.answer.setHint(transfersQuesInfos.get(position).getSubTitle());
        }
        if(transfersQuesInfos.get(position).getAnswer() != null){
            holder.answer.setText(transfersQuesInfos.get(position).getAnswer());
        }

        holder.answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(transfersQuesInfos.get(position).getStringQuestionId().equals("transfer_arrival_time")||
                        transfersQuesInfos.get(position).getStringQuestionId().equals("transfer_departure_time")||
                        transfersQuesInfos.get(position).getStringQuestionId().equals("boarding_time")){
                    //open time picker dialog
                    //timePickerDialog();
                }

                if(transfersQuesInfos.get(position).getStringQuestionId().equals("transfer_departure_date")||
                        transfersQuesInfos.get(position).getStringQuestionId().equals("transfer_arrival_date")){
                    //open date picker dialog
                }
            }
        });


        holder.answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(holder.answer.getText().toString() != null &&
                        holder.answer.getText().toString().length() != 0){
                    transfersQuesInfos.get(position).setAnswer(holder.answer.getText().toString());
                }else {
                    if(holder.answer.getText().toString().length() == 0){
                        transfersQuesInfos.get(position).setAnswer(null);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return transfersQuesInfos.size();
    }

    private void timePickerDialog(){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.transfers_timepicker_dialog);

        final ListView lv_timepicker = (ListView) dialog.findViewById(R.id.lv_times);
        lv_timepicker.setAdapter(new ArrayAdapter<String>(activity, R.layout.spinner_item,R.id.spinn_text, time_slot_arr));

        lv_timepicker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        dialog.show();
    }

}
