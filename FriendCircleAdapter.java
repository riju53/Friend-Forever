package com.example.rijunath.friendforever;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class FriendCircleAdapter extends BaseAdapter {

    private LayoutInflater inf;
    private Context context;
    private ArrayList<UserDetail> listFriends;


    //why here you define FriendCircleAdapter.
    public FriendCircleAdapter(Context context, ArrayList<UserDetail> list) {
        this.context = context;
        this.listFriends = list;
        this.inf = LayoutInflater.from(context);
    }

    //what is the actual do refreshDataset
    public void refreshDataSet(ArrayList<UserDetail> list) {
        this.listFriends.clear();
        this.listFriends.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return (listFriends == null) ? 0 : listFriends.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inf.inflate(R.layout.row1, parent, false);
            viewHolder.txtFirstName = (TextView) convertView.findViewById(R.id.tv2);
            viewHolder.txtLastName = (TextView) convertView.findViewById(R.id.tv3);
            viewHolder.cross=(ImageView)convertView.findViewById(R.id.cross);
            viewHolder.flag=(ImageView)convertView.findViewById(R.id.im_flag);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        UserDetail mUserDetail = listFriends.get(position);
        viewHolder.txtFirstName.setText(mUserDetail.getFname());
        viewHolder.txtLastName.setText(mUserDetail.getLname());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ChatActivity.class);
                i.putExtra("frnd",listFriends.get(position).getEmail());
                context.startActivity(i);
            }

        });

        return convertView;
    }

    class ViewHolder {
        TextView txtFirstName;
        TextView txtLastName;
        ImageView cross;
        ImageView flag;

    }

}
