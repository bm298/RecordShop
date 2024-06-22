package com.NC_RecordShop.RecordShop.ExceptionHandling;

public class InvalidAlbumInputException extends RuntimeException{

    public InvalidAlbumInputException(String message) {
        super(message);
    }
}
