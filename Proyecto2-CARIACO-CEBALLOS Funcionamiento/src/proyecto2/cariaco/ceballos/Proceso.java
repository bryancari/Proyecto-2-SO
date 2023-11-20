package proyecto2.cariaco.ceballos;

/**
 * Clase que define a cada personaje indiferentemente de la empresa, el cual es
 * equivalente a un proceso Falta crearle más atributos que serían de las
 * estadísticas del personaje las cuales se le deberían definir al instanciar la
 * clase
 *
 * @author Juan Ceballos
 */
public class Proceso {

    private String id; //Debe ser único para cada personaje
    private int prioridad;
    private int contador;
    private int habilidad;
    private int vida;
    private int fuerza;
    private int agilidad;
    private Proceso next; //next es un apuntador al siguiente proceso para que se puedan usar en las colas

    /**
     * Constructor para el personaje
     *
     * @param newId El identificador único para cada personaje (Indiferentemente
     * de que esté repetido)
     * @param prioridadInicial La prioridad con la que comienza un personaje
     * @param habilidad Puntos de habilidad
     * @param vida Puntos de vida
     * @param fuerza Puntos de fuerza
     * @param agilidad Puntos de agilidad
     */
    public Proceso(String newId, int prioridadInicial, int habilidad, int vida, int fuerza, int agilidad) {
        this.id = newId;
        this.prioridad = prioridadInicial;
        this.contador = 0;
        this.habilidad = habilidad;
        this.fuerza = fuerza;
        this.agilidad = agilidad;
        this.vida = vida;
        this.next = null;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the prioridad
     */
    public int getPrioridad() {
        return prioridad;
    }

    /**
     * @param prioridad the prioridad to set
     */
    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    /**
     * @return the contador
     */
    public int getContador() {
        return contador;
    }

    /**
     * @param contador the contador to set
     */
    public void setContador(int contador) {
        this.contador = contador;
    }

    /**
     * @return the next
     */
    public Proceso getNext() {
        return next;
    }

    /**
     * @param next the next to set
     */
    public void setNext(Proceso next) {
        this.next = next;
    }

    /**
     * @return the habilidad
     */
    public int getHabilidad() {
        return habilidad;
    }

    /**
     * @param habilidad the habilidad to set
     */
    public void setHabilidad(int habilidad) {
        this.habilidad = habilidad;
    }

    /**
     * @return the vida
     */
    public int getVida() {
        return vida;
    }

    /**
     * @param vida the vida to set
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * @return the fuerza
     */
    public int getFuerza() {
        return fuerza;
    }

    /**
     * @param fuerza the fuerza to set
     */
    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    /**
     * @return the agilidad
     */
    public int getAgilidad() {
        return agilidad;
    }

    /**
     * @param agilidad the agilidad to set
     */
    public void setAgilidad(int agilidad) {
        this.agilidad = agilidad;
    }
}
