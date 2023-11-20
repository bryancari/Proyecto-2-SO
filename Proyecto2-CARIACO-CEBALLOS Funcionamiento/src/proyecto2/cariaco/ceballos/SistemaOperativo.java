package proyecto2.cariaco.ceballos;

import java.util.concurrent.Semaphore;

/**
 * Clase definida para cumplir el rol del sistema operativo y así organizar los
 * personajes y las colas, pasarselas al procesador
 *
 * @author Bryan
 */
public class SistemaOperativo extends Thread {

    private Cola nivel_1_N;
    private Cola nivel_2_N;
    private Cola nivel_3_N;
    private Cola refuerzo_N;
    private Cola nivel_1_C;
    private Cola nivel_2_C;
    private Cola nivel_3_C;
    private Cola refuerzo_C;
    private int identificador_C;
    private int identificador_N;
    private int ciclos;
    private Proceso capcom;
    private Proceso nintendo;
    private int ultimo_resultado;
    private int ganadores_capcom;
    private int ganadores_nintendo;
    private final Semaphore sema;
    private final Interfaz inter;

    /**
     * Constructor del Sistema Operativo
     *
     * @param sema Semaforo dado por la clase area, es el mismo que usa el
     * procesador
     * @param inicial Número de personajes a crear al iniciar el programa para
     * cada compañía
     * @param interfaz es la inferfaz en la cual va a trabajar
     */
    public SistemaOperativo(Semaphore sema, int inicial, Interfaz interfaz) {
        this.inter = interfaz;

        this.sema = sema;
        this.nivel_1_N = new Cola();
        this.nivel_2_N = new Cola();
        this.nivel_3_N = new Cola();
        this.refuerzo_N = new Cola();
        this.nivel_1_C = new Cola();
        this.nivel_2_C = new Cola();
        this.nivel_3_C = new Cola();
        this.refuerzo_C = new Cola();
        this.identificador_C = 1;
        this.identificador_N = 1;
        this.ciclos = 0;
        this.ganadores_capcom = 0;
        this.ganadores_nintendo = 0;

        int aux = inicial;
        while (aux != 0) {
            this.crearPersonaje("C");
            this.crearPersonaje("N");
            aux--;
        }

        if (nivel_1_C.esVacia()) {
            if (nivel_2_C.esVacia()) {
                if (nivel_3_C.esVacia()) {
                } else {
                    capcom = nivel_3_C.desencolar();
                }
            } else {
                capcom = nivel_2_C.desencolar();
            }
        } else {
            capcom = nivel_1_C.desencolar();
        }

        if (nivel_1_N.esVacia()) {
            if (nivel_2_N.esVacia()) {
                if (nivel_3_N.esVacia()) {
                } else {
                    nintendo = nivel_3_N.desencolar();
                }
            } else {
                nintendo = nivel_2_N.desencolar();
            }
        } else {
            nintendo = nivel_1_N.desencolar();
        }

        inter.getProcesador().setCapcom(capcom);
        inter.getProcesador().setNintendo(nintendo);

        inter.cola1Capcom.setText(nivel_1_C.imprimir());
        inter.cola2Capcom.setText(nivel_2_C.imprimir());
        inter.cola3Capcom.setText(nivel_3_C.imprimir());

        inter.cola1Nintendo.setText(nivel_1_N.imprimir());
        inter.cola2Nintendo.setText(nivel_2_N.imprimir());
        inter.cola3Nintendo.setText(nivel_3_N.imprimir());

        inter.peleadorNintendo.setText(nintendo.getId());
        inter.peleadorCapcom.setText(capcom.getId());
    }

