package com.orcunerkek.ingbankorcunerkek.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.orcunerkek.ingbankorcunerkek.model.DbModel;

import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;


    private static final String DATABASE_NAME = "db_repo";

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbModel.TABLE_NAME);

    }

    public long addRepo(String owner, String repo_name) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(DbModel.COLUMN_OWNER, owner);
        values.put(DbModel.COLUMN_REPO_NAME, repo_name);


        long id = db.insert(DbModel.TABLE_NAME, null, values);


        db.close();


        return id;
    }

    public boolean getRepoInfo(String repo_name) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " +DbModel.TABLE_NAME +" WHERE repo_name = '"+ repo_name+"'", null );

        if (cursor != null)
            cursor.moveToFirst();

        try {
            DbModel repoInfo = new DbModel(
                    cursor.getInt(cursor.getColumnIndex(DbModel.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(DbModel.COLUMN_OWNER)),
                    cursor.getString(cursor.getColumnIndex(DbModel.COLUMN_REPO_NAME)));

            cursor.close();
            if(repoInfo.getRepo_name()!=null || !repoInfo.getRepo_name().equals("")){
                return true; }
            else {
                return false;
            }
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public List<DbModel> getRepoList() {
        List<DbModel> repoList = new ArrayList<>();


        String selectQuery = "SELECT  * FROM " + DbModel.TABLE_NAME + " ORDER BY " +
                DbModel.COLUMN_ID + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                DbModel repo_info = new DbModel();
                repo_info.setId(cursor.getInt(cursor.getColumnIndex(DbModel.COLUMN_ID)));
                repo_info.setOwner(cursor.getString(cursor.getColumnIndex(DbModel.COLUMN_OWNER)));
                repo_info.setRepo_name(cursor.getString(cursor.getColumnIndex(DbModel.COLUMN_REPO_NAME)));

                repoList.add(repo_info);
            } while (cursor.moveToNext());
        }


        db.close();


        return repoList;
    }

    public void updateRepoInfo(DbModel repoInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbModel.COLUMN_OWNER, repoInfo.getOwner());
        values.put(DbModel.COLUMN_REPO_NAME, repoInfo.getRepo_name());

        db.update(DbModel.TABLE_NAME, values, DbModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(DbModel.getColumnId())});
    }


    public void deleteRepoInfo(DbModel repoInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbModel.TABLE_NAME, DbModel.COLUMN_REPO_NAME + " = ?",
                new String[]{String.valueOf(repoInfo.getRepo_name())});
        db.close();
    }

}
