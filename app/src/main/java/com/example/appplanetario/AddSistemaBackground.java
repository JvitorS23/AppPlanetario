package com.example.appplanetario;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import com.example.appplanetario.SistemaPlanetario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddSistemaBackground extends AsyncTask<SistemaPlanetario, Void, String>{

    public static Connection con;//conexão com o banco
    public Context mContext;//activity que chama
    public ProgressDialog mDialog;//load
    private AddSistemaBackground.OnAddSistemaCompletedListener onAddSistemaCompletedListener;

    public AddSistemaBackground(Context mContext) {
        this.mContext = mContext;
    }

    public interface OnAddSistemaCompletedListener{
        void onAddSistemaCompleted(String result);
    }

    public void setOnAddSistemaCompletedListener(OnAddSistemaCompletedListener onAddSistemaCompletedListener) {
        this.onAddSistemaCompletedListener = onAddSistemaCompletedListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Adicionando Sistema Planetário...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    @Override
    protected String doInBackground(SistemaPlanetario... sistema) {
        boolean conectou = false;
        conectou = connect();

        if(!conectou)
            return "ERRO-CONEXAO";


        String sql = "SELECT id_galaxia FROM astros.galaxia WHERE id_galaxia = ?";

        //esse método passa o sql ao banco mas n executa
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERRO-CONEXAO";
        }

        //Especifica aq os parâmetros da query na sequência das ?
        try {
            ps.setInt(1, sistema[0].getId_galaxia());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String result = "";

        try {
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                result = "OK";

            }else{
                return "ID_galaxia";            }

        } catch (SQLException e) {
            result = "ERRO-CONSULTA";
            e.printStackTrace();
        }

        sql = "INSERT INTO astros.sistema_planetario VALUES(?, ?, ?, ?, ?, ?)";

        //esse método passa o sql ao banco mas n executa
        ps = null;
        try {
            ps = con.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERRO-CONEXAO";
        }

        //Especifica aq os parâmetros da query na sequência das ?
        try {
            ps.setInt(1, sistema[0].getId());
            ps.setInt(2, sistema[0].getId_galaxia());
            ps.setInt(3, sistema[0].getQtde_planetas());
            ps.setInt(4, sistema[0].getQtde_estrelas());
            ps.setInt(5, sistema[0].getIdade());
            ps.setString(6, sistema[0].getNome());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        result = "OK";
        try {
            ps.execute();
        } catch (SQLException e) {
            result = "ERRO-INSERCAO";
            e.printStackTrace();
        }
        try {
            if(this.con!=null){
                this.con.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    protected void onPostExecute(String str){
        super.onPostExecute(str);

        mDialog.dismiss();
        if(onAddSistemaCompletedListener != null){
            //Chama o listener passando a string
            onAddSistemaCompletedListener.onAddSistemaCompleted(str);
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
            cnfe.printStackTrace();
        } catch (SQLException e) {
            if(this.con==null)
                return false;
            e.printStackTrace();
        }
        return true;
    }
}
