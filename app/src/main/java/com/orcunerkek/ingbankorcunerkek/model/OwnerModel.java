package com.orcunerkek.ingbankorcunerkek.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OwnerModel implements Parcelable {

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("avatar_url")
    @Expose
    private String avatar_url;

    public OwnerModel(String login, String avatar_url) {
        this.login = login;
        this.avatar_url = avatar_url;
    }

    protected OwnerModel(Parcel in) {
        login = in.readString();
        avatar_url = in.readString();
    }

    public static final Creator<OwnerModel> CREATOR = new Creator<OwnerModel>() {
        @Override
        public OwnerModel createFromParcel(Parcel in) {
            return new OwnerModel(in);
        }

        @Override
        public OwnerModel[] newArray(int size) {
            return new OwnerModel[size];
        }
    };

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(login);
        dest.writeString(avatar_url);
    }
}
