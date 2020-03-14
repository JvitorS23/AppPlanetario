package com.example.appplanetario;

import java.io.Serializable;
import java.util.ArrayList;

public class Planeta extends Entidade implements Serializable {
   public float tamanho;
   public float massa;
   public float gravidade;
   public ArrayList<String> composicao;


    public Planeta(int id, String nome) {
        super(id, nome);
    }

    public Planeta(int id, String nome, float tam_planeta, float massa_planeta, float gravidade_planeta,
                   ArrayList<String> comp_planeta) {
        super(id, nome);
        this.tamanho = tam_planeta;
        this.massa = massa_planeta;
        this.gravidade = gravidade_planeta;
        this.composicao = comp_planeta;

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

    public float getGravidade() {
        return gravidade;
    }

    public void setGravidade(float gravidade) {
        this.gravidade = gravidade;
    }

    public ArrayList<String> getComposicao() {
        return composicao;
    }

    public void setComposicao(ArrayList<String> composicao) {
        this.composicao = composicao;
    }




}
