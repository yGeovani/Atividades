package com.stockmanager.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter("/usuarios.html")
public class controleAcesso implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession sessao = req.getSession(false);

        if (sessao == null || sessao.getAttribute("perfil") == null) {
            resp.sendRedirect("login.html");
            return;
        }

        String perfil = (String) sessao.getAttribute("perfil");

        if (!"admin".equalsIgnoreCase(perfil)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado.");
            return;
        }

        chain.doFilter(request, response);
    }
}