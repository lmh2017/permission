package com.defu.permission.interceptor;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtil {

    public static ResponseMO success(){
        return new ResponseMO(0,"成功");
    }

    public static ResponseMO error(){
        return new ResponseMO(CommonConstants.RESPONSE_CODE_FAILURE,"");
    }

    public static ResponseMO error(String msg){
        return new ResponseMO(CommonConstants.RESPONSE_CODE_FAILURE,msg);
    }

    public static ResponseMO error(int code,String msg){
        return new ResponseMO(code,msg);
    }

    public static ResponseMO error(int code,String msg,String debugInfo){
        return new ResponseMO(code,msg,debugInfo);
    }

    public static <T>ResponseMO successWithData(T data){
        ResponseMO responseMO = new ResponseMO();
        responseMO.setData(data);
        return responseMO;
    }

    public static void responseNotAuth(HttpServletResponse response){
        PrintWriter pw = null;
        try {
            response.setStatus(CommonConstants.HTTP_NOT_AUTH);
            response.setContentType("application/json; charset=utf-8");
            pw = response.getWriter();
            pw.write(JSON.toJSONString(ResponseUtil.error(CommonConstants.RESPONSE_CODE_ANONYMOUS,"token校验不通过，请重新登录")));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != pw){
                pw.flush();
                pw.close();
            }
        }


    }

}
