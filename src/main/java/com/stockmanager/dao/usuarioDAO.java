package com.stockmanager.dao;

import com.stockmanager.model.usuario;
import com.stockmanager.util.conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class usuarioDAO {

    public void cadastrar(usuario usuario) throws SQLException {

        String sql = "INSERT INTO usuario (nome, email, senha, perfil) VALUES (?, ?, ?, ?)";

        try (Connection conexaoBanco = conexao.conectar();
             PreparedStatement comando = conexaoBanco.prepareStatement(sql)) {

            comando.setString(1, usuario.getNome());
            comando.setString(2, usuario.getEmail());
            comando.setString(3, usuario.getSenha());
            comando.setString(4, usuario.getPerfil());

            comando.executeUpdate();
        }
    }

    public usuario buscarPorEmail(String email) throws SQLException {

        String sql = "SELECT * FROM usuario WHERE email = ?";

        try (Connection conexaoBanco = conexao.conectar();
             PreparedStatement comando = conexaoBanco.prepareStatement(sql)) {

            comando.setString(1, email);

            try (ResultSet resultado = comando.executeQuery()) {

                if (resultado.next()) {

                    usuario usuario = new usuario();

                    usuario.setId(resultado.getInt("id"));
                    usuario.setNome(resultado.getString("nome"));
                    usuario.setEmail(resultado.getString("email"));
                    usuario.setSenha(resultado.getString("senha"));
                    usuario.setPerfil(resultado.getString("perfil"));

                    return usuario;
                }
            }
        }

        return null;
    }
}