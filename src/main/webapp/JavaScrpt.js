// ========================================
// ELEMENTOS DO DOM
// ========================================

const formulario = document.getElementById("formulario-produto");

const nome = document.getElementById("nome");
const codigo = document.getElementById("codigo");
const categoria = document.getElementById("categoria");
const quantidade = document.getElementById("quantidade");
const preco = document.getElementById("preco");
const cep = document.getElementById("cep");

const logradouro = document.getElementById("logradouro");
const bairro = document.getElementById("bairro");
const cidade = document.getElementById("cidade");
const estado = document.getElementById("estado");

const mensagemFormulario =
    document.getElementById("mensagem-formulario");


// ========================================
// FUNÇÃO PARA MOSTRAR ERROS
// ========================================

function mostrarErro(campo, mensagem) {

    campo.classList.add("campo-invalido");

    const elementoErro =
        document.getElementById(`erro-${campo.id}`);

    if (elementoErro) {
        elementoErro.textContent = mensagem;
    }
}


// ========================================
// FUNÇÃO PARA LIMPAR ERROS
// ========================================

function limparErro(campo) {

    campo.classList.remove("campo-invalido");

    const elementoErro =
        document.getElementById(`erro-${campo.id}`);

    if (elementoErro) {
        elementoErro.textContent = "";
    }
}


// ========================================
// VALIDAÇÃO DO FORMULÁRIO
// ========================================

function validarFormulario() {

    let formularioValido = true;

    // Limpa mensagens anteriores
    document.querySelectorAll(".mensagem-erro").forEach(function (elemento) {
        elemento.textContent = "";
    });

    document.querySelectorAll(".campo-invalido").forEach(function (elemento) {
        elemento.classList.remove("campo-invalido");
    });


    // Nome
    if (nome.value.trim() === "") {

        mostrarErro(
            nome,
            "Informe o nome do produto."
        );

        formularioValido = false;
    }


    // Código
    if (codigo.value.trim() === "") {

        mostrarErro(
            codigo,
            "Informe o código do produto."
        );

        formularioValido = false;
    }


    // Categoria
    if (categoria.value === "") {

        mostrarErro(
            categoria,
            "Selecione uma categoria."
        );

        formularioValido = false;
    }


    // Quantidade
    if (
        quantidade.value === "" ||
        Number(quantidade.value) < 0
    ) {

        mostrarErro(
            quantidade,
            "Informe uma quantidade válida."
        );

        formularioValido = false;
    }


    // Preço
    if (
        preco.value === "" ||
        Number(preco.value) < 0
    ) {

        mostrarErro(
            preco,
            "Informe um preço válido."
        );

        formularioValido = false;
    }


    // CEP
    const cepNumeros = cep.value.replace(/\D/g, "");

    if (cepNumeros.length !== 8) {

        mostrarErro(
            cep,
            "Digite um CEP válido com 8 números."
        );

        formularioValido = false;
    }


    return formularioValido;
}


// ========================================
// BUSCA DE CEP
// ========================================

async function buscarCep() {

    const cepNumeros = cep.value.replace(/\D/g, "");

    // Só realiza a busca quando houver 8 números
    if (cepNumeros.length !== 8) {
        return;
    }

    limparErro(cep);

    // Mostra que a busca está acontecendo
    logradouro.value = "Consultando...";
    bairro.value = "";
    cidade.value = "";
    estado.value = "";

    try {

        const resposta = await fetch(
            `https://viacep.com.br/ws/${cepNumeros}/json/`
        );

        if (!resposta.ok) {
            throw new Error("Erro na comunicação com o servidor.");
        }

        const dados = await resposta.json();


        // CEP inexistente
        if (dados.erro) {

            mostrarErro(
                cep,
                "CEP não encontrado."
            );

            logradouro.value = "";
            bairro.value = "";
            cidade.value = "";
            estado.value = "";

            return;
        }


        // Preenchimento automático
        logradouro.value = dados.logradouro || "";
        bairro.value = dados.bairro || "";
        cidade.value = dados.localidade || "";
        estado.value = dados.uf || "";

    }

    catch (erro) {

        console.error(erro);

        mostrarErro(
            cep,
            "Não foi possível consultar o CEP."
        );

        logradouro.value = "";
        bairro.value = "";
        cidade.value = "";
        estado.value = "";
    }
}


// ========================================
// FORMATAÇÃO DO CEP
// ========================================

cep.addEventListener("input", function () {

    let valor = cep.value.replace(/\D/g, "");

    if (valor.length > 5) {
        valor =
            valor.substring(0, 5) +
            "-" +
            valor.substring(5, 8);
    }

    cep.value = valor;
});


// ========================================
// EVENTO DE BUSCA DO CEP
// ========================================

cep.addEventListener("blur", buscarCep);


// ========================================
// VALIDAÇÃO DURANTE A DIGITAÇÃO
// ========================================

nome.addEventListener("input", function () {

    if (nome.value.trim() !== "") {
        limparErro(nome);
    }

});


codigo.addEventListener("input", function () {

    if (codigo.value.trim() !== "") {
        limparErro(codigo);
    }

});


categoria.addEventListener("change", function () {

    if (categoria.value !== "") {
        limparErro(categoria);
    }

});


quantidade.addEventListener("input", function () {

    if (
        quantidade.value !== "" &&
        Number(quantidade.value) >= 0
    ) {
        limparErro(quantidade);
    }

});


