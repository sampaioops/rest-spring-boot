package com.sampaio.cursoestudo.enums;

import sun.misc.Perf;

public enum Perfil {

    CLIENTE("ROLE_CLIENTE"),
    ADMIN("ROLE_ADMIN"); // Essa descrição é padrão o framework exige!

    private String descricao;

    Perfil(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Perfil toEnum(String name){
        return Perfil.valueOf(name);
    }
}
