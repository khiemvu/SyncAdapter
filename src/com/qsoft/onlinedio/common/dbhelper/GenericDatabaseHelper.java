package com.qsoft.onlinedio.common.dbhelper;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.*;

/**
 * User: Le
 * Date: 10/25/13
 */
public class GenericDatabaseHelper extends OrmLiteSqliteOpenHelper
{
// ------------------------------ FIELDS ------------------------------

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "onlineDio.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 2;

    private Map<Class, Object> mappedDao = new HashMap<Class, Object>();

    private List<Class> models;

    private Context context;

// -------------------------- STATIC METHODS --------------------------

    //    Reflection to get all model
    public static List<Class> getModels(Context context, String packageName)
            throws IOException, URISyntaxException, ClassNotFoundException,
            PackageManager.NameNotFoundException
    {
        String apkName = context.getPackageManager().getApplicationInfo(packageName, 0).sourceDir;
        DexFile dexFile = new DexFile(apkName);

//        PathClassLoader classLoader2 = new PathClassLoader(apkName, Thread.currentThread().getContextClassLoader());
        PathClassLoader classLoader2 = new PathClassLoader(apkName, context.getApplicationContext().getClassLoader());
        DexClassLoader classLoader = new DexClassLoader(apkName, new ContextWrapper(context).getCacheDir().getAbsolutePath(), null, classLoader2);

        List<Class> classes = new ArrayList<Class>();
        Enumeration<String> entries = dexFile.entries();

        while (entries.hasMoreElements())
        {
            String entry = entries.nextElement();
            // only check items that exist in source package and not in libraries, etc.
            if (entry.startsWith(packageName))
            {
                Log.i("getData", "Entry: " + entry);

                Class<?> entryClass = classLoader.loadClass(entry);//dexFile.loadClass(entry, classLoader);
                if (entryClass != null)
                {
                    Annotation[] annotations = entryClass.getAnnotations();
                    for (Annotation annotation : annotations)
                    {
                        if (annotation instanceof DatabaseTable)
                        {
                            classes.add(entryClass);
                        }
                    }
                }
            }
        }

        return classes;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public GenericDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public List<Class> getModels() throws URISyntaxException, ClassNotFoundException, PackageManager.NameNotFoundException, IOException
    {
        if (models == null)
        {
            models = getModels(context, "com.qsoft.onlinedio");
        }
        return models;
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close()
    {
        super.close();
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion)
    {
        try
        {
            Log.i(GenericDatabaseHelper.class.getName(), "onUpgrade");

            for (Class model : getModels())
            {
                TableUtils.dropTable(connectionSource, model, true);
            }
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        }
        catch (SQLException e)
        {
            Log.e(GenericDatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
        catch (Exception e)
        {
            Log.i("onUpgrade", "Well: " + e.getMessage());
        }
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource)
    {
        try
        {
            Log.i(GenericDatabaseHelper.class.getName(), "onCreate");

            for (Class model : getModels())
            {
                TableUtils.createTable(connectionSource, model);
            }
        }
        catch (SQLException e)
        {
            Log.e(GenericDatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
        catch (Exception e)
        {
            Log.i("onCreate", "Well: " + e.getMessage());
        }
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our Account class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public <D, ID> RuntimeExceptionDao<D, ID> getRuntimeDao(Class<D> modelType, Class<ID> idType) throws SQLException
    {
        if (mappedDao.get(modelType) == null)
        {
            mappedDao.put(modelType, new RuntimeExceptionDao<D, ID>(getDao(modelType, idType)));
        }
        return (RuntimeExceptionDao<D, ID>) mappedDao.get(modelType);
    }

    public <D, ID> Dao<D, ID> getDao(Class<D> daoType, Class<ID> idType) throws SQLException
    {
        // special reflection fu is now handled internally by create dao calling the database type
        Dao<D, ID> dao = DaoManager.createDao(getConnectionSource(), daoType);
        return dao;
    }
}
