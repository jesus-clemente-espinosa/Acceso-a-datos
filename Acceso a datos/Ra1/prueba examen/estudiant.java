import java.io.*;

public class estudiant implements Serializable{
    private int id,edat;
    private String nom;
    private double nota;
    public estudiant(int id, String nom, int edat, double nota){
        this.id=id;
        this.nom=nom;
        this.edat=edat;
        this.nota=nota;
    }
    public int getId(){
        return id;
    }
    public String getNom(){
        return nom;
    }
    public int getEdat(){
        return edat;
    }
    public double getNota(){
        return nota;
    }
    public void setId(int id){
        this.id=id;
    }
    public void setNom(String nom){
        this.nom=nom;
    }
    public void setEdat(int edat){
        this.edat=edat;
    }
    public void setNota(double nota){
        this.nota=nota;
    }
    @Override
    public String toString(){
        return "ID: "+id+", Nom: "+nom+", Edat: "+edat+", Nota: "+nota;
    }
}