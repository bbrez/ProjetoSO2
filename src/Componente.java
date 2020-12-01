public abstract class Componente extends Thread {
    protected void report(String nome, String mensagem) {
        System.out.println("[" + nome + "]: " + mensagem);
    }
}
