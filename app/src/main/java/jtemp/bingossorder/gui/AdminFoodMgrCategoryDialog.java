package jtemp.bingossorder.gui;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.admin.AdminFoodManager;
import jtemp.bingossorder.entity.EntityFoodCategory;

/**
 * Created by ZMS on 2016/11/22.
 */

public class AdminFoodMgrCategoryDialog extends Dialog {

    @BindView(R.id.food_category_add)
    Button categoryAdd;

    @BindView(R.id.food_category_delete)
    Button categoryDelete;

    @BindView(R.id.food_category_update)
    Button categoryUpdate;

    @BindView(R.id.food_category_taocan)
    Switch categoryTaocanSwitch;

    @BindView(R.id.food_category_is_taocan)
    ViewGroup categoryIsTaocan;

    @BindView(R.id.food_category_name)
    EditText categoryName;

    @BindView(R.id.food_category_order)
    EditText categoryOrder;

    @BindView(R.id.food_category_limit)
    EditText categoryLimit;

    @BindView(R.id.food_category_taocan_relation)
    ViewGroup categoryRelation;

    EntityFoodCategory foodCategory;

    AdminFoodMgrCategoryFragment adminFoodMgrCategoryFragment;

    public AdminFoodMgrCategoryDialog(Context context, AdminFoodMgrCategoryFragment adminFoodMgrCategoryFragment) {
        super(context);
        this.adminFoodMgrCategoryFragment = adminFoodMgrCategoryFragment;
        setContentView(R.layout.food_category_mgr_dialog);
        ButterKnife.bind(this);
    }

    public void initContent(EntityFoodCategory foodCategory) {
        if (foodCategory == null) {
            initContentAddCategory();
        } else {
            initContentEditCategory(foodCategory);
        }
    }

    private void initContentAddCategory() {

        //设置可见性
        categoryAdd.setVisibility(View.VISIBLE);
        categoryDelete.setVisibility(View.INVISIBLE);
        categoryUpdate.setVisibility(View.INVISIBLE);
        categoryIsTaocan.setVisibility(View.INVISIBLE);

        //设置默认值
        categoryTaocanSwitch.setChecked(false);
        categoryName.setText("");

        Integer maxId = DataSupport.max(EntityFoodCategory.class, "id", Integer.class);
        maxId = (maxId == null ? 1 : maxId + 1);
        categoryOrder.setText(String.valueOf(maxId));
    }

    private void initContentEditCategory(EntityFoodCategory foodCategory) {
        this.foodCategory = foodCategory;
        //设置可见性
        categoryAdd.setVisibility(View.INVISIBLE);
        categoryDelete.setVisibility(View.VISIBLE);
        categoryUpdate.setVisibility(View.VISIBLE);

        //设置默认值
        categoryName.setText(foodCategory.getCategoryName());
        categoryOrder.setText(String.valueOf(foodCategory.getCategoryOrder()));
        categoryLimit.setText(String.valueOf(foodCategory.getPurchaseLimit()));
        categoryTaocanSwitch.setChecked(foodCategory.isTaocan());
        switchTaoCan();
    }

