package com.example.appplanetario;

import java.io.Serializable;

public class Estrela extends Entidade implements Serializable {

    public int idade;
    public float dist_terra;
    public float gravidade;
    public float tamanho;
    public float massa;
    public String tipo_estrela;
    public boolean morreu;

    public Estrela(int id, String nome, int idade, float dist_terra, float gravidade, float tamanho, float massa, String tipo_estrela) {
        super(id, nome);
        this.idade = idade;
        this.dist_terra = dist_terra;
        this.gravidade = gravidade;
        this.tamanho = tamanho;
        this.massa = massa;
        this.tipo_estrela = tipo_estrela;
    }

    public Estrela(int id, String nome, int idade, float dist_terra, float gravidade, float tamanho, String tipo_estrela, boolean morreu) {
        super(id, nome);
        this.idade = idade;
        this.dist_terra = dist_terra;
        this.gravidade = gravidade;
        this.tamanho = tamanho;
        this.tipo_estrela = tipo_estrela;
        this.morreu = morreu;
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

    public float getMassa() {
        return massa;
    }

    public void setMassa(float massa) {
        this.massa = massa;
    }

    public String getTipo_estrela() {
        return tipo_estrela;
    }

    public void setTipo_estrela(String tipo_estrela) {
        this.tipo_estrela = tipo_estrela;
    }

    public boolean isMorreu() {
        return morreu;
    }

    public void setMorreu(boolean morreu) {
        this.morreu = morreu;
    }
}
