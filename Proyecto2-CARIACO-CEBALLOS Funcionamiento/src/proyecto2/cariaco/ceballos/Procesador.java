package proyecto2.cariaco.ceballos;

import java.util.concurrent.Semaphore;

/**
 * Clase definida para el procesador el cual lleva a cabo los enfrentamientos y
 * arroja resultados que utilizará el SO para saber que acción realizer
 *
 * @author Juan Ceballos
 */
public class Procesador extends Thread {

    private int tiempo;
    private String estado_actual;
    private Proceso capcom;
    private Proceso nintendo;
    private int ultimo_resultado;
    private final Semaphore sema;
    private final Interfaz inter;

    public Procesador(Semaphore sema, int tiempo, Interfaz interfaz) {
        this.inter = interfaz;
        this.sema = sema;
        this.tiempo = tiempo;
        this.estado_actual = "Esperando";
    }

    /**
     * Función del rol que cumple el procesador
     *
     */
    public void escenarioEscogido() {
        int auxProba = (int) (Math.floor(Math.random() * 100));
        if (auxProba >= 0 && auxProba < 40) {
            int auxCombate = combate();
            switch (auxCombate) {
                case 1:
                    ultimo_resultado = 1; //Ganó el combate capcom
                    System.out.println("Paso 1: Ganó capcom");
                    break;
                case 2:
                    ultimo_resultado = 2; //Ganó el combate nintendo
                    System.out.println("Paso 1:Ganó nintendo");
                    break;
            }
        } else if (auxProba >= 40 && auxProba < 67) {
            ultimo_resultado = 3; //Hay empate
            System.out.println("Paso 1: Hubo empate");
        } else {
            ultimo_resultado = 4; //No se lleva a cabo el combate
            System.out.println("Paso 1: No hubo combate");
        }
    }

    /**
     * Función que realiza el combate, cada ítem utiliza probabilidades si llega
     * a suceder que tengan distinto nivel, es decir que llegan a ser de
     * distintos niveles (Poco probable, pero nunca imposible) Habrá un factor
     * de ayuda al de menor nivel basado en la suerte, 35% de suerte si tienen
     * dos niveles de diferencia y 25% de suerte si tienen un nivel de
     * diferencia, funciona sin suerte si están al mismo nivel
     *
     * @return retorna el personaje ganador
     */
    public int combate() {
        int puntosCapcom = 0;
        int puntosNintendo = 0;
        int auxDiferenciaNivel = capcom.getPrioridad() - nintendo.getPrioridad();

        if (capcom.getAgilidad() > nintendo.getAgilidad()) {
            int auxProba = (int) (Math.floor(Math.random() * 100));
            if (auxDiferenciaNivel == -2 && auxProba <= 35) {
                puntosCapcom--;
                puntosNintendo++;
            } else if (auxDiferenciaNivel == -1 && auxProba <= 25) {
                puntosCapcom--;
                puntosNintendo++;
            }

            puntosCapcom++;
        } else if (capcom.getAgilidad() == nintendo.getAgilidad()) {
            puntosCapcom++;
            puntosNintendo++;
        } else {
            int auxProba = (int) (Math.floor(Math.random() * 100));
            if (auxDiferenciaNivel == 2 && auxProba <= 35) {
                puntosCapcom++;
                puntosNintendo--;
            } else if (auxDiferenciaNivel == 1 && auxProba <= 25) {
                puntosCapcom++;
                puntosNintendo--;
            }
            puntosNintendo++;
        }

        if (capcom.getFuerza() > nintendo.getFuerza()) {
            int auxProba = (int) (Math.floor(Math.random() * 100));
            if (auxDiferenciaNivel == -2 && auxProba <= 35) {
                puntosCapcom--;
                puntosNintendo++;
            } else if (auxDiferenciaNivel == -1 && auxProba <= 25) {
                puntosCapcom--;
                puntosNintendo++;
            }
            puntosCapcom++;
        } else if (capcom.getFuerza() == nintendo.getFuerza()) {
            puntosCapcom++;
            puntosNintendo++;
        } else {
            int auxProba = (int) (Math.floor(Math.random() * 100));
            if (auxDiferenciaNivel == 2 && auxProba <= 35) {
                puntosCapcom++;
                puntosNintendo--;
            } else if (auxDiferenciaNivel == 1 && auxProba <= 25) {
                puntosCapcom++;
                puntosNintendo--;
            }
            puntosNintendo++;
        }

        if (capcom.getHabilidad() > nintendo.getHabilidad()) {
            int auxProba = (int) (Math.floor(Math.random() * 100));
            if (auxDiferenciaNivel == -2 && auxProba <= 35) {
                puntosCapcom--;
                puntosNintendo++;
            } else if (auxDiferenciaNivel == -1 && auxProba <= 25) {
                puntosCapcom--;
                puntosNintendo++;
            }
            puntosCapcom++;
        } else if (capcom.getHabilidad() == nintendo.getHabilidad()) {
            puntosCapcom++;
            puntosNintendo++;
        } else {
            int auxProba = (int) (Math.floor(Math.random() * 100));
            if (auxDiferenciaNivel == 2 && auxProba <= 35) {
                puntosCapcom++;
                puntosNintendo--;
            } else if (auxDiferenciaNivel == 1 && auxProba <= 25) {
                puntosCapcom++;
                puntosNintendo--;
            }
            puntosNintendo++;
        }

        if (capcom.getVida() > nintendo.getVida()) {
            int auxProba = (int) (Math.floor(Math.random() * 100));
            if (auxDiferenciaNivel == -2 && auxProba <= 65) {
                puntosCapcom--;
                puntosNintendo++;
            } else if (auxDiferenciaNivel == -1 && auxProba <= 25) {
                puntosCapcom--;
                puntosNintendo++;
            }
            puntosCapcom++;
        } else if (capcom.getVida() == nintendo.getVida()) {
            puntosCapcom++;
            puntosNintendo++;
        } else {
            int auxProba = (int) (Math.floor(Math.random() * 100));
            if (auxDiferenciaNivel == 2 && auxProba <= 35) {
                puntosCapcom++;
                puntosNintendo--;
            } else if (auxDiferenciaNivel == 1 && auxProba <= 25) {
                puntosCapcom++;
                puntosNintendo--;
            }
            puntosNintendo++;
        }

        if (puntosCapcom > puntosNintendo) {
            return 1; //Caso gana capcom
        } else if (puntosCapcom == puntosNintendo) {
            int auxSuerte = (int) (Math.floor(Math.random() * (2 - 1 + 1) + 1));
            if (auxSuerte == 1) {
                return 1; //Por cuestión de suerte gana capcom
            } else {
                return 2; //Por cuestión de suerte gana nintendo
            }

        } else {
            return 2; //Caso gana nintendo
        }
    }

