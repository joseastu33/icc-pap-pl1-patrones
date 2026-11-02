package icc.pap.pl1.patrones.prototype;

import icc.pap.pl1.patrones.modelo.Pedido;
import icc.pap.pl1.patrones.modelo.TipoIngrediente;
import icc.pap.pl1.patrones.modelo.TipoProducto;
import icc.pap.pl1.patrones.servicio.ServicioPedidos;

public class MetodoPrueba {
    public static void main(String[] args) {
        RegistroPrototipos registro = new RegistroPrototipos();
        ///VERIFICACION: Verificar que SON INSTANCIAS distintas en MEMORIA
        ProductoBase cafe1 = registro.obtenerProductoDelCatalogo(TipoProducto.CAFE, "P-001");
        ProductoBase cafe2 = registro.obtenerProductoDelCatalogo(TipoProducto.CAFE, "P-002");

        System.out.println(cafe1.id());
        System.out.println(cafe2.id());

        System.out.println(cafe1 == cafe2);

        ProductoBase prototipo = registro.obtenerPrototipo(TipoProducto.CAFE);

        System.out.println(cafe1 == prototipo);

        ServicioPedidos servicio = new ServicioPedidos(registro);

        //CA02: ServicioPedidos genera sus PROPIOS IDENTIFICADORES
        Pedido pedido1 = servicio.crearPedido(TipoProducto.CAFE);
        Pedido pedido2 = servicio.crearPedido(TipoProducto.CAFE);

        System.out.println(pedido1.id());
        System.out.println(pedido2.id());

        System.out.println(pedido1.precio());
        System.out.println(pedido2.precio());

        //CA03: 
        Pedido personalizado = servicio.agregarIngredienteAPedido(
        pedido1.id(), TipoIngrediente.AZUCAR
        );

        personalizado = servicio.agregarIngredienteAPedido(
        pedido1.id(), TipoIngrediente.CREMA
        );

        System.out.println(personalizado.descripcion());
        System.out.println(personalizado.precio());

        //CA04: Comprobar que el segundo café NO SEA MODIFICADO
        System.out.println(pedido2.id());
        System.out.println(pedido2.descripcion());
        System.out.println(pedido2.precio());

        //CA05
        Pedido ensalada = servicio.crearPedido(TipoProducto.ENSALADA);

        ensalada = servicio.agregarIngredienteAPedido(
        ensalada.id(), TipoIngrediente.TOMATE
        );

        ensalada = servicio.agregarIngredienteAPedido(
        ensalada.id(), TipoIngrediente.POLLO
        );

        System.out.println(ensalada.id());
        System.out.println(ensalada.descripcion());
        System.out.println(ensalada.precio());

        //CA06
        Pedido pizza = servicio.crearPedido(TipoProducto.PIZZA);

        pizza = servicio.agregarIngredienteAPedido(
        pizza.id(), TipoIngrediente.QUESO
        );

        pizza = servicio.agregarIngredienteAPedido(
        pizza.id(), TipoIngrediente.PEPPERONI
        );

        System.out.println(pizza.id());
        System.out.println(pizza.descripcion());
        System.out.println(pizza.precio());

        //CA07
        try {
        servicio.agregarIngredienteAPedido(pedido2.id(), TipoIngrediente.QUESO);
        } catch (IllegalArgumentException e) {
            System.out.println("Error controlado: " + e.getMessage());
        }

        System.out.println(pedido2.descripcion());
        System.out.println(pedido2.precio());

        //CA08
        try {
        servicio.agregarIngredienteAPedido("P-999", TipoIngrediente.AZUCAR);
        } catch (IllegalArgumentException e) {
            System.out.println("Error controlado: " + e.getMessage());
        }
        System.out.println("Cantidad de pedidos: " + servicio.obtenerPedidos().size());

        //CA09
        try {
        servicio.crearPedido(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Error controlado: " + e.getMessage());
        }

        System.out.println("Cantidad de pedidos: " + servicio.obtenerPedidos().size());

        // COMPROBAR PEDIDOS REGISTRADOS
        for (Pedido p : servicio.obtenerPedidos()) {
            System.out.println("ID: " + p.id()
            + " | Producto: " + p.descripcion());}

        // CA10
        Pedido cafeDobleAzucar = servicio.buscarPedidoId("P-002");

        cafeDobleAzucar = servicio.agregarIngredienteAPedido(
        cafeDobleAzucar.id(), TipoIngrediente.AZUCAR);

        cafeDobleAzucar = servicio.agregarIngredienteAPedido(
        cafeDobleAzucar.id(), TipoIngrediente.AZUCAR);

        System.out.println(cafeDobleAzucar.id());
        System.out.println(cafeDobleAzucar.descripcion());
        System.out.println(cafeDobleAzucar.precio());
        //CA11
        servicio.reiniciarPedidos();

        System.out.println("Cantidad de pedidos: "
        + servicio.obtenerPedidos().size());

        Pedido nuevoCafe = servicio.crearPedido(TipoProducto.CAFE);

        System.out.println(nuevoCafe.id());
        System.out.println(nuevoCafe.descripcion());
        System.out.println(nuevoCafe.precio());


    }
}
