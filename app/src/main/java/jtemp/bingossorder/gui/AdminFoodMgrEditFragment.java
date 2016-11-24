package jtemp.bingossorder.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eunut.ext.imagepicker.ImagePicker;
import com.eunut.ext.imagepicker.activity.AlbumActivity;
import com.eunut.ext.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jtemp.bingossorder.Const;
import jtemp.bingossorder.activity.AdminFoodListByCategoryActivity;
import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.admin.AdminFoodManager;
import jtemp.bingossorder.entity.EntityFood;
import jtemp.bingossorder.entity.EntityFoodCategory;
import jtemp.bingossorder.entity.EntityFoodSpec;
import jtemp.bingossorder.entity.FoodSpecType;
import jtemp.bingossorder.utils.AndroidUtils;
import jtemp.bingossorder.utils.FileUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminFoodMgrEditFragment extends Fragment {

    @BindView(R.id.food_edit_types)
    RadioGroup food_edit_types;

    @BindView(R.id.food_edit_size)
    ViewGroup food_edit_size;

    @BindView(R.id.food_edit_flavour)
    ViewGroup food_edit_flavour;

    @BindView(R.id.food_edit_avoid)
    ViewGroup food_edit_avoid;

    @BindView(R.id.food_edit_name)
    EditText foodName;

    @BindView(R.id.food_edit_name_en)
    EditText foodNameEn;

    @BindView(R.id.food_edit_price)
    EditText price;

    @BindView(R.id.food_edit_recommend)
    CheckBox recommend;

    @BindView(R.id.food_edit_saleable)
    CheckBox saleable;

    @BindView(R.id.food_edit_pic)
    ImageView image;

    @BindView(R.id.food_edit_relations)
    ViewGroup foodRelationContainer;

    private Map<String, View> relationCategoryView = new HashMap<>();

    String imagePath;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_food_mgr_edit, container, false);
        ButterKnife.bind(this, view);
        initContent(view);
        return view;
    }

    @OnClick({R.id.food_edit_scroll, R.id.food_edit_pic})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.food_edit_scroll:
                AndroidUtils.hideSoftKeyboard(getActivity());
                break;
            case R.id.food_edit_pic:
                chooseFoodImage();
                break;
        }
    }

    private void chooseFoodImage() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(true);
        imagePicker.setFocusHeight(Const.FOOD_IMAGE_SIZE);
        imagePicker.setFocusWidth(Const.FOOD_IMAGE_SIZE);
        Intent intent = new Intent(getContext(), AlbumActivity.class);
        startActivityForResult(intent, R.id.food_edit_pic & 0x0000ffff);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && (resultCode == Activity.RESULT_OK || resultCode == ImagePicker.RESULT_CODE_ITEMS)) {
            switch (requestCode) {
                case R.id.food_edit_pic & 0x0000ffff:
                    ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    String path = images.get(0).path;
                    imagePath = path;
                    Glide.with(getContext()).load(path).asBitmap().into(image);
                    break;
                case R.id.food_edit_relations & 0x0000ffff:
                    break;
            }
        }
    }

    private void initContent(View view) {
        food_edit_types.removeAllViewsInLayout();
        List<EntityFoodCategory> foodCategoryList = AdminFoodManager.findAllCategory();
        for (EntityFoodCategory category : foodCategoryList) {
            RadioButton button = new RadioButton(getContext());
            button.setText(category.getCategoryName());
            button.setTag(category);
            food_edit_types.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelectCategory((EntityFoodCategory) v.getTag());
                }
            });
        }

        food_edit_size.removeAllViewsInLayout();
        food_edit_avoid.removeAllViewsInLayout();
        food_edit_flavour.removeAllViewsInLayout();
        List<EntityFoodSpec> foodSpecList = AdminFoodManager.findAllSpec();
        for (EntityFoodSpec spec : foodSpecList) {
            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(spec.getSpecName());
            checkBox.setTag(spec);
            switch (FoodSpecType.valueOf(spec.getSpecType())) {
                case SIZE:
                    food_edit_size.addView(checkBox);
                    break;
                case FLAVOUR:
                    food_edit_flavour.addView(checkBox);
                    break;
                case AVOID:
                    food_edit_avoid.addView(checkBox);
                    break;
            }
        }
    }

    private void onSelectCategory(EntityFoodCategory category) {
        relationCategoryView.clear();
        foodRelationContainer.removeAllViewsInLayout();
        if (category.isTaocan()) {
            for (final EntityFoodCategory relationCategory : category.getRelationCategory().values()) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.food_edit_relations_item, null);
                TextView textView = (TextView) view.findViewById(R.id.food_edit_relation_category);
                textView.setText(relationCategory.getCategoryName());
                Button choose = (Button) view.findViewById(R.id.food_edit_relations_choose);
                view.setTag(relationCategory);
                relationCategoryView.put(String.valueOf(category.getId()), view);
                choose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startChooseFoodActivity(relationCategory);
                    }
                });
                foodRelationContainer.addView(view);
            }
            foodRelationContainer.setVisibility(View.VISIBLE);
        } else {
            foodRelationContainer.setVisibility(View.INVISIBLE);
        }
        ViewGroup parent = (ViewGroup) foodRelationContainer.getParent();
        parent.updateViewLayout(foodRelationContainer, foodRelationContainer.getLayoutParams());
    }

    /**
     * 选择关联菜品
     *
     * @param relationCategory
     */
    private void startChooseFoodActivity(EntityFoodCategory relationCategory) {
        Intent intent = new Intent(getContext(), AdminFoodListByCategoryActivity.class);
        intent.putExtra(AdminFoodListByCategoryActivity.PARAM_FOOD_CATEGORY, String.valueOf(relationCategory.getId()));
        startActivityForResult(intent, R.id.food_edit_relations & 0x0000ffff);
    }

    public void saveFood() {
        AndroidUtils.hideSoftKeyboard(getActivity());
        RadioButton chooseType = (RadioButton) getView().findViewById(food_edit_types.getCheckedRadioButtonId());
        if (chooseType == null) {
            Toast.makeText(getContext(), "请选择菜品类型", Toast.LENGTH_SHORT).show();
            return;
        }
        EntityFoodCategory category = (EntityFoodCategory) chooseType.getTag();

        String name = foodName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(getContext(), "请输入菜品名称", Toast.LENGTH_SHORT).show();
            return;
        }

        String nameEn = foodNameEn.getText().toString().trim();

        double price = -1;
        try {
            price = Double.parseDouble(this.price.getText().toString().trim());
        } catch (Exception e) {
        }
        if (price <= 0) {
            Toast.makeText(getContext(), "请输入菜品价格", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean recommend = this.recommend.isChecked();
        boolean saleable = this.saleable.isChecked();
        if (imagePath == null || imagePath.trim().isEmpty()) {
            Toast.makeText(getContext(), "请选择图像", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(getContext(), imagePath, Toast.LENGTH_SHORT).show();

        EntityFood food = new EntityFood();
        food.setEntityFoodCategory(category);
        food.setName(name);
        food.setNameEn(nameEn);
        food.setPrice(price);
        food.setRecommend(recommend);
        food.setSaleable(saleable);
        food.setImage(FileUtils.readBytes(imagePath));
        AdminFoodManager.saveFood(food);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        imagePath = null;
    }
}
