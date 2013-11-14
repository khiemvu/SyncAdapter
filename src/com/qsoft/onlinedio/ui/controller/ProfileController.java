package com.qsoft.onlinedio.ui.controller;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import com.googlecode.androidannotations.annotations.*;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.qsoft.onlinedio.R;
import com.qsoft.onlinedio.database.Contract;
import com.qsoft.onlinedio.database.entity.ProfileModel;
import com.qsoft.onlinedio.filecache.ImageLoader;
import com.qsoft.onlinedio.restfullservice.RestClientProfile;
import com.qsoft.onlinedio.restfullservice.container.ProfileContainer;
import com.qsoft.onlinedio.syncadapter.ProfileParseToServer;
import com.qsoft.onlinedio.ui.activity.ProfileActivity;
import com.qsoft.onlinedio.ui.activity.SlidebarActivity_;
import com.qsoft.onlinedio.validate.Constant;

/**
 * User: khiemvx
 * Date: 11/12/13
 */
@EBean
public class ProfileController
{
    @RestService
    RestClientProfile restClientProfile;

    @SystemService
    AccountManager mAccountManager;

    private ProfileParseToServer parseToServer = new ProfileParseToServer();
    private String[] countries;
    private String[] countries_code;
    static final int DATE_DIALOG_ID = 0;
    private static final int PICK_IMAGE = 1;
    private static final int CAMERA_REQUEST = 1888;
    public int year, month, day;
    private String code;
    private boolean isSelected;
    ProfileModel model = new ProfileModel();
    private String auth_token;
    private String user_id;
    private String TAG = this.getClass().getSimpleName();
    private Account mConnectedAccount;
    private AlertDialog alertDialog;
    private AlertDialog alertCountryDialog;

    @RootContext
    ProfileActivity context;

    ImageLoader imageLoader = new ImageLoader(context);

    public void prepareData()
    {
        countries = context.getResources().getStringArray(
                R.array.country_array);
        countries_code = context.getResources().getStringArray(R.array.country_code);
    }

    @Background
    public void getTokenAndAccount()
    {
        Intent intent = context.getIntent();
        auth_token = intent.getStringExtra(Constant.AUTHEN_TOKEN.getValue());
        mConnectedAccount = intent.getParcelableExtra(Constant.ACCOUNT_CONNECTED.getValue());
        user_id = intent.getStringExtra(Constant.USER_ID.getValue());
    }

