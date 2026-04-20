package se.yrgo.dataaccess;

public class MoreThanOneRecordFoundException extends RuntimeException {
    public MoreThanOneRecordFoundException(String message) {
        super(message);
    }
}
