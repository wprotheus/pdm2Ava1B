package com.wprotheus.wellingtonbatistaparreiraneto.viewmodel;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.wprotheus.wellingtonbatistaparreiraneto.model.Stone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TratarDados extends Fragment {
    private List<String> listaStone = new ArrayList<>();
    private DadosViewModel viewModel = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(DadosViewModel.class);
    }

    public List<String> terceiroVetor(Stone stone) {
        List<String> lMixed = new ArrayList<>();
        for (String s : stone.getPedras())
            for (Integer n : stone.getNumeros())
                lMixed.add(s + " " + n);
        return lMixed;
    }

    public List<String> pegarParteTerceiroVetor(List<String> stone, int multi) {
        List<String> combinacao = new ArrayList<>();
        for (int i = 0; i < multi; i++)
            combinacao.add(stone.get(i));
        return combinacao;
    }

    public List<String> cincoEscolhidos(List<String> stringList) {
        return new Random()
                .ints(0, stringList.size())
                .distinct()
                .limit(5)
                .mapToObj(stringList::get)
                .collect(Collectors.toList());
    }

    public List<String> prepararValoresFinalizacao(List<String> escolhidos) {
        List<String> resultado = new ArrayList<>();
        for (int z = 0; z < escolhidos.size(); z++) {
            String nome = escolhidos.get(z).replaceAll("[^a-zA-Z]", "");
            int valor = Integer.parseInt(escolhidos.get(z).replaceAll("[^0-9]", ""));
            if (nome.equals("Diamante"))
                resultado.add(nome + " " + (valor + 80) + '\n');
            if (nome.equals("Esmeralda"))
                resultado.add(nome + " " + (valor + 40) + '\n');
            if (nome.equals("Ametista"))
                resultado.add(nome + " " + (valor + 20) + '\n');
            if (nome.equals("Selenita"))
                resultado.add(nome + " " + (valor + 10) + '\n');
        }
        return resultado;
    }

    public int[] quantidadeParImpar(List<String> stringList) {
        int[] parImpar = new int[2];
        for (int z = 0; z < stringList.size(); z++) {
            int valor = Integer.parseInt(stringList.get(z).replaceAll("[^0-9]", ""));
            if (valor % 2 == 0)
                parImpar[0]++;
            else
                parImpar[1]++;
        }
        return parImpar;
    }
}