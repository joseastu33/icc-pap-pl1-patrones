package icc.pap.pl1.patrones.decorator;

import java.math.BigDecimal;

import icc.pap.pl1.patrones.modelo.Pedido;

public class Tomate extends DecoradorIngrediente {
    protected Pedido pedido;
    private static final BigDecimal PRECIOTOMATE = new BigDecimal("1.10"); //CONSTANTE: valor base del TOMATE no CAMBIARA

    public Tomate(Pedido pedido){ //el PADRE PROPORCIONA la ESTRUCTURA para GUARDAR el OBJETO ENVUELTO -> quien lo envuelve es TOMATE
        super(pedido);
    }

    @Override 
    public String descripcion(){
        return super.descripcion() + "con Tomate"; //**super** es una REFERENCIA a la CLASE PADRE para USAR SUS METODOS
    }

    @Override 
    public BigDecimal precio(){
        return super.precio().add(PRECIOTOMATE);
    }     

    public static BigDecimal precioIngredienteTomate(){
        return PRECIOTOMATE;
    }

}
