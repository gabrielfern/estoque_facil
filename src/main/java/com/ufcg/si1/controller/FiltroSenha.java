package com.ufcg.si1.controller;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FiltroSenha extends GenericFilterBean {



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String senhaAdmin = "banana";

        HttpServletRequest req = (HttpServletRequest) request;

        String senha = req.getParameter("senha");

        if(!senhaAdmin.equals(senha)) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

        chain.doFilter(request, response);
    }


}
