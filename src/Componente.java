/**
 * Componente: classe que serve como base para todos os outros componentes (Teclado, Processador e Disco).
 */
public abstract class Componente extends Thread {
    protected void relatar(String nome, String mensagem) {
        System.out.println("[" + nome + "]: " + mensagem);
    }
}
