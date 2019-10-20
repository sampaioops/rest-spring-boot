package com.sampaio.cursoestudo.resources;

import com.sampaio.cursoestudo.modelo.Categoria;
import com.sampaio.cursoestudo.modelo.Cliente;
import com.sampaio.cursoestudo.modelo.Pedido;

import com.sampaio.cursoestudo.modelo.dto.CategoriaDTO;
import com.sampaio.cursoestudo.servico.PedidoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoServico pedidoServico;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Pedido> find(@PathVariable Long id) {

        Pedido pedido = pedidoServico.buscar(id);

        return ResponseEntity.ok().body(pedido);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody Pedido pedido){
        pedido = pedidoServico.salvar(pedido);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<Page<Pedido>> todosPorPagina(
            @RequestParam(value = "page", defaultValue = "0") Integer pagina,
            @RequestParam(value = "linhasPorPagina", defaultValue = "24")Integer linhasPorPagina,
            @RequestParam(value = "orderBy", defaultValue = "dataHora")String orderBy,
            @RequestParam(value = "direcao", defaultValue = "DESC")String direcao) {

        Page<Pedido> pedidos = pedidoServico.buscarPaginas(pagina, linhasPorPagina, orderBy, direcao);

        return ResponseEntity.ok().body(pedidos);
    }
}
