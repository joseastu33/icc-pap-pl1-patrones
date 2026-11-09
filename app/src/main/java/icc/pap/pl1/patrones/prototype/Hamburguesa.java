package icc.pap.pl1.patrones.prototype;

import java.math.BigDecimal;

import icc.pap.pl1.patrones.modelo.TipoProducto;

public class Hamburguesa extends ProductoBase {

    public Hamburguesa(String id){
        super("Hamburguesa sencilla", id, new BigDecimal("4.50"), TipoProducto.HAMBURGUESA);
    }
    
    @Override
    public ProductoBase copiar(String nuevoId) {
        return new Hamburguesa(nuevoId);
    }
    
}
