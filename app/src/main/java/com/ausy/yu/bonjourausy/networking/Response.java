package com.ausy.yu.bonjourausy.networking;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yukaiwen on 17/04/2017.
 */

public class Response {

    @SerializedName("status")
    public String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @SuppressWarnings({"unused", "used by Retrofit"})
    public Response() {
    }

    public Response(String status) {
        this.status = status;
    }
}
