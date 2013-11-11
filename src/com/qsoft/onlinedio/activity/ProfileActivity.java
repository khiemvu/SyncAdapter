package com.qsoft.onlinedio.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.*;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.onlinedio.R;
import com.qsoft.onlinedio.database.Contract;
import com.qsoft.onlinedio.database.entity.ProfileModel;
import com.qsoft.onlinedio.filecache.ImageLoader;
import com.qsoft.onlinedio.syncadapter.ProfileParseToServer;

/**
 * User: Dell 3360
 * Date: 10/17/13
 * Time: 8:39 AM
 */
@EActivity(R.layout.profile_layout)
public class ProfileActivity extends Activity
{
    ImageLoader imageLoader = new ImageLoader(this);
    ProfileModel model = new ProfileModel();
    private String auth_token;
    private String user_id;
    private String TAG = this.getClass().getSimpleName();
    private Account mConnectedAccount;
    private AccountManager mAccountManager;
    ProfileParseToServer parseToServer = new ProfileParseToServer();
    String[] countries;
    String[] countries_code;

    @ViewById(R.id.pr_imgAvatar)
    ImageView pr_imgAvatar;

    @ViewById(R.id.pr_ivBackground)
    ImageView pr_ivBackground;

    @ViewById(R.id.dg_btTakePicture)
    Button btTakePicture;

    @ViewById(R.id.dg_btChoosePicture)
    Button btChoosePicture;

    @ViewById(R.id.pr_btCancel)
    Button btCancel;

    @ViewById(R.id.pr_btnSelectLeft_check)
    Button btGenderSelectLeft;

    @ViewById(R.id.pr_btnSelectRight_check)
    Button btGenderSelectRight;

    @ViewById(R.id.pr_btCancel)
    Button pr_btCancel;

    @ViewById(R.id.pr_btSave)
    Button pr_btSave;

    @ViewById(R.id.pr_edFullName)
    EditText pr_edFullName;

    @ViewById(R.id.pr_edPhone)
    EditText pr_edPhone;

    @ViewById(R.id.pr_edBirthday)
    EditText pr_edBirthday;

    @ViewById(R.id.pr_edCountry)
    EditText pr_edCountry;

    @ViewById(R.id.pr_edDisplayName)
    EditText pr_etDisplayName;

    @ViewById(R.id.pr_etDescription)
    EditText pr_etDescription;

    @ViewById(R.id.pr_ibDeleteFullName)
    ImageButton pr_ibDeleteFullName;

    @ViewById(R.id.pr_ibDeletePhone)
    ImageButton pr_ibDeletePhone;

    @ViewById(R.id.pr_rlBackGround)
    RelativeLayout pr_rlBackGround;

    AlertDialog alertDialog;
    AlertDialog alertCountryDialog;

    static final int DATE_DIALOG_ID = 0;
    private static final int PICK_IMAGE = 1;
    private static final int CAMERA_REQUEST = 1888;
    public int year, month, day;
    final Context context = this;

    String code;
    private boolean isSelected;

    @AfterViews
    protected void afterViews(){
        mAccountManager = AccountManager.get(this);
        prepareData();
        getTokenAndAccount();
        putDataDB();
    }

    private void prepareData()
    {
        countries = getResources().getStringArray(
                R.array.country_array);
        countries_code = getResources().getStringArray(R.array.country_code);
    }

    private void getTokenAndAccount()
    {
        Intent intent = getIntent();
        auth_token = intent.getStringExtra(FirstLaunchActivity.AUTHEN_TOKEN);
        mConnectedAccount = intent.getParcelableExtra(FirstLaunchActivity.ACCOUNT_CONNECTED);
        user_id = intent.getStringExtra(LoginActivity.USER_ID);
    }

