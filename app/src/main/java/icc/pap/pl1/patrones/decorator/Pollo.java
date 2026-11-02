package icc.pap.pl1.patrones.decorator;

import java.math.BigDecimal;

import icc.pap.pl1.patrones.modelo.Pedido;

public class Pollo extends DecoradorIngrediente {
    protected Pedido pedido;
    private static final BigDecimal PRECIOPOLLO = new BigDecimal("1.50");//CONSTANTE: valor base del POLLO no CAMBIARA
    
    public Pollo(Pedido pedido){ //el PADRE PROPORCIONA la ESTRUCTURA para GUARDAR el OBJETO ENVUELTO -> quien lo envuelve es POLLO
        super(pedido);
    }

    @Override 
    public String descripcion(){
        return super.descripcion() + "con Pollo"; //**super** es una REFERENCIA a la CLASE PADRE para USAR SUS METODOS
    }

    @Override 
    public BigDecimal precio(){
        return super.precio().add(PRECIOPOLLO);
    }    
}
