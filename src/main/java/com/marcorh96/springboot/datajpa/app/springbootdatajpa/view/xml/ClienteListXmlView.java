package com.marcorh96.springboot.datajpa.app.springbootdatajpa.view.xml;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.marcorh96.springboot.datajpa.app.springbootdatajpa.models.entity.Cliente;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Component("listar.xml")
public class ClienteListXmlView extends MarshallingView {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public ClienteListXmlView(Jaxb2Marshaller marshaller) {
        super(marshaller);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        model.remove("titulo");
        model.remove("page");

        List<Cliente> clientes = (List<Cliente>) model.get("clientes");
        model.remove("clientes");

        model.put("clienteList", new ClienteList(clientes));

        logger.info(model.put("clienteList", new ClienteList(clientes)));

        super.renderMergedOutputModel(model, request, response);
    }
}
