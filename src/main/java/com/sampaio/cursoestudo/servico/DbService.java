package com.sampaio.cursoestudo.servico;

import com.sampaio.cursoestudo.enums.PagamentoStatus;
import com.sampaio.cursoestudo.enums.TipoCliente;
import com.sampaio.cursoestudo.modelo.*;
import com.sampaio.cursoestudo.repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

@Service
public class DbService {

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @Autowired
    private EstadoRepositorio estadoRepositorio;

    @Autowired
    private CidadeRepositorio cidadeRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private EnderecoRepositorio enderecoRepositorio;

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @Autowired
    private PagamentoRepositorio pagamentoRepositorio;

    @Autowired
    private PedidoItemRepositorio pedidoItemRepositorio;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void instantianteTestDatabase(){
        Categoria categoria1 = new Categoria( "Informática");
        Categoria categoria2 = new Categoria( "Escritório");
        Categoria categoria3 = new Categoria( "Mesa e Banho");
        Categoria categoria4 = new Categoria( "Bebidas");
        Categoria categoria5 = new Categoria( "Perfumaria");
        Categoria categoria6 = new Categoria( "Casa");
        Categoria categoria7 = new Categoria( "Doces");

        Produto produto1 = new Produto("Computador", 2000d);
        Produto produto2 = new Produto("Impressora", 600d);
        Produto produto3 = new Produto("Mouse", 70d);


        categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
        categoria2.getProdutos().addAll(Arrays.asList(produto2));

        produto1.getCategorias().addAll(Arrays.asList(categoria1));
        produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2));
        produto3.getCategorias().addAll(Arrays.asList(categoria1));



        categoriaRepositorio.saveAll(Arrays.asList(categoria1, categoria2, categoria3, categoria4, categoria5, categoria6, categoria7));
        produtoRepositorio.saveAll(Arrays.asList(produto1, produto2, produto3));


        Estado estado1 = new Estado("Minas Gerais");
        Estado estado2 = new Estado("São Paulo");

        Cidade cidade1 = new Cidade("Uberlândia", estado1);

        Cidade cidade2 = new Cidade("Campinas", estado2);

        Cidade cidade3 = new Cidade("São Paulo", estado2);

        estado1.getCidades().add(cidade1);
        estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));

        estadoRepositorio.saveAll(Arrays.asList(estado1, estado2));
        cidadeRepositorio.saveAll(Arrays.asList(cidade1, cidade2, cidade3));

        Cliente cliente = new Cliente("Daniel", "sampaioops@outlook.com", "17449665751", TipoCliente.PESSOAFISICA, bCryptPasswordEncoder.encode("teste123"));
        cliente.getTelefones().addAll(Arrays.asList("22997514050", "22981016444"));

        Endereco endereco1 = new Endereco("Rua Veneza", "100", "casa 3", "Jardim Excelsior", "28915040", cliente, cidade1);
        Endereco endereco2 = new Endereco("Rua Deb", "23", "Ao lado", "Mulher Linda", "28945141", cliente, cidade3);

        cliente.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));


        clienteRepositorio.save(cliente);
        enderecoRepositorio.saveAll(Arrays.asList(endereco1, endereco2));


        Pedido pedido1 = new Pedido(new Date(), cliente, endereco1);
        Pedido pedido2 = new Pedido(new Date(), cliente, endereco2);

        Pagamento pagamento1 = new PagamentoCartao(PagamentoStatus.QUITADO, pedido1, 6);
        pedido1.setPagamento(pagamento1);

        Pagamento pagamento2 = new PagamentoBoleto(PagamentoStatus.PENDENTE, pedido2, new Date(), null);
        pedido2.setPagamento(pagamento2);

        cliente.getPedidos().addAll(Arrays.asList(pedido1, pedido2));

        pedidoRepositorio.saveAll(Arrays.asList(pedido1, pedido2));
        pagamentoRepositorio.saveAll(Arrays.asList(pagamento1, pagamento2));

        PedidoItem pedidoItem1 = new PedidoItem(pedido1, produto1, 0d, 1, 2000.00d);
        PedidoItem pedidoItem2 = new PedidoItem(pedido1, produto3, 0d, 2, 80.00d);
        PedidoItem pedidoItem3 = new PedidoItem(pedido2, produto2, 100.00d, 1, 800.00d);

        pedido1.getItems().addAll(Arrays.asList(pedidoItem1, pedidoItem2));
        pedido2.getItems().add(pedidoItem3);

        produto1.getItems().add(pedidoItem1);
        produto2.getItems().add(pedidoItem3);
        produto3.getItems().add(pedidoItem2);


        pedidoItemRepositorio.saveAll(Arrays.asList(pedidoItem1, pedidoItem2, pedidoItem3));
    }
}
