public class Processador extends Componente {
    private final Computador computador;

    public Processador(Computador computador) {
        this.computador = computador;
    }

    protected void relatar(String mensagem) {
        super.relatar("Processador", mensagem);
    }

    public void run() {
        while (computador.isRodando()) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.relatar("Executei uma instrução");
        }
    }
}
