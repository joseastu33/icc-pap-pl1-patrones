package icc.pap.pl1.patrones.prototype;

import icc.pap.pl1.patrones.decorator.Azucar;
import icc.pap.pl1.patrones.decorator.Queso;
import icc.pap.pl1.patrones.modelo.Pedido;
import icc.pap.pl1.patrones.modelo.TipoIngrediente;
import icc.pap.pl1.patrones.modelo.TipoProducto;
import icc.pap.pl1.patrones.servicio.ServicioPedidos;
import icc.pap.pl1.patrones.vista.VentanaPedidos;

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

        //pedidos
        Pedido pedido1 = servicio.crearPedido(TipoProducto.CAFE);
        Pedido pedido2 = servicio.crearPedido(TipoProducto.CAFE);

        //CA01: Clonar café dos veces
        System.out.println("CA01: Clonar dos veces");
        ProductoBase cafeUno = registro.obtenerProductoDelCatalogo(TipoProducto.CAFE, "P-001");
        ProductoBase cafeDos = registro.obtenerProductoDelCatalogo(TipoProducto.CAFE, "P-002");

        System.out.println(cafeUno.id());
        System.out.println(cafeDos.id());

        System.out.println(cafe1 == cafe2);
        System.out.println(cafe1 == prototipo);

        //Salida:
        //P-001
        //P-002
        //false
        //false

        //CA02: Comparar clon y objeto de registro
        System.out.println("CA02: Clon obtenido del catálogo y el prototipo son objetos distintos");
        System.out.println(cafe1 == prototipo);

        //Salida:
        //false

        //CA03: Al primer Café agregar Azucar y Crema
        System.out.println("CA03: Modificar el primer café y el segundo permanece sin ingredientes y conserva su precio");
        Pedido pedido = servicio.agregarIngredienteAPedido(pedido1.id(), TipoIngrediente.AZUCAR);
        pedido = servicio.agregarIngredienteAPedido(pedido1.id(), TipoIngrediente.CREMA); 

        System.out.println(pedido.descripcion());
        System.out.println(pedido.precio());

        System.out.println(pedido2.descripcion());
        System.out.println(pedido2.precio());

        //Salida:
        //Pedido guardado: Azucar
        //Pedido guardado: Crema
        //Café + Azucar + Crema
        //3.90
        //Café
        //2.20

        //CA04: Consultar el segundo café sin ingredientes
        System.out.println("CA04: El segundo pedido no debe afectarse por los cambios realizados en el primer pedido");
        Pedido pedidoDos = servicio.buscarPedidoId("P-002");
        System.out.println(pedidoDos.descripcion());
        System.out.println(pedidoDos.precio());

        //Salida:
        //Café
        //2.20

        //CA05:Clonar Ensalada y agregar Tomate y Pollo
        System.out.println("CA05: Se puede crear una ensalada y añadirle dos ingredientes compatiblaes");
        Pedido ensaladaUno = servicio.crearPedido(TipoProducto.ENSALADA);

        servicio.agregarIngredienteAPedido(ensaladaUno.id(), TipoIngrediente.TOMATE);
        servicio.agregarIngredienteAPedido(ensaladaUno.id(), TipoIngrediente.POLLO);

        Pedido pedidoEnsalada = servicio.buscarPedidoId(ensaladaUno.id());

        System.out.println(pedidoEnsalada.descripcion());
        System.out.println(pedidoEnsalada.precio());

        //Salida:
        //Ensalada + Tomate + Pollo
        //5.60

        //CA06:Clonar Pizza y agregar Queso y Pepperoni
        System.out.println("CA06: Se puede crear una pizza y añadirle dos ingredientes compatibles");
        Pedido pizzaUno = servicio.crearPedido(TipoProducto.PIZZA);

        servicio.agregarIngredienteAPedido(pizzaUno.id(), TipoIngrediente.QUESO);
        servicio.agregarIngredienteAPedido(pizzaUno.id(), TipoIngrediente.PEPPERONI);

        Pedido pedidoPizza = servicio.buscarPedidoId(pizzaUno.id());

        System.out.println(pedidoPizza.descripcion());
        System.out.println(pedidoPizza.precio());

        //Salida:
        //Pedido guardado: Queso
        //Pedido guardado: Pepperoni
        //Pizza sencilla + Queso + Pepperoni
        //7.10

        //CA07:Intentar agregar Queso a Café
        System.out.println("CA07: Intentar agregar ingrediente compatible y rechazo al hacerlo ");
        Pedido cafe = servicio.crearPedido(TipoProducto.CAFE);

        try {
            servicio.agregarIngredienteAPedido(
            cafe.id(), TipoIngrediente.QUESO);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());}

        Pedido cafeConsultado = servicio.buscarPedidoId(cafe.id());

        System.out.println(cafeConsultado.descripcion());
        System.out.println(cafeConsultado.precio());

        //Salida: 
        //Error: Ingrediente no compatible
        //Café
        //2.20

        //CA08:Agregar ingredientes sin pedido seleccionado
        System.out.println("CA08: Intenta agregar ingredientes a un pedido inexistente y lo rechaza");
        try {
            servicio.agregarIngredienteAPedido("P-999", TipoIngrediente.AZUCAR);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());}
        
            //Salida:
        //Error: Pedido no encontrado

        //CA09:Solicitar clon de clave inexistente
        System.out.println("CA09: Verifica que se rechace una clave que no es perteneciente al enum");
        try {
            TipoProducto tipoInexistente = TipoProducto.valueOf("HAMBURGUESA");

            registro.obtenerProductoDelCatalogo(tipoInexistente, "P-009");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());}

        //Salida:
        //Error: 
        // No enum constant icc.pap.pl1.patrones.modelo.TipoProducto.HAMBURGUESA

        //CA10:Agregar dos veces Azúcar a un Café
        System.out.println("CA10: Se comprueba que se puede agregar dos veces el mismo ingrediente compatible");
        Pedido cafeAgregarDos= servicio.crearPedido(TipoProducto.CAFE);

        servicio.agregarIngredienteAPedido(cafeAgregarDos.id(), TipoIngrediente.AZUCAR);
        servicio.agregarIngredienteAPedido(cafeAgregarDos.id(), TipoIngrediente.AZUCAR);

        Pedido cafeConsultadoDos = servicio.buscarPedidoId(cafeAgregarDos.id());

        System.out.println(cafeConsultadoDos.descripcion());
        System.out.println(cafeConsultadoDos.precio());

        //Salida:
        //Pedido guardado: Azucar
        //Pedido guardado: Azucar
        //Café + Azúcar + Azúcar
        //3.20

        //CA11: Reiniciar y volver a clonar Café
        System.out.println("CA11: El reinicio de la lista no destruye el catálogo");
        servicio.reiniciarPedidos();

        System.out.println(servicio.obtenerPedidos().size());
        Pedido nuevoCafe = servicio.crearPedido(TipoProducto.CAFE);

        System.out.println(nuevoCafe.id());
        System.out.println(nuevoCafe.descripcion());
        System.out.println(nuevoCafe.precio());

        //Salida:
        //0
        //P-007
        //Café
        //2.20

        //inicializacion de la ventana
        VentanaPedidos ventana = new VentanaPedidos(registro);
        ventana.setVisible(true);
        
        RegistroPrototipos catalogoProd = new RegistroPrototipos(); //creamos el CATALOGO
        //permite CONSULTAR y devolver un PRODUCTO del MAPA
        //necesitamos hacer de ese producto un PEDIDO

        ServicioPedidos servP = new ServicioPedidos(catalogoProd); //creamos el GESTOR de COPIAS de Productos del CATALOGO
        //recibimos el catalogo para usar sus Metodos

        Pedido pedidoDePrueba = servP.crearPedido(TipoProducto.PIZZA); //busca el TipoProducto en el CATALOGO
        Pedido pedidoDePruebaDos = servP.crearPedido(TipoProducto.PIZZA);

        servP.agregarIngredienteAPedido("P-001", TipoIngrediente.QUESO);

        Pedido pedidoDePruebaVuelta = servP.buscarPedidoId("P-001"); //retornar el primer pedido
        Pedido pedidoDePruebaDosVuelta = servP.buscarPedidoId("P-002");
        System.out.println("PRIMER PEDIDO: " + pedidoDePruebaVuelta.descripcion());
        System.out.println("SEGUNDO PEDIDO: " + pedidoDePruebaDosVuelta.descripcion());
        System.out.println("Son instancias iguales?" + " " + pedidoDePruebaVuelta.equals(pedidoDePruebaDosVuelta));

        pedidoDePruebaVuelta = pedidoDePruebaDos;
        System.out.println("Son instancias iguales?" + " " + pedidoDePruebaVuelta.equals(pedidoDePruebaDosVuelta));

        //Demostración DECORATOR
        servP.agregarIngredienteAPedido("P-001", TipoIngrediente.PEPPERONI);
        //se ENVUELVE a un decorador QUESO que ENVUELVE al PEDIDO
        Pedido pedidoAgregadoVuelta = servP.buscarPedidoId("P-001");
        System.out.println("PRECIO DECORADO: " + pedidoAgregadoVuelta.precio());
        System.out.println("PEDIDO DECORADO: " + pedidoAgregadoVuelta.descripcion());

        //pedido que contiene a hamburguesa
        ServicioPedidos servPedidoHamburguesa = new ServicioPedidos(new RegistroPrototipos());
        Pedido pedidoH = servPedidoHamburguesa.crearPedido(TipoProducto.HAMBURGUESA);
        servPedidoHamburguesa.agregarIngredienteAPedido("P-004", TipoIngrediente.TOMATE);
        
        System.out.println("DESCRIPCION HAMBURGUESA: " + servPedidoHamburguesa.buscarPedidoId("P-004").descripcion());
        System.out.println("PRECIO HAMBURGUESA: " + servPedidoHamburguesa.buscarPedidoId("P-004").precio());


        










    }
}
