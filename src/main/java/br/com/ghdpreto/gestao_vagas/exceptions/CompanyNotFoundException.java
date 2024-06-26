package br.com.ghdpreto.gestao_vagas.exceptions;

public class CompanyNotFoundException extends RuntimeException{
    public CompanyNotFoundException() {
        super("Company não encontrada.");
    }

}


