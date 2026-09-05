package com.stockmanager.dao;

import com.stockmanager.model.produto;
import com.stockmanager.util.conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class produtoDAO {

    // CREATE - Cadastrar produto
    public void cadastrar(produto produto) throws SQLException {

        String sql = "INSERT INTO produto " +
                "(nome, codigo, categoria, quantidade, preco, fornecedor, cep, " +
                "logradouro, bairro, cidade, estado, descricao) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexaoBanco = conexao.conectar();
             PreparedStatement comando = conexaoBanco.prepareStatement(sql)) {

            comando.setString(1, produto.getNome());
            comando.setString(2, produto.getCodigo());
            comando.setString(3, produto.getCategoria());
            comando.setInt(4, produto.getQuantidade());
            comando.setDouble(5, produto.getPreco());
            comando.setString(6, produto.getFornecedor());
            comando.setString(7, produto.getCep());
            comando.setString(8, produto.getLogradouro());
            comando.setString(9, produto.getBairro());
            comando.setString(10, produto.getCidade());
            comando.setString(11, produto.getEstado());
            comando.setString(12, produto.getDescricao());

            comando.executeUpdate();
        }
    }

    // READ - Listar produtos
    public List<produto> listar() throws SQLException {

        List<produto> produtos = new ArrayList<>();

        String sql = "SELECT * FROM produto ORDER BY id DESC";

        try (Connection conexaoBanco = conexao.conectar();
             PreparedStatement comando = conexaoBanco.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {

                produto produto = new produto();

                produto.setId(resultado.getInt("id"));
                produto.setNome(resultado.getString("nome"));
                produto.setCodigo(resultado.getString("codigo"));
                produto.setCategoria(resultado.getString("categoria"));
                produto.setQuantidade(resultado.getInt("quantidade"));
                produto.setPreco(resultado.getDouble("preco"));
                produto.setFornecedor(resultado.getString("fornecedor"));
                produto.setCep(resultado.getString("cep"));
                produto.setLogradouro(resultado.getString("logradouro"));
                produto.setBairro(resultado.getString("bairro"));
                produto.setCidade(resultado.getString("cidade"));
                produto.setEstado(resultado.getString("estado"));
                produto.setDescricao(resultado.getString("descricao"));

                produtos.add(produto);
            }
        }

        return produtos;
    }

    // READ - Buscar produto pelo ID
    public produto buscarPorId(int id) throws SQLException {

        String sql = "SELECT * FROM produto WHERE id = ?";

        try (Connection conexaoBanco = conexao.conectar();
             PreparedStatement comando = conexaoBanco.prepareStatement(sql)) {

            comando.setInt(1, id);

            try (ResultSet resultado = comando.executeQuery()) {

                if (resultado.next()) {

                    produto produto = new produto();

                    produto.setId(resultado.getInt("id"));
                    produto.setNome(resultado.getString("nome"));
                    produto.setCodigo(resultado.getString("codigo"));
                    produto.setCategoria(resultado.getString("categoria"));
                    produto.setQuantidade(resultado.getInt("quantidade"));
                    produto.setPreco(resultado.getDouble("preco"));
                    produto.setFornecedor(resultado.getString("fornecedor"));
                    produto.setCep(resultado.getString("cep"));
                    produto.setLogradouro(resultado.getString("logradouro"));
                    produto.setBairro(resultado.getString("bairro"));
                    produto.setCidade(resultado.getString("cidade"));
                    produto.setEstado(resultado.getString("estado"));
                    produto.setDescricao(resultado.getString("descricao"));

                    return produto;
                }
            }
        }

        return null;
    }

    // UPDATE - Atualizar produto
    public void atualizar(produto produto) throws SQLException {

        String sql = "UPDATE produto SET " +
                "nome = ?, codigo = ?, categoria = ?, quantidade = ?, preco = ?, " +
                "fornecedor = ?, cep = ?, logradouro = ?, bairro = ?, cidade = ?, " +
                "estado = ?, descricao = ? " +
                "WHERE id = ?";

        try (Connection conexaoBanco = conexao.conectar();
             PreparedStatement comando = conexaoBanco.prepareStatement(sql)) {

            comando.setString(1, produto.getNome());
            comando.setString(2, produto.getCodigo());
            comando.setString(3, produto.getCategoria());
            comando.setInt(4, produto.getQuantidade());
            comando.setDouble(5, produto.getPreco());
            comando.setString(6, produto.getFornecedor());
            comando.setString(7, produto.getCep());
            comando.setString(8, produto.getLogradouro());
            comando.setString(9, produto.getBairro());
            comando.setString(10, produto.getCidade());
            comando.setString(11, produto.getEstado());
            comando.setString(12, produto.getDescricao());
            comando.setInt(13, produto.getId());

            comando.executeUpdate();
        }
    }

    // DELETE - Excluir produto
    public void excluir(int id) throws SQLException {

        String sql = "DELETE FROM produto WHERE id = ?";

        try (Connection conexaoBanco = conexao.conectar();
             PreparedStatement comando = conexaoBanco.prepareStatement(sql)) {

            comando.setInt(1, id);

            comando.executeUpdate();
        }
    }
}