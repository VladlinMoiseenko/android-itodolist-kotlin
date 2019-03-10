package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthorizeModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private AuthorizeDataModel data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public AuthorizeDataModel getData() {
        return data;
    }

    public void setData(AuthorizeDataModel data) {
        this.data = data;
    }

}