    /**
     * Función para crear un personaje/proceso nuevo, la validación del ID se
     * hace mediante un contador para cada empresa que tiene como atributos la
     * clase
     *
     * @param empresa Un string igual a C o N dependiendo si es de la empresa
     * Capcom o Nintendo respectivamente
     */
    public void crearPersonaje(String empresa) {
        int auxHabilidad = (int) (Math.floor(Math.random() * 100));
        int auxVida = (int) (Math.floor(Math.random() * 100));
        int auxFuerza = (int) (Math.floor(Math.random() * 100));
        int auxAgilidad = (int) (Math.floor(Math.random() * 100));

        int auxPrioridad = 0;

        int numHabilidad = 0;
        int numVida = 0;
        int numFuerza = 0;
        int numAgilidad = 0;

        if (auxHabilidad <= 60) {
            auxPrioridad++;
            numHabilidad++;
        }
        if (auxVida <= 70) {
            auxPrioridad++;
            numVida++;
        }
        if (auxFuerza <= 50) {
            auxPrioridad++;
            numFuerza++;
        }
        if (auxAgilidad <= 40) {
            auxPrioridad++;
            numFuerza++;
        }

        /*
        Los rangos para cada característica son los siguientes:
            -Habilidad:
                Alto nivel: 100-75
                Medio nivel: 74-45
                Bajo nivel: 44-5
            -Vida:
                Alto nivel: 1000-700
                Medio nivel: 699-400
                Bajo nivel: 399-100
            -Fuerza:
                Alto nivel: 5000-3800
                Medio nivel: 3799-2200
                Bajo nivel: 2199-500        
            -Agilidad:
                Alto nivel: 800-650
                Medio nivel: 649-400
                Bajo nivel: 399-100
         */
        switch (auxPrioridad) {
            /*
            Si auxPrioridad queda en 0 es porque el personaje es de la peor calidad, todos sus puntajes serán de rango de bajo nivel           
             */
            case 0:

                auxHabilidad = (int) (Math.floor(Math.random() * (44 - 5 + 1) + 5));
                auxVida = (int) (Math.floor(Math.random() * (399 - 100 + 1) + 100));
                auxFuerza = (int) (Math.floor(Math.random() * (2199 - 500 + 1) + 500));
                auxAgilidad = (int) (Math.floor(Math.random() * (399 - 100 + 1) + 100));

                auxPrioridad = 3;
                break;
            /*
            Si auxPrioridad es igual a 1 o 2 es porque cumplió una o dos condiciones y por eso es de calidad media 
            Tendrá una o dos características de alto nivel y las demás de medio nivel
             */
            case 1:
                if (numHabilidad == 1) {
                    auxHabilidad = (int) (Math.floor(Math.random() * (100 - 75 + 1) + 75));
                } else {
                    auxHabilidad = (int) (Math.floor(Math.random() * (74 - 45 + 1) + 45));
                }

                if (numVida == 1) {
                    auxVida = (int) (Math.floor(Math.random() * (1000 - 700 + 1) + 700));
                } else {
                    auxVida = (int) (Math.floor(Math.random() * (699 - 400 + 1) + 400));
                }
                if (numFuerza == 1) {
                    auxFuerza = (int) (Math.floor(Math.random() * (5000 - 3800 + 1) + 3800));
                } else {
                    auxFuerza = (int) (Math.floor(Math.random() * (3799 - 2200 + 1) + 2200));
                }
                if (numAgilidad == 1) {
                    auxAgilidad = (int) (Math.floor(Math.random() * (800 - 650 + 1) + 650));
                } else {
                    auxAgilidad = (int) (Math.floor(Math.random() * (649 - 400 + 1) + 400));
                }
                auxPrioridad = 2;
                break;
            /*
             Si cae en el else es porque tiene 3 o 4 condiciones cumplidas lo que significa que es de calidad el personaje
                Entonces tendrá 3 o 4 características de alto nivel y las demás de calidad media
                
             */
            default:
                if (numHabilidad == 1) {
                    auxHabilidad = (int) (Math.floor(Math.random() * (100 - 75 + 1) + 75));
                } else {
                    auxHabilidad = (int) (Math.floor(Math.random() * (74 - 45 + 1) + 45));
                }

                if (numVida == 1) {
                    auxVida = (int) (Math.floor(Math.random() * (1000 - 700 + 1) + 700));
                } else {
                    auxVida = (int) (Math.floor(Math.random() * (699 - 400 + 1) + 400));
                }
                if (numFuerza == 1) {
                    auxFuerza = (int) (Math.floor(Math.random() * (5000 - 3800 + 1) + 3800));
                } else {
                    auxFuerza = (int) (Math.floor(Math.random() * (3799 - 2200 + 1) + 2200));
                }
                if (numAgilidad == 1) {
                    auxAgilidad = (int) (Math.floor(Math.random() * (800 - 650 + 1) + 650));
                } else {
                    auxAgilidad = (int) (Math.floor(Math.random() * (649 - 400 + 1) + 400));
                }
                auxPrioridad = 1;
                break;
        }

        if ("C".equals(empresa)) {
            Proceso nProcesoC = new Proceso("C-" + String.valueOf(getIdentificador_C()), auxPrioridad, auxHabilidad, auxVida, auxFuerza, auxAgilidad);
            identificador_C++;
            switch (auxPrioridad) {
                case 1:
                    getNivel_1_C().encolar(nProcesoC);
                    break;
                case 2:
                    getNivel_2_C().encolar(nProcesoC);
                    break;
                default:
                    getNivel_3_C().encolar(nProcesoC);
                    break;
            }
        } else {
            Proceso nProcesoN = new Proceso("N-" + String.valueOf(getIdentificador_N()), auxPrioridad, auxHabilidad, auxVida, auxFuerza, auxAgilidad);
            identificador_N++;
            switch (auxPrioridad) {
                case 1:
                    getNivel_1_N().encolar(nProcesoN);
                    break;
                case 2:
                    getNivel_2_N().encolar(nProcesoN);
                    break;
                default:
                    getNivel_3_N().encolar(nProcesoN);
                    break;
            }
        }
    }

