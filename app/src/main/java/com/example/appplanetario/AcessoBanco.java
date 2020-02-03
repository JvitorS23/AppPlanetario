package com.example.appplanetario;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AcessoBanco extends AsyncTask<String, Void, ResultSet> {
    public static Connection con;


    public AcessoBanco() {
        super();
    }

    @Override
    protected ResultSet doInBackground(String ... nome) {

        connect();
        String sql = "SELECT * FROM voz.alimento WHERE nome = ?";

        //esse método passa o sql ao banco mas n executa
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(nome[0]);


        //Especifica aq os parâmetros da query na sequência das ?
        try {
            ps.setString(1, nome[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs = null;
        try {
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    protected void onPostExecute(ResultSet rs) {
        try {
            if(rs.next()){
                System.out.println("Resultado: "+rs.getString("nome"));

            }else{
                System.out.println("Alimento não encontrado!!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    protected void connect() {

        try {
            /** Pasando o nome do Driver do PostgreSQL */
            Class.forName("org.postgresql.Driver");

            /** Obtendo a conexao com o banco de dados*/
            this.con = DriverManager.getConnection("jdbc:postgresql://db-server.cl0sgknwftbr.us-east-1.rds.amazonaws.com:5432/VozCarbo", "postgres", "rabada123");

            /** Retorna um erro caso nao encontre o driver, ou alguma informacao sobre o mesmo esteja errada */
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Erro ao conectar o driver");
            cnfe.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
