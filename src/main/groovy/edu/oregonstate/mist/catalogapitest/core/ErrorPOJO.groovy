package edu.oregonstate.mist.catalogapitest.core

/**
 * Error representation class.
 */
class ErrorPOJO {
    String errorMessage
    Integer errorCode

    public ErrorPOJO() {
        // Jackson deserialization
    }

    public ErrorPOJO(String errorMessage, Integer errorCode) {
        this.errorMessage = errorMessage
        this.errorCode = errorCode
    }
}