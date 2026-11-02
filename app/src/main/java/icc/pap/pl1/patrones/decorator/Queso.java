package icc.pap.pl1.patrones.decorator;

import java.math.BigDecimal;

import icc.pap.pl1.patrones.modelo.Pedido;

public class Queso extends DecoradorIngrediente {
    protected Pedido pedido;
    private static final BigDecimal PRECIOQUESO = new BigDecimal("1.00"); //CONSTANTE: valor base del QUESO no CAMBIARA

    public Queso(Pedido pedido){ //el PADRE PROPORCIONA la ESTRUCTURA para GUARDAR el OBJETO ENVUELTO -> quien lo envuelve es QUESO
        super(pedido);
    }

    @Override 
    public String descripcion(){
        return super.descripcion() + "con Queso"; //**super** es una REFERENCIA a la CLASE PADRE para USAR SUS METODOS
    }

    @Override 
    public BigDecimal precio(){
        return super.precio().add(PRECIOQUESO);
    }     

    public static BigDecimal precioIngredienteQueso(){
        return PRECIOQUESO;
    }
    
}
