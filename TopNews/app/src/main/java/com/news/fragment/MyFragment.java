package com.news.fragment;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import com.mylhyl.superdialog.SuperDialog;
import com.news.*;
import com.news.bean.Event;
import com.news.bean.User;
import com.news.requestcode.RequestCode;
import com.news.util.DialogUtils;
import com.news.util.SQLUtils;
import com.news.view.ImageViewPlus;
import com.news.view.LoginDialog;
import com.news.view.RegisterDialog;
import org.greenrobot.eventbus.EventBus;

import static android.app.Activity.RESULT_OK;

public class MyFragment extends Fragment implements View.OnClickListener {
    private LinearLayout llMineFocus, llMineTaobao, llMineJingdong, llMineConnectionUs, llMineCheckUpdate, llMineExit;
    private View view, dialogView, dialogRegisterView;
    private ImageViewPlus ivHead;
    private LoginDialog loginDialog;
    private RegisterDialog registerDialog;
    private TextView tvDialogRegister, tvLoginName,tvUserLoginExit;
    private EditText etDialogPhoneNumberAccount, etDialogAccountRegister, etDialogPassword, etDialogPasswordRegister,
            getEtDialogPasswordConfirm;
    private Button btnLogin, btnRegister;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        initView();
        initData();
        setListener();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView() {
        llMineFocus = view.findViewById(R.id.ll_mine_focus);
        llMineTaobao = view.findViewById(R.id.ll_mine_taobao);
        llMineJingdong = view.findViewById(R.id.ll_mine_jingdong);
        llMineConnectionUs = view.findViewById(R.id.ll_mine_connection_us);
        llMineCheckUpdate = view.findViewById(R.id.ll_mine_check_version);
        llMineExit = view.findViewById(R.id.ll_mine_exit);
        ivHead = view.findViewById(R.id.iv_head);
        tvLoginName = view.findViewById(R.id.tv_login_name);
        tvUserLoginExit = view.findViewById(R.id.tv_user_login_exit);
        //登录对话框视图
        dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_login, null);
        etDialogPassword = dialogView.findViewById(R.id.et_dialog_password);
        etDialogPhoneNumberAccount = dialogView.findViewById(R.id.et_dialog_phone_number_account);
        btnLogin = dialogView.findViewById(R.id.btn_dialog_login);
        tvDialogRegister = dialogView.findViewById(R.id.tv_dialog_register);
        //注册对话框视图
        dialogRegisterView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_register, null);
        etDialogAccountRegister = dialogRegisterView.findViewById(R.id.et_dialog_account_register);
        etDialogPasswordRegister = dialogRegisterView.findViewById(R.id.et_dialog_password_register);
        getEtDialogPasswordConfirm = dialogRegisterView.findViewById(R.id.et_dialog_password_confirm);
        btnRegister = dialogRegisterView.findViewById(R.id.btn_dialog_register);
    }

    void initData() {
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Bundle bundle = getArguments();
        String userName = bundle.getString("loginName");
        if (userName != null && !"".equals(userName)) {
            Log.i("userName",userName);
            tvLoginName.setText(userName);
            tvUserLoginExit.setText(R.string.exit);
            tvUserLoginExit.setTextColor(getResources().getColor(R.color.colorAccent));

            User user = SQLUtils.queryUserByUserName(userName);
            setIvHead(user);
        }
    }

    private void setListener() {
        llMineFocus.setOnClickListener(this);
        llMineTaobao.setOnClickListener(this);
        llMineJingdong.setOnClickListener(this);
        llMineConnectionUs.setOnClickListener(this);
        llMineCheckUpdate.setOnClickListener(this);
        llMineExit.setOnClickListener(this);
        ivHead.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvDialogRegister.setOnClickListener(this);
    }

    boolean checkSendPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    RequestCode.REQUEST_CODE_READ_EXTERNAL_STORAGE);
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //我的关注
            case R.id.ll_mine_focus:
                if (!"".equals(tvLoginName.getText().toString())) {
                    Intent myFocusIntent = new Intent(getActivity(), MyFocusActivity.class);
                    myFocusIntent.putExtra("loginName", tvLoginName.getText().toString());
                    startActivity(myFocusIntent);
                } else {
                    DialogUtils.showDialog(getActivity(), "提示", "您还未登录", new SuperDialog.OnClickPositiveListener() {
                        @Override
                        public void onClick(View v) {
                            loginDialog = new LoginDialog(getActivity(), R.style.LoginDialog, dialogView);
                            loginDialog.show();
                        }
                    }, null);
                }
                break;
            //淘宝商城
            case R.id.ll_mine_taobao:
                startActivity(new Intent(getActivity(), TaobaoActivity.class));
                break;
            //京东特供
            case R.id.ll_mine_jingdong:
                startActivity(new Intent(getActivity(), JingDongActivity.class));
                break;
            //关于我们
            case R.id.ll_mine_connection_us:
                startActivity(new Intent(getActivity(), ConnectionUsActivity.class));
                break;
            //检查版本
            case R.id.ll_mine_check_version:
                Toast.makeText(getActivity(), "已是最新版本", Toast.LENGTH_LONG).show();
                break;
            //登录按钮
            case R.id.ll_mine_exit:
                //用户未登录状态
                if ("".equals(tvLoginName.getText().toString())){
                    loginDialog = new LoginDialog(getActivity(), R.style.LoginDialog, dialogView);
                    loginDialog.show();
                    //用户已登录状态
                }else {
                    //初始化用户名和头像
                    tvLoginName.setText("");
                    ivHead.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon));
                    //清空sharedpreferences中的数据
                    editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    Log.e("userName",preferences.getString("userName",""));
                    Log.e("password",preferences.getString("password",""));
                    //清空post数据
                    EventBus.getDefault().post(new Event());
                    //初始化登录按钮
                    tvUserLoginExit.setText(R.string.login);
                    tvUserLoginExit.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                break;
            //头像
            case R.id.iv_head:
                if ("".equals(tvLoginName.getText().toString())) {
                    loginDialog = new LoginDialog(getActivity(), R.style.LoginDialog, dialogView);
                    loginDialog.show();
                } else {
                    if (checkSendPermission()) {
                        getImageFromPhotoAlbum();
                    }
                }
                break;
            //短信验证
            case R.id.tv_dialog_check_code_register:
                break;
            //登录按钮
            case R.id.btn_dialog_login:
                User userLogin = SQLUtils.queryUserByUserName(etDialogPhoneNumberAccount.getText().toString());

                if (userLogin.getUserName() != null && !"".equals(userLogin.getUserName())) {
                    //登录成功
                    if (userLogin.getPassword().equals(etDialogPassword.getText().toString())) {
                        //设置用户名和头像
                        tvLoginName.setText(userLogin.getUserName());
                        setIvHead(userLogin);
                        //关闭对话框
                        loginDialog.dismiss();
                        ViewGroup parent = (ViewGroup) dialogView.getParent();
                        parent.removeView(dialogView);
                        //数据传递
                        EventBus.getDefault().post(new Event(userLogin.getUserName()));
                        //将用户名和密码存储到sharedpreferences
                        editor = preferences.edit();
                        editor.putString("userName",userLogin.getUserName());
                        editor.putString("password",userLogin.getPassword());
                        editor.apply();
                        Log.e("userName",preferences.getString("userName",""));
                        Log.e("password",preferences.getString("password",""));
                        //清空文本框，登录改为退出
                        etDialogPhoneNumberAccount.setText("");
                        etDialogPassword.setText("");
                        tvUserLoginExit.setText(R.string.exit);
                        tvUserLoginExit.setTextColor(getResources().getColor(R.color.colorAccent));
                    } else {
                        DialogUtils.showDialog(getActivity(), "提示", "您输入的密码错误，请重试",
                                new SuperDialog.OnClickPositiveListener() {
                                    @Override
                                    public void onClick(View v) {
                                        etDialogPassword.setText("");
                                    }
                                }, null);
                    }
                } else if (!"".equals(etDialogPhoneNumberAccount.getText().toString())) {
                    DialogUtils.showDialog(getActivity(), "提示", "您还未注册，请注册",
                            new SuperDialog.OnClickPositiveListener() {
                                @Override
                                public void onClick(View v) {
                                    registerDialog = new RegisterDialog(getActivity(), R.style.LoginDialog, dialogRegisterView);
                                    registerDialog.show();
                                }
                            }, null);
                } else {
                    DialogUtils.showDialog(getActivity(), "提示", "请输入用户名", null, null);
                }
                break;
            //注册按钮（注册界面）
            case R.id.btn_dialog_register:
                User user = SQLUtils.queryUserByUserName(etDialogAccountRegister.getText().toString());

                if (user.getUserName() != null && !"".equals(user.getUserName())) {
                    DialogUtils.showDialog(getActivity(), "提示", "该用户名已被使用，请重试",
                            new SuperDialog.OnClickPositiveListener() {
                                @Override
                                public void onClick(View v) {
                                    etDialogAccountRegister.setText("");
                                }
                            }, null);
                } else {
                    if (!getEtDialogPasswordConfirm.getText().toString().equals(etDialogPasswordRegister.getText().toString())) {
                        DialogUtils.showDialog(getActivity(), "提示", "两次密码不一，请重试",
                                new SuperDialog.OnClickPositiveListener() {
                                    @Override
                                    public void onClick(View v) {
                                        etDialogPasswordRegister.setText("");
                                        getEtDialogPasswordConfirm.setText("");
                                    }
                                }, null);
                    } else {
                        String userName = etDialogAccountRegister.getText().toString();
                        String password = etDialogPasswordRegister.getText().toString();
                        if (!"".equals(userName) && !"".equals(password)) {
                            User u = new User(userName, password, "");
                            SQLUtils.addUser(u);
                            registerDialog.dismiss();
                            ViewGroup parent = (ViewGroup) dialogRegisterView.getParent();
                            parent.removeView(dialogRegisterView);
                        }
                    }
                }

                break;
            //注册按钮（登录界面）
            case R.id.tv_dialog_register:
                registerDialog = new RegisterDialog(getActivity(), R.style.LoginDialog, dialogRegisterView);
                registerDialog.show();
                break;
        }
    }

    private int getWindowWidth() {
        WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public void setIvHead(User user){
        if (user.getImagePath() != null && !"".equals(user.getImagePath()))
            ivHead.setImageBitmap(BitmapFactory.decodeFile(user.getImagePath()));
    }

    public void getImageFromPhotoAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, RequestCode.REQUEST_CODE_PICTURE);
    }

    public String getData(Context context, Uri uri, String selection, String[] args) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, selection, args, null);
        if (cursor != null && cursor.moveToFirst()) {
            int index = cursor.getColumnIndexOrThrow(projection[0]);
            return cursor.getString(index);
        }
        return "";
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getRealPhotoPath(Context context, Uri uri) {
        String path = "";
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String documentId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] args = {id};
                path = getData(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, args);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri content = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                path = getData(context, content, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            path = getData(context, uri, null, null);
        } else if ("file".equals(uri.getScheme())) {
            path = uri.getPath();
        }
        return path;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCode.REQUEST_CODE_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getImageFromPhotoAlbum();
                } else {
                    Toast.makeText(getActivity(), "该应用没有访问相册的权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RequestCode.REQUEST_CODE_PICTURE:
                if (resultCode == RESULT_OK && data != null) {
                    String path = getRealPhotoPath(getActivity(), data.getData());
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    User user = SQLUtils.queryUserByUserName(tvLoginName.getText().toString());
                    SQLUtils.addImageToUser(user, path);
                    ivHead.setImageBitmap(bitmap);
                }
                break;
        }
    }

}