    @Background
    protected void putDataDB()
    {
        Log.i(TAG, "Get data for show on view");
        try
        {
            model = parseToServer.getShows(user_id, auth_token);
            getContentResolver().delete(Contract.CONTENT_URI_PROFILE, null, null);
            getContentResolver().insert(Contract.CONTENT_URI_PROFILE, model.getContentValues());
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
        Cursor cur = getContentResolver().query(Contract.CONTENT_URI_PROFILE, null, null, null, null);
        ProfileModel temp = new ProfileModel();
        if (cur != null)
        {
            while (cur.moveToNext())
            {
                temp = ProfileModel.fromCursor(cur);
                pr_etDisplayName.setText(temp.getDisplay_name());
                pr_edFullName.setText(temp.getFull_name());
                pr_edPhone.setText(temp.getPhone());
                if (temp.getGender() == 1)
                {
                    isSelected = true;
                    btGenderSelectLeft.setBackgroundDrawable(getResources().getDrawable(R.drawable.pr_btn_select_left));
                    btGenderSelectRight.setBackgroundDrawable(getResources().getDrawable(R.drawable.pr_btn_unselect_right));
                }
                else
                {
                    isSelected = false;
                    btGenderSelectLeft.setBackgroundDrawable(getResources().getDrawable(R.drawable.pr_btn_unselect_left));
                    btGenderSelectRight.setBackgroundDrawable(getResources().getDrawable(R.drawable.pr_btn_select_right));
                }
                pr_edBirthday.setText(temp.getBirthday());
                int position = 0;
                for (int i = 0; i < countries_code.length; i++)
                {
                    if (temp.getCountry_id().equals(countries_code[i]))
                    {
                        position = i;
                    }
                }

                pr_edCountry.setText(countries[position].toString());
                pr_etDescription.setText(temp.getDescription());
                imageLoader.DisplayImage(temp.getAvatar(), pr_imgAvatar);
                imageLoader.DisplayImage(temp.getCover_image(), pr_ivBackground);
            }
            cur.close();
        }
    }

    @Click ({R.id.pr_imgAvatar,R.id.pr_ibDeleteFullName,R.id.pr_ibDeletePhone,R.id.pr_edBirthday,
            R.id.pr_edCountry,R.id.pr_btnSelectLeft_check,R.id.pr_btnSelectRight_check,
            R.id.pr_rlBackGround,R.id.pr_btCancel,R.id.pr_btSave, R.id.dg_btChoosePicture, R.id.dg_btTakePicture})
    protected void onClickListener(View view)
    {
        switch (view.getId()){
            case R.id.pr_imgAvatar:
                code = "cover_image";
                showDialogSelectImage();
                break;
            case R.id.pr_rlBackGround:
                code = "background_image";
                showDialogSelectImage();
                break;
            case R.id.pr_ibDeleteFullName:
                pr_edFullName.setText("");
                break;
            case R.id.pr_ibDeletePhone:
                pr_edPhone.setText("");
                break;
            case R.id.pr_edBirthday:
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.pr_edCountry:
                showDialogCountry();
                break;
            case R.id.pr_btnSelectLeft_check:
                btGenderSelectLeft.setBackgroundDrawable(getResources().getDrawable(R.drawable.pr_btn_select_left));
                btGenderSelectRight.setBackgroundDrawable(getResources().getDrawable(R.drawable.pr_btn_unselect_right));
                break;
            case R.id.pr_btnSelectRight_check:
                btGenderSelectLeft.setBackgroundDrawable(getResources().getDrawable(R.drawable.pr_btn_unselect_left));
                btGenderSelectRight.setBackgroundDrawable(getResources().getDrawable(R.drawable.pr_btn_select_right));
                break;
            case R.id.pr_btCancel:
                callBackSlidebarActivity();
                break;
            case R.id.pr_btSave:
                ProfileModel profileUpdate = getProfileModel();

                try
                {
                    parseToServer.putShow(auth_token, user_id, profileUpdate);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                try
                {
                    model = parseToServer.getShows(user_id, auth_token);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                getContentResolver().update(Contract.CONTENT_URI_PROFILE, model.getContentValues(), null, null);
                Toast.makeText(getApplicationContext(), "Update successfull", 1);
                callBackSlidebarActivity();
                break;
        }
    }

    private ProfileModel getProfileModel()
    {
        ProfileModel profileUpdate = new ProfileModel();
        profileUpdate.setId(Integer.parseInt(user_id));
        profileUpdate.setFull_name(pr_edFullName.getText().toString());
        profileUpdate.setDisplay_name(pr_etDisplayName.getText().toString());
        profileUpdate.setDescription(pr_etDescription.getText().toString());
        profileUpdate.setPhone(pr_edPhone.getText().toString());
        profileUpdate.setBirthday(pr_edBirthday.getText().toString());
        String temp = pr_edCountry.getText().toString();
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
        Intent intent = new Intent(this, SlidebarActivity_.class);
        intent.putExtra(FirstLaunchActivity.AUTHEN_TOKEN, auth_token);
        intent.putExtra(LoginActivity.USER_ID, user_id);
        intent.putExtra(FirstLaunchActivity.ACCOUNT_CONNECTED, mConnectedAccount);
        startActivity(intent);
    }

    @TextChange({R.id.pr_edFullName, R.id.pr_edPhone})
    protected void onTextChangesOnSomeTextViews(CharSequence s, int start, int before,
                                                int count)
    {
        if (!pr_edFullName.getText().toString().isEmpty())
        {
            pr_ibDeleteFullName.setVisibility(View.VISIBLE);
            pr_ibDeletePhone.setVisibility(View.INVISIBLE);
        }
        if (!pr_edPhone.getText().toString().isEmpty())
        {
            pr_ibDeleteFullName.setVisibility(View.VISIBLE);
            pr_ibDeletePhone.setVisibility(View.INVISIBLE);
        }

    }
    @BeforeTextChange({R.id.pr_edFullName, R.id.pr_edPhone})
    protected void beforeTextChangedOnSomeTextViews()
    {
    }

    @AfterTextChange({R.id.pr_edFullName, R.id.pr_edPhone})
    protected void afterTextChangedOnSomeTextViews(Editable s)
    {
        pr_edFullName.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus && !pr_edFullName.getText().toString().isEmpty())
                {
                    pr_ibDeleteFullName.setVisibility(View.VISIBLE);
                }
                else
                {
                    pr_ibDeleteFullName.setVisibility(View.INVISIBLE);
                }
            }
        });
        pr_edPhone.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus && !pr_edPhone.getText().toString().isEmpty())
                {
                    pr_ibDeletePhone.setVisibility(View.VISIBLE);
                }
                else
                {
                    pr_ibDeletePhone.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        switch (id)
        {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month, day);
        }
        return null;
    }

    private void showDialogCountry()
    {

        pr_edCountry.setOnClickListener(new View.OnClickListener()
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
                        pr_edCountry.setText(countries[item].toString());
                        alertCountryDialog.dismiss();
                    }
                });
                alertCountryDialog = builder.create();
                alertCountryDialog.show();
            }
        });
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

            pr_edBirthday.setText(new StringBuilder().append(month + 1)
                    .append("/").append(day).append("/").append(year).append(" "));

        }
    };

    private void showDialogSelectImage()
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View yourCustomView = inflater.inflate(R.layout.dg_choose_image_fragment, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(yourCustomView);
        alertDialog = builder.create();
        alertDialog.setTitle("Profile Image");
        alertDialog.show();
        btTakePicture = (Button) yourCustomView.findViewById(R.id.dg_btTakePicture);
        btChoosePicture = (Button) yourCustomView.findViewById(R.id.dg_btChoosePicture);
        btCancel = (Button) yourCustomView.findViewById(R.id.dg_btCancel);

        btTakePicture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                takePhoto(view);
            }
        });
        btChoosePicture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                chooseAPicture();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener()
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
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    public void takePhoto(View v)
    {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap circleBitmap;
        if (requestCode == CAMERA_REQUEST)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            circleBitmap = resizeBitMap(bitmap);
            if (code.equals("cover_image"))
            {
                pr_imgAvatar.setImageBitmap(circleBitmap);
            }
            else if (code.equals("background_image"))
            {
                Drawable cover = new BitmapDrawable(bitmap);
                pr_rlBackGround.setBackgroundDrawable(cover);
            }

        }
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bmp = BitmapFactory.decodeFile(filePath);
            circleBitmap = resizeBitMap(bmp);
            if (code.equals("cover_image"))
            {
                pr_imgAvatar.setImageBitmap(circleBitmap);
            }
            else if (code.equals("background_image"))
            {
                Drawable cover = new BitmapDrawable(bmp);
                pr_rlBackGround.setBackgroundDrawable(cover);
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