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
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
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
public class ProfileActivity extends Activity
{
    ImageLoader imageLoader;
    ProfileModel model = new ProfileModel();
    private String auth_token;
    private String user_id;
    private String TAG = this.getClass().getSimpleName();
    private Account mConnectedAccount;
    private AccountManager mAccountManager;
    ProfileParseToServer parseToServer = new ProfileParseToServer();
    String []countries;
    String []countries_code;

    ImageView pr_imgAvatar;
    Button btTakePicture, btChoosePicture, btCancel, btGenderSelectLeft, btGenderSelectRight, pr_btCancel,pr_btSave;

    EditText pr_edFullName, pr_edPhone, pr_edBirthday, pr_edCountry, pr_etDisplayName, pr_etDescription;
    ImageButton pr_ibDeleteFullName, pr_ibDeletePhone;
    RelativeLayout pr_rlBackGround;
    AlertDialog alertDialog, alertCountryDialog;

    static final int DATE_DIALOG_ID = 0;
    private static final int PICK_IMAGE = 1;
    private static final int CAMERA_REQUEST = 1888;
    public int year, month, day;
    final Context context = this;

    String code;
    private boolean isSelected;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        getComponentOnView();
        getTokenAndAccount();
        showDataOnView();
        onclickListener();
    }

    private void getTokenAndAccount()
    {
        //get authen_token and account from first lauch
        Intent intent = getIntent();
        auth_token = intent.getStringExtra(FirstLaunchActivity.AUTHEN_TOKEN);
        mConnectedAccount = intent.getParcelableExtra(FirstLaunchActivity.ACCOUNT_CONNECTED);
        user_id = intent.getStringExtra(LoginActivity.USER_ID);
    }

    private void getComponentOnView()
    {
        imageLoader = new ImageLoader(this);
        pr_imgAvatar = (ImageView) findViewById(R.id.pr_imgAvatar);
        pr_etDisplayName = (EditText) findViewById(R.id.pr_edDisplayName);
        pr_edFullName = (EditText) findViewById(R.id.pr_edFullName);
        pr_etDescription = (EditText) findViewById(R.id.pr_etDescription);
        pr_edPhone = (EditText) findViewById(R.id.pr_edPhone);
        pr_ibDeleteFullName = (ImageButton) findViewById(R.id.pr_ibDeleteFullName);
        pr_ibDeletePhone = (ImageButton) findViewById(R.id.pr_ibDeletePhone);
        pr_edBirthday = (EditText) findViewById(R.id.pr_edBirthday);
        pr_edCountry = (EditText) findViewById(R.id.pr_edCountry);
        btGenderSelectLeft = (Button) findViewById(R.id.pr_btnSelectLeft_check);
        btGenderSelectRight = (Button) findViewById(R.id.pr_btnSelectRight_check);
        pr_rlBackGround = (RelativeLayout) findViewById(R.id.pr_rlBackGround);
        pr_btCancel = (Button) findViewById(R.id.pr_btCancel);
        pr_btSave = (Button) findViewById(R.id.pr_btSave);
        countries = getResources().getStringArray(
                R.array.country_array);
        countries_code = getResources().getStringArray(R.array.country_code);
        mAccountManager = AccountManager.get(this);
    }

    private void showDataOnView()
    {
        Log.i(TAG, "Get data for show on view");
        new AsyncTask<String, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(String... params) {
                Cursor cur = getContentResolver().query(Contract.CONTENT_URI_PROFILE, null, null, null, null);
                try
                {
                    model = parseToServer.getShows(user_id,auth_token);
                    if(cur == null)
                        getContentResolver().insert(Contract.CONTENT_URI_PROFILE,model.getContentValues());
                    else
                        getContentResolver().delete(Contract.CONTENT_URI_PROFILE,null,null);
                        getContentResolver().insert(Contract.CONTENT_URI_PROFILE,model.getContentValues());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                return cur;
            }

            @Override
            protected void onPostExecute(Cursor cur ) {
                ProfileModel temp = new ProfileModel();
                if(cur != null)
                {
                    while (cur.moveToNext())
                    {
                        temp = ProfileModel.fromCursor(cur);
                        pr_etDisplayName.setText(temp.getDisplay_name());
                        pr_edFullName.setText(temp.getFull_name());
                        pr_edPhone.setText(temp.getPhone());
                        if(temp.getGender() == 1){
                            isSelected = true;
                            btGenderSelectLeft.setBackgroundDrawable(getResources().getDrawable(R.drawable.pr_btn_select_left));
                            btGenderSelectRight.setBackgroundDrawable(getResources().getDrawable(R.drawable.pr_btn_unselect_right));
                        }
                        else{
                            isSelected = false;
                            btGenderSelectLeft.setBackgroundDrawable(getResources().getDrawable(R.drawable.pr_btn_unselect_left));
                            btGenderSelectRight.setBackgroundDrawable(getResources().getDrawable(R.drawable.pr_btn_select_right));
                        }
                        pr_edBirthday.setText(temp.getBirthday());
                        int position = 0;
                        for(int i = 0; i < countries_code.length; i++)
                            if(temp.getCountry_id().equals(countries_code[i]))
                                position = i;

                        pr_edCountry.setText(countries[position].toString());
                        pr_etDescription.setText(temp.getDescription());
                        imageLoader.DisplayImage(temp.getAvatar(),pr_imgAvatar);
                    }
                cur.close();
                }
            }
        }.execute();
    }

    private void onclickListener()
    {
        pr_imgAvatar.setOnClickListener(onclickListener);
        pr_edFullName.setOnClickListener(onclickListener);
        pr_edPhone.setOnClickListener(onclickListener);
        pr_edBirthday.setOnClickListener(onclickListener);
        pr_edCountry.setOnClickListener(onclickListener);
        btGenderSelectLeft.setOnClickListener(onclickListener);
        btGenderSelectRight.setOnClickListener(onclickListener);
        pr_rlBackGround.setOnClickListener(onclickListener);
        pr_btCancel.setOnClickListener(onclickListener);
        pr_btSave.setOnClickListener(onclickListener);
    }

    private final View.OnClickListener onclickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
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
                case R.id.pr_edFullName:
                    onTextChange();
                    break;
                case R.id.pr_ibDeleteFullName:
                    pr_edFullName.setText("");
                    pr_ibDeleteFullName.setVisibility(View.INVISIBLE);
                    break;
                case R.id.pr_edPhone:
                    onTextChange();
                    break;
                case R.id.pr_ibDeletePhone:
                    pr_edPhone.setText("");
                    pr_ibDeletePhone.setVisibility(View.INVISIBLE);
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
                    ProfileModel profileUpdate = new ProfileModel();
                    profileUpdate.setId(Integer.parseInt(user_id));
                    profileUpdate.setFull_name(pr_edFullName.getText().toString());
                    profileUpdate.setDisplay_name(pr_etDisplayName.getText().toString());
                    profileUpdate.setDescription(pr_etDescription.getText().toString());
                    profileUpdate.setPhone(pr_edPhone.getText().toString());
                    profileUpdate.setBirthday(pr_edBirthday.getText().toString());
                    String temp = pr_edCountry.getText().toString();
                    int country_id = 0;
                    for(int i = 0; i < countries.length; i++){
                        if(temp.equals(countries[i]))
                            country_id = i;
                    }
                    profileUpdate.setCountry_id(countries_code[country_id]);
                    int gender;
                    if (isSelected){
                        gender = 1;
                    }
                    else {
                        gender = 0;
                    }
                    profileUpdate.setGender(gender);

                    try
                    {
                        parseToServer.putShow(auth_token,user_id,profileUpdate);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    try
                    {
                        model = parseToServer.getShows(user_id,auth_token);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    getContentResolver().update(Contract.CONTENT_URI_PROFILE, model.getContentValues(), null, null);
                    Toast.makeText(getApplicationContext(),"Update successfull",1);
                    callBackSlidebarActivity();
                    break;
            }
        }
    };

    private void callBackSlidebarActivity()
    {
        Intent intent = new Intent(this, SlidebarActivity.class);
        intent.putExtra(FirstLaunchActivity.AUTHEN_TOKEN, auth_token);
        intent.putExtra(LoginActivity.USER_ID,user_id);
        intent.putExtra(FirstLaunchActivity.ACCOUNT_CONNECTED, mConnectedAccount);
        startActivity(intent);
        finish();
    }

    private void onTextChange()
    {
        //textChange edit text FullName
        pr_edFullName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {
                if (!pr_edFullName.getText().equals(" "))
                {
                    pr_ibDeleteFullName.setVisibility(View.VISIBLE);
                    pr_ibDeleteFullName.setOnClickListener(onclickListener);
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        //textChange edit text Phone
        pr_edPhone.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {
                if (!pr_edPhone.getText().equals(" "))
                {
                    pr_ibDeletePhone.setVisibility(View.VISIBLE);
                    pr_ibDeletePhone.setOnClickListener(onclickListener);
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
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

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
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