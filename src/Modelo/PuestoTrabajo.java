/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Enith Vargas PB G53
 */
public class PuestoTrabajo {
    private int idPuestoTrabajo;
    private String nombrePuestoTrabajo;
    
    //Insertamos constructor sin ningún argumento, click derecho insert code constructor generate

    public PuestoTrabajo() {
       this.idPuestoTrabajo = idPuestoTrabajo;
       this.nombrePuestoTrabajo = nombrePuestoTrabajo;
    }
    //Insertamos los getter and setter, click derecho insert code getter and setter select all generate

    public int getIdPuestoTrabajo() {
        return idPuestoTrabajo;
    }

    public void setIdPuestoTrabajo(int idPuestoTrabajo) {
        this.idPuestoTrabajo = idPuestoTrabajo;
    }

    public String getNombrePuestoTrabajo() {
        return nombrePuestoTrabajo;
    }

    public void setNombrePuestoTrabajo(String nombrePuestoTrabajo) {
        this.nombrePuestoTrabajo = nombrePuestoTrabajo;
    }

    @Override
    public String toString() {
        return getNombrePuestoTrabajo();
    }
    
    
}
