package com.xo.common.quartz;

/**
 * @author: xo
 * @DATE: 2020/9/29
 * @Description: SystemException
 **/
public class SystemException extends Exception {

    public SystemException()
    {
        super();
    }

    public SystemException(Throwable t){
        super(t);
    }

    public SystemException(String msg)
    {
        super(msg);
    }

    public SystemException(Exception e)
    {
        super(e);
    }

}
