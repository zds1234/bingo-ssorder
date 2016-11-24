package com.eunut.ext.imagepicker.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.eunut.ext.imagepicker.ImagePicker;
import com.eunut.ext.imagepicker.ImageSource;
import com.eunut.ext.imagepicker.Utils;
import com.eunut.ext.imagepicker.adapter.AlbumAdapter;
import com.eunut.ext.imagepicker.adapter.FolderAdapter;
import com.eunut.ext.imagepicker.bean.ImageFolder;
import com.eunut.ext.imagepicker.bean.ImageItem;
import com.eunut.sdk.R;
import com.eunut.util.PermissionUtil;
import com.eunut.widget.TopBar;

import java.util.List;

public class AlbumActivity extends FragmentActivity implements ImageSource.OnImagesLoadedListener, AlbumAdapter.OnImageItemClickListener, ImagePicker.OnImageSelectedListener, View.OnClickListener {

    private ImagePicker imagePicker;

    private boolean isOrigin = false;  //是否选中原图
    private int screenWidth;     //屏幕的宽
    private int screenHeight;    //屏幕的高
    private GridView mGridView;  //图片展示控件
    private TopBar mTopBar;        //顶部栏
    private View mFooterBar;     //底部栏
    private TextView mBtnOk;       //确定按钮
    private Button mBtnDir;      //文件夹切换按钮
    private Button mBtnPre;      //预览按钮
    private FolderAdapter mFolderAdapter;    //图片文件夹的适配器
    private ListPopupWindow mFolderPopupWindow;  //ImageSet的PopupWindow
    private List<ImageFolder> mImageFolders;   //所有的图片文件夹
    private AlbumAdapter mAlbumAdapter;  //图片九宫格展示的适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eunut_activity_album);

        imagePicker = ImagePicker.getInstance();
        imagePicker.clear();
        imagePicker.addOnImageSelectedListener(this);
        DisplayMetrics dm = Utils.getScreenPix(this);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        mTopBar = (TopBar) findViewById(R.id.top_bar);
        mTopBar.setOnActionClickListener(new TopBar.ActionClickListener() {
            @Override
            public void onActionClick(View v, int position) {
                Intent intent = new Intent();
                intent.putExtra(ImagePicker.EXTRA_RESULT_ITEMS, imagePicker.getSelectedImages());
                setResult(ImagePicker.RESULT_CODE_ITEMS, intent);  //多选不允许裁剪裁剪，返回数据
                finish();
            }
        });
        mBtnOk = (TextView) mTopBar.getActionView().getChildAt(0);

        mBtnDir = (Button) findViewById(R.id.btn_dir);
        mBtnDir.setOnClickListener(this);
        mBtnPre = (Button) findViewById(R.id.btn_preview);
        mBtnPre.setOnClickListener(this);
        mGridView = (GridView) findViewById(R.id.gridview);
        mFooterBar = findViewById(R.id.footer_bar);
        if (imagePicker.isMultiMode()) {
            mBtnOk.setVisibility(View.VISIBLE);
            mBtnPre.setVisibility(View.VISIBLE);
        } else {
            mBtnOk.setVisibility(View.GONE);
            mBtnPre.setVisibility(View.GONE);
        }

        mAlbumAdapter = new AlbumAdapter(this, null);
        mFolderAdapter = new FolderAdapter(this, null);

        onImageSelected(0, null, false);
        if (PermissionUtil.with(this).check(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new ImageSource(this, null, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            int result = grantResults[i];
            if (Manifest.permission.READ_EXTERNAL_STORAGE.equals(permission) && result == PackageManager.PERMISSION_GRANTED) {
                new ImageSource(this, null, this);
            }
        }
    }

    @Override
    protected void onDestroy() {
        imagePicker.removeOnImageSelectedListener(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_dir) {
            if (mImageFolders == null) {
                Log.i("ImageGridActivity", "您的手机没有图片");
                return;
            }
            //点击文件夹按钮
            if (mFolderPopupWindow == null) createPopupFolderList(screenWidth, screenHeight);
            backgroundAlpha(0.3f);   //改变View的背景透明度
            mFolderAdapter.refreshData(mImageFolders);  //刷新数据
            if (mFolderPopupWindow.isShowing()) {
                mFolderPopupWindow.dismiss();
            } else {
                mFolderPopupWindow.show();
                //默认选择当前选择的上一个，当目录很多时，直接定位到已选中的条目
                int index = mFolderAdapter.getSelectIndex();
                index = index == 0 ? index : index - 1;
                mFolderPopupWindow.getListView().setSelection(index);
            }
        } else if (id == R.id.btn_preview) {
            Intent intent = new Intent(AlbumActivity.this, PreviewActivity.class);
            intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
            intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imagePicker.getSelectedImages());
            intent.putExtra(PreviewActivity.ISORIGIN, isOrigin);
            startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
        }
    }

