package com.stockmanager.controller;

import com.stockmanager.dao.produtoDAO;
import com.stockmanager.model.produto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/produtos")
public class produtoServlet extends HttpServlet {

    private produtoDAO dao;

    @Override
    public void init() {
        dao = new produtoDAO();
    }

    // ==============================
    // LISTAR PRODUTOS
    // ==============================

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String idParametro = request.getParameter("id");

            if (idParametro != null) {

                produto p = dao.buscarPorId(Integer.parseInt(idParametro));

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                if (p == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(
                            "{\"erro\":\"Produto não encontrado.\"}");
                    return;
                }

                String json = "{"
                        + "\"id\":" + p.getId() + ","
                        + "\"nome\":\"" + escaparJson(p.getNome()) + "\","
                        + "\"codigo\":\"" + escaparJson(p.getCodigo()) + "\","
                        + "\"categoria\":\"" + escaparJson(p.getCategoria()) + "\","
                        + "\"quantidade\":" + p.getQuantidade() + ","
                        + "\"preco\":" + p.getPreco() + ","
                        + "\"fornecedor\":\"" + escaparJson(p.getFornecedor()) + "\","
                        + "\"cep\":\"" + escaparJson(p.getCep()) + "\","
                        + "\"logradouro\":\"" + escaparJson(p.getLogradouro()) + "\","
                        + "\"bairro\":\"" + escaparJson(p.getBairro()) + "\","
                        + "\"cidade\":\"" + escaparJson(p.getCidade()) + "\","
                        + "\"estado\":\"" + escaparJson(p.getEstado()) + "\","
                        + "\"descricao\":\"" + escaparJson(p.getDescricao()) + "\""
                        + "}";

                response.getWriter().write(json);

                return;
            }

            List<produto> produtos = dao.listar();

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < produtos.size(); i++) {

                produto p = produtos.get(i);

                json.append("{")
                        .append("\"id\":").append(p.getId()).append(",")
                        .append("\"nome\":\"").append(escaparJson(p.getNome())).append("\",")
                        .append("\"codigo\":\"").append(escaparJson(p.getCodigo())).append("\",")
                        .append("\"categoria\":\"").append(escaparJson(p.getCategoria())).append("\",")
                        .append("\"quantidade\":").append(p.getQuantidade()).append(",")
                        .append("\"preco\":").append(p.getPreco()).append(",")
                        .append("\"fornecedor\":\"").append(escaparJson(p.getFornecedor())).append("\",")
                        .append("\"cep\":\"").append(escaparJson(p.getCep())).append("\",")
                        .append("\"logradouro\":\"").append(escaparJson(p.getLogradouro())).append("\",")
                        .append("\"bairro\":\"").append(escaparJson(p.getBairro())).append("\",")
                        .append("\"cidade\":\"").append(escaparJson(p.getCidade())).append("\",")
                        .append("\"estado\":\"").append(escaparJson(p.getEstado())).append("\",")
                        .append("\"descricao\":\"").append(escaparJson(p.getDescricao())).append("\"")
                        .append("}");

                if (i < produtos.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");

            response.getWriter().write(json.toString());

        } catch (SQLException e) {

            e.printStackTrace();

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            response.getWriter().write(
                    "{\"erro\":\"" + escaparJson(e.getMessage()) + "\"}");
        }
    }

    // ==============================
    // CADASTRAR PRODUTO
    // ==============================

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            request.setCharacterEncoding("UTF-8");

            produto p = criarProduto(request);

            dao.cadastrar(p);

            respostaJson(response,
                    HttpServletResponse.SC_CREATED,
                    "{\"mensagem\":\"Produto cadastrado com sucesso!\"}");

        } catch (SQLException | NumberFormatException e) {

            e.printStackTrace();

            respostaJson(response,
                    HttpServletResponse.SC_BAD_REQUEST,
                    "{\"erro\":\"Não foi possível cadastrar o produto.\"}");
        }
    }

    // ==============================
    // ATUALIZAR PRODUTO
    // ==============================

    @Override
    protected void doPut(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            request.setCharacterEncoding("UTF-8");

            int id = Integer.parseInt(request.getParameter("id"));

            produto p = criarProduto(request);
            p.setId(id);

            dao.atualizar(p);

            respostaJson(response,
                    HttpServletResponse.SC_OK,
                    "{\"mensagem\":\"Produto atualizado com sucesso!\"}");

        } catch (SQLException | NumberFormatException e) {

            e.printStackTrace();

            respostaJson(response,
                    HttpServletResponse.SC_BAD_REQUEST,
                    "{\"erro\":\"Não foi possível atualizar o produto.\"}");
        }
    }

    // ==============================
    // EXCLUIR PRODUTO
    // ==============================

    @Override
    protected void doDelete(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int id = Integer.parseInt(request.getParameter("id"));

            dao.excluir(id);

            respostaJson(response,
                    HttpServletResponse.SC_OK,
                    "{\"mensagem\":\"Produto excluído com sucesso!\"}");

        } catch (SQLException | NumberFormatException e) {

            e.printStackTrace();

            respostaJson(response,
                    HttpServletResponse.SC_BAD_REQUEST,
                    "{\"erro\":\"" + escaparJson(e.getMessage()) + "\"}");
        }
    }

    // ==============================
    // CRIAR OBJETO PRODUTO
    // ==============================

    private produto criarProduto(HttpServletRequest request) {

        produto p = new produto();

        p.setNome(request.getParameter("nome"));
        p.setCodigo(request.getParameter("codigo"));
        p.setCategoria(request.getParameter("categoria"));
        p.setQuantidade(Integer.parseInt(request.getParameter("quantidade")));

        p.setPreco(Double.parseDouble(
                request.getParameter("preco").replace(",", ".")));

        p.setFornecedor(request.getParameter("fornecedor"));
        p.setCep(request.getParameter("cep"));
        p.setLogradouro(request.getParameter("logradouro"));
        p.setBairro(request.getParameter("bairro"));
        p.setCidade(request.getParameter("cidade"));
        p.setEstado(request.getParameter("estado"));
        p.setDescricao(request.getParameter("descricao"));

        return p;
    }

    // ==============================
    // RESPOSTA JSON
    // ==============================

    private void respostaJson(HttpServletResponse response,
            int status,
            String json)
            throws IOException {

        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(json);
    }

    // ==============================
    // ESCAPAR JSON
    // ==============================

    private String escaparJson(String texto) {

        if (texto == null) {
            return "";
        }

        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}