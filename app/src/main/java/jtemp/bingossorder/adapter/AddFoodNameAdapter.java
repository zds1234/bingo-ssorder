package jtemp.bingossorder.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.model.AddCategory;

/**
 * Created by hasee on 2016/12/4.
 */

public class AddFoodNameAdapter extends CommenAdapter<AddCategory> {
    private Context context;

    public AddFoodNameAdapter(Context context, List<AddCategory> datas) {
        super(context, datas);
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.item_dialog_add_name_adapter;
    }

    @Override
    protected int[] getViewsId() {
        return new int[]{
                R.id.text1,R.id.radio_button
        };
    }

    @Override
    protected void addData2View(SparseArray<View> viewMap, AddCategory addCategory, int position) {
        TextView categoryName = (TextView) viewMap.get(R.id.text1);
        RadioButton radioButton = (RadioButton) viewMap.get(R.id.radio_button);
    }
}




















