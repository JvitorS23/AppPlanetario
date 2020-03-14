package com.example.appplanetario;

import java.io.Serializable;

public class Galaxia extends Entidade implements Serializable {

    public int qtd_sistemas ;
    public float dist_terra;

    public Galaxia(int id, String nome) {
        super(id, nome);
    }

    public Galaxia(int id, String nome, int qtd_sistemas, float dist_terra) {
        super(id, nome);
        this.qtd_sistemas = qtd_sistemas;
        this.dist_terra = dist_terra;
    }

    public int getQtd_sistemas() {
        return qtd_sistemas;
    }

    public void setQtd_sistemas(int qtd_sistemas) {
        this.qtd_sistemas = qtd_sistemas;
    }

    public float getDist_terra() {
        return dist_terra;
    }

    public void setDist_terra(float dist_terra) {
        this.dist_terra = dist_terra;
    }
}
