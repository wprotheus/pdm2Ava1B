package com.wprotheus.wellingtonbatistaparreiraneto.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.wprotheus.wellingtonbatistaparreiraneto.R;
import com.wprotheus.wellingtonbatistaparreiraneto.databinding.FragmentFinalizacaoBinding;
import com.wprotheus.wellingtonbatistaparreiraneto.viewmodel.DadosViewModel;
import com.wprotheus.wellingtonbatistaparreiraneto.viewmodel.TratarDados;

public class FinalizacaoFragment extends Fragment {
    private FragmentFinalizacaoBinding finalizacaoBinding;
    private TratarDados tDados = new TratarDados();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DadosViewModel viewModel = new ViewModelProvider(requireActivity()).get(DadosViewModel.class);
        finalizacaoBinding = FragmentFinalizacaoBinding.inflate(inflater, container, false);
        View view = finalizacaoBinding.getRoot();

        viewModel.getmLiveNomeUser().observe(getViewLifecycleOwner(), s -> {
            viewModel.getmLiveMixed().observe(getViewLifecycleOwner(), stringList -> {
                if (!s.isEmpty()) {
                    finalizacaoBinding.tvUserNome.setText(s);
                    finalizacaoBinding.lavFinish.setAnimation(R.raw.finish);
                    int[] qtdEvenOdd = new int[2];
                    qtdEvenOdd = tDados.quantidadeParImpar(stringList);
                    finalizacaoBinding.tvQtdPar.setText(String.valueOf(qtdEvenOdd[0]));
                    finalizacaoBinding.tvQtdImpar.setText(String.valueOf(qtdEvenOdd[1]));
                }
            });
        });
        return view;
    }
}