package icc.pap.pl1.patrones.prototype;

import java.math.BigDecimal;

import icc.pap.pl1.patrones.modelo.Pedido;
import icc.pap.pl1.patrones.modelo.TipoProducto;
/**
 * 
 * ProductoBase: clase intermedia
 */

//implementa PEDIDO porque la misma INFORMACION del producto está en el PEDIDO de ESE MISMO PRODUCTO
public abstract class ProductoBase implements Pedido, Prototipo<ProductoBase>{ //Prototipo NO TIENE por que SER GENERICO si solo devuelvo un ProductoBase
    
    //cada producto va a TENER este COMPORTAMIENTO
    private String descripcion;
    private String id;
    private BigDecimal precio;
    private TipoProducto tipo;

    public ProductoBase(String descripcion, String id, BigDecimal precio, TipoProducto tipo){
        this.descripcion = descripcion;
        this.id = id;
        this.precio = precio;
        this.tipo = tipo;
    }

    @Override 
    public String descripcion(){
        return descripcion;
    }

    @Override 
    public String id(){ //se evita colocar LOS TRES METODOS en las TRES CLASES Hijas PERTENECIENTES a ProductoBase
        return id;
    }

    @Override 
    public BigDecimal precio(){
        return precio;
    }
    
    @Override 
    public TipoProducto tipo(){
        return tipo;
    }

    @Override
    public abstract ProductoBase copiar(String nuevoId); //delega EL DESAROLLO del METODO A las CLASES HIJAS (Pizza, Cafe, Ensalada)


}
