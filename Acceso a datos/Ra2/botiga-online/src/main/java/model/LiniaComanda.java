/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Jesus
 */
public class LiniaComanda {
    private int id;
    private int comandaId;
    private int producteId;
    private int quantitat;
    private double preuUnitari;
    public LiniaComanda(){};
    public LiniaComanda(int id, int comandaId,int producteId, int quantitat, double preuUnitari){
    this.id=id;
    this.comandaId=comandaId;
    this.producteId=producteId;
    this.quantitat=quantitat;
    this.preuUnitari=preuUnitari;
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
     * @return the comandaId
     */
    public int getComandaId() {
        return comandaId;
    }

    /**
     * @param comandaId the comandaId to set
     */
    public void setComandaId(int comandaId) {
        this.comandaId = comandaId;
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
     * @return the quantitat
     */
    public int getQuantitat() {
        return quantitat;
    }

    /**
     * @param quantitat the quantitat to set
     */
    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

    /**
     * @return the preuUnitari
     */
    public double getPreuUnitari() {
        return preuUnitari;
    }

    /**
     * @param preuUnitari the preuUnitari to set
     */
    public void setPreuUnitari(double preuUnitari) {
        this.preuUnitari = preuUnitari;
    }
    
}
