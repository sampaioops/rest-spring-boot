package com.sampaio.cursoestudo.resources;

import com.sampaio.cursoestudo.modelo.Categoria;
import com.sampaio.cursoestudo.modelo.dto.CategoriaDTO;
import com.sampaio.cursoestudo.servico.CategoriaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaServico categoriaServico;

    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<List<CategoriaDTO>> todosDto() {

        List<Categoria> categorias = categoriaServico.buscarTodos();

        List<CategoriaDTO> categoriaDTOS = categorias.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());

        return ResponseEntity.ok().body(categoriaDTOS);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<CategoriaDTO>> todosDtoPorPagina(
            @RequestParam(value = "page", defaultValue = "0") Integer pagina,
            @RequestParam(value = "linhasPorPagina", defaultValue = "24")Integer linhasPorPagina,
            @RequestParam(value = "orderBy", defaultValue = "nome")String orderBy,
            @RequestParam(value = "direcao", defaultValue = "ASC")String direcao) {

        Page<Categoria> categorias = categoriaServico.buscarPaginas(pagina, linhasPorPagina, orderBy, direcao);

        Page<CategoriaDTO> categoriaDTOS = categorias.map(obj -> new CategoriaDTO(obj));

        return ResponseEntity.ok().body(categoriaDTOS);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Categoria> find(@PathVariable Long id) {

        Categoria categoria = categoriaServico.buscar(id);


        return ResponseEntity.ok().body(categoria);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO categoriaDTO){
        Categoria categoria = categoriaServico.retornaCategoria(categoriaDTO);
        categoria = categoriaServico.salvar(categoria);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO categoriaDTO, @PathVariable Long id){
        Categoria categoria = categoriaServico.retornaCategoria(categoriaDTO);
        categoria.setId(id);
        categoria = categoriaServico.update(categoria);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaServico.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
