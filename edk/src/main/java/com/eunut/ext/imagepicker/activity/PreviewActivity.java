package com.eunut.ext.imagepicker.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.format.Formatter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import com.eunut.ext.imagepicker.ImagePicker;
import com.eunut.ext.imagepicker.adapter.PreviewAdapter;
import com.eunut.ext.imagepicker.bean.ImageItem;
import com.eunut.ext.imagepicker.widget.SuperCheckBox;
import com.eunut.sdk.R;
import com.eunut.widget.TopBar;
import com.eunut.widget.viewpage.FixedViewPager;

import java.util.ArrayList;

public class PreviewActivity extends FragmentActivity implements ImagePicker.OnImageSelectedListener, CompoundButton.OnCheckedChangeListener {

    public static final String ISORIGIN = "isOrigin";

    private boolean isOrigin;    //是否选中原图
    private ImagePicker imagePicker;
    private ArrayList<ImageItem> mImageItems;      //跳转进ImagePreviewFragment的图片文件夹
    private int mCurrentPosition = 0;         //跳转进ImagePreviewFragment时的序号，第几个图片
    private SuperCheckBox mCbCheck;           //是否选中当前图片的CheckBox
    private SuperCheckBox mCbOrigin;          //原图
    private TextView mBtnOk;                    //确认图片的选择
    private ArrayList<ImageItem> selectedImages;   //所有已经选中的图片
    private View content, bottomBar;
    private TopBar topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eunut_activity_preview);
        isOrigin = getIntent().getBooleanExtra(PreviewActivity.ISORIGIN, false);
        mCurrentPosition = getIntent().getIntExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
        mImageItems = (ArrayList<ImageItem>) getIntent().getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
        imagePicker = ImagePicker.getInstance();
        imagePicker.addOnImageSelectedListener(this);
        selectedImages = imagePicker.getSelectedImages();

        //因为状态栏透明后，布局整体会上移，所以给头部加上状态栏的margin值，保证头部不会被覆盖
        topBar = (TopBar) findViewById(R.id.top_bar);
        mBtnOk = (TextView) topBar.getActionView().getChildAt(0);
        topBar.setOnActionClickListener(new TopBar.ActionClickListener() {
            @Override
            public void onActionClick(View v, int position) {
                Intent intent = new Intent();
                intent.putExtra(ImagePicker.EXTRA_RESULT_ITEMS, imagePicker.getSelectedImages());
                setResult(ImagePicker.RESULT_CODE_ITEMS, intent);
                finish();
            }
        });
        //初始化控件
        content = findViewById(R.id.content);

        bottomBar = findViewById(R.id.bottom_bar);
        mCbCheck = (SuperCheckBox) findViewById(R.id.cb_check);
        mCbOrigin = (SuperCheckBox) findViewById(R.id.cb_origin);
        mCbOrigin.setText(getString(R.string.origin));
        mCbOrigin.setOnCheckedChangeListener(this);
        mCbOrigin.setChecked(isOrigin);
        FixedViewPager viewPager = (FixedViewPager) findViewById(R.id.viewpager);
        PreviewAdapter adapter = new PreviewAdapter(this, mImageItems);
        adapter.setPhotoViewClickListener(new PreviewAdapter.PhotoViewClickListener() {
            @Override
            public void OnPhotoTapListener(View view, float v, float v1) {
                onImageSingleTap();
            }
        });
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(mCurrentPosition, false);

        //初始化当前页面的状态
        onImageSelected(0, null, false);
        ImageItem item = mImageItems.get(mCurrentPosition);
        boolean isSelected = imagePicker.isSelect(item);
        topBar.setTitle(getString(R.string.preview_image_count, mCurrentPosition + 1, mImageItems.size()));
        mCbCheck.setChecked(isSelected);
        //滑动ViewPager的时候，根据外界的数据改变当前的选中状态和当前的图片的位置描述文本
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                ImageItem item = mImageItems.get(mCurrentPosition);
                boolean isSelected = imagePicker.isSelect(item);
                mCbCheck.setChecked(isSelected);
                topBar.setTitle(getString(R.string.preview_image_count, mCurrentPosition + 1, mImageItems.size()));
            }
        });
        //当点击当前选中按钮的时候，需要根据当前的选中状态添加和移除图片
        mCbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageItem imageItem = mImageItems.get(mCurrentPosition);
                int selectLimit = imagePicker.getSelectLimit();
                if (mCbCheck.isChecked() && selectedImages.size() >= selectLimit) {
                    Toast.makeText(PreviewActivity.this, PreviewActivity.this.getString(R.string.select_limit, selectLimit), Toast.LENGTH_SHORT)
                        .show();
                    mCbCheck.setChecked(false);
                } else {
                    imagePicker.addSelectedImageItem(mCurrentPosition, imageItem, mCbCheck.isChecked());
                }
            }
        });
    }

    /**
     * 图片添加成功后，修改当前图片的选中数量
     * 当调用 addSelectedImageItem 或 deleteSelectedImageItem 都会触发当前回调
     */
    @Override
    public void onImageSelected(int position, ImageItem item, boolean isAdd) {
        if (imagePicker.getSelectImageCount() > 0) {
            mBtnOk.setText(getString(R.string.select_complete, imagePicker.getSelectImageCount(), imagePicker.getSelectLimit()));
            mBtnOk.setEnabled(true);
        } else {
            mBtnOk.setText(getString(R.string.complete));
            mBtnOk.setEnabled(false);
        }

        if (mCbOrigin.isChecked()) {
            long size = 0;
            for (ImageItem imageItem : selectedImages)
                size += imageItem.size;
            String fileSize = Formatter.formatFileSize(this, size);
            mCbOrigin.setText(getString(R.string.origin_size, fileSize));
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(PreviewActivity.ISORIGIN, isOrigin);
        setResult(ImagePicker.RESULT_CODE_BACK, intent);
        finish();
        super.onBackPressed();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if (id == R.id.cb_origin) {
            if (isChecked) {
                long size = 0;
                for (ImageItem item : selectedImages)
                    size += item.size;
                String fileSize = Formatter.formatFileSize(this, size);
                isOrigin = true;
                mCbOrigin.setText(getString(R.string.origin_size, fileSize));
            } else {
                isOrigin = false;
                mCbOrigin.setText(getString(R.string.origin));
            }
        }
    }

    @Override
    protected void onDestroy() {
        imagePicker.removeOnImageSelectedListener(this);
        super.onDestroy();
    }

    /** 单击时，隐藏头和尾 */
    public void onImageSingleTap() {
        if (topBar.getVisibility() == View.VISIBLE) {
            topBar.setVisibility(View.GONE);
            bottomBar.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= 16)
                content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        } else {
            topBar.setVisibility(View.VISIBLE);
            bottomBar.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= 16)
                content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }
}
