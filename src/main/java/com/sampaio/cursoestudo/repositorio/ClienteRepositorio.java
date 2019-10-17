package com.sampaio.cursoestudo.repositorio;

import com.sampaio.cursoestudo.modelo.Cliente;
import com.sampaio.cursoestudo.modelo.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {


    @Transactional(readOnly = true)
    Cliente findByEmail(String email);

}
