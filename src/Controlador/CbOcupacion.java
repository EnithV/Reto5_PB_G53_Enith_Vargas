package Controlador;

import Modelo.Conexion;
import Modelo.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Enith Vargas PB G53
 */
public class CbOcupacion {
    Connection connection;
    Conexion conexion = new Conexion();
    Statement st;
    ResultSet rs;
    //Insertamos el constructor con click derecho insert code constructor select none generate

    public CbOcupacion() {
    }
    //Creamos una función que retornará la consulta de sucursales
    public ArrayList getListaPuestoTrabajo(){
       //Creamos mensaje para validar que cuando llamamos esta función sí estamos ingresando en la función
        System.out.println("Función getListaOcupaciones()");
        //Creamos el ArrayList que va a recibir esa información
        ArrayList mListaPuestosTrabajo = new ArrayList();
        //Creamos una instancia de la clase PuestoTrabajo y la vamos a iniciar null (sin nada de información)
        PuestoTrabajo puestoTrabajo = null;
        try {
            connection = conexion.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery("SELECT idPuestoTrabajo, nombrePuestoTrabajo FROM puestotrabajo;");
            //Para recorrer todos los resultados que nos devuelve el rs utilizamos un while
            while(rs.next()){
                puestoTrabajo = new PuestoTrabajo();
                puestoTrabajo.setIdPuestoTrabajo(rs.getInt("idPuestoTrabajo"));
                puestoTrabajo.setNombrePuestoTrabajo(rs.getString("nombrePuestoTrabajo"));
                mListaPuestosTrabajo.add(puestoTrabajo);
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        return mListaPuestosTrabajo;     
    }
}
