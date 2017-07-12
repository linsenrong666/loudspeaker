package com.linsr.loudspeaker.gui.adapters.holder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.linsr.loudspeaker.R;
import com.linsr.loudspeaker.model.RecordModel;

/**
 * Description
 *
 * @author linsenrong on 2017/7/12 18:11
 */

public class RecordHolder extends BaseViewHolder<RecordModel> {

    private TextView name;

    public RecordHolder(ViewGroup parent) {
        super(parent, R.layout.item_record);
        name = $(R.id.item_record_name_tv);
    }

    @Override
    public void setData(RecordModel data) {
        name.setText(data.getRecordName());
    }
}
