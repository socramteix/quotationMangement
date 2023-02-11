package com.ericsson.quotationmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private String message;
    private String details;
    private String httpCodeMessage;
}
