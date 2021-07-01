package com.TripToEasy.sight_seeing.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.TripToEasy.R;
import com.TripToEasy.model.requestModel.FriendsData;
import com.TripToEasy.utils.webservice.ApiConstants;

import java.util.ArrayList;
import java.util.List;

public class TagFriendsListAdp extends BaseAdapter implements Filterable {
    private LayoutInflater layoutInflater;
    List<FriendsData> mStringFilterList;
    List<FriendsData> mData;
    ValueFilter valueFilter;
    Context context;


    onCheckBoxClicked mOnCheckBoxClicked;

    public TagFriendsListAdp(Context context, List<FriendsData> customizedListView, onCheckBoxClicked mOnCheckBoxClicked) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mStringFilterList = customizedListView;
        mData = customizedListView;
        this.context = context;
        this.mOnCheckBoxClicked=mOnCheckBoxClicked;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final SearchHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_friend_list, parent, false);
            holder = new SearchHolder();
            holder.cbTag = convertView.findViewById(R.id.cbTag);
            holder.ivFriendPhoto = convertView.findViewById(R.id.ivFriendPhoto);
            convertView.setTag(holder);
        } else {
            holder = (SearchHolder) convertView.getTag();
        }
        holder.cbTag.setOnCheckedChangeListener(null);

        holder.cbTag.setText(mData.get(position).getFirst_name());

        if (mData.get(position).isTagged()) {
            holder.cbTag.setChecked(true);
        } else {
            holder.cbTag.setChecked(false);
        }

        Glide.with(context).load(ApiConstants.IMAGE_BASE + mData.get(position).getImage()).into(holder.ivFriendPhoto);




        holder.cbTag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mData.get(position).setTagged(b);
                mOnCheckBoxClicked.onTagged(mData.get(position).getUser_id(),mData.get(position).isTagged());
            }
        });


        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }


    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                List filterList = new ArrayList<>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getFirst_name().toUpperCase()).contains(constraint)) {
                        filterList.add(mStringFilterList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            mData = (ArrayList<FriendsData>) results.values;
            notifyDataSetChanged();
        }

    }

    @Override
    public boolean isEnabled(int position) {
        // return super.isEnabled(position);

        return true;
    }

    private class SearchHolder {
        AppCompatCheckBox cbTag;
        ImageView ivFriendPhoto;

    }


    public interface  onCheckBoxClicked{
        void onTagged(String Userid, boolean state);

    }






}
