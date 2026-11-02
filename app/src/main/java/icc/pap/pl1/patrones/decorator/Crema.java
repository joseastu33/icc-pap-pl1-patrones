package icc.pap.pl1.patrones.decorator;

import java.math.BigDecimal;

import icc.pap.pl1.patrones.modelo.Pedido;

public class Crema extends DecoradorIngrediente {
    protected Pedido pedido;
    private static final BigDecimal PRECIOCREMA = new BigDecimal("1.20");//CONSTANTE: valor base de la CREMA no CAMBIARA

    public Crema(Pedido pedido){ //el PADRE PROPORCIONA la ESTRUCTURA para GUARDAR el OBJETO ENVUELTO -> quien lo envuelve es CREMA
        super(pedido);
    }

    @Override 
    public String descripcion(){
        return super.descripcion() + "con Crema"; //**super** es una REFERENCIA a la CLASE PADRE para USAR SUS METODOS
    }

    @Override 
    public BigDecimal precio(){
        return super.precio().add(PRECIOCREMA);
    }
}
