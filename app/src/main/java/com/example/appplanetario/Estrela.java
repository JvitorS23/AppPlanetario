package com.example.appplanetario;

public class Estrela extends Entidade  {

    public int idade;
    public float dist_terra;
    public float gravidade;

    public Estrela(int id, String nome) {
        super(id, nome);
    }

    public Estrela(int id, String nome, int idade, float dist_terra, float gravidade) {
        super(id, nome);
        this.idade = idade;
        this.dist_terra = dist_terra;
        this.gravidade = gravidade;


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
}
