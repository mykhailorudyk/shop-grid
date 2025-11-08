package com.rudyk.shopgrid.common.exception;

public class ResourceDuplicationException extends RuntimeException {

    public ResourceDuplicationException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s with field %s = %s is already exists", resourceName, fieldName, fieldValue));
    }

    public ResourceDuplicationException(String resourceName, String fieldName, Object fieldValue, Throwable cause) {
        super(String.format("%s with field %s = %s is already exists", resourceName, fieldName, fieldValue), cause);
    }

}
