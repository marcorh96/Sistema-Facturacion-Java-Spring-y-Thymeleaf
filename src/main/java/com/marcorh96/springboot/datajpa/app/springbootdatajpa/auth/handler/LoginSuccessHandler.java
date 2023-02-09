package com.marcorh96.springboot.datajpa.app.springbootdatajpa.auth.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        SessionFlashMapManager flashManager = new SessionFlashMapManager();
        FlashMap flashMap = new FlashMap();

        flashMap.put("success", "Hola "+ authentication.getName() +" has iniciado sesión con éxito!");
        flashManager.saveOutputFlashMap(flashMap, request, response);

        if(authentication != null){
            logger.info("El usuario " + authentication.getName() + " ha iniciado sesion con exito!");
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
