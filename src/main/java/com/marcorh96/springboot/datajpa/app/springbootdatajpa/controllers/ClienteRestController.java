package com.marcorh96.springboot.datajpa.app.springbootdatajpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.marcorh96.springboot.datajpa.app.springbootdatajpa.models.services.IClienteService;
import com.marcorh96.springboot.datajpa.app.springbootdatajpa.view.xml.ClienteList;

@RestController
@RequestMapping("/api-clientes")
public class ClienteRestController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("/listar")
    public  ClienteList listarRest() {
        return new ClienteList(clienteService.findAll()) ;
    }
}
