package com.example.npguide;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.util.List;

public class MyAdapter extends BaseAdapter implements OnClickListener {
    Context mContext;
    private Activity activity;
    private List<String> mList;
    private List<Boolean> mListShow; // 這個用來記錄哪幾個 item 是被打勾的
    private InnerItemOnclickListener mListener;

    private static LayoutInflater inflater = null;

    public MyAdapter(Context mContext, Activity a, List<String> list, List<Boolean> listShow) {
        activity = a;
        this.mContext = mContext;
        this.mList = list;  //名稱(library)
        this.mListShow = listShow; //是否打勾
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_style, null);
            viewHolder.cb = (CheckBox) convertView.findViewById(R.id.checkBox2);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.textview_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.cb.setOnClickListener(this);
        viewHolder.tv.setOnClickListener(this);
        viewHolder.cb.setTag(position);
        viewHolder.cb.setChecked(mListShow.get(position));  // 設定是否勾選
        viewHolder.tv.setTag(position);
        viewHolder.tv.setText(mList.get(position));
        return convertView;

        /*
        View vi = convertView;
        if(convertView==null)
        {
            vi = inflater.inflate(R.layout.item_style, null);
        }

        //CheckedTextView chkBshow = (CheckedTextView) vi.findViewById(R.id.check1);
        //chkBshow.setText(mList.get(position).toString());

        //CheckBox chkBshow = (CheckBox) vi.findViewById(R.id.checkBox2);
        TextView libraryName = (TextView)vi.findViewById(R.id.textView2);

        //設定是否打勾與textView要顯示名稱
        //chkBshow.setChecked((Boolean) mListShow.get(position));
        libraryName.setText((String) mList.get(position));

        Log.d("KK", "getView(" + position + ", convertView, ViewGroup parent)");

        return vi;*/
    }


    public final static class ViewHolder {
        CheckBox cb;
        TextView tv;
    }

    public void setOnInnerItemOnClickListener(InnerItemOnclickListener listener) {
        this.mListener = listener;
    }


    @Override
    public void onClick(View view) {
        mListener.itemClick(view);
    }

    interface InnerItemOnclickListener {
        void itemClick(View v);
    }

    public void refresh(List<Boolean> listshow) {
        //this.mList = list;
        this.mListShow = listshow;
        notifyDataSetChanged();
    }
}
