package com.sampaio.cursoestudo.repositorio;

import com.sampaio.cursoestudo.modelo.Categoria;
import com.sampaio.cursoestudo.modelo.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoItemRepositorio extends JpaRepository<PedidoItem, Long> {



}