    @Override
    public void run() {
        try {
            sleep(2000);
            while (true) {
                sema.acquire(1);
                inter.getProcesador().setEstado_actual("Esperando");
                inter.estadoProcesador.setText("Esperando");
                inter.resultado.setText("Esperando batalla");
                System.out.println("Paso 5: Setea espera en interfaz");

                ultimo_resultado = inter.getUltimo_resultado();

                int auxSalir = (int) (Math.floor(Math.random() * 100));
                if (!refuerzo_C.esVacia()) {
                    Proceso auxProcesoC = refuerzo_C.desencolar();
                    if (auxSalir <= 40) {
                        nivel_1_C.encolar(auxProcesoC);
                        System.out.println("Paso 6: regresan a cola 1 desde refuerzo el de Capcom");
                    } else {
                        refuerzo_C.encolar(auxProcesoC);
                        System.out.println("Paso 6: regresan a refuerzo los de refuerzo el de capcom");
                    }
                    System.out.println("Paso 6: No sale de refuerzo porque está vacía");
                }

                if (!refuerzo_N.esVacia()) {
                    Proceso auxProcesoN = refuerzo_N.desencolar();
                    if (auxSalir <= 40) {
                        nivel_1_N.encolar(auxProcesoN);
                        System.out.println("Paso 7: regresan a cola 1 desde refuerzo el de nintendo");
                    } else {
                        refuerzo_N.encolar(auxProcesoN);
                        System.out.println("Paso 7: regresan a refuerzo los de refuerzo el de nintendo");
                    }
                    System.out.println("Paso 7: No sale de refuerzo porque está vacía");
                }

                nivel_2_N.moverCola(nivel_1_N);
                nivel_3_N.moverCola(nivel_2_N);
                nivel_2_C.moverCola(nivel_1_C);
                nivel_3_C.moverCola(nivel_2_C);
                System.out.println("Paso 8: Se movieron las colas");

                switch (ultimo_resultado) {
                    case 1:
                        ganadores_capcom++;
                        System.out.println("Paso 9: Aumento ganadores capcom");
                        break;
                    case 2:
                        ganadores_nintendo++;
                        System.out.println("Paso 9: Aumento ganadores nintendo");
                        break;
                    case 3:
                        Proceso auxNuevoN = nintendo;
                        Proceso auxNuevoC = capcom;
                        nivel_1_N.encolar(auxNuevoN);
                        nivel_1_C.encolar(auxNuevoC);
                        System.out.println("Paso 9: Encolados en sus colas 1 por empate");
                        break;
                    default:
                        Proceso auxRefuN = nintendo;
                        Proceso auxRefuC = capcom;
                        refuerzo_C.encolar(auxRefuC);
                        refuerzo_N.encolar(auxRefuN);
                        System.out.println("Paso 9: Encolados en sus colas de refuerzo");
                        break;
                }

                if (ciclos == 1) {
                    int auxProba = (int) (Math.floor(Math.random() * 100));
                    if (auxProba <= 80) {
                        crearPersonaje("N");
                        crearPersonaje("C");
                    }
                    System.out.println("Paso 10: Hubo intento de crear personajes");
                    ciclos--;
                } else {
                    System.out.println("Paso 10: Hubo espera de ciclo para crear personaje");
                    ciclos++;
                }

                if (nivel_1_C.esVacia()) {
                    if (nivel_2_C.esVacia()) {
                        if (nivel_3_C.esVacia()) {
                            System.out.println("Todas las colas vacías");
                        } else {
                            capcom = nivel_3_C.desencolar();
                            System.out.println("Paso 11: Encontrado contrincante en cola 3 Capcom");
                        }
                    } else {
                        capcom = nivel_2_C.desencolar();
                        System.out.println("Paso 11: Encontrado contrincante en cola 2 Capcom");
                    }
                } else {
                    capcom = nivel_1_C.desencolar();
                    System.out.println("Paso 11: Encontrado contrincante en cola 1 Capcom");
                }

                if (nivel_1_N.esVacia()) {
                    if (nivel_2_N.esVacia()) {
                        if (nivel_3_N.esVacia()) {
                            System.out.println("Todas las colas vacías");
                        } else {
                            nintendo = nivel_3_N.desencolar();
                            System.out.println("Paso 12: Encontrado contrincante en cola 3 Nintendo");
                        }
                    } else {
                        nintendo = nivel_2_N.desencolar();
                        System.out.println("Paso 12: Encontrado contrincante en cola 2 Nintendo");
                    }
                } else {
                    nintendo = nivel_1_N.desencolar();
                    System.out.println("Paso 12: Encontrado contrincante en cola 1 Nintendo");
                }

                inter.getProcesador().setCapcom(capcom);
                inter.getProcesador().setNintendo(nintendo);
                System.out.println("Paso 13: Seteo en interfaz de los competidores");

                inter.cola1Capcom.setText(nivel_1_C.imprimir());
                inter.cola2Capcom.setText(nivel_2_C.imprimir());
                inter.cola3Capcom.setText(nivel_3_C.imprimir());
                inter.refuerzoCapcom.setText(refuerzo_C.imprimir());
                inter.ganadoresCapcom.setText(String.valueOf(ganadores_capcom));

                inter.cola1Nintendo.setText(nivel_1_N.imprimir());
                inter.cola2Nintendo.setText(nivel_2_N.imprimir());
                inter.cola3Nintendo.setText(nivel_3_N.imprimir());
                inter.refuerzoNintendo.setText(refuerzo_N.imprimir());
                inter.ganadoresNintendo.setText(String.valueOf(ganadores_nintendo));

                inter.peleadorCapcom.setText(capcom.getId());
                inter.peleadorNintendo.setText(nintendo.getId());
                System.out.println("Paso 14: Seteo de colas ");

                sema.release();
                //sleep(4500 + 1000 * inter.getTiempoProcesador()); //Tres segundos de espera para que se pueda observar el "Esperando del procesador"
            }
        } catch (Exception e) {

        }
    }

