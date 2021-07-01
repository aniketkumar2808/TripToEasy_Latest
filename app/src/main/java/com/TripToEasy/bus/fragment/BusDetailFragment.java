package com.TripToEasy.bus.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.RequestParams;
import com.TripToEasy.R;
import com.TripToEasy.bus.adapter.CustomAdapter;
import com.TripToEasy.bus.adapter.DropAdapter;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.parsingModel.bus.DropOffBean;
import com.TripToEasy.model.parsingModel.bus.PickUpBean;
import com.TripToEasy.model.parsingModel.bus.SeatingBean;
import com.TripToEasy.model.requestModel.bus.BusDetailRequest;
import com.TripToEasy.traveller.TravellerFragment;
import com.TripToEasy.utils.Global;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

public class BusDetailFragment extends BaseFragment implements WebInterface, AdapterView.OnItemClickListener {
    HashMap<Integer,String> seating_selection_list1 = new HashMap<>();
    HashMap<Integer,String> seq_no_list = new HashMap<>();
    HashMap<Integer,String> fare_list = new HashMap<>();
    private static DecimalFormat df2 = new DecimalFormat(".##");
    @BindView(R.id.seating_table)
    TableLayout seating_table;

    @BindView(R.id.top_lay)
    LinearLayout topLayout;

    @BindView(R.id.text_one)
    TextView seatCount;

    @BindView(R.id.total_price)
    TextView totalPrice;

    @BindView(R.id.destination_lower)
    TextView lowerBirthText;

    @BindView(R.id.destination_upper)
    TextView upperBirthText;

    @OnClick(R.id.destination_lower)
    void lowerBirth(){
       /* if (click == 0) {
            lowerBirthText.setBackgroundColor(Color.parseColor("#ffffff"));
            lowerBirthText.setBackgroundColor(Color.parseColor("#2c7ee9"));
            setSleeperLayout(rows_g, columns_g, seating_list, "1");
            click = 1;
        }*/
        lowerBirthText.setTextColor(getActivity().getResources().getColor(R.color.white));
        lowerBirthText.setBackground(getActivity().getResources().getDrawable(R.drawable.orange_butten));
        upperBirthText.setTextColor(getActivity().getResources().getColor(R.color.b_six));
        upperBirthText.setBackground(getActivity().getResources().getDrawable(R.drawable.white_butten));
        setOldSleeperLayout(rows_g,columns_g,seating_list,"Lower");

    }

    @OnClick(R.id.destination_upper)
    void upperBirth(){
       /* if (click == 1) {
            upperBirthText.setBackgroundColor(Color.parseColor("#2c7ee9"));
            upperBirthText.setBackgroundColor(Color.parseColor("#ffffff"));
            setSleeperLayout(rows_g, columns_g, seating_list, "2");
            click = 0;
        }*/
        upperBirthText.setTextColor(getActivity().getResources().getColor(R.color.white));
        upperBirthText.setBackground(getActivity().getResources().getDrawable(R.drawable.orange_butten));

        lowerBirthText.setTextColor(getActivity().getResources().getColor(R.color.b_six));
        lowerBirthText.setBackground(getActivity().getResources().getDrawable(R.drawable.white_butten));

        setOldSleeperLayout(rows_g,columns_g,seating_list,"Upper");
    }

