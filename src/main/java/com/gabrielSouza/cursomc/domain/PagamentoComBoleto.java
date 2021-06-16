package com.gabrielSouza.cursomc.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gabrielSouza.cursomc.domain.Enums.EstadoPagamento;

@Entity
public class PagamentoComBoleto extends Pagamento {
	
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date DataVencimento;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date DataPagamento;

	public PagamentoComBoleto() {

	}

	public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date DataVencimento,
			Date DataPagamento) {
		super(id, estado, pedido);
		this.DataPagamento = DataPagamento;
		this.DataVencimento = DataVencimento;
	}

	public Date getDataVencimento() {
		return DataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		DataVencimento = dataVencimento;
	}

	public Date getDataPagamento() {
		return DataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		DataPagamento = dataPagamento;
	}

}
