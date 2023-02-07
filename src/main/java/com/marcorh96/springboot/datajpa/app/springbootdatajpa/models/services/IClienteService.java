package com.marcorh96.springboot.datajpa.app.springbootdatajpa.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.marcorh96.springboot.datajpa.app.springbootdatajpa.models.entity.Cliente;
import com.marcorh96.springboot.datajpa.app.springbootdatajpa.models.entity.Producto;

public interface IClienteService {
    public List<Cliente> findAll();

    public Page<Cliente> findAll(Pageable pageable);

    public void save(Cliente cliente);

    public Cliente findOne(Long id);

    public void delete(Long id);

    public List<Producto> findByNombre(String term);
}
