/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.marlonhernandez.bean;

/**
 *
 * @author Marlon PC
 * @date 9/06/2021
 * @time 15:41:30
 * Código Técnico: IN5BV
 *
 *
 */
public class TipoCliente {
    
    private int id;
    private String descripcion;

    public TipoCliente() {
    }
    
    public TipoCliente(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    @Override
    public String toString() {
        return  id + "|" + " " + descripcion;
    }
    
}
