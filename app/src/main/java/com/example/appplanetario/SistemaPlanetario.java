package com.example.appplanetario;

import java.io.Serializable;

public class SistemaPlanetario extends Entidade implements Serializable {

    public int qtde_planetas, qtde_estrelas, idade;


    public SistemaPlanetario(int id, String nome, int qtde_planetas, int qtde_estrelas, int idade) {
        super(id, nome);
        this.qtde_planetas = qtde_planetas;
        this.qtde_estrelas = qtde_estrelas;
        this.idade = idade;
    }

    public int getQtde_planetas() {
        return qtde_planetas;
    }

    public void setQtde_planetas(int qtde_planetas) {
        this.qtde_planetas = qtde_planetas;
    }

    public int getQtde_estrelas() {
        return qtde_estrelas;
    }

    public void setQtde_estrelas(int qtde_estrelas) {
        this.qtde_estrelas = qtde_estrelas;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}
