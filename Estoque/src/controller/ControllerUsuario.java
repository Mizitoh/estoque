/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.DAOUsuario;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
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
    
}