    @OnClick(R.id.book_now)
    void bookNowAction(){

        String seatNom = seatCount.getText().toString();

        if (TextUtils.isEmpty(seatNom)) {
            Toast.makeText(getActivity(), "Please select seat.", Toast.LENGTH_SHORT).show();

        } else if (pickupLocation == null) {
            boarding_name.setFocusable(true);
            boarding_name.setError("");
            Toast.makeText(getActivity(), "Please select boarding point.", Toast.LENGTH_SHORT).show();

        } else if ((dropingTo == null) || dropingTo.equalsIgnoreCase("")) {

            dropping_name.setFocusable(true);
            dropping_name.setError("");
            Toast.makeText(getActivity(), "Please select dropping point.", Toast.LENGTH_SHORT).show();
        } else
        {
            if(!totalPrice.getText().toString().equals("0") ||
                    !totalPrice.getText().toString().equals(""))
            {
                proceedPay();
            }else
            {
                Toast.makeText(getActivity(), "Please select a seat to proceed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @BindView(R.id.boarding_name)
    TextView boarding_name;

    @BindView(R.id.dropping_name)
    TextView dropping_name;


    @OnClick(R.id.boarding_name)
    void selectBoardingPoint(){
        selectBoard();
    }
    @OnClick(R.id.dropping_name)
    void selectDroppingPoint(){
        selectDrop();
    }

    SeatingBean seatingbean;
    PickUpBean pickupbean;
    List<SeatingBean> finalSeat = new ArrayList<SeatingBean>();
    ArrayList<SeatingBean> seating_list = new ArrayList<>();
    ArrayList<PickUpBean> pick_up_list = new ArrayList<PickUpBean>();
    ArrayList<DropOffBean> drop_off_list = new ArrayList<DropOffBean>();
    List<Integer> tracking_list = new ArrayList<Integer>();
    List<String> seating_selection_list = new ArrayList<String>();
    HashMap<String,String> map_rates_storing = new HashMap<String,String>();
    JSONArray cancArray = new JSONArray();
    String routScheduleId, routCode, departDate, resultToken, searchId, bookingSource,
            isSleeper = "",boardingname="",boarding_id="",schedule_id="",schedule_date="",
            busoperator="",from_id="", to_id="",isAc="",from_bus="",to_bus="",time="",
            arrival_time="",base_fare="",resp_fare="", bus_name="",bus_type="",bus_type_name="",CommAmount="",
            token="",token_key="",journeyDate,arrival_date,rootSchedulerId,
            searSelectionResponse, pickupLocation = null;
    Integer rows_g, columns_g,click;
    //private int click = 0;
    WebServiceController webServiceController;

    @SuppressLint("ValidFragment")
    public BusDetailFragment(String from_id,String to_id,String routScheduleId, String routCode, String departDate,
                             String resultToken, String searchId, String bookingSource) {
        this.from_id=from_id;
        this.to_id=to_id;
        this.routScheduleId = routScheduleId;
        this.routCode = routCode;
        this.departDate = departDate;
        this.resultToken = resultToken;
        this.searchId = searchId;
        this.bookingSource = bookingSource;
    }

    public BusDetailFragment() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.bus_seating_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webServiceController =
                new WebServiceController(getActivity(),BusDetailFragment.this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callDetailApi();
    }

    private void callDetailApi() {
        BusDetailRequest busDetailRequest =
                new BusDetailRequest(routScheduleId, departDate, routCode,
                        searchId, resultToken, bookingSource);
        RequestParams requestParams = new RequestParams();
        requestParams.put("bus_details", gson.toJson(busDetailRequest));
        webServiceController.postRequest(apiConstants.BUS_DETAIL, requestParams, 1);
    }

    String currency="";
    //{"status":0,"message":"Unable To Fetch The Data"}
    @Override
    public void getResponse(String response, int flag) {
        try{
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.getInt("status") == 1){
                //TODO : Parse data
                JSONObject datObject = jsonObject.getJSONObject("data");
                JSONObject detailsObject = datObject.getJSONObject("details");
                JSONObject routeObject = detailsObject.getJSONObject("Route");

                if(routeObject.has("HasSleeper")){
                    isSleeper = routeObject.getString("HasSleeper");
                }else {
                    isSleeper="false";
                }

                schedule_id = routeObject.getString("RouteScheduleId");
                schedule_date = routeObject.getString("DepartureTime");
                from_bus = routeObject.getString("From");
                to_bus = routeObject.getString("To");
                isAc = routeObject.getString("HasAC");
                journeyDate= routeObject.getString("DeptTime");
                arrival_time = routeObject.getString("ArrivalTime");
                arrival_date= routeObject.getString("ArrTime");
                base_fare = String.valueOf((double)routeObject.getInt("Fare")/ Double.parseDouble(Global.currencyValue));
                resp_fare = String.valueOf((double)routeObject.getInt("Fare")/ Double.parseDouble(Global.currencyValue));
                bus_name = routeObject.getString("CompanyName");
                bus_type = routeObject.getString("BusLabel");
                bus_type_name = routeObject.getString("BusTypeName");
                //CommAmount = routeObject.optString("CommAmount");
                if(routeObject.has("CommAmount")){
                    CommAmount = String.valueOf((double)routeObject.getInt("CommAmount")/
                            Double.parseDouble(Global.currencyValue));
                }else {
                    CommAmount="0.0";
                }

                JSONObject currencyObj = datObject.optJSONObject("currency_obj");


                currency = currencyObj.optString("to_currency");
                JSONObject resultObj = detailsObject.getJSONObject("result");
                cancArray = resultObj.optJSONArray("Canc");
                JSONObject layoutObj = resultObj.getJSONObject("layout");
                rows_g = layoutObj.getInt("MaxRows");
                columns_g = layoutObj.getInt("MaxCols");

                JSONArray valueArray = resultObj.getJSONArray("value");
                int i=0;
                while (i < valueArray.length()){
                    JSONObject valueObj = valueArray.getJSONObject(i);
                    seatingbean = new SeatingBean(valueObj.getInt("seq_no"),
                            valueObj.getInt("row"),valueObj.getInt("col"),
                            valueObj.getInt("width"),valueObj.getInt("height"),
                            valueObj.getInt("seat_type"), valueObj.getString("seat_no"),
                            (double)valueObj.getInt("total_fare")/Double.parseDouble(Global.currencyValue),
                            //(double)valueObj.getInt("Fare")/Double.parseDouble(Global.currencyValue),
                            //valueObj.getInt("base_fare"),
                            (double)valueObj.getInt("base_fare")/Double.parseDouble(Global.currencyValue),
                            valueObj.getInt("status"), valueObj.getString("decks"),
                            valueObj.getInt("MaxRows"),valueObj.getInt("MaxCols"),
                            valueObj.getInt("IsAvailable"));
                    seating_list.add(seatingbean);
                    i++;
                }

                JSONArray json_pickup = resultObj.optJSONArray("Pickups");
                JSONObject json_pick_object = null;

                for (int j = 0; j< json_pickup.length(); j++) {
                    json_pick_object = json_pickup.getJSONObject(j);

                    pickupbean = new PickUpBean();

                    pickupbean.setPickupid(json_pick_object.getString("PickupCode"));
                    pickupbean.setPickupname(json_pick_object.getString("PickupName"));
                    pickupbean.setPickuptime(json_pick_object.getString("PickupTime"));
                    pickupbean.setAddress(json_pick_object.getString("Address"));
                    pickupbean.setLandmark(json_pick_object.getString("Landmark"));

                    pickupbean.setContact(json_pick_object.getString("Contact"));

                    pick_up_list.add(pickupbean);
                }

                JSONArray dropArray = resultObj.optJSONArray("Dropoffs");


                for (int k = 0; k < dropArray.length(); k++) {
                    try {
                        JSONObject dropObj = dropArray.getJSONObject(k);

                        DropOffBean dropOffBean = new DropOffBean();
                        dropOffBean.setDropoffCode(dropObj.getString("DropoffCode"));
                        dropOffBean.setDropoffName(dropObj.getString("DropoffName"));
                        dropOffBean.setDropoffTime(dropObj.getString("DropoffTime"));

                        drop_off_list.add(dropOffBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if(isSleeper.equals("true")){
                    topLayout.setVisibility(View.VISIBLE);

                    if(rows_g>0 && columns_g>0)
                    {
                        setOldSleeperLayout(rows_g,columns_g,seating_list,"Lower");
                    }

                }else {
                    topLayout.setVisibility(View.GONE);

                    if(rows_g>0 && columns_g>0)
                    {
                        setOldSleeperLayout(rows_g,columns_g,seating_list,"Lower");
                    }

                }

            }else {
                commonUtils.toastShort(jsonObject.getString("message"), getActivity());
                getActivity().onBackPressed();
            }
        }catch (Exception e){
            e.printStackTrace();
            commonUtils.toastShort(e.getMessage(), getActivity());
        }
    }


    private void setOldSleeperLayout(Integer rows, Integer columns,
                                     final ArrayList<SeatingBean> seating_items, String deck) {
        if(seating_table.getChildCount() > 0){
            seating_table.removeAllViews();
        }

        TableRow[] tablerow = new TableRow[rows+1];
        ImageView[][] imageview = new ImageView[rows][columns];

        tablerow[0] = new TableRow(getActivity());

        for(int r=0;r<columns;r++){

            imageview[r][0] = new ImageView(getActivity());

            if(r == columns -1 && deck == "Lower"){
                imageview[r][0].setImageResource(R.drawable.ic_stairing);
            }else{

            }
            tablerow[0].addView(imageview[r][0]);
        }

        try {
            TableLayout.LayoutParams params12 = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            params12.setMargins(0,15,12,0);
            seating_table.addView(tablerow[0],params12);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        /**
         * Main logic
         * */
        for(int c= 0 ; c<rows; c++)
        {
            tablerow[c+1] = new TableRow(getActivity());
            for(int r=0;r<columns;r++){
                imageview[c][r] = new ImageView(getActivity());
                for(int iter=0; iter<seating_items.size() ; iter++) {
                    SeatingBean bean = seating_items.get(iter);
                    if(bean.getDecks().equals(deck)) {
                        if (c == bean.getRow() && r == bean.getCol()) {
                            if (bean.getIsAvailable() == 0 && bean.getSeatNo().equals("")) {
                                //Do Nothing
                            }else {
                                if (bean.getStatus() == 1 || bean.getStatus()==2 || bean.getStatus()==3)
                                {
                                    Log.e("available...", "true");
                                    imageview[c][r].setId(iter);
                                    imageview[c][r].setOnClickListener(new View.OnClickListener() {

                                        @SuppressLint({"UseValueOf", "SetTextI18n"})
                                        @Override
                                        public void onClick(View v) {
                                            ImageView view = (ImageView) v;
                                            seatingbean = seating_items.get(view.getId());

                                            if (tracking_list.contains(view.getId()))
                                            {
                                                Log.e("tracking_list", "contains view");
                                                if(seatingbean.getSeatType() == 2)
                                                {
                                                    /** SLEEPER */
                                                    if(seatingbean.getStatus()==1 || seatingbean.getStatus()==2 || seatingbean.getStatus()==3)
                                                    {
                                                        view.setImageResource(ordinarySleeperSeat("2"));
                                                    }
                                                    /*if(busSeatingBean.getIsAvail() == 2){
                                                        view.setImageResource(ordinarySleeperSeat(String.valueOf(busSeatingBean.getWidth())));
                                                    }else if(busSeatingBean.getIsAvail() == 3){
                                                        view.setImageResource(ordinarySleeperladies(String.valueOf(busSeatingBean.getWidth())));
                                                    }else{
                                                        view.setImageResource(ordinarySleeperSeat(String.valueOf(busSeatingBean.getWidth())));
                                                    }*/
                                                }else{
                                                    /** SEATER */
                                                    if(seatingbean.getStatus()==1 || seatingbean.getStatus()==2 || seatingbean.getStatus()==3)
                                                    {
                                                        view.setImageResource(R.drawable.ic_available);
                                                    }
                                                    /*if(busSeatingBean.getIsAvail() == 2){
                                                        view.setImageResource(R.drawable.item_seat_albooked);
                                                    }else if(busSeatingBean.getIsAvail() == 3){
                                                        view.setImageResource(R.drawable.item_seat_albooked);
                                                    }else if(busSeatingBean.getIsAvail() == 1){
                                                        view.setImageResource(R.drawable.item_seat_ordinary);
                                                    }*/
                                                }

                                              /*  if(!totalPrice.getText().toString().equals("")){
                                                    double totalclaal = Double.parseDouble(totalPrice.getText().toString())-Double.parseDouble(String.valueOf(seatingbean.getTotalFare()));
                                                    totalPrice.setText(""+Math.round(totalclaal));
                                                }*/
                                                if(!totalPrice.getText().toString().equals("")){
                                                    double totalclaal = Double.parseDouble(totalPrice.getText().toString().split(" ")[1])-Double.parseDouble(String.valueOf(seatingbean.getTotalFare()));
                                                    totalPrice.setText(Global.currencySymbol+" "+(df2.format(totalclaal)));
                                                }
                                                tracking_list.remove(new Integer(view.getId()));
                                                seating_selection_list1.remove(view.getId());
                                                seq_no_list.remove(view.getId());
                                                fare_list.remove(view.getId());
                                                StringBuilder sb = new StringBuilder();
                                                Collection<String> val = seating_selection_list1.values();
                                                ArrayList<String> ssl = new ArrayList<String>(val);
                                                for (String s : ssl) {
                                                    sb.append(s);
                                                    if (sb.length() > 0)
                                                        sb.append(",");
                                                }

                                                seatCount.setText(sb.toString());

                                                if(map_rates_storing.containsKey(seatingbean.getSeatNo())){
                                                    map_rates_storing.remove(seatingbean.getSeatNo());
                                                    finalSeat.add(seatingbean);
                                                }
                                            }
                                            else
                                            {
                                                if(tracking_list.size() < 6)
                                                {
                                                    if(seatingbean.getSeatType() == 2)
                                                    {
                                                        /** SLEEPER */
                                                        if(seatingbean.getStatus()==1 || seatingbean.getStatus()==2 || seatingbean.getStatus()==3)
                                                        {
                                                            view.setImageResource(ordinarySleeperSelected("2"));
                                                        }
                                                    }else {
                                                        /** SEATER */
                                                        if(seatingbean.getStatus()==1 || seatingbean.getStatus()==2 || seatingbean.getStatus()==3)
                                                        {
                                                            view.setImageResource(R.drawable.ic_selected);
                                                        }
                                                    }

                                                    tracking_list.add(view.getId());
                                                    seating_selection_list1.put(view.getId(),seatingbean.getSeatNo());
                                                    seq_no_list.put(view.getId(),String.valueOf(seatingbean.getSeqNo()));
                                                    fare_list.put(view.getId(),String.valueOf(seatingbean.getTotalFare()));

                                                    if(map_rates_storing.containsKey(seatingbean.getSeatNo())){
                                                        map_rates_storing.put(seatingbean.getSeatNo(),String.valueOf(seatingbean.getTotalFare()));
                                                        finalSeat.add(seatingbean);
                                                    }else{
                                                        map_rates_storing.put(seatingbean.getSeatNo(),String.valueOf(seatingbean.getTotalFare()));
                                                        finalSeat.add(seatingbean);
                                                    }

                                                   /* if(!totalPrice.getText().toString().equals("")){
                                                        double totalclaal = Double.parseDouble(totalPrice.getText().toString())+Double.parseDouble(String.valueOf(seatingbean.getTotalFare()));
                                                        totalPrice.setText(""+Math.round(totalclaal));
                                                    }else{
                                                        totalPrice.setText(""+Math.round(Float.parseFloat(String.valueOf(seatingbean.getTotalFare()))));
                                                    }*/
                                                    if(!totalPrice.getText().toString().equals("")){
                                                        double totalclaal = Double.parseDouble(totalPrice.getText().toString().split(" ")[1])+Double.parseDouble(String.valueOf(seatingbean.getTotalFare()));
                                                        totalPrice.setText(Global.currencySymbol+" "+(df2.format(totalclaal)));
                                                    }else{
                                                        totalPrice.setText(Global.currencySymbol+" "+(df2.format(Float.parseFloat(String.valueOf(seatingbean.getTotalFare())))));
                                                    }

                                                    StringBuilder sb = new StringBuilder();
                                                    Collection<String> val = seating_selection_list1.values();
                                                    ArrayList<String> ssl = new ArrayList<String>(val);
                                                    for (String s : ssl) {
                                                        sb.append(s);
                                                        if (sb.length() > 0)
                                                            sb.append(",");
                                                    }

                                                    seatCount.setText(sb.toString());
                                                } else {
                                                    Toast.makeText(getActivity(), "Maximum 6 seats allowed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    });


                                    if(bean.getSeatType() == 2)
                                    {
                                        /** SLEEPER */
                                        topLayout.setVisibility(View.VISIBLE);
                                        click = 1;
                                        if(bean.getStatus()==1 || bean.getStatus()==2 || bean.getStatus()==3)
                                        {
                                            imageview[c][r].setImageResource(ordinarySleeperSeat("2"));
                                        }else
                                        {
                                            if(bean.getStatus()==0 || bean.getStatus()==-2)
                                            {
                                                imageview[c][r].setImageResource(ordinarySleeperbooked("2"));
                                            }else if(bean.getStatus()==-3)
                                            {
                                                imageview[c][r].setImageResource(ordinarySleeperladies("2"));
                                            }
                                        }
                                        if (tracking_list.contains(iter)) {
                                            imageview[c][r].setImageResource(ordinarySleeperSelected(String.valueOf(bean.getWidth())));
                                        }
                                    }else
                                    {
                                        /** SEATER */
                                        if(bean.getStatus()==1 || bean.getStatus()==2 || bean.getStatus()==3)
                                        {
                                            imageview[c][r].setImageResource(R.drawable.ic_available);
                                        }else
                                        {
                                            if(bean.getStatus()==0 || bean.getStatus()==-2)
                                            {
                                                imageview[c][r].setImageResource(R.drawable.seat_blocked);
                                            }else if(bean.getStatus()==-3)
                                            {
                                                imageview[c][r].setImageResource(R.drawable.seat_res_ladies);
                                            }
                                        }
                                        if (tracking_list.contains(iter)) {
                                            imageview[c][r].setImageResource(R.drawable.ic_blocked);
                                        }
                                    }

                                    /*if(bean.getSeat_ty() == 2)
                                    {
                                        top_lay.setVisibility(View.VISIBLE);
                                        click = 1;
                                      if (bean.getIsAvail() == 2) {
                                            imageview[c][r].setImageResource(ordinarySleeperSelected(String.valueOf(bean.getWidth())));
                                        } else if (bean.getIsAvail() == 3) {
                                            imageview[c][r].setImageResource(ordinarySleeperladies(String.valueOf(bean.getWidth())));
                                        } else {
                                            imageview[c][r].setImageResource(ordinarySleeperSeat(String.valueOf(bean.getWidth())));
                                        }

                                        if (tracking_list.contains(iter)) {
                                            imageview[c][r].setImageResource(ordinarySleeperSelected(String.valueOf(bean.getWidth())));
                                        }

                                    } else{
                                        if(busSeatingBean.getIsAvail() == 2){
                                            imageview[c][r].setImageResource(R.drawable.item_seat_albooked);
                                        }else if(busSeatingBean.getIsAvail() == 3){
                                            imageview[c][r].setImageResource(R.drawable.item_seat_albooked);
                                        }else if(busSeatingBean.getIsAvail() == 1){
                                            imageview[c][r].setImageResource(R.drawable.item_seat_ordinary);
                                        }else if(busSeatingBean.getIsAvail() == -2){
                                            imageview[c][r].setImageResource(R.drawable.item_seat_albooked);
                                        }else if(busSeatingBean.getIsAvail() == -3){
                                            imageview[c][r].setImageResource(R.drawable.item_seat_albooked);
                                        }
                                        if (tracking_list.contains(iter)) {
                                            imageview[c][r].setImageResource(R.drawable.item_seat_booked);
                                        }
                                    }*/

                                }else{
                                    if(bean.getSeatType() == 2)
                                    {
                                        /** SLEEPER */
                                        if(bean.getStatus()==0 || bean.getStatus()==-2)
                                        {
                                            imageview[c][r].setImageResource(ordinarySleeperbooked("2"));
                                        }else if(bean.getStatus()==-3)
                                        {
                                            imageview[c][r].setImageResource(ordinarySleeperladies("2"));
                                        }

                                    }else{
                                        /** SEATER */
                                        if(bean.getStatus()==0 || bean.getStatus()==-2)
                                        {
                                            imageview[c][r].setImageResource(R.drawable.ic_blocked);
                                        }else if(bean.getStatus()==-3)
                                        {
                                            imageview[c][r].setImageResource(R.drawable.ic_booked_female);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                tablerow[c+1].addView(imageview[c][r]);
            }
            try{
                TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,15,12,0);
                seating_table.addView(tablerow[c+1],params);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public int ordinarySleeperSeat(String width){
        return (width.equals("1"))? R.drawable.ic_available_sleep:R.drawable.ic_available_sleep;
    }

    public int ordinarySleeperladies(String width){
        return (width.equals("1"))? R.drawable.ic_res_female_sleep:R.drawable.ic_res_female_sleep;
    }

    public int ordinarySleeperbooked(String width){
        return (width.equals("1"))? R.drawable.ic_blocked_sleep:R.drawable.ic_blocked_sleep;
    }

    public int ordinarySleeperSelected(String width){
        return (width.equals("1"))? R.drawable.ic_selected_sleep:R.drawable.ic_selected_sleep;
    }

    public int ordinarySleeperMaleSelected(String width){
        return (width.equals("1"))? R.drawable.ic_res_male_sleep:R.drawable.ic_res_male_sleep;
    }
    Dialog builder;

    public void selectBoard(){
        builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);

        final EditText edittext = new EditText(getActivity());
        edittext.setTextColor(Color.BLACK);
        edittext.setHint("Select a boarding point");
        edittext.setSingleLine(true);
        edittext.setMaxLines(1);
        final ListView listview = new ListView(getActivity());
        listview.setBackgroundColor(Color.WHITE);
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(edittext);
        layout.addView(listview);
        builder.setContentView(layout);
        CustomAdapter adapter = new CustomAdapter(getActivity(), pick_up_list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
        edittext.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s,
                                          int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int textlength = edittext.getText().length();
                List<PickUpBean> array_sort = new ArrayList<PickUpBean>();
                array_sort.clear();

                for (int i = 0; i < pick_up_list.size(); i++) {
                    if (textlength <= pick_up_list.get(i).getPickupname().length()) {

                        if (pick_up_list.get(i).getPickupname().toLowerCase().contains(edittext.getText().toString().toLowerCase().trim())) {
                            array_sort.add(pick_up_list.get(i));
                        }
                    }
                }
                listview.setAdapter(new CustomAdapter(getActivity(), array_sort));
            }
        });
        builder.show();
    }

    public void selectDrop(){
        builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final EditText edittext = new EditText(getActivity());
        edittext.setTextColor(Color.BLACK);
        edittext.setHint("Select a Dropping point");
        edittext.setSingleLine(true);
        edittext.setMaxLines(1);
        final ListView listview = new ListView(getActivity());
        listview.setBackgroundColor(Color.WHITE);
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(edittext);
        layout.addView(listview);
        builder.setContentView(layout);
        DropAdapter adapter = new DropAdapter(getActivity(), drop_off_list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
        edittext.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s,
                                          int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int textlength = edittext.getText().length();
                List<DropOffBean> array_sort = new ArrayList<DropOffBean>();
                array_sort.clear();

                for (int i = 0; i < drop_off_list.size(); i++) {
                    if (textlength <= drop_off_list.get(i).getDropoffName().length()) {

                        if (drop_off_list.get(i).getDropoffName().toLowerCase().contains(edittext.getText().toString().toLowerCase().trim())) {
                            array_sort.add(drop_off_list.get(i));
                        }
                    }
                }
                listview.setAdapter(new DropAdapter(getActivity(), array_sort));
            }
        });
        builder.show();
    }

    String dropname = "", drop_id = "";
    String dropoffLocation = "";
    String pickTime = "", dropTime = "";
    String boardingFrom = "", dropingTo = "";


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String tag = view.getTag().toString();


        if (tag.contains("CustomAdapter")) {
            boardingname = ((TextView) view.findViewById(R.id.boad_name)).getText().toString();
            boarding_id = ((TextView) view.findViewById(R.id.boad_id)).getText().toString();
            pickupLocation = pick_up_list.get(position).getPickupid();

            pickTime = pick_up_list.get(position).getPickuptime();

            String address = pick_up_list.get(position).getAddress();
            String landmark = pick_up_list.get(position).getLandmark();
            String contact = pick_up_list.get(position).getContact();

            boardingFrom = boardingname + "," + "Address : " + address + ", Landmark : " + landmark + ", Phone :" + contact;


            boarding_name.setText(boardingname);
            builder.dismiss();
        } else {
            dropname = ((TextView) view.findViewById(R.id.boad_name)).getText().toString();
            drop_id = ((TextView) view.findViewById(R.id.boad_id)).getText().toString();
            dropoffLocation = drop_off_list.get(position).getDropoffCode();

            dropTime = drop_off_list.get(position).getDropoffTime();
            dropping_name.setText(dropname);

            dropingTo = dropname;

            builder.dismiss();
        }

    }

    public void proceedPay() {
        try {
            Bundle probundle = new Bundle();
            probundle.putString("from", from_bus);
            probundle.putString("to", to_bus);
            probundle.putString("bus", bus_name);
            probundle.putString("seats", seatCount.getText().toString().split(" ")[0]);
            probundle.putString("total", totalPrice.getText().toString());
            // probundle.putString("token", token);
            probundle.putString("token_key", token_key);
            probundle.putString("search_id", searchId);
            probundle.putString("scheduler_id", schedule_id);
            probundle.putString("journey_date", journeyDate);
            probundle.putString("pickup_id", pickupLocation);


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("RouteScheduleId", schedule_id);
            jsonObject.put("JourneyDate", journeyDate);
            jsonObject.put("PickUpID", boarding_id);
            jsonObject.put("DropID", drop_id);


            jsonObject.put("DepartureTime", schedule_date);
            jsonObject.put("ArrivalTime", arrival_time);

            //jsonObject.put("ArrTime", arrival_date);

            jsonObject.put("departure_from", from_bus);
            jsonObject.put("arrival_to", to_bus);
            jsonObject.put("Form_id", from_id);
            jsonObject.put("To_id", to_id);
            jsonObject.put("boarding_from", boardingFrom);
            jsonObject.put("dropping_to", dropingTo);
            jsonObject.put("bus_type", bus_type_name);
            jsonObject.put("operator", bus_name);
            jsonObject.put("CommPCT", null);
            jsonObject.put("CommAmount", CommAmount);
            jsonObject.put("CancPolicy", cancArray);


            JSONObject seatObj = new JSONObject();
           /* seatObj.put("markup_price_summary", totalPrice.getText().toString());
            seatObj.put("total_price_summary", totalPrice.getText().toString());
            seatObj.put("domain_deduction_fare", totalPrice.getText().toString());*/
            seatObj.put("markup_price_summary", totalPrice.getText().toString().split(" ")[1]);
            seatObj.put("total_price_summary", totalPrice.getText().toString().split(" ")[1]);
            seatObj.put("domain_deduction_fare", totalPrice.getText().toString().split(" ")[1]);

            seatObj.put("default_currency", Global.currencySymbol);

            JSONObject seats = new JSONObject();


            for (int i = 0; i < finalSeat.size(); i++) {
                SeatingBean seatingBean = finalSeat.get(i);
                JSONObject jObj = new JSONObject();

                jObj.put("Fare", base_fare);
                jObj.put("Markup_Fare", resp_fare);
                jObj.put("IsAcSeat", isAc);
                jObj.put("SeatType", seatingBean.getSeatType());
                jObj.put("seq_no", seatingBean.getSeqNo());


                String deck = seatingBean.getDecks();
                if (deck.equalsIgnoreCase("Lower")) {
                    deck = "Lower";
                } else {
                    deck = "Upper";
                }
                jObj.put("decks", deck);

                seats.put("" + seatingBean.getSeatNo(), jObj);

            }


            seatObj.put("seats", seats);
            jsonObject.put("seat_attr", seatObj);


            String tokenString = jsonObject.toString();

           // Log.d(TAG, "tokenString  >" + tokenString);


            // byte[] data = tokenString.getBytes("UTF-8");
            //  String base64 = Base64.encodeToString(data, Base64.DEFAULT);

            probundle.putString("token", tokenString);
            probundle.putString("ResultToken", resultToken);
            probundle.putString("ArrivalDate",arrival_date);

           // IntentAndFragmentService.fragmentdisplay(getActivity(), R.id.bus_frame, new BusTravellerFragment(), probundle, true, false);
                intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                new TravellerFragment(
                        seating_selection_list1.size(),
                        0, 0,0,
                        totalPrice.getText().toString().split(" ")[1], 3), probundle, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}