    /**
     * @return the nivel_1_N
     */
    public Cola getNivel_1_N() {
        return nivel_1_N;
    }

    /**
     * @param nivel_1_N the nivel_1_N to set
     */
    public void setNivel_1_N(Cola nivel_1_N) {
        this.nivel_1_N = nivel_1_N;
    }

    /**
     * @return the nivel_2_N
     */
    public Cola getNivel_2_N() {
        return nivel_2_N;
    }

    /**
     * @param nivel_2_N the nivel_2_N to set
     */
    public void setNivel_2_N(Cola nivel_2_N) {
        this.nivel_2_N = nivel_2_N;
    }

    /**
     * @return the nivel_3_N
     */
    public Cola getNivel_3_N() {
        return nivel_3_N;
    }

    /**
     * @param nivel_3_N the nivel_3_N to set
     */
    public void setNivel_3_N(Cola nivel_3_N) {
        this.nivel_3_N = nivel_3_N;
    }

    /**
     * @return the refuerzo_N
     */
    public Cola getRefuerzo_N() {
        return refuerzo_N;
    }

    /**
     * @param refuerzo_N the refuerzo_N to set
     */
    public void setRefuerzo_N(Cola refuerzo_N) {
        this.refuerzo_N = refuerzo_N;
    }

    /**
     * @return the nivel_1_C
     */
    public Cola getNivel_1_C() {
        return nivel_1_C;
    }

