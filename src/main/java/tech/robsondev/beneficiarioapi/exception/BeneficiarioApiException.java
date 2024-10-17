package tech.robsondev.beneficiarioapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class BeneficiarioApiException extends RuntimeException {

    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pb.setTitle("Beneficiario Api Internal Server Error");
        return pb;
    }

}
