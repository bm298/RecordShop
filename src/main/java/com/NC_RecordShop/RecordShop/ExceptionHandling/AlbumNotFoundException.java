package com.NC_RecordShop.RecordShop.ExceptionHandling;

public class AlbumNotFoundException extends RuntimeException {
    public AlbumNotFoundException(String message) {
        super(message);
    }
}
