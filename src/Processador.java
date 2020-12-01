public class Processador extends Componente {
    private final Computador computador;

    public Processador(Computador computador) {
        this.computador = computador;
    }

    protected void report(String mensagem) {
        super.report("Processador", mensagem);
    }

    public void run() {
        while (computador.isRodando()) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.report("Executei uma instrução");
        }
    }
}
