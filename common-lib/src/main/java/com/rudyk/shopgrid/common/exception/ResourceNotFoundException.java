package com.rudyk.shopgrid.common.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s with field %s = %s not found", resourceName, fieldName, fieldValue));
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue, Throwable cause) {
        super(String.format("%s with field %s = %s not found", resourceName, fieldName, fieldValue), cause);
    }

}
