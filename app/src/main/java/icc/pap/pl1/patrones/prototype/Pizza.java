package icc.pap.pl1.patrones.prototype;

import java.math.BigDecimal;

import icc.pap.pl1.patrones.modelo.TipoProducto;

public class Pizza extends ProductoBase {

    public Pizza(String id){
        super("Pizza sencilla", id, new BigDecimal("5.00"), TipoProducto.PIZZA);//valores BASE de la PIZZA
    }

    @Override
    public ProductoBase copiar(String nuevoId) {
        return new Pizza(nuevoId);
    }
    
}
