package com.gabrielSouza.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gabrielSouza.cursomc.domain.Categoria;
import com.gabrielSouza.cursomc.domain.Cidade;
import com.gabrielSouza.cursomc.domain.Cliente;
import com.gabrielSouza.cursomc.domain.Endereco;
import com.gabrielSouza.cursomc.domain.Estado;
import com.gabrielSouza.cursomc.domain.ItemPedido;
import com.gabrielSouza.cursomc.domain.Pagamento;
import com.gabrielSouza.cursomc.domain.PagamentoComBoleto;
import com.gabrielSouza.cursomc.domain.PagamentoComCartao;
import com.gabrielSouza.cursomc.domain.Pedido;
import com.gabrielSouza.cursomc.domain.Produto;
import com.gabrielSouza.cursomc.domain.Enums.EstadoPagamento;
import com.gabrielSouza.cursomc.domain.Enums.TipoCliente;
import com.gabrielSouza.cursomc.repositories.CategoriaRepository;
import com.gabrielSouza.cursomc.repositories.CidadeRepository;
import com.gabrielSouza.cursomc.repositories.ClienteRepository;
import com.gabrielSouza.cursomc.repositories.EndereçoRepository;
import com.gabrielSouza.cursomc.repositories.EstadoRepository;
import com.gabrielSouza.cursomc.repositories.ItemPedidoRepository;
import com.gabrielSouza.cursomc.repositories.PagamentoRepository;
import com.gabrielSouza.cursomc.repositories.PedidoRepository;
import com.gabrielSouza.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication  implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
		
	@Autowired
	private EndereçoRepository endereçoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception { 
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama, mesa e Banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new  Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new  Categoria(null, "Perfumaria");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3= new Produto(null, "Mouse", 80.00);
	
		
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7));
		
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		
		Estado e1 = new Estado(null, "SP");
		Estado e2 = new Estado(null, "RS");
		
		Cidade c1 = new Cidade(null, "Osasco", e1);
		Cidade c2 = new Cidade(null, "Carapicuíba", e1);
		Cidade c3 = new Cidade(null, "Canela", e2);
		Cidade c4 = new Cidade(null, "Gramado", e2);
		
		
		e1.getCidades().addAll(Arrays.asList(c1,c2));
		e2.getCidades().addAll(Arrays.asList(c3,c4));
	 
		 
		estadoRepository.saveAll(Arrays.asList(e1,e2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3,c4));	
		
		
		Cliente cli1 = new Cliente(null, "Jorge Santos", "jorge.santos@gmail.com", "12345678910", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("11 91234567", "11 98910111"));
		
		Endereco end1 = new Endereco(null, "Av. Paulista", "105",
				"Ap 14A", "Bela Vista 5", "06328960", cli1, c2);		
		Endereco end2 = new Endereco(null, "Rua Augusta", "168", "Portão Cinza",
				"São João", "06321870", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(end1,end2));
		
		clienteRepository.save(cli1);
		endereçoRepository.saveAll(Arrays.asList(end1,end2));
		
		
		SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
		
		Pedido ped1 = new Pedido(null, sdf.parse("26/05/2021 00:20"), cli1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("26/05/2021 00:20"), cli1, end2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/06/2021 00:15"),null);
		ped2.setPagamento(pagto2);
		
		
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
				
	}

}
