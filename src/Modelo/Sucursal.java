
package Modelo;

/*
 * @author Enith Vargas PB G53
 */

public class Sucursal {
    private int idSucursal;
    private String nombreSucursal;
    
    //Insertamos constructor sin ningún argumento, click derecho insert code constructor generate

    public Sucursal() {
       
    }

    public Sucursal(int idSucursal, String nombreSucursal) {
        this.idSucursal = idSucursal;
        this.nombreSucursal = nombreSucursal;
    }

    
    //Insertamos los getter and setter, click derecho insert code getter and setter select all generate
    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }
    //Insertamos toString(): click secundario insert code toString(), select none (lo insertamos vacío) generate

    @Override
    public String toString() {
        //Esto es para devolver la información del objeto, como lo dejé vacío, solicito que me devuelva el nombre de la sucursal
        return getNombreSucursal();
    }   
}
