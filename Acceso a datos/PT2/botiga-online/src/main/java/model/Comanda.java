/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author Jesus
 */
public class Comanda {
    private int id;
    private int clientId;
    private Timestamp data;
    private double total;
    public Comanda(int id, int clientId, Timestamp data, double total){
        this.id=id;
        this.clientId=clientId;
        this.data=data;
        this.total=total;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the clientId
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * @param clientId the clientId to set
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * @return the data
     */
    public Timestamp getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Timestamp data) {
        this.data = data;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }
    
    
}
