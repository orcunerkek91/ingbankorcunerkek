package com.orcunerkek.ingbankorcunerkek.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GithubModel implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("owner")
    @Expose
    private OwnerModel owner;

    @SerializedName("stargazers_count")
    @Expose
    private Integer stargazers_count;

    @SerializedName("open_issues_count")
    @Expose
    private Integer open_issues_count;


    private boolean starFlag;

    public GithubModel(String name, OwnerModel owner, Integer stargazers_count, Integer open_issues_count, boolean starFlag) {
        this.name = name;
        this.owner = owner;
        this.stargazers_count = stargazers_count;
        this.open_issues_count = open_issues_count;
        this.starFlag = starFlag;
    }

    protected GithubModel(Parcel in) {
        name = in.readString();
        owner = in.readParcelable(OwnerModel.class.getClassLoader());
        if (in.readByte() == 0) {
            stargazers_count = null;
        } else {
            stargazers_count = in.readInt();
        }
        if (in.readByte() == 0) {
            open_issues_count = null;
        } else {
            open_issues_count = in.readInt();
        }
        starFlag = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(owner, flags);
        if (stargazers_count == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(stargazers_count);
        }
        if (open_issues_count == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(open_issues_count);
        }
        dest.writeByte((byte) (starFlag ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GithubModel> CREATOR = new Creator<GithubModel>() {
        @Override
        public GithubModel createFromParcel(Parcel in) {
            return new GithubModel(in);
        }

        @Override
        public GithubModel[] newArray(int size) {
            return new GithubModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OwnerModel getOwner() {
        return owner;
    }

    public void setOwner(OwnerModel owner) {
        this.owner = owner;
    }

    public Integer getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(Integer stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public Integer getOpen_issues_count() {
        return open_issues_count;
    }

    public void setOpen_issues_count(Integer open_issues_count) {
        this.open_issues_count = open_issues_count;
    }

    public boolean isStarFlag() {
        return starFlag;
    }

    public void setStarFlag(boolean starFlag) {
        this.starFlag = starFlag;
    }
}
