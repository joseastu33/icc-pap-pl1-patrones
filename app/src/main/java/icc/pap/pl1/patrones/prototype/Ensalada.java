package icc.pap.pl1.patrones.prototype;

import java.math.BigDecimal;

import icc.pap.pl1.patrones.modelo.TipoProducto;

public class Ensalada extends ProductoBase{

    //al EXTENDER usan los ATRIBUTOS y METODOS del PADRE y PUEDEN modificar SU COMPORTAMIENTO
    public Ensalada(String id){
        super(id, "Ensalada sencilla", new BigDecimal("3.00"), TipoProducto.ENSALADA); //valores BASE de la ENSADA
    }

    @Override
    public ProductoBase copiar(String nuevoId) { //METODO de copia de PROTORYPE
        return new Ensalada(nuevoId);
    }

    
}
