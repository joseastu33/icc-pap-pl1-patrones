package icc.pap.pl1.patrones.decorator;

import java.math.BigDecimal;

import icc.pap.pl1.patrones.modelo.Pedido;

public abstract class DecoradorIngrediente implements Pedido {
    protected Pedido pedido;

    public DecoradorIngrediente(Pedido pedido){ //tipo PEDIDO permite devolver otro DECORADOR o un PRODUCTO
        //Hay un tipo comun entre decorador y PRODUCTO
        this.pedido = pedido;
    }
    @Override 
    public String descripcion(){
        return pedido.descripcion();
    }
    @Override 
    public BigDecimal precio(){
        return pedido.precio();
    }

    
}
