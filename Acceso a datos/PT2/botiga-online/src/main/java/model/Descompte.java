/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Jesus
 */
public class Descompte {
    private int id;
    private int producteId;
    private String tipus;
    private double valor;
    public Descompte(int id, int producteId, String tipus, double valor){
        this.id=id;
        this.producteId=producteId;
        this.tipus=tipus;
        this.valor=valor;
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
     * @return the producteId
     */
    public int getProducteId() {
        return producteId;
    }

    /**
     * @param producteId the producteId to set
     */
    public void setProducteId(int producteId) {
        this.producteId = producteId;
    }

    /**
     * @return the tipus
     */
    public String getTipus() {
        return tipus;
    }

    /**
     * @param tipus the tipus to set
     */
    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    /**
     * @return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(double valor) {
        this.valor = valor;
    }
    @Override
    public String toString() {
    return id + " - producte " + producteId + " | " + valor + tipus;
}
    
}
