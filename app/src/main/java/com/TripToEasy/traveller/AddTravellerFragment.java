package com.TripToEasy.traveller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.database.DBAdapter;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.parsingModel.TravellerModel;
import com.TripToEasy.traveller.interfaces.NotifyTraveller;
import com.TripToEasy.utils.Global;
import com.TripToEasy.utils.MyDatePicker;

import butterknife.BindView;
import butterknife.OnClick;

public class AddTravellerFragment extends BaseFragment {

    @BindView(R.id.spinn_countrycode)
    Spinner spinn_countrycode;

    @BindView(R.id.layout_internation)
    LinearLayout layout_internation;

    @BindView(R.id.add_type)
    TextView addType;

    @BindView(R.id.first_name)
    EditText firstName;

    @BindView(R.id.last_name)
    EditText lastName;

    @BindView(R.id.male)
    TextView maleText;

    @BindView(R.id.female)
    TextView femaleText;

    @BindView(R.id.delete_icon)
    ImageView deleteImage;

    @BindView(R.id.et_dob) EditText et_dob;
    @BindView(R.id.ll_db) LinearLayout ll_db;

    @BindView(R.id.et_passport)
    EditText et_passport;

    @BindView(R.id.et_expiry)
    EditText et_expiry;

  /*  @BindView(R.id.tv_countrycode)
    TextView tv_countrycode;*/

    @OnClick(R.id.male)
    void maleSelection(){
        setMaleView();
    }

    private void setMaleView()
    {
        genderValue = 1;

        maleText.setBackground(getActivity().getResources().getDrawable(R.drawable.orange_butten));
        femaleText.setBackground(getActivity().getResources().getDrawable(R.drawable.white_butten));

        maleText.setTextColor(getActivity().getResources().getColor(R.color.white));
        femaleText.setTextColor(getActivity().getResources().getColor(R.color.black_sixty));
    }

    @OnClick(R.id.female)
    void femaleSelection(){
        setFemaleView();
    }

    private void setFemaleView() {
        genderValue = 2;

        maleText.setBackground(getActivity().getResources().getDrawable(R.drawable.white_butten));
        femaleText.setBackground(getActivity().getResources().getDrawable(R.drawable.orange_butten));

        maleText.setTextColor(getActivity().getResources().getColor(R.color.black_sixty));
        femaleText.setTextColor(getActivity().getResources().getColor(R.color.white));
    }

    /*
    @OnClick(R.id.save_data)
    void saveData(){
        if(firstName.getText().toString().length() == 0){
            commonUtils.toastShort("Please enter First Name", getActivity());
        }else if(lastName.getText().toString().length() == 0){
            commonUtils.toastShort("Please enter Last Name", getActivity());
        }else if(genderValue == 0){
            commonUtils.toastShort("Please select gender", getActivity());
        }else {
            TravellerModel travellerModel = new TravellerModel();
            travellerModel.setFirstName(firstName.getText().toString());
            travellerModel.setLastName(lastName.getText().toString());
            travellerModel.setGenderType(genderValue);
            travellerModel.setActionType(actionValue);
            dbAdapter.open();
            if(dbId == null){
                dbAdapter.insertTraveller(travellerModel);
            }else {
                dbAdapter.updateTraveller(travellerModel, dbId);
            }
            dbAdapter.close();
            notifyTraveller.notifyAddTraveller(actionValue);
        }
    }

    @OnClick(R.id.delete_icon)
    void deleteTraveller(){
        dbAdapter.open();
        int value = dbAdapter.deleteTraveller(dbId);
        dbAdapter.close();
        if(value == 1){
            notifyTraveller.notifyAddTraveller(actionValue);
        }else {
            commonUtils.toastShort("Failed to delete the selected item", getActivity());
        }
    }*/

