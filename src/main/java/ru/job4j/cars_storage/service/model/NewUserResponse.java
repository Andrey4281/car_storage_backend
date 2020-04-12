package ru.job4j.cars_storage.service.model;

/**
 * Entity for response to request creating new user
 */
public class NewUserResponse {

    /**
     * Success creating of new user
     */
    private Boolean success;

    /**
     * Text of error which appeared during of creating the new user
     */
    private String errorMessage;


    public NewUserResponse() {
        this.success = true;
    }

    public NewUserResponse(String errorMessage) {
        this.success = false;
        this.errorMessage = errorMessage;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
