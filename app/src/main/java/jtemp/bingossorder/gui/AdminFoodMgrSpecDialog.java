package jtemp.bingossorder.gui;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.admin.FoodManager;
import jtemp.bingossorder.entity.FoodSpec;
import jtemp.bingossorder.code.FoodSpecType;

/**
 * Created by ZMS on 2016/11/23.
 */

public class AdminFoodMgrSpecDialog extends Dialog {

    @BindView(R.id.food_spec_save)
    Button save;

    @BindView(R.id.food_spec_delete)
    Button delete;

    @BindView(R.id.food_spec)
    RadioGroup spec;

    @BindView(R.id.food_spec_name)
    EditText name;


    AdminFoodMgrSpecFragment adminFoodMgrSpecFragment;

    public AdminFoodMgrSpecDialog(AdminFoodMgrSpecFragment fragment, Context context) {
        super(context);
        this.adminFoodMgrSpecFragment = fragment;
        setContentView(R.layout.food_mgr_spec_dialog);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.food_spec_save, R.id.food_spec_delete})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.food_spec_delete:
                deleteSpec();
                break;
            case R.id.food_spec_save:
                saveSpec();
                break;
        }
    }

    private void deleteSpec() {
//        String specName = name.getText().toString().trim();
//        FoodManager.deleteFoodSpecByName(specName);
//        adminFoodMgrSpecFragment.loadSpecList();
//        hide();
    }

    private void saveSpec() {
//        String specName = name.getText().toString().trim();
//        if (specName.isEmpty()) {
//            Toast.makeText(getContext(), "请输入名称", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        FoodSpec entityFoodSpec = new FoodSpec();
//        switch (spec.getCheckedRadioButtonId()) {
//            case R.id.food_spec_size:
//                entityFoodSpec.setSpecType(FoodSpecType.SIZE.name());
//                break;
//            case R.id.food_spec_flavour:
//                entityFoodSpec.setSpecType(FoodSpecType.FLAVOUR.name());
//                break;
//            case R.id.food_spec_avoid:
//                entityFoodSpec.setSpecType(FoodSpecType.AVOID.name());
//                break;
//            default:
//                break;
//        }
//        entityFoodSpec.setSpecName(specName);
//        FoodManager.saveFoodSpec(entityFoodSpec);
//
//        adminFoodMgrSpecFragment.loadSpecList();
//        hide();
    }

    public void setSpecData(FoodSpec entityFoodSpec) {
        if (entityFoodSpec != null) {
            name.setText(entityFoodSpec.getSpecName());
            FoodSpecType foodSpecType = FoodSpecType.valueOf(entityFoodSpec.getSpecType());
            switch (foodSpecType) {
                case SIZE:
                    spec.check(R.id.food_spec_size);
                    break;
                case FLAVOUR:
                    spec.check(R.id.food_spec_flavour);
                    break;
                case AVOID:
                    spec.check(R.id.food_spec_avoid);
                    break;
                default:
                    break;
            }
        } else {
            spec.check(R.id.food_spec_size);
            name.setText("");
        }
    }
}