    /**
     * @param nivel_1_C the nivel_1_C to set
     */
    public void setNivel_1_C(Cola nivel_1_C) {
        this.nivel_1_C = nivel_1_C;
    }

    /**
     * @return the nivel_2_C
     */
    public Cola getNivel_2_C() {
        return nivel_2_C;
    }

    /**
     * @param nivel_2_C the nivel_2_C to set
     */
    public void setNivel_2_C(Cola nivel_2_C) {
        this.nivel_2_C = nivel_2_C;
    }

    /**
     * @return the nivel_3_C
     */
    public Cola getNivel_3_C() {
        return nivel_3_C;
    }

    /**
     * @param nivel_3_C the nivel_3_C to set
     */
    public void setNivel_3_C(Cola nivel_3_C) {
        this.nivel_3_C = nivel_3_C;
    }

    /**
     * @return the refuerzo_C
     */
    public Cola getRefuerzo_C() {
        return refuerzo_C;
    }

    /**
     * @param refuerzo_C the refuerzo_C to set
     */
    public void setRefuerzo_C(Cola refuerzo_C) {
        this.refuerzo_C = refuerzo_C;
    }

    /**
     * @return the identificador_C
     */
    public int getIdentificador_C() {
        return identificador_C;
    }

    /**
     * @param identificador_C the identificador_C to set
     */
    public void setIdentificador_C(int identificador_C) {
        this.identificador_C = identificador_C;
    }

    /**
     * @return the identificador_N
     */
    public int getIdentificador_N() {
        return identificador_N;
    }

    /**
     * @param identificador_N the identificador_N to set
     */
    public void setIdentificador_N(int identificador_N) {
        this.identificador_N = identificador_N;
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

    /**
     * @return the ganadores_capcom
     */
    public int getGanadores_capcom() {
        return ganadores_capcom;
    }

    /**
     * @param ganadores_capcom the ganadores_capcom to set
     */
    public void setGanadores_capcom(int ganadores_capcom) {
        this.ganadores_capcom = ganadores_capcom;
    }

    /**
     * @return the ganadores_nintendo
     */
    public int getGanadores_nintendo() {
        return ganadores_nintendo;
    }

    /**
     * @param ganadores_nintendo the ganadores_nintendo to set
     */
    public void setGanadores_nintendo(int ganadores_nintendo) {
        this.ganadores_nintendo = ganadores_nintendo;
    }
}
