package tech.robsondev.beneficiarioapi.enuns;

import lombok.Getter;

@Getter
public enum DocumentoTipoEnum {

    CPF("Cadastro de Pessoa Física"),
    CNPJ("Cadastro Nacional da Pessoa"),
    RG("Registro Geral"),
    RCN("Registro Civil de Nascimento"),
    CNH("Carteira de motorista"),
    TITULOELEITORAL("Titulo Eleitoral");

    private String nome;

    DocumentoTipoEnum(String nome) {
        this.nome = nome;
    }
}
