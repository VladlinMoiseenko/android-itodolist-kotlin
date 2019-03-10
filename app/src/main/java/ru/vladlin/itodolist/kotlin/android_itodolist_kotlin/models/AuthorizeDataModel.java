package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthorizeDataModel {

    @SerializedName("authorization_code")
    @Expose
    private String authorizationCode;
    @SerializedName("expires_at")
    @Expose
    private Integer expiresAt;

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public Integer getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Integer expiresAt) {
        this.expiresAt = expiresAt;
    }

}
