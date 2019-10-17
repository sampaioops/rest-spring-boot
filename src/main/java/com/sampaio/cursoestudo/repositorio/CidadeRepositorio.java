package com.sampaio.cursoestudo.repositorio;

import com.sampaio.cursoestudo.modelo.Cidade;
import com.sampaio.cursoestudo.modelo.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepositorio extends JpaRepository<Cidade, Long> {



}
