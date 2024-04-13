package com.wprotheus.wellingtonbatistaparreiraneto.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.wprotheus.wellingtonbatistaparreiraneto.databinding.FragmentSeparacaoBinding;
import com.wprotheus.wellingtonbatistaparreiraneto.viewmodel.DadosViewModel;
import com.wprotheus.wellingtonbatistaparreiraneto.viewmodel.TratarDados;

import java.util.ArrayList;
import java.util.List;

public class SeparacaoFragment extends Fragment {
    private FragmentSeparacaoBinding separacaoBinding;
    private List<String> escolhidos = new ArrayList<>();
    private List<String> mostrar = new ArrayList<>();
    private TratarDados tDados = new TratarDados();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DadosViewModel viewModel = new ViewModelProvider(requireActivity()).get(DadosViewModel.class);
        separacaoBinding = FragmentSeparacaoBinding.inflate(inflater, container, false);
        View view = separacaoBinding.getRoot();

        separacaoBinding.btnSistema.setOnClickListener(v -> {
            viewModel.getmLiveMixed().observe(getViewLifecycleOwner(), stringList -> {
                escolhidos = tDados.cincoEscolhidos(stringList);
                mostrar = tDados.prepararValoresFinalizacao(escolhidos);
                separacaoBinding.tvValoresOriginais.setText(escolhidos.toString().replaceAll(",", "\n").replaceAll("[(\\[),(\\])]", ""));
                separacaoBinding.tvResultadoValor.setText(mostrar.toString().replaceAll("[(\\[),(\\])]", ""));
            });
        });
        return view;
    }
}