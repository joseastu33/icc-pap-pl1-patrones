package icc.pap.pl1.patrones.decorator;

import java.math.BigDecimal;

import icc.pap.pl1.patrones.modelo.Pedido;

public class Azucar extends DecoradorIngrediente {
    protected Pedido pedido;
    private static final BigDecimal PRECIO = new BigDecimal("0.50"); //CONSTANTE: el valor NO VA A CAMBIAR por eso se usa CONSTANTE

    public Azucar(Pedido pedido){
        super(pedido); //el PADRE PROPORCIONA la ESTRUCTURA para GUARDAR el OBJETO ENVUELTO -> quien lo envuelve es AZUCAR
    }
    
    @Override 
    public String descripcion(){
        return super.descripcion() + "con Azúcar"; //**super** es una REFERENCIA a la CLASE PADRE para USAR SUS METODOS
    }

    @Override 
    public BigDecimal precio(){
        return super.precio().add(PRECIO);
    }

    
}
