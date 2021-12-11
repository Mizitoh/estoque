/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.DAOUsuario;
import java.sql.SQLException;
import java.util.List;
import model.ModelUsuario;

/**
 *
 * @author Usuario
 */
public class ControllerUsuario {
    
    DAOUsuario daoUsuario = new DAOUsuario();    

    public boolean salvarUsuarioController(ModelUsuario modelUsuario) throws SQLException {
 
        String sql = "INSERT INTO usuarios (nome, login, senha) VALUES (?, ?, ?)";
	DAOUsuario dao = new DAOUsuario();
	dao.incluir(sql, modelUsuario.getNome(), modelUsuario.getLogin(), modelUsuario.getSenha());
        dao.fecha();
        return true;
    }

    public List<ModelUsuario> listaUsuario() throws SQLException {
        return this.daoUsuario.listaUsuarioDAO();
    }

    public boolean excluirUsuarioController(int jtfCodigo) throws SQLException {
        return this.daoUsuario.excluirUsuario(jtfCodigo);
    }
    
    public boolean alterarUsuarioController(int jtfCodigo, String jtfNome, String JtfLogin) throws SQLException {
        return this.daoUsuario.alterarUsuario(jtfCodigo, jtfNome, JtfLogin);
    }

    public boolean validaLogin(String login, String senha) throws SQLException {
        return this.daoUsuario.validaUsuario(login, senha);
    }
    
}
