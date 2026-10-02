

import java.io.*;

public class Juego implements Serializable {
    private String titol,genere,plataforma;
    private int anyLlançament;
    private double preu;
    public Juego(String titol, String genere, String plataforma, int anyLlançament, double preu){
        this.titol=titol;
        this.genere=genere;
        this.plataforma=plataforma;
        this.anyLlançament=anyLlançament;
        this.preu=preu;
    }

    /**
     * @return the titol
     */
    public String getTitol() {
        return titol;
    }

    /**
     * @param titol the titol to set
     */
    public void setTitol(String titol) {
        this.titol = titol;
    }

    /**
     * @return the genere
     */
    public String getGenere() {
        return genere;
    }

    /**
     * @param genere the genere to set
     */
    public void setGenere(String genere) {
        this.genere = genere;
    }

    /**
     * @return the plataforma
     */
    public String getPlataforma() {
        return plataforma;
    }

    /**
     * @param plataforma the plataforma to set
     */
    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    /**
     * @return the anyLlançament
     */
    public int getAnyLlançament() {
        return anyLlançament;
    }

    /**
     * @param anyLlançament the anyLlançament to set
     */
    public void setAnyLlançament(int anyLlançament) {
        this.anyLlançament = anyLlançament;
    }

    /**
     * @return the preu
     */
    public double getPreu() {
        return preu;
    }

    /**
     * @param preu the preu to set
     */
    public void setPreu(double preu) {
        this.preu = preu;
    }
    @Override
    public String toString(){
        return "Titol: "+titol+", Genere: "+genere+", Any de llançament: "+anyLlançament+", Plataforma: "+plataforma+", Preu: "+preu;
    }
}
