public abstract class Componente extends Thread {
    protected void relatar(String nome, String mensagem) {
        System.out.println("[" + nome + "]: " + mensagem);
    }
}
