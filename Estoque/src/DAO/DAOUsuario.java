/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import conexoes.ConexaoSQLPostgres;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.ModelUsuario;

/**
 *
 * @author Usuario
 */
public class DAOUsuario extends ConexaoSQLPostgres {

    private Connection conexao;

    public int incluir(String sql, Object... atributos) {
        try {
            PreparedStatement stmt = pegaConexao().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            adicionarAtributos(stmt, atributos);

            if (stmt.executeUpdate() > 0) {
                ResultSet resultado = stmt.getGeneratedKeys();

                if (resultado.next()) {
                    return resultado.getInt(1);
                }
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
            //coloque uma mensagem que informe o erro e reestarta a tela de gravação
        }
    }

    private void adicionarAtributos(PreparedStatement stmt, Object[] atributos) throws SQLException {

        int indice = 1;
        for (Object atributo : atributos) {
            if (atributo instanceof String) {
                stmt.setString(indice, (String) atributo);
            } else if (atributo instanceof Integer) {
                stmt.setInt(indice, (Integer) atributo);
            } else if (atributo instanceof Date) {
                stmt.setDate(indice, (Date) atributo);
            }

            indice++;
        }
    }

    private Connection pegaConexao() throws SQLException {
        try {
            if (conexao != null && !conexao.isClosed()) {
                return conexao;
            }
        } catch (SQLException e) {

        }

        conexao = (Connection) ConexaoSQLPostgres.captaConexao();
        return conexao;
    }

    public void fecha() {
        try {
            pegaConexao().close();
        } catch (SQLException e) {
        } finally {
            conexao = null;
        }
    }

    public ResultSet ExecuteSQL(String sql) {
        try {
            PreparedStatement stmt = pegaConexao().prepareStatement(sql);
            ResultSet resultset = stmt.executeQuery();
            return resultset;
        } catch (SQLException sqlex) {
            //JOptionPane.showMessageDialog(null, "nao foi possivel executar o comando sql, "
            //        + sqlex + ", o sql passado foi " + sql);
        }
        return null;
    }
    
    public List<ModelUsuario> listaUsuarioDAO() throws SQLException{
        List<ModelUsuario> listaUsuario = new ArrayList<>();
        ModelUsuario modelUsuario = new ModelUsuario();
        pegaConexao();
        ResultSet resultado = null;
        PreparedStatement stmt = null;
        
        String sql = "Select usuarios.id, usuarios.nome, usuarios.login from usuarios order by id";
        resultado = ExecuteSQL(sql);
        try {
            while (resultado.next()) {
                modelUsuario = new ModelUsuario();
                modelUsuario.setId(resultado.getInt("id"));
                modelUsuario.setNome(resultado.getString("nome"));
                modelUsuario.setLogin(resultado.getString("login"));
                listaUsuario.add(modelUsuario);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar" + erro);
        }
        fecha();
        return listaUsuario;
    }
    
    public boolean excluirUsuario(int id) throws SQLException{
        ModelUsuario modelUsuario = new ModelUsuario();
        pegaConexao();
        ResultSet stmt = null;
        
        String sql = "delete from usuarios where usuarios.id = " + id;
        stmt = ExecuteSQL(sql);
        
        if (stmt != null) {
            try{
            stmt.close();
            } catch (SQLException ex){
                ex.getMessage();
                JOptionPane.showInputDialog("erro ao excluir usuario" + ex);
                System.out.println("DAO.DAOUsuario.excluirUsuario()");
            }
        }
        
        fecha();
        return true;
    }
    
    public boolean alterarUsuario(int id, String nome, String login) throws SQLException{
        ModelUsuario modelUsuario = new ModelUsuario();
        pegaConexao();
        ResultSet stmt = null;
        
        String sql = "update usuarios set nome = '"+ nome +"', login = '" + login +"' where id = " + id;
        stmt = ExecuteSQL(sql);
        
        
        if (stmt != null) {
            try{
            stmt.close();
            } catch (SQLException ex){
                ex.getMessage();
                JOptionPane.showInputDialog("erro ao alterar usuario" + ex);
                System.out.println("DAO.DAOUsuario.excluirUsuario()");
            }
        }        
        fecha();
        return true;
    }
    
    public boolean alterarUsuario(String login, String senha) throws SQLException{
        ModelUsuario modelUsuario;
        String sql = "Select usuarios.login, usuarios.senha from usuarios where usuarios.login = '" + login + "'"
                + " and usuarios.senha = '"+ senha +"'";
        pegaConexao();
        ResultSet resultado = null;
        PreparedStatement stmt = null;
        resultado = ExecuteSQL(sql);
        try{                        
            while (resultado.next()) {
                modelUsuario = new ModelUsuario();
                modelUsuario.setLogin(resultado.getString("login"));
                modelUsuario.setSenha(resultado.getString("senha"));
                return true;
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar" + erro);
            return false;
        }
        fecha();
        return false;
    }
}
