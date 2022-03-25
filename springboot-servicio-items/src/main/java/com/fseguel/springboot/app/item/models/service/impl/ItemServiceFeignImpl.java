package com.fseguel.springboot.app.item.models.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.fseguel.springboot.app.item.clientes.IProductoClienteRest;
import com.fseguel.springboot.app.item.models.Item;
import com.fseguel.springboot.app.item.models.Producto;
import com.fseguel.springboot.app.item.models.service.ItemService;

@Service("itemServiceFeignImpl")
@Primary
public class ItemServiceFeignImpl implements ItemService {
	
	@Autowired
	private IProductoClienteRest clienteFeign;
	
	@Override
	public List<Item> findAll() {
		return clienteFeign.listar().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		return new Item(clienteFeign.ver(id), cantidad);
	}

	@Override
	public Producto save(Producto producto) {
		return clienteFeign.crear(producto);
	}

	@Override
	public Producto update(Producto producto, Long id) {
		return clienteFeign.editar(producto, id); 
	}

	@Override
	public void delete(Long id) {
		clienteFeign.borrar(id);
	}

}
