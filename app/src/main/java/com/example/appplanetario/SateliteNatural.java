package com.example.appplanetario;

import java.io.Serializable;
import java.util.ArrayList;

public class SateliteNatural extends  Entidade implements Serializable {

    public float tamanho;
    public float massa;
    public ArrayList<String> composicao;


    public SateliteNatural(int id, String nome) {
        super(id, nome);
    }

    public SateliteNatural(int id, String nome, float tamanho, float massa, ArrayList<String> composicao) {
        super(id, nome);
        this.tamanho = tamanho;
        this.massa = massa;
        this.composicao = composicao;
    }

    public float getTamanho() {
        return tamanho;
    }

    public void setTamanho(float tamanho) {
        this.tamanho = tamanho;
    }

    public float getMassa() {
        return massa;
    }

    public void setMassa(float massa) {
        this.massa = massa;
    }

    public ArrayList<String> getComposicao() {
        return composicao;
    }

    public void setComposicao(ArrayList<String> composicao) {
        this.composicao = composicao;
    }
}