    @Background
    public void putDataDB()
    {
        Log.i(TAG, "Get data for show on view");
        try
        {
            ProfileContainer container = restClientProfile.getProfiles(user_id, auth_token);
            model = container.getData();
            context.getContentResolver().delete(Contract.CONTENT_URI_PROFILE, null, null);
            context.getContentResolver().insert(Contract.CONTENT_URI_PROFILE, model.getContentValues());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        showDataView();
    }

    @UiThread
    protected void showDataView()
    {
        Cursor cur = context.getContentResolver().query(Contract.CONTENT_URI_PROFILE, null, null, null, null);
        ProfileModel temp = new ProfileModel();
        if (cur != null)
        {
            while (cur.moveToNext())
            {
                temp = ProfileModel.fromCursor(cur);
                context.getPr_etDisplayName().setText(temp.getDisplay_name());
                context.getPr_edFullName().setText(temp.getFull_name());
                context.getPr_edPhone().setText(temp.getPhone());
                if (temp.getGender() == 1)
                {
                    isSelected = true;
                    context.getBtGenderSelectLeft().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.pr_btn_select_left));
                    context.getBtGenderSelectRight().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.pr_btn_unselect_right));
                }
                else
                {
                    isSelected = false;
                    context.getBtGenderSelectLeft().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.pr_btn_unselect_left));
                    context.getBtGenderSelectRight().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.pr_btn_select_right));
                }
                context.getPr_edBirthday().setText(temp.getBirthday());
                int position = 0;
                for (int i = 0; i < countries_code.length; i++)
                {
                    if (temp.getCountry_id().equals(countries_code[i]))
                    {
                        position = i;
                    }
                }

                context.getPr_edCountry().setText(countries[position].toString());
                context.getPr_etDescription().setText(temp.getDescription());
                imageLoader.DisplayImage(temp.getAvatar(), context.getPr_imgAvatar());
                imageLoader.DisplayImage(temp.getCover_image(), context.getPr_ivBackground());
            }
            cur.close();
        }
    }

    @Click({R.id.pr_imgAvatar, R.id.pr_ibDeleteFullName, R.id.pr_ibDeletePhone, R.id.pr_edBirthday,
            R.id.pr_edCountry, R.id.pr_btnSelectLeft_check, R.id.pr_btnSelectRight_check,
            R.id.pr_rlBackGround, R.id.pr_btCancel, R.id.pr_btSave, R.id.dg_btChoosePicture, R.id.dg_btTakePicture})
    protected void onClickListener(View view)
    {
        switch (view.getId())
        {
            case R.id.pr_imgAvatar:
                code = "cover_image";
                showDialogSelectImage();
                break;
            case R.id.pr_rlBackGround:
                code = "background_image";
                showDialogSelectImage();
                break;
            case R.id.pr_ibDeleteFullName:
                context.getPr_edFullName().setText("");
                break;
            case R.id.pr_ibDeletePhone:
                context.getPr_edPhone().setText("");
                break;
            case R.id.pr_edBirthday:
                context.showDialog(DATE_DIALOG_ID);
                break;
            case R.id.pr_edCountry:
                showDialogCountry();
                break;
            case R.id.pr_btnSelectLeft_check:
                context.getBtGenderSelectLeft().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.pr_btn_select_left));
                context.getBtGenderSelectRight().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.pr_btn_unselect_right));
                break;
            case R.id.pr_btnSelectRight_check:
                context.getBtGenderSelectLeft().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.pr_btn_unselect_left));
                context.getBtGenderSelectRight().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.pr_btn_select_right));
                break;
            case R.id.pr_btCancel:
                callBackSlidebarActivity();
                break;
            case R.id.pr_btSave:
                ProfileModel profileUpdate = getProfileModel();

                try
                {
                    restClientProfile.updateProfiles(auth_token, user_id, profileUpdate);
//                    parseToServer.putShow(auth_token, user_id, profileUpdate);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                try
                {
                    ProfileContainer container = restClientProfile.getProfiles(user_id, auth_token);
                    model = container.getData();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                context.getContentResolver().update(Contract.CONTENT_URI_PROFILE, model.getContentValues(), null, null);
                Toast.makeText(context.getApplicationContext(), "Update successfull", 1);
                callBackSlidebarActivity();
                break;
        }
    }

    private ProfileModel getProfileModel()
    {
        ProfileModel profileUpdate = new ProfileModel();
        profileUpdate.setId(Integer.parseInt(user_id));
        profileUpdate.setFull_name(context.getPr_edFullName().getText().toString());
        profileUpdate.setDisplay_name(context.getPr_etDisplayName().getText().toString());
        profileUpdate.setDescription(context.getPr_etDescription().getText().toString());
        profileUpdate.setPhone(context.getPr_edPhone().getText().toString());
        profileUpdate.setBirthday(context.getPr_edBirthday().getText().toString());
        String temp = context.getPr_edCountry().getText().toString();
        int country_id = 0;
        for (int i = 0; i < countries.length; i++)
        {
            if (temp.equals(countries[i]))
            {
                country_id = i;
            }
        }
        profileUpdate.setCountry_id(countries_code[country_id]);
        int gender;
        if (isSelected)
        {
            gender = 1;
        }
        else
        {
            gender = 0;
        }
        profileUpdate.setGender(gender);
        return profileUpdate;
    }

    private void callBackSlidebarActivity()
    {
        Intent intent = new Intent(context, SlidebarActivity_.class);
        intent.putExtra(Constant.AUTHEN_TOKEN.getValue(), auth_token);
        intent.putExtra(Constant.USER_ID.getValue(), user_id);
        intent.putExtra(Constant.ACCOUNT_CONNECTED.getValue(), mConnectedAccount);
        context.startActivity(intent);
    }

    @TextChange({R.id.pr_edFullName, R.id.pr_edPhone})
    protected void onTextChangesOnSomeTextViews(CharSequence s, int start, int before,
                                                int count)
    {
        if (!context.getPr_edFullName().getText().toString().isEmpty())
        {
            context.getPr_ibDeleteFullName().setVisibility(View.VISIBLE);
            context.getPr_ibDeletePhone().setVisibility(View.INVISIBLE);
        }
        if (!context.getPr_edPhone().getText().toString().isEmpty())
        {
            context.getPr_ibDeleteFullName().setVisibility(View.VISIBLE);
            context.getPr_ibDeletePhone().setVisibility(View.INVISIBLE);
        }

    }

    @AfterTextChange({R.id.pr_edFullName, R.id.pr_edPhone})
    protected void afterTextChangedOnSomeTextViews(Editable s)
    {
        context.getPr_edFullName().setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus && !context.getPr_edFullName().getText().toString().isEmpty())
                {
                    context.getPr_ibDeleteFullName().setVisibility(View.VISIBLE);
                }
                else
                {
                    context.getPr_ibDeleteFullName().setVisibility(View.INVISIBLE);
                }
            }
        });
        context.getPr_edPhone().setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus && !context.getPr_edPhone().getText().toString().isEmpty())
                {
                    context.getPr_ibDeletePhone().setVisibility(View.VISIBLE);
                }
                else
                {
                    context.getPr_ibDeletePhone().setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    protected Dialog onCreateDialog(int id)
    {
        switch (id)
        {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(context, datePickerListener,
                        year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener()
    {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay)
        {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            context.getPr_edBirthday().setText(new StringBuilder().append(month + 1)
                    .append("/").append(day).append("/").append(year).append(" "));

        }
    };

    private void showDialogCountry()
    {

        context.getPr_edCountry().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Select Country");

                builder.setSingleChoiceItems(countries, -1, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int item)
                    {
                        context.getPr_edCountry().setText(countries[item].toString());
                        alertCountryDialog.dismiss();
                    }
                });
                alertCountryDialog = builder.create();
                alertCountryDialog.show();
            }
        });
    }


    private void showDialogSelectImage()
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View yourCustomView = inflater.inflate(R.layout.dg_choose_image_fragment, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(yourCustomView);
        alertDialog = builder.create();
        alertDialog.setTitle("Profile Image");
        alertDialog.show();
        context.setBtTakePicture((Button) yourCustomView.findViewById(R.id.dg_btTakePicture));
        context.setBtChoosePicture((Button) yourCustomView.findViewById(R.id.dg_btChoosePicture));
        context.setBtCancel((Button) yourCustomView.findViewById(R.id.dg_btCancel));
        context.getBtTakePicture().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                takePhoto(view);
            }
        });
        context.getBtChoosePicture().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                chooseAPicture();
            }
        });
        context.getBtCancel().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                alertDialog.cancel();
            }
        });
    }

    private void chooseAPicture()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    public void takePhoto(View v)
    {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        context.startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Bitmap circleBitmap;
        if (requestCode == CAMERA_REQUEST)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            circleBitmap = resizeBitMap(bitmap);
            if (code.equals("cover_image"))
            {
                context.getPr_imgAvatar().setImageBitmap(circleBitmap);
            }
            else if (code.equals("background_image"))
            {
                Drawable cover = new BitmapDrawable(bitmap);
                context.getPr_rlBackGround().setBackgroundDrawable(cover);
            }

        }
        if (requestCode == PICK_IMAGE && resultCode == context.RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bmp = BitmapFactory.decodeFile(filePath);
            circleBitmap = resizeBitMap(bmp);
            if (code.equals("cover_image"))
            {
                context.getPr_imgAvatar().setImageBitmap(circleBitmap);
            }
            else if (code.equals("background_image"))
            {
                Drawable cover = new BitmapDrawable(bmp);
                context.getPr_rlBackGround().setBackgroundDrawable(cover);
            }

        }
        alertDialog.cancel();
    }

    private Bitmap resizeBitMap(Bitmap bitmap)
    {
        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        return circleBitmap;
    }

}
