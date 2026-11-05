/*
ARIEL VILLA OLIVAREZ
ANGEL EMILIANO REYNOSO LÓPEZ
JULIAN EMMANUEL MENDEZ RODRIGUEZ
 */
package back_end;

/**
 *
 * @author villa
 */
public class Arista {
    private String idDestino;
    private double peso;

    public Arista(String idDestino, double peso) {
        this.idDestino = idDestino;
        this.peso = peso;
    }

    public String getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(String idDestino) {
        this.idDestino = idDestino;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
    
    
}
