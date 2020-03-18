package com.example.appplanetario;

import java.io.Serializable;
import java.util.ArrayList;

public class SateliteNatural extends  Entidade implements Serializable {

    public float tamanho;
    public float peso;
    public String[] composicao;


    public SateliteNatural(int id, String nome) {
        super(id, nome);
    }

    public SateliteNatural(int id, String nome, float tamanho, float peso, String[] composicao) {
        super(id, nome);
        this.tamanho = tamanho;
        this.peso = peso;
        this.composicao = composicao;
    }

    public String[] getComposicao() {
        return composicao;
    }

    public void setComposicao(String[] composicao) {
        this.composicao = composicao;
    }

    public float getTamanho() {
        return tamanho;
    }

    public void setTamanho(float tamanho) {
        this.tamanho = tamanho;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return  "ID = " + id +
                "\nNome = " + nome;
    }
}
