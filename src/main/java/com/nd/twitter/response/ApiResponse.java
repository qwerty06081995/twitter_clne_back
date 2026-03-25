package com.nd.twitter.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ApiResponse {
    private String message;
    private boolean status;
}
