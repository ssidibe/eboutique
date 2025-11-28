package ml.finances.caisff.eboutique.services;

import ml.finances.caisff.eboutique.ApiErrorMessage;
import org.springframework.http.ResponseEntity;

public class MyApiException extends Exception{
    private final ResponseEntity<ApiErrorMessage> response;

    public MyApiException(Integer code, String message) {
        ApiErrorMessage msg = new ApiErrorMessage(code, message);
        response=ResponseEntity.status(code).body(msg);
    }

    public ResponseEntity<ApiErrorMessage> getResponse() {
        return response;
    }

}
