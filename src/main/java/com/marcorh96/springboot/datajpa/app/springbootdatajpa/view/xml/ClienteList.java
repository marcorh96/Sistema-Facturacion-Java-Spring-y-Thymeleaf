package com.marcorh96.springboot.datajpa.app.springbootdatajpa.view.xml;

import java.util.List;




import com.marcorh96.springboot.datajpa.app.springbootdatajpa.models.entity.Cliente;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "clientesList")
public class ClienteList {

    @XmlElement(name = "cliente")
    public List<Cliente> clientes;

    public ClienteList() {
    }

    public ClienteList(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

}
