package com.hospital.util;

import java.io.*;

public class Persistencia {

    public static void salvar(Object obj, String nomeArquivo) {
        try {
            FileOutputStream arquivoGeral = new FileOutputStream(nomeArquivo);
            ObjectOutputStream objetoGravador = new ObjectOutputStream(arquivoGeral);
            objetoGravador.writeObject(obj);
            objetoGravador.flush();
            objetoGravador.close();
            arquivoGeral.close();
        } catch (Exception e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    public static Object carregar(String nomeArquivo) {
        try {
            File arquivo = new File(nomeArquivo);
            if (!arquivo.exists()) {
                return null;
            }
            FileInputStream arquivoLeitura = new FileInputStream(nomeArquivo);
            ObjectInputStream objetoLeitor = new ObjectInputStream(arquivoLeitura);
            Object obj = objetoLeitor.readObject();
            objetoLeitor.close();
            arquivoLeitura.close();
            return obj;
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
            return null;
        }
    }
}