    @OnClick({R.id.food_category_taocan, R.id.food_category_add, R.id.food_category_delete, R.id.food_category_update})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.food_category_taocan:
                switchTaoCan();
                break;
            case R.id.food_category_add:
                addFoodCategory();
                break;
            case R.id.food_category_delete:
                deleteFoodCategory();
                break;
            case R.id.food_category_update:
                updateFoodCategory();
                break;
            default:
                break;
        }
    }

    private void updateFoodCategory() {
        if (foodCategory != null) {
            try {
                String name = categoryName.getText().toString().trim();
                int limit = Integer.parseInt(categoryLimit.getText().toString().trim());
                int order = Integer.parseInt(categoryOrder.getText().toString().trim());
                boolean taocan = isSwitchedTaoCan();
                if (name == null || name.isEmpty()) {
                    Toast.makeText(getContext(), "请输入菜品名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                String relations = "";
                if (taocan) {
                    ViewGroup viewGroup = (LinearLayout) findViewById(R.id.food_category_taocan_relation);
                    int childCount = viewGroup.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view = viewGroup.getChildAt(i);
                        if (view instanceof CheckBox) {
                            CheckBox box = (CheckBox) view;
                            if (box.isChecked()) {
                                relations += (box.getTag() + ";");
                            }
                        }
                    }
                }
                foodCategory.setCategoryName(name);
                foodCategory.setPurchaseLimit(limit);
                foodCategory.setCategoryOrder(order);
                foodCategory.setTaocan(taocan);
                foodCategory.setRelations(relations);
                foodCategory.save();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "请检查输入", Toast.LENGTH_SHORT).show();
            }
        }
        adminFoodMgrCategoryFragment.loadCategoryList();
        hide();
    }

    private void deleteFoodCategory() {
        if (foodCategory != null) {
            AdminFoodManager.deleteFoodCategory(foodCategory.getId());
        }
        adminFoodMgrCategoryFragment.loadCategoryList();
        hide();
    }

    private void addFoodCategory() {
        try {
            String name = categoryName.getText().toString().trim();
            int limit = Integer.parseInt(categoryLimit.getText().toString().trim());
            int order = Integer.parseInt(categoryOrder.getText().toString().trim());
            boolean taocan = isSwitchedTaoCan();
            if (name == null || name.isEmpty()) {
                Toast.makeText(getContext(), "请输入菜品名称", Toast.LENGTH_SHORT).show();
                return;
            }
            String relations = "";
            if (taocan) {
                ViewGroup viewGroup = (LinearLayout) findViewById(R.id.food_category_taocan_relation);
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = viewGroup.getChildAt(i);
                    if (view instanceof CheckBox) {
                        CheckBox box = (CheckBox) view;
                        if (box.isChecked()) {
                            relations += (box.getTag() + ";");
                        }
                    }
                }
            }
            EntityFoodCategory entityFoodCategory = new EntityFoodCategory();
            entityFoodCategory.setCategoryName(name);
            entityFoodCategory.setPurchaseLimit(limit);
            entityFoodCategory.setCategoryOrder(order);
            entityFoodCategory.setTaocan(taocan);
            entityFoodCategory.setRelations(relations);
            if (entityFoodCategory.saveIfNotExist("categoryName=?", name)) {
                Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
                loadCategoryList();
                this.hide();
            } else {
                Toast.makeText(getContext(), "菜品已存在", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "请检查输入", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadCategoryList() {
        adminFoodMgrCategoryFragment.loadCategoryList();
    }

    private boolean isSwitchedTaoCan() {
        return categoryTaocanSwitch.isChecked();
    }

    private void switchTaoCan() {
        if (isSwitchedTaoCan()) {
            categoryRelation.removeAllViewsInLayout();
            List<EntityFoodCategory> list = AdminFoodManager.findAllCategory();
            Set<String> relations = new HashSet<>();
            if (foodCategory != null) {
                String rel = foodCategory.getRelations();
                rel = rel == null ? "" : rel.trim();
                relations.addAll(Arrays.asList(rel.split(";")));
            }
            for (Iterator<EntityFoodCategory> it = list.iterator(); it.hasNext(); ) {
                EntityFoodCategory category = it.next();
                if (!category.isTaocan()) {
                    CheckBox box = new CheckBox(getContext());
                    box.setTag(category.getId());
                    box.setText(category.getCategoryName());
                    if (relations.contains(String.valueOf(category.getId()))) {
                        box.setChecked(true);
                    }
                    categoryRelation.addView(box);
                }
            }
            categoryIsTaocan.setVisibility(View.VISIBLE);
        } else {
            categoryIsTaocan.setVisibility(View.INVISIBLE);
        }
    }
}