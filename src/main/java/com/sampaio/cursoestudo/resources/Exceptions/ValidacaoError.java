package com.sampaio.cursoestudo.resources.Exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidacaoError extends ErroPadrao {

    private List<CampoMessagem> erros = new ArrayList<>();

    public ValidacaoError(Integer status, String msg, Long timeStamp) {
        super(status, msg, timeStamp);
    }

    public List<CampoMessagem> getErros() {
        return erros;
    }

    public void setError(String nome, String messagem){
        this.erros.add(new CampoMessagem(nome, messagem));
    }
}
