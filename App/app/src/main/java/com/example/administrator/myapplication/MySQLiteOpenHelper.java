package com.example.administrator.myapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2015-07-06.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Dictionary.db";
    private static final String PACKAGE_DIR = "/data/data/com.example.administrator.myapplication/databases";

    public MySQLiteOpenHelper(Context context) {
       // super(context, "Dictionary.db", null, 1);
        super(context, PACKAGE_DIR+"/"+DATABASE_NAME, null, 1);
        initialize(context);
    }

    public void initialize(Context context) {
        boolean existsDB;
        File file = new File(PACKAGE_DIR + "/" + DATABASE_NAME);
        if(file.exists()) {

        }
        else {
            copyDB(context);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "drop table if exists Dictionary";
        db.execSQL(sql);

        onCreate(db);
    }

    public void copyDB(Context context) {
        File folder = new File(PACKAGE_DIR);
        File outfile = new File(PACKAGE_DIR + "/" + DATABASE_NAME);

        if(folder.exists()) {
        } else {
            folder.mkdirs();
        }

        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        // ������ ���� ��쿡�� ����
        if(outfile.length() <= 0) {
            AssetManager assetManager = context.getResources().getAssets();
            try {
                // ���� ����.
                InputStream inStream = assetManager.open(DATABASE_NAME);
                try {
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inStream);
                    outfile.createNewFile();
                    fileOutputStream = new FileOutputStream(outfile);
                    bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                    int read = -1;
                    byte[] buffer = new byte[1024];
                    while( (read = bufferedInputStream.read(buffer,0, 1024)) != -1) {
                        bufferedOutputStream.write(buffer, 0, read);
                    }

                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                    fileOutputStream.close();
                    bufferedInputStream.close();
                    inStream.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
