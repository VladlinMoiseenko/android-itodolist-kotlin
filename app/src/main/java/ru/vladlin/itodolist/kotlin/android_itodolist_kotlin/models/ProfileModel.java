package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private ProfileDataModel data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ProfileDataModel getData() {
        return data;
    }

    public void setData(ProfileDataModel data) {
        this.data = data;
    }

}
