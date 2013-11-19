package com.qsoft.onlinedio.database.provider;

import com.qsoft.onlinedio.common.dbhelper.GenericDatabaseHelper;
import com.qsoft.onlinedio.database.entity.HomeModel;
import com.qsoft.onlinedio.database.entity.HomeModelContract;
import com.qsoft.onlinedio.database.entity.ProfileModel;
import com.qsoft.onlinedio.database.entity.ProfileModelContract;
import com.tojc.ormlite.android.OrmLiteSimpleContentProvider;
import com.tojc.ormlite.android.framework.MatcherController;
import com.tojc.ormlite.android.framework.MimeTypeVnd;

/**
 * User: khiemvx
 * Date: 11/18/13
 */
public class ContentProvider extends OrmLiteSimpleContentProvider<GenericDatabaseHelper>
{
    @Override
    protected Class<GenericDatabaseHelper> getHelperClass()
    {
        return GenericDatabaseHelper.class;
    }

    @Override
    public boolean onCreate()
    {
        setMatcherController(new MatcherController()
                .add(HomeModel.class, MimeTypeVnd.SubType.DIRECTORY, "", HomeModelContract.CONTENT_URI_PATTERN_MANY)
                .add(HomeModel.class, MimeTypeVnd.SubType.ITEM, "#", HomeModelContract.CONTENT_URI_PATTERN_ONE)
                .add(ProfileModel.class, MimeTypeVnd.SubType.DIRECTORY, "", ProfileModelContract.CONTENT_URI_PATTERN_MANY)
                .add(ProfileModel.class, MimeTypeVnd.SubType.ITEM, "#", ProfileModelContract.CONTENT_URI_PATTERN_ONE)
        );
        return true;
    }
}