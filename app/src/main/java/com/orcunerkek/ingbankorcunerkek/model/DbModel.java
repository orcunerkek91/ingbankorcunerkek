package com.orcunerkek.ingbankorcunerkek.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DbModel implements Parcelable {
    public static final String TABLE_NAME = "db_repo_table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_OWNER = "owner";
    public static final String COLUMN_REPO_NAME = "repo_name";

    String owner , repo_name;
    Integer id;

    public DbModel(){

    }

    public DbModel(Integer id, String owner, String repo_name) {
        this.owner = owner;
        this.repo_name = repo_name;
        this.id = id;
    }

    protected DbModel(Parcel in) {
        owner = in.readString();
        repo_name = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
    }

    public static final Creator<DbModel> CREATOR = new Creator<DbModel>() {
        @Override
        public DbModel createFromParcel(Parcel in) {
            return new DbModel(in);
        }

        @Override
        public DbModel[] newArray(int size) {
            return new DbModel[size];
        }
    };

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getColumnId() {
        return COLUMN_ID;
    }

    public static String getColumnOwner() {
        return COLUMN_OWNER;
    }

    public static String getColumnRepoName() {
        return COLUMN_REPO_NAME;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRepo_name() {
        return repo_name;
    }

    public void setRepo_name(String repo_name) {
        this.repo_name = repo_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static String getCreateTable() {
        return CREATE_TABLE;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(owner);
        dest.writeString(repo_name);
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_OWNER + " TEXT,"
                    + COLUMN_REPO_NAME + " TEXT"
                    + ")";
}