    /** 创建弹出的ListView */
    private void createPopupFolderList(int width, int height) {
        mFolderPopupWindow = new ListPopupWindow(this);
        mFolderPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mFolderPopupWindow.setAdapter(mFolderAdapter);
        mFolderPopupWindow.setContentWidth(width);
        mFolderPopupWindow.setWidth(width);  //如果不设置，就是 AnchorView 的宽度
        int maxHeight = height * 5 / 8;
        int realHeight = mFolderAdapter.getItemViewHeight() * mFolderAdapter.getCount();
        int popHeight = realHeight > maxHeight ? maxHeight : realHeight;
        mFolderPopupWindow.setHeight(popHeight);
        mFolderPopupWindow.setAnchorView(mFooterBar);  //ListPopupWindow总会相对于这个View
        mFolderPopupWindow.setModal(true);  //是否为模态，影响返回键的处理
        //mFolderPopupWindow.setAnimationStyle(R.style.popupwindow_anim_style);
        mFolderPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        mFolderPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mFolderAdapter.setSelectIndex(position);
                imagePicker.setCurrentImageFolderPosition(position);
                mFolderPopupWindow.dismiss();
                ImageFolder imageFolder = (ImageFolder) adapterView.getAdapter().getItem(position);
                if (null != imageFolder) {
                    mAlbumAdapter.refreshData(imageFolder.images);
                    mBtnDir.setText(imageFolder.name);
                }
                mGridView.smoothScrollToPosition(0);//滑动到顶部
            }
        });
    }

    /** 设置屏幕透明度  0.0透明  1.0不透明 */
    public void backgroundAlpha(float alpha) {
        mGridView.setAlpha(alpha);
        mTopBar.setAlpha(alpha);
        mFooterBar.setAlpha(1.0f);
    }

    @Override
    public void onImagesLoaded(List<ImageFolder> imageFolders) {
        this.mImageFolders = imageFolders;
        imagePicker.setImageFolders(imageFolders);
        if (imageFolders.size() == 0) mAlbumAdapter.refreshData(null);
        else mAlbumAdapter.refreshData(imageFolders.get(0).images);
        mAlbumAdapter.setOnImageItemClickListener(this);
        mGridView.setAdapter(mAlbumAdapter);
        mFolderAdapter.refreshData(imageFolders);
    }

    @Override
    public void onImageItemClick(View view, ImageItem imageItem, int position) {
        //根据是否有相机按钮确定位置
        position = imagePicker.isShowCamera() ? position - 1 : position;
        if (imagePicker.isMultiMode()) {
            Intent intent = new Intent(AlbumActivity.this, PreviewActivity.class);
            intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
            intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imagePicker.getCurrentImageFolderItems());
            intent.putExtra(PreviewActivity.ISORIGIN, isOrigin);
            startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);  //如果是多选，点击图片进入预览界面
        } else {
            imagePicker.clearSelectedImages();
            imagePicker.addSelectedImageItem(position, imagePicker.getCurrentImageFolderItems().get(position), true);
            if (imagePicker.isCrop()) {
                Intent intent = new Intent(AlbumActivity.this, CropActivity.class);
                startActivityForResult(intent, ImagePicker.REQUEST_CODE_CROP);  //单选需要裁剪，进入裁剪界面
            } else {
                Intent intent = new Intent();
                intent.putExtra(ImagePicker.EXTRA_RESULT_ITEMS, imagePicker.getSelectedImages());
                setResult(ImagePicker.RESULT_CODE_ITEMS, intent);   //单选不需要裁剪，返回数据
                finish();
            }
        }
    }

    @Override
    public void onImageSelected(int position, ImageItem item, boolean isAdd) {
        if (imagePicker.getSelectImageCount() > 0) {
            mBtnOk.setText(getString(R.string.select_complete, imagePicker.getSelectImageCount(), imagePicker.getSelectLimit()));
            mBtnOk.setEnabled(true);
            mBtnPre.setEnabled(true);
        } else {
            mBtnOk.setText(getString(R.string.complete));
            mBtnOk.setEnabled(false);
            mBtnPre.setEnabled(false);
        }
        mBtnPre.setText(getResources().getString(R.string.preview_count, imagePicker.getSelectImageCount()));
        mAlbumAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == ImagePicker.RESULT_CODE_BACK) {
                isOrigin = data.getBooleanExtra(PreviewActivity.ISORIGIN, false);
            } else {
                //从拍照界面返回
                //点击 X , 没有选择照片
                if (data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) == null) {
                    //什么都不做
                } else {
                    //说明是从裁剪页面过来的数据，直接返回就可以
                    setResult(ImagePicker.RESULT_CODE_ITEMS, data);
                    finish();
                }
            }
        } else {
            //如果是裁剪，因为裁剪指定了存储的Uri，所以返回的data一定为null
            if (resultCode == RESULT_OK && requestCode == ImagePicker.REQUEST_CODE_TAKE) {
                //发送广播通知图片增加了
                ImagePicker.galleryAddPic(this, imagePicker.getTakeImageFile());
                ImageItem imageItem = new ImageItem();
                imageItem.path = imagePicker.getTakeImageFile().getAbsolutePath();
                imagePicker.clearSelectedImages();
                imagePicker.addSelectedImageItem(0, imageItem, true);
                if (imagePicker.isCrop()) {
                    Intent intent = new Intent(AlbumActivity.this, CropActivity.class);
                    startActivityForResult(intent, ImagePicker.REQUEST_CODE_CROP);  //单选需要裁剪，进入裁剪界面
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(ImagePicker.EXTRA_RESULT_ITEMS, imagePicker.getSelectedImages());
                    setResult(ImagePicker.RESULT_CODE_ITEMS, intent);   //单选不需要裁剪，返回数据
                    finish();
                }
            }
        }
    }
}