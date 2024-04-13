package com.wprotheus.wellingtonbatistaparreiraneto.viewmodel;

import com.google.gson.Gson;
import com.wprotheus.wellingtonbatistaparreiraneto.model.Stone;
import com.wprotheus.wellingtonbatistaparreiraneto.util.Auxiliar;
import com.wprotheus.wellingtonbatistaparreiraneto.util.Conexao;

import java.io.InputStream;

public class DataSetJSON {
    private final String URL = "https://my-json-server.typicode.com/wprotheus/pdm2Ava1B/db";

    public Stone baixarJSON() {
        try {
            Conexao conexao = new Conexao();
            InputStream inputStream = conexao.obterRespostaHTTP(URL);
            Auxiliar auxilia = new Auxiliar();
            String textoFromJson = auxilia.converter(inputStream);

            if (!textoFromJson.isEmpty())
                return new Gson().fromJson(textoFromJson, Stone.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}