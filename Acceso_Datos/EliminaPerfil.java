/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Acceso_Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author DanielFlores
 */
public class EliminaPerfil {
    private Base_Datos DB = new Base_Datos();
    int id;
    String perfil;
    
    public EliminaPerfil(){}
    
    public EliminaPerfil(String perfil, int id){
    this.perfil = perfil;
    this.id = id;
    }
    
    public void setPerfil(String perfil){this.perfil = perfil;}
    public String getPerfil(){return this.perfil;}
    
    public void setId(int id){this.id = id;}
    public int getId(){return id;} 
    
}
