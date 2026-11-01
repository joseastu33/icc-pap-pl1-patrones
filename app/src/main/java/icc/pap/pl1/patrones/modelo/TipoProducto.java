package icc.pap.pl1.patrones.modelo;

public enum TipoProducto {
    //decision de implementacion: ENUM para representar TRES OPCIONES
    ///alternativa considerada: implementar 3 metodos
    /// boolean esPizza()
    /// boolean esCafe()
    /// boolean esEnsalada()
    /// Problema: los tres metodos hacen la misma pregunta ¿que producto es? , es responsabilidad repetida
    CAFE,
    PIZZA,
    ENSALADA
}
