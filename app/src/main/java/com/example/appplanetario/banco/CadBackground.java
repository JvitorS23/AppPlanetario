package com.example.appplanetario.banco;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadBackground extends AsyncTask<String, Void, String> {

        public interface OnCadCompletedListener{
            void onCadCompleted(String result);
        }

        public static Connection con;
        public Context mContext;
        public ProgressDialog mDialog;

        private OnCadCompletedListener onCadCompletedListener;

        public void setOnCadCompletedListener(OnCadCompletedListener onCadCompletedListener){
            this.onCadCompletedListener = onCadCompletedListener;
        }

        public CadBackground(Context context) {
            super();
            this.mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mDialog = new ProgressDialog(mContext);
            mDialog.setMessage("Realizando cadastro...");
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        }

        @Override
        protected String doInBackground(String ... user) {
            boolean conectou = false;
            conectou = connect();

            if(!conectou)
                return "ERRO-CONEXAO";

            String sql = "INSERT INTO astros.usuario VALUES(?, md5(?))";

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
                ps.setString(1, user[0]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                ps.setString(2, user[1]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String result = "OK";
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
            if(onCadCompletedListener != null){
                //Chama o listener passando a string
                onCadCompletedListener.onCadCompleted(str);
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
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;


            } catch(NoClassDefFoundError e){
                e.printStackTrace();
                return false;

            }

            return true;

        }


}
