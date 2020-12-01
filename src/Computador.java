public class Computador {
    private boolean rodando;
    private Processador processador;
    private Disco disco;
    private Teclado teclado;

    public boolean isRodando() {
        return rodando;
    }

    public void setRodando(boolean rodando) {
        this.rodando = rodando;
    }
}
