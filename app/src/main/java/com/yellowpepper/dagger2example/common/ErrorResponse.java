package com.yellowpepper.dagger2example.common;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mickey35vn on 3/23/17.
 */

public class ErrorResponse {

    @SerializedName("error")
    public String errorType;

    @SerializedName("error_description")
    public String message;

    @SerializedName("status")
    public String status;

    @SerializedName("description")
    public String description;
}
