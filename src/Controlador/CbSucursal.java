package Controlador;

import Modelo.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * @author Enith Vargas PB G53
 */
public class CbSucursal {
    Connection connection;
    Conexion conexion = new Conexion();
    Statement st;
    ResultSet rs;
    //Insertamos el constructor con click derecho insert code constructor select none generate

    public CbSucursal() {
    }
    //Creamos una función que retornará la consulta de sucursales
    public ArrayList getListaSucursales(){
        //Creamos mensaje para validar que cuando llamamos esta función sí estamos ingresando en la función
        System.out.println("Función getListaSucursales()");
        //Creamos el ArrayList que va a recibir esa información
        ArrayList mListaSucursales = new ArrayList();
        //Creamos una instancia de la clase sucursal y la vamos a iniciar null (sin nada de información)
        Sucursal sucursal = null;
        try {
            connection = conexion.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery("SELECT idSucursal, nombreSucursal FROM sucursal;");
            //Para recorrer todos los resultados que nos devuelve el rs utilizamos un while
            while(rs.next()){
                sucursal = new Sucursal();
                sucursal.setIdSucursal(rs.getInt("idSucursal"));
                sucursal.setNombreSucursal(rs.getString("nombreSucursal"));
                mListaSucursales.add(sucursal);
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        return mListaSucursales;    
    }
}

