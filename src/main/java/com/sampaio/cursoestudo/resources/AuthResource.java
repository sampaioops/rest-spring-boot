package com.sampaio.cursoestudo.resources;

import com.sampaio.cursoestudo.modelo.dto.EmailDTO;
import com.sampaio.cursoestudo.seguranca.JWTUtils;
import com.sampaio.cursoestudo.seguranca.UserSS;
import com.sampaio.cursoestudo.servico.AuthServico;
import com.sampaio.cursoestudo.servico.UsuarioServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthResource {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthServico authServico;

    @RequestMapping(value="/refresh_token", method= RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = UsuarioServico.authenticated();
        String token = jwtUtils.gerarToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/forgot", method= RequestMethod.POST)
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDTO) {
        authServico.sendNewPassword(emailDTO.getEmail());
        return ResponseEntity.noContent().build();
    }
}
