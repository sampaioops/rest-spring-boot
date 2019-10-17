package com.sampaio.cursoestudo.resources.Exceptions;

import java.io.Serializable;

public class CampoMessagem implements Serializable {

    private String nomeCampo;
    private String mensagem;

    public CampoMessagem() {
    }

    public CampoMessagem(String nomeCampo, String mensagem) {
        this.nomeCampo = nomeCampo;
        this.mensagem = mensagem;
    }

    public String getNomeCampo() {
        return nomeCampo;
    }

    public void setNomeCampo(String nomeCampo) {
        this.nomeCampo = nomeCampo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }


}
