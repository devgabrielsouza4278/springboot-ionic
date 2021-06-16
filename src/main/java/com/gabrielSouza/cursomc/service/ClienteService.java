package com.gabrielSouza.cursomc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabrielSouza.cursomc.domain.Cidade;
import com.gabrielSouza.cursomc.domain.Cliente;
import com.gabrielSouza.cursomc.domain.Endereco;
import com.gabrielSouza.cursomc.domain.Enums.TipoCliente;
import com.gabrielSouza.cursomc.dto.ClienteDTO;
import com.gabrielSouza.cursomc.dto.ClienteNewDTO;
import com.gabrielSouza.cursomc.repositories.CidadeRepository;
import com.gabrielSouza.cursomc.repositories.ClienteRepository;
import com.gabrielSouza.cursomc.repositories.EndereçoRepository;
import com.gabrielSouza.cursomc.service.exception.DataIntegrityException;
import com.gabrielSouza.cursomc.service.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EndereçoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj); 
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj,obj);		
		return repo.save(newObj);

	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma cliente que contenha pedidos");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);

	}

	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	public Cliente fromNewDTO( ClienteNewDTO objDTO) {
		Cliente c1 = new Cliente (null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()));
		Cidade cid = new Cidade(objDTO.getCidadeID(), null, null);
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), c1, cid);
		c1.getEnderecos().add(end);
		c1.getTelefones().add(objDTO.getTelefone1());
		
		if(objDTO.getTelefone2()!= null) {
			c1.getTelefones().add(objDTO.getTelefone2());
		}
		
		if(objDTO.getTelefone3()!= null) {
			c1.getTelefones().add(objDTO.getTelefone3());
		}
		
		return c1;
	}
	
	
	private void updateData (Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
