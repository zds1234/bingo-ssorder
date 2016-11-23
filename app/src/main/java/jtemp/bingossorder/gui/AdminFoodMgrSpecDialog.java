package jtemp.bingossorder.gui;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.admin.AdminFoodManager;
import jtemp.bingossorder.entity.EntityFoodSpec;
import jtemp.bingossorder.entity.FoodSpecType;
import jtemp.bingossorder.utils.BindView;
import jtemp.bingossorder.utils.ViewBinder;

/**
 * Created by ZMS on 2016/11/23.
 */

public class AdminFoodMgrSpecDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.food_spec_save)
    private Button save;

    @BindView(R.id.food_spec_delete)
    private Button delete;

    @BindView(R.id.food_spec)
    private RadioGroup spec;

    @BindView(R.id.food_spec_name)
    private EditText name;


    private AdminFoodMgrSpecFragment adminFoodMgrSpecFragment;

    public AdminFoodMgrSpecDialog(AdminFoodMgrSpecFragment fragment, Context context) {
        super(context);
        this.adminFoodMgrSpecFragment = fragment;
        setContentView(R.layout.food_mgr_spec_dialog);
        ViewBinder.bind(this, this);
        save.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
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
        String specName = name.getText().toString().trim();
        AdminFoodManager.deleteFoodSpecByName(specName);
        adminFoodMgrSpecFragment.loadSpecList();
        hide();
    }

    private void saveSpec() {
        String specName = name.getText().toString().trim();
        if (specName.isEmpty()) {
            Toast.makeText(getContext(), "请输入名称", Toast.LENGTH_SHORT).show();
            return;
        }

        EntityFoodSpec entityFoodSpec = new EntityFoodSpec();
        switch (spec.getCheckedRadioButtonId()) {
            case R.id.food_spec_size:
                entityFoodSpec.setSpecType(FoodSpecType.SIZE.name());
                break;
            case R.id.food_spec_flavour:
                entityFoodSpec.setSpecType(FoodSpecType.FLAVOUR.name());
                break;
            case R.id.food_spec_avoid:
                entityFoodSpec.setSpecType(FoodSpecType.AVOID.name());
                break;
            default:
                break;
        }
        entityFoodSpec.setSpecName(specName);
        AdminFoodManager.saveFoodSpec(entityFoodSpec);

        adminFoodMgrSpecFragment.loadSpecList();
        hide();
    }

    public void setSpecData(EntityFoodSpec entityFoodSpec) {
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
