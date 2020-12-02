public class Computador {
    private boolean rodando;
    private Processador processador;
    private Disco disco;
    private Teclado teclado;

    private final String diretorioBackup = "./backup";
    private final String arquivoListagem = "./listagem.txt";

    public boolean isRodando() {
        return rodando;
    }

    public void setRodando(boolean rodando) {
        this.rodando = rodando;
    }

    public String getDiretorioBackup() {
        return diretorioBackup;
    }

    public String getArquivoListagem() {
        return arquivoListagem;
    }
}
