package com.linsr.loudspeaker.gui.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.linsr.loudspeaker.gui.adapters.holder.RecordHolder;
import com.linsr.loudspeaker.model.RecordModel;

import java.util.List;

/**
 * Description
 *
 * @author linsenrong on 2017/7/12 09:29
 */

public class RecordsAdapter extends RecyclerArrayAdapter<RecordModel> {


    public RecordsAdapter(Context context, List<RecordModel> objects) {
        super(context, objects);
    }


    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        RecordHolder repairHolder = new RecordHolder(parent);
        return repairHolder;
    }

}
