package com.linsr.loudspeaker.gui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linsr.loudspeaker.R;
import com.linsr.loudspeaker.model.RecordModel;

import java.util.List;

/**
 * Description
 *
 * @author linsenrong on 2017/7/12 09:29
 */

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.RecordsHolder> {

    private Context mContext;
    private List<RecordModel> mList;

    public RecordsAdapter(Context context, List<RecordModel> list) {
        mList = list;
        mContext = context;
    }

    @Override
    public RecordsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_record, parent, false);
        return new RecordsHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordsHolder holder, int position) {
        RecordModel model = mList.get(position);
        holder.name.setText(model.getRecordName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class RecordsHolder extends RecyclerView.ViewHolder {

        private TextView name;

        private RecordsHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_record_name_tv);
        }
    }
}
