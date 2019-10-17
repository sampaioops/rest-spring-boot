package com.sampaio.cursoestudo.repositorio;

import com.sampaio.cursoestudo.modelo.Categoria;
import com.sampaio.cursoestudo.modelo.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepositorio extends JpaRepository<Pagamento, Long> {



}
