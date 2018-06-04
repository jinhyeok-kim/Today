package com.example.jinhyeok.whattoday;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class WorkListAdapter extends BaseAdapter {

    private List works;
    private Context context;

    public WorkListAdapter(List works, Context context){
        this.works = works;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.works.size();
    }

    @Override
    public Object getItem(int position) {
        return this.works.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if(convertView == null){
            // convertView가 없으면 초기화
            convertView = new LinearLayout(context);
            ((LinearLayout) convertView).setOrientation(LinearLayout.HORIZONTAL);

            TextView tvId = new TextView(context);
            tvId.setPadding(10, 0, 20, 0);
            tvId.setTextColor(Color.rgb(0, 0, 0));

            TextView tvTime = new TextView(context);
            tvTime.setPadding(20, 0, 20, 0);
            tvTime.setTextColor(Color.rgb(0, 0, 0));

            TextView tvContent = new TextView(context);
            tvContent.setPadding(20, 0, 20, 0);
            tvContent.setTextColor(Color.rgb(0, 0, 0));

            ((LinearLayout) convertView).addView(tvId);
            ((LinearLayout) convertView).addView(tvTime);
            ((LinearLayout) convertView).addView(tvContent);

            holder = new Holder();
            holder.tvId = tvId;
            holder.tvTime = tvTime;
            holder.tvContent = tvContent;
            convertView.setTag(holder);

        }else{ // convertView가 있으면 홀더를 꺼냄
            holder = (Holder) convertView.getTag();
        }
        // 한 명의 데이터를 받아와서 입력
        DayWork dayWork = (DayWork) getItem(position);
        holder.tvId.setText(dayWork.get_id() + "");
        holder.tvTime.setText(dayWork.getTime() + "");
        holder.tvContent.setText(dayWork.getContent() + "");

        return convertView;
    }

    private class Holder {
        public TextView tvId;
        public TextView tvTime;
        public TextView tvContent;

    }
}
