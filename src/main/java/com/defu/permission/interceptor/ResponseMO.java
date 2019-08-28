package com.defu.permission.interceptor;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseMO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public int code = CommonConstants.RESPONSE_CODE_SUCCESS;

    public String msg;

    public T data;

    public String debugInfo;

    public ResponseMO(){

    }

    public ResponseMO(int code){
        this.code = code;
    }

    public ResponseMO(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public ResponseMO(int code,String msg,String debugInfo){
        this.code = code;
        this.msg = msg;
        this.debugInfo = debugInfo;
    }

    public boolean success(){
        if(code == CommonConstants.RESPONSE_CODE_SUCCESS){
            return true;
        }
        return false;
    }


}
