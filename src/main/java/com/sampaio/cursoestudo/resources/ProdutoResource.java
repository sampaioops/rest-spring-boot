package com.sampaio.cursoestudo.resources;




import com.sampaio.cursoestudo.modelo.Categoria;
import com.sampaio.cursoestudo.modelo.Produto;
import com.sampaio.cursoestudo.modelo.dto.CategoriaDTO;
import com.sampaio.cursoestudo.modelo.dto.ProdutoDTO;
import com.sampaio.cursoestudo.servico.ProdutoServico;
import com.sampaio.cursoestudo.utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoServico produtoServico;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Produto> find(@PathVariable Long id) {

        Produto produto = produtoServico.buscar(id);

        return ResponseEntity.ok().body(produto);
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ProdutoDTO>> todosDtoPorPagina(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer pagina,
            @RequestParam(value = "linhasPorPagina", defaultValue = "24")Integer linhasPorPagina,
            @RequestParam(value = "orderBy", defaultValue = "nome")String orderBy,
            @RequestParam(value = "direcao", defaultValue = "ASC")String direcao) {

        List<Long> idsCategorias = UrlUtils.converteIntList(categorias);
        String nomeDecode = UrlUtils.convertParam(nome);

        Page<Produto> produtos = produtoServico.pesquisar(nomeDecode, idsCategorias, pagina, linhasPorPagina, orderBy, direcao);

        Page<ProdutoDTO> categoriaDTOS = produtos.map(obj -> new ProdutoDTO(obj));

        return ResponseEntity.ok().body(categoriaDTOS);
    }
}
