package com.example.appplanetario;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrbitaBackground extends AsyncTask<String, Void, String> {

    public interface OnOrbitaCompletedListener{
        void onOrbitaCompleted(String result, ResultSet resultado);
    }

    public static Connection con;//conexão com o banco
    public Context mContext;//activity que chama
    public ProgressDialog mDialog;//load
    public String opcao;
    public ResultSet consulta;

    private OnOrbitaCompletedListener onOrbitaCompletedListener;

    public void setOnOrbitaCompletedListener(OnOrbitaCompletedListener onOrbitaCompletedListener){
        this.onOrbitaCompletedListener = onOrbitaCompletedListener;
    }

    public OrbitaBackground(Context context, String opcao){
        this.mContext = context;
        this.opcao = opcao;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Realizando relacionamento...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    @Override
    protected String doInBackground(String... result) {
        boolean conectou = false;
        String sql = "";
        conectou = connect();

        if(!conectou)
            return "ERRO-CONEXAO";

        switch (opcao){
            case "Adicionar":
                sql = "INSERT INTO astros.orbita VALUES (?, ?, ?)";
                break;
            case "Remover":
                sql = "DELETE FROM astros.orbita WHERE id_planeta = ? AND id_sn = ? AND id_estrela = ?";
                break;
            case "Consultar-Orbita-Planeta":
                sql = "SELECT DISTINCT orb.id_sn, sn.nome_sn, sn.comp_sn, sn.tam_sn, sn.peso_sn " +
                      "FROM astros.orbita orb JOIN astros.planeta pl " +
                          "ON(orb.id_planeta = pl.id_planeta) JOIN astros.satelite_natural sn " +
                          "ON(orb.id_sn = sn.id_sn) " +
                      "WHERE orb.id_planeta = ?";
                break;
            case "Consultar-Orbita-Estrela":
                sql = "SELECT DISTINCT orb.id_planeta, pl.nome_planeta, pl.tam_planeta, pl.peso_planeta, pl.vel_rotacao, pl.gravidade_planeta, pl.comp_planeta " +
                      "FROM astros.orbita orb JOIN astros.estrela es " +
                        "ON(orb.id_estrela = es.id_estrela) JOIN astros.planeta pl " +
                        "ON(orb.id_planeta = pl.id_planeta) " +
                      "WHERE orb.id_estrela = ?";
                break;
        }

        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("EXCEPTION1");
            return "ERRO-CONEXAO";
        }

        try {
            switch (opcao){
                case "Adicionar":
                    ps.setInt(1, Integer.parseInt(result[0]));//planeta
                    ps.setInt(2, Integer.parseInt(result[1]));//satélite natural
                    ps.setInt(3, Integer.parseInt(result[2]));//estrela
                    break;
                case "Remover":
                    ps.setInt(1, Integer.parseInt(result[0]));//planeta
                    ps.setInt(2, Integer.parseInt(result[1]));//satélite natural
                    ps.setInt(3, Integer.parseInt(result[2]));//estrela
                    break;
                case "Consultar-Orbita-Planeta":
                    ps.setInt(1, Integer.parseInt(result[0]));//id do planeta que quero saber quais os sn que o orbita
                    break;
                case "Consultar-Orbita-Estrela":
                    ps.setInt(1, Integer.parseInt(result[0]));//id da estrela que quero saber quais pl que a orbita;
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("EXCEPTION2");
            switch (opcao){
                case "Adicionar":
                    return "ERRO-INSERCAO";
                case "Remover":
                    return "ERRO-REMOCAO";
                case "Consultar-Orbita-Planeta":
                    return "ERRO-CONSULTA";
                case "Consultar-Orbita-Estrela":
                    return "ERRO-CONSULTA";
            }

        }


        String resultado = "OK";
        try{
            switch (opcao){
                case "Adicionar":
                    this.consulta = null;
                    ps.execute();
                    break;
                case "Remover":
                    this.consulta = null;
                    ps.execute();
                    break;
                case "Consultar-Orbita-Planeta":
                    this.consulta = ps.executeQuery();
                    break;
                case "Consultar-Orbita-Estrela":
                    this.consulta = ps.executeQuery();
                    break;
            }
        }catch (SQLException e){
            System.out.println("EXCEPTION3");
            e.printStackTrace();
            switch (opcao){
                case "Adicionar":
                    return "ERRO-INSERCAO";
                case "Remover":
                    return "ERRO-REMOCAO";
                case "Consultar-Orbita-Planeta":
                    return "ERRO-CONSULTA";
                case "Consultar-Orbita-Estrela":
                    return "ERRO-CONSULTA";
            }
        }

        try {
            if(this.con!=null){
                this.con.close();
            }
        } catch (SQLException e) {
            System.out.println("EXCEPTION4");
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    protected void onPostExecute(String str){
        super.onPostExecute(str);
        mDialog.dismiss();
        if(onOrbitaCompletedListener != null){
            //Chama o listener passando a string
            onOrbitaCompletedListener.onOrbitaCompleted(str, this.consulta);
        }
    }

    protected boolean connect() {

        try {
            /** Pasando o nome do Driver do PostgreSQL */
            Class.forName("org.postgresql.Driver");

            /** Obtendo a conexao com o banco de dados*/
            this.con = DriverManager.getConnection("jdbc:postgresql://ec2-52-202-185-87.compute-1.amazonaws.com:5432/d3kpi243df7o13?sslmode=require", "zgashvtuvobqho", "c66b10ef01f1847512fee89609de964b73142d8f811661916ed17ad87df6868d");

            /** Retorna um erro caso nao encontre o driver, ou alguma informacao sobre o mesmo esteja errada */
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Erro ao conectar o driver");
            System.out.println("EXCEPTION5");
            cnfe.printStackTrace();
        } catch (SQLException e) {
            if(this.con==null)
                return false;
            System.out.println("EXCEPTION6");
            e.printStackTrace();
        }
        return true;
    }
}
