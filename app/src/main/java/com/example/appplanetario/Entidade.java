package com.example.appplanetario;

import android.os.AsyncTask;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Entidade implements Serializable {
    public int id;
    public String nome;

    @Override
    public String toString() {
        return "Entidade{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }

    public Entidade(int id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setNome(String nome){
        this.nome = nome ;
    }

    public int getId(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

}
