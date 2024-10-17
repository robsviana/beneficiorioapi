package tech.robsondev.beneficiarioapi.controller;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.robsondev.beneficiarioapi.exception.BeneficiarioApiException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BeneficiarioApiException.class)
    public ProblemDetail handleBeneficiarioApiException(BeneficiarioApiException exception) {
        return exception.toProblemDetail();
    }
}
