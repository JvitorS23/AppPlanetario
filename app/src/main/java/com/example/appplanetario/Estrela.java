package com.example.appplanetario;

import java.io.Serializable;

public class Estrela extends Entidade implements Serializable {

    public int idade;
    public float dist_terra;
    public float gravidade;
    public float tamanho;

    public String tipo_estrela;
    public boolean morte;

    public Estrela(int id, String nome, int idade, float dist_terra, float gravidade, float tamanho, String tipo_estrela) {
        super(id, nome);
        this.idade = idade;
        this.dist_terra = dist_terra;
        this.gravidade = gravidade;
        this.tamanho = tamanho;
        this.tipo_estrela = tipo_estrela;
    }

    public boolean isMorte() {
        return morte;
    }

    public void setMorte(boolean morte) {
        this.morte = morte;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public float getDist_terra() {
        return dist_terra;
    }

    public void setDist_terra(float dist_terra) {
        this.dist_terra = dist_terra;
    }

    public float getGravidade() {
        return gravidade;
    }

    public void setGravidade(float gravidade) {
        this.gravidade = gravidade;
    }

    public float getTamanho() {
        return tamanho;
    }

    public void setTamanho(float tamanho) {
        this.tamanho = tamanho;
    }


    public String getTipo_estrela() {
        return tipo_estrela;
    }

    public void setTipo_estrela(String tipo_estrela) {
        this.tipo_estrela = tipo_estrela;
    }
}
