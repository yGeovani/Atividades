package com.stockmanager.controller;

import com.stockmanager.dao.usuarioDAO;
import com.stockmanager.model.usuario;
import com.stockmanager.util.seguranca;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class loginServlet extends HttpServlet {

    private usuarioDAO dao;

    @Override
    public void init() {
        dao = new usuarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        try {
            usuario usuario = dao.buscarPorEmail(email);

            if (usuario != null &&
                seguranca.verificarSenha(senha, usuario.getSenha())) {

                HttpSession sessao = request.getSession();

                sessao.setAttribute("usuarioId", usuario.getId());
                sessao.setAttribute("nomeUsuario", usuario.getNome());
                sessao.setAttribute("perfil", usuario.getPerfil());

                response.sendRedirect("index.html");

            } else {
                response.sendRedirect("login.html?erro=1");
            }

        } catch (Exception e) {
            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Erro ao realizar login."
            );
        }
    }
}