package jogasa.simarro.proyectenadal.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Pedido implements Serializable {


    private int id;
    private String nombre,metodoFacturacion,direccionEnvio;
    private String fechacreacionPedido;
    private float precio;
    private ArrayList<Producto> productos=new ArrayList<Producto>();
    private int cantidadPedido;
    private Usuario usuarioCreador;
    private boolean isFinished;

    public Pedido(){}
    public Pedido(String nombre, String metodoFacturacion, String direccionEnvio, String fechacreacionPedido, float precio, ArrayList<Producto> productos) {
        this.nombre = nombre;
        this.metodoFacturacion = metodoFacturacion;
        this.direccionEnvio = direccionEnvio;
        this.fechacreacionPedido = fechacreacionPedido;
        this.precio = precio;
        this.productos = productos;
    }
    //PEDIDO SIN ACABAR
    public Pedido(String nombre){
        this.nombre=nombre;
    }

    public int getCantidadPedido() {
        return cantidadPedido;
    }

    public void setCantidadPedido(int cantidadPedido) {
        this.cantidadPedido = cantidadPedido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMetodoFacturacion() {
        return metodoFacturacion;
    }

    public void setMetodoFacturacion(String metodoFacturacion) {
        this.metodoFacturacion = metodoFacturacion;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public String getFechacreacionPedido() {
        return fechacreacionPedido;
    }

    public void setFechacreacionPedido(String fechacreacionPedido) {
        this.fechacreacionPedido = fechacreacionPedido;
    }

    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }
}
