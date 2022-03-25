package com.fseguel.springboot.app.productos.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fseguel.springboot.app.productos.models.dao.IProductoDao;
import com.fseguel.springboot.app.productos.models.entity.Producto;
import com.fseguel.springboot.app.productos.models.service.IProductoService;

@Service
public class ProductoService implements IProductoService {

	@Autowired
	private IProductoDao productoDao;  
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		return (List<Producto>) productoDao.findAll();
	}

	@Override
	public Producto findById(Long id) {
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Producto save(Producto producto) {
		return productoDao.save(producto);		
	}

	@Override
	public void deleteById(Long id) {
		productoDao.deleteById(id);		
	}

}
