package com.sampaio.cursoestudo.servico;

import com.sampaio.cursoestudo.exception.ObjectNotFoundExcpetion;
import com.sampaio.cursoestudo.modelo.Categoria;
import com.sampaio.cursoestudo.modelo.Produto;
import com.sampaio.cursoestudo.repositorio.CategoriaRepositorio;
import com.sampaio.cursoestudo.repositorio.ProdutoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoServico implements Serializable {

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    public Produto buscar(Long id){
        Optional<Produto> pedido = produtoRepositorio.findById(id);

        return pedido.orElseThrow(()-> new ObjectNotFoundExcpetion(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
    }

    public Page<Produto> pesquisar(String nome, List<Long> ids,Integer pagina, Integer linhasPorPagina, String orderBy, String direcao){

        PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direcao), orderBy);
        List<Categoria> categorias = categoriaRepositorio.findAllById(ids);

        return produtoRepositorio.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);

    }

}
