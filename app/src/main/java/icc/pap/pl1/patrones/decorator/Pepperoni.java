package icc.pap.pl1.patrones.decorator;

import java.math.BigDecimal;

import icc.pap.pl1.patrones.modelo.Pedido;

public class Pepperoni extends DecoradorIngrediente{
    protected Pedido pedido;
    private static final BigDecimal PRECIOPEPPERONI = new BigDecimal("1.10"); //CONSTANTE: valor base del PEPPERONI no CAMBIARA

    public Pepperoni(Pedido pedido){ //el PADRE PROPORCIONA la ESTRUCTURA para GUARDAR el OBJETO ENVUELTO -> quien lo envuelve es PEPPERONI
        super(pedido);
    }

    @Override 
    public String descripcion(){
        return super.descripcion() + "con Pepperoni"; //**super** es una REFERENCIA a la CLASE PADRE para USAR SUS METODOS
    }

    @Override 
    public BigDecimal precio(){
        return super.precio().add(PRECIOPEPPERONI);
    }

    public static BigDecimal precioIngredientePepperoni(){
        return PRECIOPEPPERONI;
    }
}