    @Override
    public void run() {
        try {
            sleep(1000);
            while (true) {
                sema.acquire(1);
                estado_actual = "Decidiendo";
                inter.estadoProcesador.setText(estado_actual);

                escenarioEscogido();
                inter.setUltimo_resultado(ultimo_resultado);
                System.out.println("Paso 2: Seteo el resultado");
                sleep(tiempo * 100);

                switch (ultimo_resultado) {
                    case 1:
                        inter.resultado.setText("Ganó capcom");
                        inter.getGanadores().encolar(capcom);
                        inter.colaGanadores.setText(inter.getGanadores().imprimir());
                        System.out.println("Paso 3:Seteo capcom en ganadores");
                        break;
                    case 2:
                        inter.resultado.setText("Ganó Nintendo");
                        inter.getGanadores().encolar(nintendo);
                        inter.colaGanadores.setText(inter.getGanadores().imprimir());
                        System.out.println("Paso 3: Seteo nintendo en ganadores");
                        break;
                    case 3:
                        inter.resultado.setText("Empate");
                        System.out.println("Paso 3:Seteo de empate en interfaz");
                        break;
                    default:
                        inter.resultado.setText("No se pudo combatir");
                        System.out.println("Paso 3: Seteo de no hubo combate en interfaz");
                        break;
                }

                estado_actual = "Anunciando";
                inter.estadoProcesador.setText(estado_actual);
                System.out.println("Paso 4: Seteo de anuncio en interfaz");
                sleep(300 * 10);
                sema.release();
            }
        } catch (Exception e) {

        }
    }

    /**
     * @return the tiempo
     */
    public int getTiempo() {
        return tiempo;
    }

    /**
     * @param tiempo the tiempo to set
     */
    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    /**
     * @return the estado_actual
     */
    public String getEstado_actual() {
        return estado_actual;
    }

    /**
     * @param estado_actual the estado_actual to set
     */
    public void setEstado_actual(String estado_actual) {
        this.estado_actual = estado_actual;
    }

    /**
     * @return the capcom
     */
    public Proceso getCapcom() {
        return capcom;
    }

    /**
     * @param capcom the capcom to set
     */
    public void setCapcom(Proceso capcom) {
        this.capcom = capcom;
    }

    /**
     * @return the nintendo
     */
    public Proceso getNintendo() {
        return nintendo;
    }

    /**
     * @param nintendo the nintendo to set
     */
    public void setNintendo(Proceso nintendo) {
        this.nintendo = nintendo;
    }

    /**
     * @return the ultimo_resultado
     */
    public int getUltimo_resultado() {
        return ultimo_resultado;
    }

    /**
     * @param ultimo_resultado the ultimo_resultado to set
     */
    public void setUltimo_resultado(int ultimo_resultado) {
        this.ultimo_resultado = ultimo_resultado;
    }
}
