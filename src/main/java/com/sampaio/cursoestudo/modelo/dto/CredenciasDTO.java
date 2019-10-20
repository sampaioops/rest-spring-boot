package com.sampaio.cursoestudo.modelo.dto;

import java.io.Serializable;

public class CredenciasDTO implements Serializable {

    private String email;
    private String senha;

    public CredenciasDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
