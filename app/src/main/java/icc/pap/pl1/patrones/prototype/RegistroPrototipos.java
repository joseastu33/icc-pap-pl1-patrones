package icc.pap.pl1.patrones.prototype;

import java.util.HashMap;
import java.util.Map;

import icc.pap.pl1.patrones.modelo.TipoProducto;

public class RegistroPrototipos {
    private Map<TipoProducto, ProductoBase> mapaPrototipos = new HashMap<>(); //para relacionar TIPO con PRODUCTO CONCRETO (QUE viene de ProductoBase)
    
    public RegistroPrototipos(){
        
        mapaPrototipos.put(TipoProducto.ENSALADA, new Ensalada("PROTOTIPO-CONCRETO-ENSALADA"));
        mapaPrototipos.put(TipoProducto.PIZZA, new Pizza("PROTOTIPO-CONCRETO-PIZZA"));
        mapaPrototipos.put(TipoProducto.CAFE, new Cafe("PROTOTIPO-CONCRETO-CAFE"));
    }
    public ProductoBase obtenerProductoDelCatalogo(TipoProducto tipoProducto, String idProducto){
        ProductoBase producto = mapaPrototipos.get(tipoProducto); //obtiene del MAPA

        if(producto == null){
            throw new IllegalArgumentException("Producto no registrado: " + tipoProducto); //VERIFICAR si se ENCUENTRA
        }
        return producto.copiar(idProducto); //se devuelve OTRO PRODUCTO
    }
    
}
