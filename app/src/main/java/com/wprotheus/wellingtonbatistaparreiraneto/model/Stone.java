package com.wprotheus.wellingtonbatistaparreiraneto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Stone implements Serializable {
    private List<String> pedras = new ArrayList<>();
    private List<Integer> numeros = new ArrayList<>();

    @Override
    public String toString() {
        return new StringBuilder("Stone {")
                .append("Pedras: ").append(getPedras())
                .append(", Números: ").append(getNumeros())
                .append('}').toString();
    }
}