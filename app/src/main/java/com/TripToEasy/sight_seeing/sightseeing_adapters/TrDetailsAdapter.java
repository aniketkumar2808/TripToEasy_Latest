package com.TripToEasy.sight_seeing.sightseeing_adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.TripToEasy.R;
import com.TripToEasy.model.parsingModel.sightSeeing.TRData;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TrDetailsAdapter extends RecyclerView.Adapter<TrDetailsAdapter.ViewHolder>
{
    public List<TRData> trDataList;
    //  Mr, Mrs , Miss, Master
    List<String> mTitle = new ArrayList<>();
    List<String> mWeightType = new ArrayList<>();
    List<String> mHeightType = new ArrayList<>();
    ArrayAdapter TitleAdp, mWeightADP, mHeightADp;
    Context mContext;
    JSONArray mBookingQus;
    int SelWeightPos = -1;
    int SelHeightPos = -1;

    public TrDetailsAdapter(List<TRData> trDataList, Context mContext, JSONArray mBookingQus)
    {
        this.trDataList = trDataList;
        mTitle.add("Mr");
        mTitle.add("Mrs");
        mTitle.add("Miss");
        mTitle.add("Master");
        this.mContext = mContext;
        this.mBookingQus = mBookingQus;
        mWeightType.add("kgs");
        mWeightType.add("ponds");
        mHeightType.add("cm");
        mHeightType.add("ft/in");
        mWeightADP = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, android.R.id.text1, mWeightType);
        mHeightADp = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, android.R.id.text1, mHeightType);
        TitleAdp = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, android.R.id.text1, mTitle);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tr_details, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {
        holder.txtHeader.setText(trDataList.get(position).getHeader());
        holder.spinnerTitle.setAdapter(TitleAdp);


        holder.spinner_Height_type.setAdapter(mHeightADp);

        holder.edFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                trDataList.get(position).setFirstName(editable.toString());
            }
        });

        holder.edLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                trDataList.get(position).setLastName(editable.toString());
            }
        });

        holder.spinnerTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                trDataList.get(position).setTitle(i + 1);
                SelWeightPos = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
        try
        {
            for (int i = 0; i < mBookingQus.length(); i++)
            {
                JSONObject lJsonObject = mBookingQus.getJSONObject(i);
                if (lJsonObject.getString("stringQuestionId").equalsIgnoreCase("weights_passengerWeights"))
                {
                    holder.llWeight.setVisibility(View.VISIBLE);
                    holder.spinner_weight_type.setAdapter(mWeightADP);

                    trDataList.get(position).setShowWeight(true);

                    if (position == 0) {
                        holder.spinner_weight_type.setEnabled(true);
                        if (SelWeightPos != -1)
                            holder.spinner_weight_type.setSelection(SelWeightPos);
                        holder.spinner_weight_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                SelWeightPos = position;
                                trDataList.get(position).setWeightType(mWeightType.get(position));
//                                holder.spinner_weight_type.setOnItemSelectedListener(null);
//                                notifyDataSetChanged();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    } else {
                        holder.spinner_weight_type.setVisibility(View.INVISIBLE);

                    }

                    holder.etWeight.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            trDataList.get(position).setWeight(editable.toString());
                        }
                    });
                }
                if (lJsonObject.getString("stringQuestionId").equalsIgnoreCase("heights_passengerHeights"))
                {
                    holder.llHeight.setVisibility(View.VISIBLE);
                    trDataList.get(position).setShowHeight(true);
                    holder.spinner_Height_type.setAdapter(mWeightADP);


                    if (position == 0) {
                        holder.spinner_Height_type.setEnabled(true);
                        if (SelWeightPos != -1)
                            holder.spinner_Height_type.setSelection(SelHeightPos);
                        holder.spinner_Height_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                trDataList.get(position).setHeightType(mHeightType.get(position));
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    } else {
                        holder.spinner_Height_type.setVisibility(View.INVISIBLE);

                    }

                    holder.etHeight.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            trDataList.get(position).setHeight(editable.toString());
                        }
                    });


                }
                if (lJsonObject.getString("stringQuestionId").equalsIgnoreCase("dateOfBirth_dob"))
                {
                    holder.llDOB.setVisibility(View.VISIBLE);
                    trDataList.get(position).setShowDOB(true);
                    holder.txtDOB.setOnClickListener(new View.OnClickListener()
                                                     {
                                                         @Override
                                                         public void onClick(View view)
                                                         {
                                                             showDatePicker(holder.txtDOB, position);
                                                         }
                                                     }
                    );


                }
                if (lJsonObject.getString("stringQuestionId").equalsIgnoreCase("passport_expiry"))
                {
                    holder.llPassportExpire.setVisibility(View.VISIBLE);
                    trDataList.get(position).setShowPassportExpire(true);
                    holder.txtPassposrt.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            showDatePicker(holder.txtPassposrt, position);
                        }
                    });
                }
            }



        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    private void showDatePicker(final TextView textView, final int position)
    {
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -10);
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        c.add(Calendar.YEAR, 10);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        textView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        if (textView.getId() == R.id.txtDOB) {
                            trDataList.get(position).setDOB(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        } else {
                            trDataList.get(position).setPassportExpiryDate(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                        }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();

    }


    @Override
    public int getItemCount() {
        return trDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.txtHeader) TextView txtHeader;
        @BindView(R.id.spinnerTitle) Spinner spinnerTitle;
        @BindView(R.id.edFirstName) EditText edFirstName;
        @BindView(R.id.edLastName) EditText edLastName;
        @BindView(R.id.txtDOB) TextView txtDOB;
        @BindView(R.id.llWeight) LinearLayout llWeight;
        @BindView(R.id.llHeight) LinearLayout llHeight;
        @BindView(R.id.llDOB) LinearLayout llDOB;
        @BindView(R.id.llPassportExpire) LinearLayout llPassportExpire;
        @BindView(R.id.txtPassposrt) TextView txtPassposrt;
        @BindView(R.id.etHeight) EditText etHeight;
        @BindView(R.id.etWeight) EditText etWeight;
        @BindView(R.id.spinner_weight_type) Spinner spinner_weight_type;
        @BindView(R.id.spinner_Height_type) Spinner spinner_Height_type;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
