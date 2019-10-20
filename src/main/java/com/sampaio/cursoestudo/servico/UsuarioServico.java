package com.sampaio.cursoestudo.servico;

import com.sampaio.cursoestudo.seguranca.UserSS;
import org.springframework.security.core.context.SecurityContextHolder;

public class UsuarioServico {

    public static UserSS authenticated() {
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
