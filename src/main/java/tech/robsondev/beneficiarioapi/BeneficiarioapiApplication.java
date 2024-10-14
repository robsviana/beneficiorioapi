package tech.robsondev.beneficiarioapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Beneficiario API", version = "1.0.0", description = "API desenvolvida manter dados de Beneficiarios"))
public class BeneficiarioapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeneficiarioapiApplication.class, args);
	}

}
