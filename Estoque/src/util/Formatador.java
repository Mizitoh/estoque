/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author Usuario
 */
public class Formatador {
    
    public double converterVirgulaParaPonto(String variavelExterna){
        double variavelFormatada;
        System.out.print(variavelExterna);
        variavelFormatada = Double.parseDouble(variavelExterna.replace(",", "."));
        System.out.print(variavelFormatada);
        return variavelFormatada;
    }
    
}
