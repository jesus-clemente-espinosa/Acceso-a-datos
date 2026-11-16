/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Jesus
 */
public class Client {
    private int id;
    private String nom;
    private String correu;
    public Client (int id, String nom, String correu){
        this.id=id;
        this.nom=nom;
        this.correu=correu;
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
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the correu
     */
    public String getCorreu() {
        return correu;
    }

    /**
     * @param correu the correu to set
     */
    public void setCorreu(String correu) {
        this.correu = correu;
    }
     @Override
    public String toString() {
        return id + " - " + nom + " (" + correu + ")";
    }
}
