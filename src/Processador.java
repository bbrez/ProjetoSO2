/**
 * Processador: classe que simula o funcionamento de um processador,
 * eh tambem responsavel pelas instruções.
 */
public class Processador extends Componente {
    private final Computador computador;

    private final int delay;
    private int contPrograma = 0; //Contador de programa (Program counter)

    //Construtor do processador
    //Entrada: Referencia pro computador que possui o componente
    //Saida: Nenhuma
    //Pre-condição: Nenhuma
    //Pos-condição: Processador criado
    public Processador(Computador computador, int delayProcessador) {
        this.setName("Thread-Processador");
        this.computador = computador;
        this.delay = delayProcessador;
    }

    //Relata o que o componente esta realizando
    //Entrada: Mensagem que sera impressa
    //Saida: Nenhuma
    //Pre-condição: String valida
    //Pos-condição: Relato realizado
    protected void relatar(String mensagem) {
        super.relatar(this.getName(), mensagem);
    }

    //Executa o processador, que sera responsavel por receber e executar instruções
    //Entrada: Nenhuma
    //Saida: Nenhuma
    //Pre-condição: Deve existir um processador
    //Pos-condição: Processador executado    
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
                sleep(this.delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
