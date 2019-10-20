package com.sampaio.cursoestudo.servico;

import com.sampaio.cursoestudo.modelo.Cliente;
import com.sampaio.cursoestudo.repositorio.ClienteRepositorio;
import com.sampaio.cursoestudo.seguranca.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServicoImpl implements UserDetailsService {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = clienteRepositorio.findByEmail(email);

        if (cliente == null) {
            throw new UsernameNotFoundException(email);
        }

        return new UserSS(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfils());
    }
}
