package icc.pap.pl1.patrones.prototype;

import icc.pap.pl1.patrones.modelo.TipoProducto;

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
    }
}
