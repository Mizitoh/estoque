package controller;

import model.ModelProduto;
import DAO.DAOProduto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
*
* @author Usuario
*/
public class ControllerProduto {

    private DAOProduto daoProduto = new DAOProduto();

    /**
    * grava Produto
    * @param pModelProduto
    * @return int
    */
    public boolean salvarProdutoController(ModelProduto pModelProduto){
        return this.daoProduto.salvarProdutoDAO(pModelProduto);
    }

    /**
    * recupera Produto
    * @param pId
    * @return ModelProduto
    */
    public ModelProduto getProdutoController(int pId){
        return this.daoProduto.getProdutoDAO(pId);
    }

    /**
    * recupera uma lista deProduto
    * @param pId
    * @return ArrayList
    */
    public ArrayList<ModelProduto> getListaProdutoController() throws SQLException{
        return this.daoProduto.getListaProdutoDAO();
    }

    /**
    * atualiza Produto
    * @param pModelProduto
    * @return boolean
    */
    public boolean atualizarProdutoController(ModelProduto pModelProduto){
        return this.daoProduto.atualizarProdutoDAO(pModelProduto);
    }

    /**
    * exclui Produto
    * @param pId
    * @return boolean
    */
    public boolean excluirProdutoController(int pId){
        return this.daoProduto.excluirProdutoDAO(pId);
    }
}