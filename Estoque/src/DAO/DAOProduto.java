package DAO;

import model.ModelProduto;
import conexoes.ConexaoSQLPostgres;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class DAOProduto extends ConexaoSQLPostgres {

    private Connection conexao;

    /**
     * grava Produto
     *
     * @param pModelProduto
     * @return int
     */
    public boolean salvarProdutoDAO(ModelProduto pModelProduto) {
        try {
            captaConexao();
            ExecuteSQL(
                    "INSERT INTO produtos ("
                    + "descricao,"
                    + "quantidade,"
                    + "preco"
                    + ") VALUES ("
                    + "'" + pModelProduto.getDescricao() + "',"
                    + "" + pModelProduto.getQuantidade() + ","
                    + "" + pModelProduto.getValor() + ""
                    + ");"
            );
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            fecha();
            return true;
        }
    }

    /**
     * recupera Produto
     *
     * @param pId
     * @return ModelProduto
     */
    public ModelProduto getProdutoDAO(int pId) {
        ModelProduto modelProduto = new ModelProduto();
        ResultSet resultado;
        try {
            captaConexao();
            resultado = ExecuteSQL(
                    "SELECT "
                    + "id,"
                    + "descricao,"
                    + "quantidade,"
                    + "preco"
                    + " FROM"
                    + " produtos"
                    + " WHERE"
                    + " id = " + pId + ""
                    + ";"
            );

            while (resultado.next()) {
                modelProduto.setDescricao(resultado.getString(1));
                modelProduto.setQuantidade(resultado.getInt(2));
                modelProduto.setValor(resultado.getDouble(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.fecha();
        }
        return modelProduto;
    }

    /**
     * recupera uma lista de Produto
     *
     * @return ArrayList
     */
    public ArrayList<ModelProduto> getListaProdutoDAO() throws SQLException {
        ArrayList<ModelProduto> listamodelProduto = new ArrayList();
        ModelProduto modelProduto = new ModelProduto();
        ResultSet resultado = null;
        captaConexao();
        resultado = ExecuteSQL(
                "SELECT "
                + "produtos.id,"
                + "produtos.descricao,"
                + "produtos.quantidade,"
                + "produtos.preco"
                + " FROM"
                + " produtos"
                + ";"
        );
        try {
            while (resultado.next()) {
                modelProduto = new ModelProduto();
                modelProduto.setId(resultado.getInt("id"));
                modelProduto.setDescricao(resultado.getString("descricao"));
                modelProduto.setQuantidade(resultado.getInt("quantidade"));
                modelProduto.setValor(resultado.getDouble("preco"));
                listamodelProduto.add(modelProduto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.fecha();
        }
        return listamodelProduto;
    }

    /**
     * atualiza Produto
     *
     * @param pModelProduto
     * @return boolean
     */
    public boolean atualizarProdutoDAO(ModelProduto pModelProduto) {
        try {
            this.captaConexao();
            this.ExecuteSQL(
                    "UPDATE produtos SET "
                    + "id = " + pModelProduto.getId() + ","
                    + "descricao = '" + pModelProduto.getDescricao() + "',"
                    + "quantidade = " + pModelProduto.getQuantidade() + ","
                    + "preco = " + pModelProduto.getValor() + ""
                    + " WHERE "
                    + "id = " + pModelProduto.getId() + ""
                    + ";"
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            this.fecha();
        }
    }

    /**
     * exclui Produto
     *
     * @param pId
     * @return boolean
     */
    public boolean excluirProdutoDAO(int pId) {
        try {
            this.captaConexao();
            this.ExecuteSQL(
                    "DELETE FROM produtos "
                    + " WHERE "
                    + "id = " + pId + ""
                    + ";"
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            this.fecha();
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
            return null;
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
}
