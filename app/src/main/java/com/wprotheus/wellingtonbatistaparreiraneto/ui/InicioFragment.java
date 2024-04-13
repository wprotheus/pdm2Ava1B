package com.wprotheus.wellingtonbatistaparreiraneto.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.wprotheus.wellingtonbatistaparreiraneto.databinding.FragmentInicioBinding;
import com.wprotheus.wellingtonbatistaparreiraneto.viewmodel.DadosViewModel;
import com.wprotheus.wellingtonbatistaparreiraneto.viewmodel.TratarDados;

import java.util.ArrayList;
import java.util.List;

public class InicioFragment extends Fragment {
    private FragmentInicioBinding inicioBinding;
    private List<String> lMixed = new ArrayList<>();
    private TratarDados tDados = new TratarDados();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DadosViewModel viewModel = new ViewModelProvider(requireActivity()).get(DadosViewModel.class);
        inicioBinding = FragmentInicioBinding.inflate(inflater, container, false);
        View view = inicioBinding.getRoot();

        viewModel.getmLiveOriginal().observe(getViewLifecycleOwner(), stone -> {
            lMixed = tDados.terceiroVetor(stone);
            viewModel.getmLiveMixed().postValue(lMixed);
            inicioBinding.tvPedras.setText(stone.getPedras().toString().replaceAll("[(\\[),(\\])]", ""));
            inicioBinding.tvNumeros.setText(stone.getNumeros().toString().replaceAll("[(\\[),(\\])]", ""));
            inicioBinding.tvResultado.setText(lMixed.toString().replaceAll("[(\\[),(\\])]", ""));
        });
        return view;
    }
}