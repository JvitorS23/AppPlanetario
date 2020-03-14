package com.example.appplanetario;

import android.os.AsyncTask;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Entidade implements Serializable {
    public int id;
    public String nome;

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

    public static class AcessoBanco  extends AsyncTask<Void, Void, Void> {

        public static Connection con = null;

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                /** Pasando o nome do Driver do PostgreSQL */
                Class.forName("com.postgresql.Driver");

                /** Obtendo a conexao com o banco de dados*/
                this.con = DriverManager.getConnection("jdbc:postgresql://db-server.cl0sgknwftbr.us-east-1.rds.amazonaws.com:5432/VozCarbo", "postgres", "rabada123");

                /** Retorna um erro caso nao encontre o driver, ou alguma informacao sobre o mesmo esteja errada */
            } catch (ClassNotFoundException cnfe) {
                System.out.println("Erro ao conectar o driver");
                cnfe.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
