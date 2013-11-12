package com.qsoft.onlinedio.ui.activity;

import android.accounts.AccountManager;
import android.app.Activity;
import android.widget.*;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.onlinedio.R;
import com.qsoft.onlinedio.ui.controller.ProfileController;

/**
 * User: Dell 3360
 * Date: 10/17/13
 * Time: 8:39 AM
 */
@EActivity(R.layout.profile_layout)
public class ProfileActivity extends Activity
{
    @Bean
    ProfileController profileController;

    @SystemService
    protected AccountManager mAccountManager;
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

    @AfterViews
    protected void afterViews(){
        profileController.prepareData();
        profileController.getTokenAndAccount();
        profileController.putDataDB();
    }

    public ImageView getPr_imgAvatar()
    {
        return pr_imgAvatar;
    }

    public void setPr_imgAvatar(ImageView pr_imgAvatar)
    {
        this.pr_imgAvatar = pr_imgAvatar;
    }

    public RelativeLayout getPr_rlBackGround()
    {
        return pr_rlBackGround;
    }

    public void setPr_rlBackGround(RelativeLayout pr_rlBackGround)
    {
        this.pr_rlBackGround = pr_rlBackGround;
    }

    public ImageView getPr_ivBackground()
    {
        return pr_ivBackground;
    }

    public void setPr_ivBackground(ImageView pr_ivBackground)
    {
        this.pr_ivBackground = pr_ivBackground;
    }

    public Button getBtTakePicture()
    {
        return btTakePicture;
    }

    public void setBtTakePicture(Button btTakePicture)
    {
        this.btTakePicture = btTakePicture;
    }

    public Button getBtChoosePicture()
    {
        return btChoosePicture;
    }

    public void setBtChoosePicture(Button btChoosePicture)
    {
        this.btChoosePicture = btChoosePicture;
    }

    public Button getBtCancel()
    {
        return btCancel;
    }

    public void setBtCancel(Button btCancel)
    {
        this.btCancel = btCancel;
    }

    public Button getBtGenderSelectLeft()
    {
        return btGenderSelectLeft;
    }

    public void setBtGenderSelectLeft(Button btGenderSelectLeft)
    {
        this.btGenderSelectLeft = btGenderSelectLeft;
    }

    public Button getBtGenderSelectRight()
    {
        return btGenderSelectRight;
    }

    public void setBtGenderSelectRight(Button btGenderSelectRight)
    {
        this.btGenderSelectRight = btGenderSelectRight;
    }

    public Button getPr_btCancel()
    {
        return pr_btCancel;
    }

    public void setPr_btCancel(Button pr_btCancel)
    {
        this.pr_btCancel = pr_btCancel;
    }

    public Button getPr_btSave()
    {
        return pr_btSave;
    }

    public void setPr_btSave(Button pr_btSave)
    {
        this.pr_btSave = pr_btSave;
    }

    public EditText getPr_edFullName()
    {
        return pr_edFullName;
    }

    public void setPr_edFullName(EditText pr_edFullName)
    {
        this.pr_edFullName = pr_edFullName;
    }

    public EditText getPr_edPhone()
    {
        return pr_edPhone;
    }

    public void setPr_edPhone(EditText pr_edPhone)
    {
        this.pr_edPhone = pr_edPhone;
    }

    public EditText getPr_edBirthday()
    {
        return pr_edBirthday;
    }

    public void setPr_edBirthday(EditText pr_edBirthday)
    {
        this.pr_edBirthday = pr_edBirthday;
    }

    public EditText getPr_edCountry()
    {
        return pr_edCountry;
    }

    public void setPr_edCountry(EditText pr_edCountry)
    {
        this.pr_edCountry = pr_edCountry;
    }

    public EditText getPr_etDisplayName()
    {
        return pr_etDisplayName;
    }

    public void setPr_etDisplayName(EditText pr_etDisplayName)
    {
        this.pr_etDisplayName = pr_etDisplayName;
    }

    public EditText getPr_etDescription()
    {
        return pr_etDescription;
    }

    public void setPr_etDescription(EditText pr_etDescription)
    {
        this.pr_etDescription = pr_etDescription;
    }

    public ImageButton getPr_ibDeleteFullName()
    {
        return pr_ibDeleteFullName;
    }

    public void setPr_ibDeleteFullName(ImageButton pr_ibDeleteFullName)
    {
        this.pr_ibDeleteFullName = pr_ibDeleteFullName;
    }

    public ImageButton getPr_ibDeletePhone()
    {
        return pr_ibDeletePhone;
    }

    public void setPr_ibDeletePhone(ImageButton pr_ibDeletePhone)
    {
        this.pr_ibDeletePhone = pr_ibDeletePhone;
    }
}