package com.midian.demo.retrofit;

/**
 * Created by Administrator on 2016/11/23 0023.
 */

public class HttpResult<T> {
    private String reason;
    private String error_code;
    private T result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
