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


            this.relatar("Executei uma instrução"); //Relata que uma instrução foi executada

            if (this.contPrograma < this.computador.getTamMemoria()) {
                int instrucao = this.computador.lerMemoria(this.contPrograma++); //Lê um endereço na memória e avança o contador de programa
                if (instrucao == this.computador.getInstBackup()) {
                    this.relatar("Valor lido: " + instrucao + " [Instrução de Backup]");
                    this.computador.getDisco().execBackup();
                } else {
                    this.relatar("Valor lido: " + instrucao);
                }
            }

            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
