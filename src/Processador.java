public class Processador extends Componente {
    private final Computador computador;

    private int contPrograma = 0; //Contador de programa (Program counter)

    public Processador(Computador computador) {
        this.setName("Thread-Processador");
        this.computador = computador;
    }

    protected void relatar(String mensagem) {
        super.relatar(this.getName(), mensagem);
    }

    public void run() {
        while (computador.isRodando()) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.relatar("Executei uma instrução"); //Relata que uma instrução foi executada
            this.relatar("Valor lido: " + this.computador.lerMemoria(contPrograma++)); //Lê um endereço na memória e relata oque foi lido
        }
    }
}
