package icc.pap.pl1.patrones.modelo;

import java.math.BigDecimal;

public interface Pedido {
    //cada pedido debe cumplir CON:
    String descripcion();
    BigDecimal precio();
    String id();
    TipoProducto tipo();
}
