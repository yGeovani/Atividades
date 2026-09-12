package com.stockmanager.util;

import org.mindrot.jbcrypt.BCrypt;

public class seguranca {

    public static String gerarHash(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    public static boolean verificarSenha(String senha, String hash) {
        return BCrypt.checkpw(senha, hash);
    }
}