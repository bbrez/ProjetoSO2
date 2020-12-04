import java.io.IOException;

/**
 * Teclado: classe que simula um teclado,
 * eh tambem responsavel por ler mensagens feitas pelo usuario.
 */
public class Teclado extends Componente {
    private final Computador computador;

    //Construtor do teclado
    //Entrada: Referencia pro computador que possui o componente
    //Saida: Nenhuma
    //Pre-condição: Nenhuma
    //Pos-condição: Teclado criado
    public Teclado(Computador computador){
    public Teclado(Computador computador) {
        this.setName("Thread-Teclado");
        this.computador = computador;
    }

    //Relata o que o componente esta realizando
    //Entrada: Mensagem que sera impressa
    //Saida: Nenhuma
    //Pre-condição: String valida
    //Pos-condição: Relato realizado
    protected void relatar() {
        super.relatar(this.getName(), "Usuário digitou algo");
    }

    //Executa o teclado, que sera responsavel por realizar a leitura de mensagens feitas pelo usuario
    //Entrada: Nenhuma
    //Saida: Nenhuma
    //Pre-condição: Deve existir um teclado
    //Pos-condição: Teclado executado
    public void run() {
        while (computador.isRodando()) {
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.relatar();
        }
    }
}
