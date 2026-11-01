package icc.pap.pl1.patrones.servicio;

import java.util.ArrayList;
import java.util.List;

import icc.pap.pl1.patrones.modelo.Pedido;
import icc.pap.pl1.patrones.modelo.TipoProducto;
import icc.pap.pl1.patrones.prototype.ProductoBase;
import icc.pap.pl1.patrones.prototype.RegistroPrototipos;

public class ServicioPedidos {

    private RegistroPrototipos registroPrototipos; //referencia para CONSULTAR EL MAPA
    private List<Pedido> listaPedidos = new ArrayList<>(); //lista de PEDIDOS (ya ordenes de OBJETOS)
    private int contadorPedidos = 0;

    public ServicioPedidos(RegistroPrototipos registroPrototipos){ //va a recibir el MAPA
        this.registroPrototipos = registroPrototipos;
    }

    public String generarIDPedido(){
        contadorPedidos++;
        return String.format("P-%03d", contadorPedidos); //genera pedidos CADA VEZ QUE ES LLAMADO y aumenta el CONTADOR para que 
        //puedam ser diferentes
    }



    public Pedido crearPedido(TipoProducto tipoProd){

        String identificadorPedido = generarIDPedido();
        ProductoBase prod = registroPrototipos.obtenerProductoDelCatalogo(tipoProd, identificadorPedido); //genera una COPIA con el identificadorPedido
        
        listaPedidos.add(prod);
        
        return prod; //Devuelve EL PRODUCTO ENCONTRADO POR SU TIPO E IDENTIFICADOR
    }


}