preco.addEventListener("input", function () {

    if (
        preco.value !== "" &&
        Number(preco.value) >= 0
    ) {
        limparErro(preco);
    }

});


// ========================================
// ENVIO DO FORMULÁRIO
// ========================================

formulario.addEventListener("submit", async function (event) {

    event.preventDefault();

    // Validação
    console.log("=== JAVASCRIPT NOVO CARREGADO ===");
    const formularioValido = validarFormulario();

    if (!formularioValido) {

        mensagemFormulario.textContent =
            "Corrija os campos destacados antes de cadastrar.";

        mensagemFormulario.className =
            "mensagem-formulario erro";

        return;
    }

    const dados = new URLSearchParams();

    dados.append("nome", nome.value);
    dados.append("codigo", codigo.value);
    dados.append("categoria", categoria.value);
    dados.append("quantidade", quantidade.value);
    dados.append("preco", preco.value);
    dados.append("fornecedor", document.getElementById("fornecedor").value);
    dados.append("cep", cep.value);
    dados.append("logradouro", logradouro.value);
    dados.append("bairro", bairro.value);
    dados.append("cidade", cidade.value);
    dados.append("estado", estado.value);
    dados.append("descricao", document.getElementById("descricao").value);

    try {

        const resposta = await fetch("produtos", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: dados
        });

        const resultado = await resposta.json();

        if (resposta.ok) {

            mensagemFormulario.textContent =
                "Produto cadastrado com sucesso!";

            mensagemFormulario.className =
                "mensagem-formulario sucesso";

            alert("Produto cadastrado com sucesso!");

            formulario.reset();

            carregarProdutos();

        } else {

            mensagemFormulario.textContent =
                resultado.erro ||
                "Não foi possível cadastrar o produto.";

            mensagemFormulario.className =
                "mensagem-formulario erro";
        }

    } catch (erro) {

        console.error(erro);

        mensagemFormulario.textContent =
            "Erro ao conectar com o servidor.";

        mensagemFormulario.className =
            "mensagem-formulario erro";
    }
});


// ========================================
// CARREGAR PRODUTOS DO BANCO
// ========================================

async function carregarProdutos() {

    console.log("CARREGAR PRODUTOS FOI EXECUTADA");

    try {

        const resposta = await fetch("produtos");

        console.log("Status da resposta:", resposta.status);

        const produtos = await resposta.json();

        console.log("Produtos recebidos:", produtos);

        const tabela = document.getElementById("tabela-produtos");

        console.log("Tabela encontrada:", tabela);

        tabela.innerHTML = "";

        produtos.forEach(function (produto) {

            let status;
            let classeStatus;

            if (produto.quantidade === 0) {
                status = "Esgotado";
                classeStatus = "esgotado";
            }
            else if (produto.quantidade <= 5) {
                status = "Estoque baixo";
                classeStatus = "baixo";
            }
            else {
                status = "Disponível";
                classeStatus = "disponivel";
            }

            const linha = document.createElement("tr");

            linha.innerHTML = `
    <td>${produto.codigo}</td>
    <td>${produto.nome}</td>
    <td>${produto.categoria}</td>
    <td>${produto.quantidade}</td>
    <td>R$ ${Number(produto.preco).toFixed(2).replace(".", ",")}</td>
    <td>
        <span class="status ${classeStatus}">
            ${status}
        </span>
    </td>
    <td>
        <button type="button" onclick="editarProduto(${produto.id})">
            Editar
        </button>

        <button type="button" onclick="excluirProduto(${produto.id})">
            Excluir
        </button>
    </td>
`;

            tabela.appendChild(linha);
        });

    } catch (erro) {

        console.error("ERRO AO CARREGAR PRODUTOS:", erro);

    }
}
carregarProdutos();

// ========================================
// EXCLUIR PRODUTO
// ========================================

async function excluirProduto(id) {

    const confirmar = confirm(
        "Tem certeza que deseja excluir este produto?"
    );

    if (!confirmar) {
        return;
    }

    try {

        const resposta = await fetch(`produtos?id=${id}`, {
            method: "DELETE"
        });

        const resultado = await resposta.json();

        if (resposta.ok) {

            alert("Produto excluído com sucesso!");

            carregarProdutos();

        } else {

            alert(resultado.erro || "Erro ao excluir produto.");
        }

    } catch (erro) {

        console.error(erro);

        alert("Erro ao conectar com o servidor.");
    }
}


// ========================================
// EDITAR PRODUTO
// ========================================

async function editarProduto(id) {

    try {

        const resposta = await fetch(`produtos?id=${id}`);

        const produto = await resposta.json();

        if (!resposta.ok) {
            alert("Não foi possível carregar o produto.");
            return;
        }

        nome.value = produto.nome;
        codigo.value = produto.codigo;
        categoria.value = produto.categoria;
        quantidade.value = produto.quantidade;
        preco.value = produto.preco;
        document.getElementById("fornecedor").value =
            produto.fornecedor || "";

        cep.value = produto.cep || "";
        logradouro.value = produto.logradouro || "";
        bairro.value = produto.bairro || "";
        cidade.value = produto.cidade || "";
        estado.value = produto.estado || "";
        document.getElementById("descricao").value =
            produto.descricao || "";

        formulario.dataset.editando = id;

        alert("Produto carregado para edição.");

    } catch (erro) {

        console.error(erro);

        alert("Erro ao carregar produto.");
    }
}