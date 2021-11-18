package com.bjpowernode.p2p.eumn;
/**
 * ClassName:BizErrorException
 * Package:com.bjpowernode.p2p.eumn
 * Description:
 *
 * @date:2021/11/17 22:34
 * @author:
 */

/**
 * Author: liuda
 * 2021/11/17
 */
public class BizErrorException extends Exception {

    private String code;
    private String message;

    public BizErrorException BizCode_000000_余额不足 = new BizErrorException("000000","余额不足");


    public BizErrorException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
