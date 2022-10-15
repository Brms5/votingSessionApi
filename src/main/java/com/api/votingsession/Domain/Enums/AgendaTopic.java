package com.api.votingsession.Domain.Enums;

public enum AgendaTopic {

    // atribuimos um valor numerico para cada um para evitar erro ao acrescentar
    // outros, evitando que seja gerado automaticamente
    ECONOMIA(1),
    CORRUPCAO(2),
    SEGURANCA(3),
    EDUCACAO(4),
    SAUDE(5);

    //precisamos criar um  code para o tipo enum
    private int code;

    //esse construtor do tipo enum precisa ser privado
    private AgendaTopic(int code) {
        this.code = code;
    }

    //para poder acessar externo
    public int getCode() {
        return code;
    }

    //precisamos de um metodo static para converter o valor numerico para o tipo enum
    public static AgendaTopic valueOf(int code) {
        for (AgendaTopic value : AgendaTopic.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid AgendaTopic code");
    }
}
