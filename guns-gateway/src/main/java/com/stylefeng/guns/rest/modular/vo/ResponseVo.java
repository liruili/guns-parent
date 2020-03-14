package com.stylefeng.guns.rest.modular.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseVo<T> {
    // 状态码，0-成功，1-业务异常，999-系统异常
    private int status;
    private String msg;
    private T data;
    private String imgPre;

    private ResponseVo(){};

    /** 成功*/
    public static<T> ResponseVo success(T data){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setStatus(0);
        responseVo.setData(data);
        return responseVo;
    }

    public static<T> ResponseVo success(T data,String imgPre){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setStatus(0);
        responseVo.setData(data);
        responseVo.setImgPre(imgPre);
        return responseVo;
    }

    public static<T> ResponseVo success(String msg){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setStatus(0);
        responseVo.setMsg(msg);
        return responseVo;
    }

    /** 业务异常*/
    public static<T> ResponseVo serviceFail(String msg){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setStatus(1);
        responseVo.setMsg(msg);
        return responseVo;
    }

    /** 系统异常*/
    public static<T> ResponseVo appFail(String msg){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setStatus(999);
        responseVo.setMsg(msg);
        return responseVo;
    }

}
