package icc.pap.pl1.patrones.servicio;

import java.util.ArrayList;
import java.util.List;

import icc.pap.pl1.patrones.decorator.Azucar;
import icc.pap.pl1.patrones.decorator.Crema;
import icc.pap.pl1.patrones.decorator.Pepperoni;
import icc.pap.pl1.patrones.decorator.Pollo;
import icc.pap.pl1.patrones.decorator.Queso;
import icc.pap.pl1.patrones.decorator.Tomate;
import icc.pap.pl1.patrones.modelo.Pedido;
import icc.pap.pl1.patrones.modelo.TipoIngrediente;
import icc.pap.pl1.patrones.modelo.TipoProducto;
import icc.pap.pl1.patrones.prototype.ProductoBase;
import icc.pap.pl1.patrones.prototype.RegistroPrototipos;

public class ServicioPedidos {

    private final RegistroPrototipos registroPrototipos; //referencia para CONSULTAR EL MAPA
    private final List<Pedido> listaPedidos = new ArrayList<>(); //lista de PEDIDOS (ya ordenes de OBJETOS)
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

        if(tipoProd == null){
            throw new IllegalArgumentException("Tipo de producto no reconocido");
        }
        
        String identificadorPedido = generarIDPedido();
        ProductoBase prod = registroPrototipos.obtenerProductoDelCatalogo(tipoProd, identificadorPedido); //genera una COPIA con el identificadorPedido
        
        listaPedidos.add(prod);
        
        return prod; //Devuelve EL PRODUCTO ENCONTRADO POR SU TIPO E IDENTIFICADOR
    }

    public List<Pedido> obtenerPedidos(){
        return listaPedidos;
    }

    public Pedido buscarPedidoId(String idPedido){ //BUSCA el PEDIDO que el USUARIO quiere DECORAR
        for(Pedido pedido : listaPedidos){
            if(pedido.id().equals(idPedido)) { //SI EL PEDIDO COINCIDE con EL NOMBRE
                return pedido;
            }
        }
        return null;

    }

    public boolean puedeAgregarse(TipoProducto producto, TipoIngrediente tipoIngrediente){
        if(producto == TipoProducto.CAFE && (tipoIngrediente == TipoIngrediente.AZUCAR || tipoIngrediente == TipoIngrediente.CREMA)){
            return true;
        }
        else if(producto == TipoProducto.ENSALADA && (tipoIngrediente == TipoIngrediente.TOMATE || tipoIngrediente == TipoIngrediente.POLLO)){
            return true;
        }
        else if(producto == TipoProducto.PIZZA && (tipoIngrediente == TipoIngrediente.QUESO || tipoIngrediente == TipoIngrediente.PEPPERONI)){
            return true;
        } //ALTERNATIVA: usar un SWITCH para CADA CASO
        return false;
    }

    //la aplicación puede tener VARIOS pedidos, NECESITAMOS SABER en CUAL MODIFICAR
    public Pedido agregarIngredienteAPedido(String idPedido, TipoIngrediente ingredienteAgregar){

        if(ingredienteAgregar == null){
            throw new IllegalArgumentException("Debe seleccionar un ingrediente");
        }

        Pedido pedido = buscarPedidoId(idPedido);
        if(pedido == null){
            throw new IllegalArgumentException("Pedido no encontrado");
        }
        boolean pedidoPuedeAgregarse = puedeAgregarse(pedido.tipo(), ingredienteAgregar);

        if(!pedidoPuedeAgregarse){
            throw new IllegalArgumentException("Ingrediente no compatible");
        }

        switch (ingredienteAgregar) {
            case AZUCAR:
                pedido = new Azucar(pedido);
                break;
            case CREMA:
                pedido = new Crema(pedido);
                break;
            case PEPPERONI:
                pedido = new Pepperoni(pedido); //si es CADA DECORADOR CONCRETO se crea el pedido AGREGADO con ESE DECORADOR
                break;
            case POLLO:
                pedido = new Pollo(pedido);
                break;
            case QUESO:
                pedido = new Queso(pedido);
                break;
            case TOMATE:
                pedido = new Tomate(pedido);
                break;
            default:
                throw new IllegalArgumentException("Ingrediente no reconocido");
        }
        for(int i = 0; i < listaPedidos.size(); i++){
            if(listaPedidos.get(i).id().equals(idPedido)){
                listaPedidos.set(i, pedido); //reemplazar un elemento en una LISTA
                System.out.println("Pedido guardado: "
        + pedido.getClass().getSimpleName());
                return pedido;
            }
        }
        throw new IllegalStateException("El pedido no se encuentra registrado en la lista");
    }
    //reiniciar la LISTA de PEDIDOS
    public void reiniciarPedidos(){
        listaPedidos.clear();
    }

  
}

