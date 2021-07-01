package com.TripToEasy.transfers.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.TripToEasy.R;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.parsingModel.transfers.TransfersResults;
import com.TripToEasy.transfers.adapter.TransfersListAdapter;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransfersResultFragment extends BaseFragment implements WebInterface {

    @BindView(R.id.rv_transfers)
    RecyclerView rv_transfers;

    @BindView(R.id.location_name)
    TextView location_name;

    String tranfersResponse=null,location,search_id,booking_source;
    TransfersListAdapter transferListAdapter;
    List<TransfersResults> transfersList=new ArrayList<TransfersResults>();

    public TransfersResultFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public TransfersResultFragment(String tranfersResponse,String location) {
        this.tranfersResponse=tranfersResponse;
        this.location=location;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_transfers_result;
    }

    /* @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
         // Inflate the layout for this fragment
         return inflater.inflate(R.layout.fragment_transfers_result, container, false);
     }*/
    WebServiceController webServiceController;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webServiceController = new WebServiceController(getActivity(),
                TransfersResultFragment.this);
        commonUtils.linearLayout(rv_transfers, getActivity());

        location_name.setText(location);
        transferListAdapter = new TransfersListAdapter(getActivity(),
                TransfersResultFragment.this, transfersList);
        rv_transfers.setAdapter(transferListAdapter);
        loadResultData();
    }

    private void loadResultData(){
        try {
            JSONObject jsonObject=new JSONObject(tranfersResponse);
            JSONObject dataObj=jsonObject.getJSONObject("data");
            search_id=dataObj.getString("search_id");
            booking_source=dataObj.getString("booking_source");
            JSONObject transferObj=dataObj.getJSONObject("transfer_list");
            JSONArray transfersArr=transferObj.getJSONObject("TransferSearchResult").getJSONArray("TransferResults");
            for(int i=0;i<transfersArr.length();i++){
                transfersList.add(new TransfersResults(
                        transfersArr.getJSONObject(i).getString("ProductName"),
                        transfersArr.getJSONObject(i).getString("ProductCode"),
                        transfersArr.getJSONObject(i).getString("ImageUrl"),
                        transfersArr.getJSONObject(i).getInt("StarRating")+"",
                        transfersArr.getJSONObject(i).getInt("ReviewCount")+"",
                        transfersArr.getJSONObject(i).getString("Duration"),
                        transfersArr.getJSONObject(i).getString("Cancellation_available"),
                        transfersArr.getJSONObject(i).getJSONObject("Price").getString("TotalDisplayFare"),
                        transfersArr.getJSONObject(i).getJSONObject("Price").getString("Currency"),
                        transfersArr.getJSONObject(i).getString("ResultToken")
                ));
            }

            transferListAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void callDetailPage(String productCode,String resultToken){
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("op","get_details");
            jsonObject.put("search_id",search_id);
            jsonObject.put("booking_source",booking_source);
            jsonObject.put("product_code",productCode);
            jsonObject.put("result_token",resultToken);

            RequestParams requestParams = new RequestParams();
            requestParams.put("transfer_details", jsonObject.toString());
            webServiceController.postRequest(
                    apiConstants.TRANSFERS_DETAIL,
                    requestParams,
                    1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getResponse(String response, int flag) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.getInt("status")==1){
                intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                        new TransfersDetailFragment(response), null, true);
            }else {
                commonUtils.toastShort("Please Try Again",getActivity());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
