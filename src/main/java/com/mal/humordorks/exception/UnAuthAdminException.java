package com.mal.humordorks.exception;

public class UnAuthAdminException extends RuntimeException{

    public UnAuthAdminException(String message){
        super(message);
    }
}
