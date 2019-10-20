package com.sampaio.cursoestudo.repositorio;

import com.sampaio.cursoestudo.modelo.Categoria;
import com.sampaio.cursoestudo.modelo.Cliente;
import com.sampaio.cursoestudo.modelo.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {

    @Transactional(readOnly = true)
    Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);

}
