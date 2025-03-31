package com.example.HopeConnect.Errors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}


