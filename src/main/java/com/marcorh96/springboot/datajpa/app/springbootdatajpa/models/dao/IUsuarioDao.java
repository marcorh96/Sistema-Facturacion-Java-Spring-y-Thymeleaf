package com.marcorh96.springboot.datajpa.app.springbootdatajpa.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.marcorh96.springboot.datajpa.app.springbootdatajpa.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{
    
    public Usuario findByUsername(String username);
}
