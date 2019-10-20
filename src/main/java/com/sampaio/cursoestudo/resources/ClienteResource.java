package com.sampaio.cursoestudo.resources;

import com.sampaio.cursoestudo.modelo.Cliente;
import com.sampaio.cursoestudo.modelo.dto.ClienteCompletoDTO;
import com.sampaio.cursoestudo.modelo.dto.ClienteDTO;
import com.sampaio.cursoestudo.servico.ClienteServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteServico clienteServico;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cliente> find(@PathVariable Long id) {

        Cliente categoria = clienteServico.buscar(id);

        return ResponseEntity.ok().body(categoria);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteCompletoDTO clienteCompletoDTO) {
        Cliente categoria = clienteServico.retornaCliente(clienteCompletoDTO);
        categoria = clienteServico.salvar(categoria);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Long id) {
        Cliente cliente = clienteServico.retornaCliente(clienteDTO);
        cliente.setId(id);
        cliente = clienteServico.update(cliente);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteServico.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ClienteDTO>> todosDto() {

        List<Cliente> clientes = clienteServico.buscarTodos();

        List<ClienteDTO> clienteDTOS = clientes.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());

        return ResponseEntity.ok().body(clienteDTOS);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<ClienteDTO>> todosDtoPorPagina(
            @RequestParam(value = "page", defaultValue = "0") Integer pagina,
            @RequestParam(value = "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direcao", defaultValue = "ASC") String direcao) {

        Page<Cliente> clientes = clienteServico.buscarPaginas(pagina, linhasPorPagina, orderBy, direcao);

        Page<ClienteDTO> categoriaDTOS = clientes.map(obj -> new ClienteDTO(obj));

        return ResponseEntity.ok().body(categoriaDTOS);
    }
}