    @OnClick(R.id.save_data)
    void saveData(){
        if(firstName.getText().toString().length() == 0)
        {
            commonUtils.toastShort("Please enter First Name", getActivity());
        }else if(lastName.getText().toString().length() == 0){
            commonUtils.toastShort("Please enter Last Name", getActivity());
        }else if(genderValue == 0){
            commonUtils.toastShort("Please select gender", getActivity());
        }else
        {
            if(Global.is_domestic_flag.equals("0") && frameType==1)
            {
              if(et_passport.getText().toString().trim().equals("")){
                    commonUtils.toastShort("Please enter Passport Number", getActivity());
                    return;
                }else if(et_expiry.getText().toString().trim().equals("")){
                    commonUtils.toastShort("Please enter Passport Expiry", getActivity());
                    return;
                }
            }

            if(actionValue==2 || actionValue==3)
                {
                   /* if(et_dob.getText().toString().trim().equals("")){
                        commonUtils.toastShort("Please enter DOB", getActivity());
                        return;
                    }*/
            }

            TravellerModel travellerModel = new TravellerModel();
            travellerModel.setFirstName(firstName.getText().toString().trim());
            travellerModel.setLastName(lastName.getText().toString().trim());
            travellerModel.setGenderType(genderValue);
            travellerModel.setActionType(actionValue);
            travellerModel.setDateOfBirth(et_dob.getText().toString().trim());
            if(Global.is_domestic_flag.equals("0") && frameType==1) {
                String passportIssueCountry=spinn_countrycode.getSelectedItem().toString();
                String numberOnly= passportIssueCountry.replaceAll("[^0-9]", "");
                travellerModel.setPassportNumber(et_passport.getText().toString().trim());
                travellerModel.setPassportExpiry(et_expiry.getText().toString().trim());
                travellerModel.setPassportCountry(numberOnly);
            }else {
                travellerModel.setPassportNumber("NA");
                travellerModel.setPassportExpiry("NA");
                travellerModel.setPassportCountry("NA");
            }
            dbAdapter.open();
            if(dbId == null){
                dbAdapter.insertTraveller(travellerModel);
            }else {
                dbAdapter.updateTraveller(travellerModel, dbId);
            }
            dbAdapter.close();
            notifyTraveller.notifyAddTraveller(actionValue,false);
        }
    }

    @OnClick(R.id.delete_icon)
    void deleteTraveller(){
        dbAdapter.open();
        int value = dbAdapter.deleteTraveller(dbId);
        dbAdapter.close();
        if(value == 1){
            notifyTraveller.notifyAddTraveller(actionValue,true);
        }else {
            commonUtils.toastShort("Failed to delete the selected item", getActivity());
        }
    }

    int actionValue, genderValue = 0;
    Integer dbId = null;
    NotifyTraveller notifyTraveller;
    DBAdapter dbAdapter;
    TravellerModel travellerModel;
    int frameType;
    @SuppressLint("ValidFragment")
    public AddTravellerFragment(int actionValue,
                                Object object,
                                Integer dbId, int frameType) {
        this.actionValue = actionValue;
        notifyTraveller = (NotifyTraveller) object;
        this.dbId = dbId;
        this.frameType=frameType;
    }

    public AddTravellerFragment() {
        /**
         * Empty constructor
         * */
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.add_traveller_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbAdapter = new DBAdapter(getActivity());

        if(frameType==1)
        {
            if(Global.is_domestic_flag.equals("0"))
            {
                layout_internation.setVisibility(View.VISIBLE);
                loadCountries();
            }
            else {
                layout_internation.setVisibility(View.GONE);
            }
        }else {
            layout_internation.setVisibility(View.GONE);
            ll_db.setVisibility(View.GONE);
        }



        switch (actionValue){
            case 1:
                addType.setText("Add Adult");
                break;
            case 2:
                addType.setText("Add Child");
                break;
            case 3:
                addType.setText("Add Infant");
                break;
        }
        if(dbId != null){
            deleteImage.setVisibility(View.VISIBLE);

            dbAdapter.open();
            travellerModel = dbAdapter.getIndividualTraveller(dbId);
            firstName.setText(travellerModel.getFirstName());
            lastName.setText(travellerModel.getLastName());

            if(travellerModel.getGenderType() == 1){
                setMaleView();
            }else {
                setFemaleView();
            }
            et_dob.setText(travellerModel.getDateOfBirth());
            et_passport.setText(travellerModel.getPassportNumber());
            et_expiry.setText(travellerModel.getPassportExpiry());
            dbAdapter.close();
        }

    }

    @OnClick(R.id.et_dob)
    void setDob(){
        Global.dateset_flag=1;
        MyDatePicker newFragment = new MyDatePicker(getActivity(),actionValue);
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    @OnClick(R.id.et_expiry)
    void setPassportExpiry(){
        Global.dateset_flag=2;
        MyDatePicker newFragment = new MyDatePicker(getActivity());
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    public void loadCountries(){
        Thread loadCountries = new Thread(){
            @Override
            public void run() {
               // dbAdapter.open();

                if (Global.arrCountry.size()==0){
                    for (int i=0;i<dbAdapter.getCountryList().size();i++){
                        Global.arrCountry.add(dbAdapter.getCountryList().get(i).getName()+"  ( +"+dbAdapter.getCountryList().get(i).getPhonecode()+" )");
                    }
                }

              //  dbAdapter.close();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        spinn_countrycode.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item,R.id.spinn_text, Global.arrCountry));
                        spinn_countrycode.setSelection(89);

                    }
                });
            }
        };
        loadCountries.start();
    }

}