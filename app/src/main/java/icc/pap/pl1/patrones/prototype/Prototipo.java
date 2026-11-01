package icc.pap.pl1.patrones.prototype;

public interface Prototipo<T> { //interfaz GENERICA
    T copiar(String nuevoId); //cualquier objeto se podria copiar con el T
}
