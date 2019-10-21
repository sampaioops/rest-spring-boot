package com.sampaio.cursoestudo.servico;

import com.sampaio.cursoestudo.exception.ObjectNotFoundExcpetion;
import com.sampaio.cursoestudo.modelo.Cliente;
import com.sampaio.cursoestudo.repositorio.ClienteRepositorio;
import com.sampaio.cursoestudo.servico.email.EmailServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailServico emailServico;

    public void sendNewPassword(String email) {
        Cliente cliente = clienteRepositorio.findByEmail(email);

        if(cliente == null){
            throw new ObjectNotFoundExcpetion("E-mail não encontrado!");
        }

        String newPass = newPassword();
        cliente.setSenha(bCryptPasswordEncoder.encode(newPass));

        clienteRepositorio.saveAndFlush(cliente);

        emailServico.enviaNovaSenhaEmail(cliente, newPass);
    }

    private String newPassword() {
        Random rand = new Random();
       return Long.toHexString(rand.nextLong());
    }
}
