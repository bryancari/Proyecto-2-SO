package proyecto2.cariaco.ceballos;

/**
 * Cola para múltiples usos dentro del proyecto
 *
 * @author Bryan
 */
public class Cola {

    private int longitud;
    private Proceso cola;
    private Proceso cabecera;

    public Cola() {
        this.longitud = 0;
        this.cola = this.cabecera = null;
    }

    /**
     * @param nuevo es el proceso ya instanciado que se va a agregar a la cola
     */
    public void encolar(Proceso nuevo) {
        if (esVacia()) {
            cabecera = cola = nuevo;
        } else {
            Proceso aux = cola;
            aux.setNext(nuevo);
            cola = aux.getNext();
        }
        longitud++;
    }

    /**
     * Desencola regresando el proceso para que en caso de que sea empate o no
     * se de la batalla este pueda ser agregado a otra cola
     *
     * @return Regresa el nodo que se va a eliminar con el próximo set en null
     */
    public Proceso desencolar() {
        if (esVacia()) {
            return null;
        } else {
            Proceso aux = cabecera;
            cabecera = aux.getNext();
            longitud--;
            aux.setNext(null);
            return aux;
        }
    }

    /**
     * Función para aumentar los contadores de la cola y también moverla si
     * cumple con la condición
     *
     * @param objetivo cola a la que se va a mover los procesos que hayan
     * completado el ciclo
     */
    public void moverCola(Cola objetivo) {
        if (esVacia()) {

        } else {
            Proceso auxAnterior = null;
            Proceso auxActual = cabecera;
            int auxSize = 0;
            while (auxSize < longitud) {
                int auxNum = auxActual.getContador();
                auxNum++;
                auxActual.setContador(auxNum);
                if (auxActual.getContador() == 8) {
                    if (auxAnterior == null) {
                        Proceso auxCambio = auxActual;
                        longitud--;
                        auxActual = auxCambio.getNext();
                        cabecera = auxActual;
                        auxCambio.setContador(0);
                        objetivo.encolar(auxCambio);
                    } else {
                        auxAnterior.setNext(auxActual.getNext());
                        longitud--;
                        auxActual.setContador(0);
                        objetivo.encolar(auxActual);
                        auxActual = auxAnterior.getNext();
                    }
                } else {
                    auxAnterior = auxActual;
                    auxActual = auxActual.getNext();
                }
                auxSize++;
            }
        }
    }
    
    /**
     * Función que regresa en tipo string la cola en base al ID de cada
     * personaje
     *
     * @return una cola de id's en tipo string
     */
    public String imprimir() {
        String auxCola = "";
        if (esVacia()) {
            return "Vacía";
        } else {
            Proceso aux = cabecera;
            int auxLong = 0;
            while (auxLong < longitud) {
                auxCola += "ID: " + aux.getId() + "-- ";
                aux = aux.getNext();
                auxLong++;
            }
        }
        return auxCola;
    }

    /**
     * @return the longitud
     */
    public int getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the cola
     */
    public Proceso getCola() {
        return cola;
    }

    /**
     * @param cola the cola to set
     */
    public void setCola(Proceso cola) {
        this.cola = cola;
    }

    /**
     * @return the cabecera
     */
    public Proceso getCabecera() {
        return cabecera;
    }

    /**
     * @param cabecera the cabecera to set
     */
    public void setCabecera(Proceso cabecera) {
        this.cabecera = cabecera;
    }

    public boolean esVacia() {
        return longitud == 0;
    }

}
