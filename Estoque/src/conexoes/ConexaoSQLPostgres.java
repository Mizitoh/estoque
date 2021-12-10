/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexoes;

import java.sql.*;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Usuario
 */
public class ConexaoSQLPostgres {

    Connection conexao;

    public static Connection captaConexao() throws SQLException {
		try {
			Properties prop = captaPropiedades();
			final String url = prop.getProperty("banco.url");
			final String usuario = prop.getProperty("banco.usuario");
			final String senha = prop.getProperty("banco.senha");
			
			return DriverManager.getConnection(url, usuario, senha);
		} catch (SQLException | IOException e) {
                        System.out.println("não conectou");
			throw new RuntimeException(e);
		}
	}
	
	private static Properties captaPropiedades() throws IOException {
		Properties prop = new Properties();
		String caminho = "/util/conexao.properties";
		prop.load(ConexaoSQLPostgres.class.getResourceAsStream(caminho));
		return prop;
	}
}