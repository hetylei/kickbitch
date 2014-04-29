package com.winnie.pub.dict;

/**
 * 
 * User: rlx@yr365.com
 * Date: 2003-12-31
 * Time: 11:41:51
 */
public class DictException extends RuntimeException {

    public DictException(String msg) {
        super(msg);
    }

    public DictException(Throwable cause) {
        super(cause);
    }

    public DictException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
