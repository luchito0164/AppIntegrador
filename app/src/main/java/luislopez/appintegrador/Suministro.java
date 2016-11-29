package luislopez.appintegrador;

/**
 * Created by Luis on 19/11/2016.
 */

public class Suministro {
    private String cliente;
    private String direccion;
    private String numeroSumi;


    public Suministro() {
    }

    public Suministro(String cliente, String direccion, String numeroSumi) {
        this.setCliente(cliente);
        this.setDireccion(direccion);
        this.setNumeroSumi(numeroSumi);
    }


    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumeroSumi() {
        return numeroSumi;
    }

    public void setNumeroSumi(String numeroSumi) {
        this.numeroSumi = numeroSumi;
    }
}
