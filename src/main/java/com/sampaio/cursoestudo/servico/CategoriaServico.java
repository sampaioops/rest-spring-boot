package com.sampaio.cursoestudo.servico;

import com.sampaio.cursoestudo.exception.DataIntegrityException;
import com.sampaio.cursoestudo.exception.ObjectNotFoundExcpetion;
import com.sampaio.cursoestudo.modelo.Categoria;
import com.sampaio.cursoestudo.modelo.dto.CategoriaDTO;
import com.sampaio.cursoestudo.repositorio.CategoriaRepositorio;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServico implements Serializable {

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;



    public Categoria buscar(Long id) {
        Optional<Categoria> categoria = categoriaRepositorio.findById(id);

        return categoria.orElseThrow(() -> new ObjectNotFoundExcpetion(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }


    public Categoria salvar(Categoria categoria) {
        categoria.setId(null);
        return categoriaRepositorio.save(categoria);
    }

    public Categoria update(Categoria categoria) {
        Categoria categoriaAtualizada = buscar(categoria.getId());
        updateData(categoriaAtualizada, categoria);
        return categoriaRepositorio.save(categoriaAtualizada);
    }

    public void deletar(Long id) {
        buscar(id);
        try{
            categoriaRepositorio.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos!");
        }
    }


    public List<Categoria> buscarTodos() {
        return categoriaRepositorio.findAll();
    }

    public Page<Categoria> buscarPaginas(Integer pagina, Integer linhasPorPagina, String orderBy, String direcao){

        PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direcao), orderBy);

        return categoriaRepositorio.findAll(pageRequest);
    }

    public Categoria retornaCategoria(CategoriaDTO categoriaDTO){
        return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
    }

    public void updateData(Categoria categoriaNew, Categoria categoria){
        categoriaNew.setNome(categoria.getNome());
    }
}

