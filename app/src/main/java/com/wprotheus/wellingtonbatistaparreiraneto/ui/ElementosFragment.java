package com.wprotheus.wellingtonbatistaparreiraneto.ui;

import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.wprotheus.wellingtonbatistaparreiraneto.R;
import com.wprotheus.wellingtonbatistaparreiraneto.databinding.FragmentElementosBinding;
import com.wprotheus.wellingtonbatistaparreiraneto.viewmodel.DadosViewModel;
import com.wprotheus.wellingtonbatistaparreiraneto.viewmodel.TratarDados;

import java.util.ArrayList;
import java.util.List;

public class ElementosFragment extends Fragment {
    private FragmentElementosBinding elementosBinding;
    private ArrayAdapter<String> adapter;
    private List<String> combinacao = new ArrayList<>();
    private TratarDados tDados = new TratarDados();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DadosViewModel viewModel = new ViewModelProvider(requireActivity()).get(DadosViewModel.class);
        elementosBinding = FragmentElementosBinding.inflate(inflater, container, false);
        View view = elementosBinding.getRoot();
        elementosBinding.tietNome.setOnFocusChangeListener(this::focoTeclado);
        elementosBinding.tietValor.setOnFocusChangeListener(this::focoTeclado);
        elementosBinding.tietValor.setRawInputType(Configuration.KEYBOARD_12KEY);
        elementosBinding.btnGerar.requestFocus();
        int multi = 0;

        elementosBinding.btnGerar.setOnClickListener(v -> {
            if (validarCampos(multi) > 0) {
                int mixed = validarCampos(multi);
                if (mixed != 0) {
                    viewModel.getmLiveMixed().observe(getViewLifecycleOwner(), stringList ->
                    {
                        combinacao = tDados.pegarParteTerceiroVetor(stringList, mixed);
                        viewModel.getmLiveNomeUser().postValue(elementosBinding.tietNome.getText().toString());
                        elementosBinding.tietNome.setText("");
                        elementosBinding.tietValor.setText("");
                        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, combinacao);
                        elementosBinding.lvDadosElementos.setAdapter(adapter);
                    });
                }
            } else
                validarCampos(multi);
        });
        return view;
    }

    @SuppressLint("RestrictedApi")
    private void focoTeclado(View v, boolean hasFocus) {
        if (!hasFocus) hideKeyboard(v);
    }

    private int validarCampos(int multi) {
        while (multi <= 0) {
            if (!elementosBinding.tietNome.getText().toString().isEmpty() &&
                    !elementosBinding.tietValor.getText().toString().isEmpty() &&
                    Integer.parseInt(String.valueOf((elementosBinding.tietValor).getText())) >= 1 &&
                    Integer.parseInt(String.valueOf((elementosBinding.tietValor).getText())) <= 40) {
                return Integer.parseInt(String.valueOf((elementosBinding.tietValor).getText()));
            } else if (elementosBinding.tietNome.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), R.string.valor_non, Toast.LENGTH_SHORT).show();
                return 0;
            } else if (elementosBinding.tietValor.getText().toString().isEmpty() ||
                    Integer.parseInt(String.valueOf((elementosBinding.tietValor).getText())) > 40 ||
                    Integer.parseInt(String.valueOf((elementosBinding.tietValor).getText())) <= 0) {
                Toast.makeText(getActivity(), R.string.orientacao, Toast.LENGTH_SHORT).show();
                return 0;
            }
        }
        return 0;
    }
}