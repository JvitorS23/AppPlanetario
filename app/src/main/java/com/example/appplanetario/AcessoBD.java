package com.example.appplanetario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AcessoBD extends Thread {
    public Connection con;


    public AcessoBD() {

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

    public void run(String op, String param) throws SQLException {
        if(op.equals("remover")){
            removeAlimento(param);
        }


    }

    public void removeAlimento(String nome) throws SQLException{
        //String com o comando SQL, cada ? é um parâmetro que deve ser especificado em seguida
        String sql = "DELETE FROM voz.alimento WHERE nome = ?;";

        //esse método passa o sql ao banco mas n executa
        PreparedStatement ps = this.con.prepareStatement(sql);

        //Especifica aq os parâmetros da query na sequência das ?
        ps.setString(1, nome);

        //executa a query
        ps.execute();
    }



}
