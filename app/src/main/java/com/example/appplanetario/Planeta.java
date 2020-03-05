package com.example.appplanetario;

import java.io.Serializable;
import java.util.ArrayList;

public class Planeta extends Entidade implements Serializable {
   public float tamanho;
   public float peso;
   public float gravidade;
   public float vel_rotacao;
   public String[] composicao;


    public Planeta(int id, String nome) {
        super(id, nome);
    }

    public Planeta(int id, String nome, float tam_planeta, float peso_planeta, float gravidade_planeta,
                   float vel_rotacao, String[] comp_planeta) {
        super(id, nome);
        this.tamanho = tam_planeta;
        this.peso = peso_planeta;
        this.gravidade = gravidade_planeta;
        this.composicao = comp_planeta;
        this.vel_rotacao = vel_rotacao;


    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getVel_rotacao() {
        return vel_rotacao;
    }

    public void setVel_rotacao(float vel_rotacao) {
        this.vel_rotacao = vel_rotacao;
    }

    public float getTamanho() {
        return tamanho;
    }

    public void setTamanho(float tamanho) {
        this.tamanho = tamanho;
    }

    public float getGravidade() {
        return gravidade;
    }

    public void setGravidade(float gravidade) {
        this.gravidade = gravidade;
    }

    public String[] getComposicao() {
        return composicao;
    }

    public void setComposicao(String[] composicao) {
        this.composicao = composicao;
    }
}
