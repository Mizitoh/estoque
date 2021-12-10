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
            JOptionPane.showMessageDialog(null, "nao foi possivel executar o comando sql, "
                    + sqlex + ", o sql passado foi " + sql);
        }
        return null;
    }
    
    public List<ModelUsuario> listaUsuarioDAO() throws SQLException{
        List<ModelUsuario> listaUsuario = new ArrayList<>();
        ModelUsuario modelUsuario = new ModelUsuario();
        pegaConexao();
        ResultSet resultado = null;
        PreparedStatement stmt = null;
        
        String sql = "Select usuarios.id, usuarios.nome, usuarios.login from usuarios";
        resultado = ExecuteSQL(sql);
        try {
            while (resultado.next()) {
                modelUsuario = new ModelUsuario();
                modelUsuario.setId(resultado.getInt("usuarios.id"));
                modelUsuario.setNome(resultado.getString("usuarios.nome"));
                modelUsuario.setLogin(resultado.getString("usuarios.login"));
                listaUsuario.add(modelUsuario);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar" + erro);
        }
        fecha();
        return listaUsuario;
    }
}
