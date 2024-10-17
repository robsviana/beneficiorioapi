package tech.robsondev.beneficiarioapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class BeneficiarioBusinessException extends BeneficiarioApiException {

    private String detalhe;

    public BeneficiarioBusinessException(String detalhe) {
        this.detalhe = detalhe;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pb.setTitle("Não foi possível processar as instruções");
        pb.getStatus();
        pb.setDetail(detalhe);
        return pb;
    }
}
