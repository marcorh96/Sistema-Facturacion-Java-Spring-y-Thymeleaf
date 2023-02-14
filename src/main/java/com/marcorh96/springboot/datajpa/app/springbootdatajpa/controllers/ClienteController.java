package com.marcorh96.springboot.datajpa.app.springbootdatajpa.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.marcorh96.springboot.datajpa.app.springbootdatajpa.models.entity.Cliente;
import com.marcorh96.springboot.datajpa.app.springbootdatajpa.models.services.IClienteService;
import com.marcorh96.springboot.datajpa.app.springbootdatajpa.models.services.IUploadFileService;
import com.marcorh96.springboot.datajpa.app.springbootdatajpa.util.paginator.PageRender;
import com.marcorh96.springboot.datajpa.app.springbootdatajpa.view.xml.ClienteList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IUploadFileService uploadFileService;
    @Autowired
    private MessageSource messageSource;

    @Secured("ROLE_USER")
    @GetMapping("uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) {
        Resource recurso = null;
        try {
            recurso = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash,
            Locale locale, @RequestParam(name = "format", defaultValue = "html") String format) {
        Cliente cliente = clienteService.fetchByIdWithFacturas(id);
        if (cliente == null) {
            flash.addAttribute("error", messageSource.getMessage("text.cliente.flash.db.error", null, locale));
            return "redirect:/listar";
        }
        model.put("cliente", cliente);
        model.put("titulo", messageSource.getMessage("text.cliente.detalle.titulo", null, locale).concat(": ")
                .concat(cliente.getNombre()));
        return "ver";
    }

    @GetMapping("/listar-rest")
    public @ResponseBody ClienteList listarRest() {
        return new ClienteList(clienteService.findAll());
    }

    @GetMapping({ "/listar", "/" })
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
            Authentication authentication, HttpServletRequest request, Locale locale,
            @RequestParam(name = "format", defaultValue = "html") String format) {
        if (authentication != null) {
            logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logger.info(
                    "utilizando forma estatica SecurityContextHolder.getContext().getAuthentication(): Usuario autenticado, username:"
                            .concat(auth.getName()));
        }
        if (hasRole("ROLE_ADMIN")) {
            logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso"));
        } else {
            logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso"));
        }
        SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request,
                "");
        if (securityContext.isUserInRole("ROLE_ADMIN")) {
            logger.info("Forma usando  SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName())
                    .concat(" tienes acceso"));
        } else {
            logger.info("Forma usando  SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName())
                    .concat(" NO tienes acceso"));
        }
        if (request.isUserInRole("ROLE_ADMIN")) {
            logger.info("Forma usando  HttpServletRequest: Hola ".concat(auth.getName()).concat(" tienes acceso"));
        } else {
            logger.info("Forma usando  HttpServletRequest: Hola ".concat(auth.getName()).concat(" NO tienes acceso"));
        }
        if (format.equals("html")) {
            Pageable pageRequest = PageRequest.of(page, 4);

            Page<Cliente> clientes = clienteService.findAll(pageRequest);

            PageRender<Cliente> pageRender = new PageRender<Cliente>("/listar", clientes);
            model.addAttribute("clientes", clientes);
            model.addAttribute("page", pageRender);
        } else {
            model.addAttribute("clientes", clienteService.findAll());
        }
        /*
         * Pageable pageRequest = PageRequest.of(page, 5);
         * Page<Cliente> clientes = clienteService.findAll(pageRequest);
         * PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
         * model.addAttribute("clientes", clientes);
         * model.addAttribute("page", pageRender);
         */
        model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));

        return "listar";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/form")
    public String crear(Map<String, Object> model, Locale locale) {
        Cliente cliente = new Cliente();
        model.put("cliente", cliente);
        model.put("titulo", messageSource.getMessage("text.cliente.form.titulo.crear", null, locale));
        return "form";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash,
            Locale locale) {
        Cliente cliente = null;
        if (id > 0) {

            cliente = clienteService.findOne(id);

            if (cliente == null) {
                flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.db.error", null, locale));
                return "redirect:/listar";
            }
        } else {
            flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.id.error", null, locale));
            return "redirect:/listar";
        }
        model.put("cliente", cliente);
        model.put("titulo", messageSource.getMessage("text.cliente.form.titulo.editar", null, locale));
        return "form";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Locale locale) {
        if (id > 0) {
            Cliente cliente = clienteService.findOne(id);
            clienteService.delete(id);
            flash.addFlashAttribute("success",
                    messageSource.getMessage("text.cliente.flash.eliminar.success", null, locale));

            if (uploadFileService.delete(cliente.getFoto())) {
                String mensajeFotoEliminar = String.format(
                        messageSource.getMessage("text.cliente.flash.foto.eliminar.success", null, locale),
                        cliente.getFoto());
                flash.addFlashAttribute("info", mensajeFotoEliminar);
            }
        }
        
        return "redirect:/listar";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/form")
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
            @RequestParam("file") MultipartFile foto, RedirectAttributes flash,
            SessionStatus status, Locale locale) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", messageSource.getMessage("text.cliente.form.titulo", null, locale));
            return "form";
        }

        if (!foto.isEmpty()) {
            if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto().length() > 0) {
                uploadFileService.delete(cliente.getFoto());
            }
            String uniqueFilename = null;
            try {
                uniqueFilename = uploadFileService.copy(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }
            flash.addFlashAttribute("info",
                    messageSource.getMessage("text.cliente.flash.foto.subir.success", null, locale) + "'"
                            + uniqueFilename + "'");
            cliente.setFoto(uniqueFilename);
        }

        String mensajeFlash = (cliente.getId() != null)
                ? messageSource.getMessage("text.cliente.flash.editar.success", null, locale)
                : messageSource.getMessage("text.cliente.flash.crear.success", null, locale);
        clienteService.save(cliente);
        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:listar";
    }

    private boolean hasRole(String role) {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return false;
        }
        Authentication auth = context.getAuthentication();
        if (auth == null) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        return authorities.contains(new SimpleGrantedAuthority(role));

        /*
         * for(GrantedAuthority authority : authorities){
         * if(role.equals(authority.getAuthority())){
         * logger.info("Hola usuario ".concat(auth.getName()).concat(" tu role es ").
         * concat(authority.getAuthority()));
         * return true;
         * }
         * }
         */
        /* return false; */
    }
}
