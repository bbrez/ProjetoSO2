import java.io.File;
import java.io.IOException;

public class Computador {
    private boolean rodando;
    private final Processador processador;
    private final Disco disco;
    private final Teclado teclado;

    private final String diretorioBackup = "./backup";
    private final String arquivoListagem = "./listagem.txt";

    public Computador() throws IOException{
        this.rodando = false;

        File dir = new File(this.diretorioBackup);
        if(!dir.exists()){
            if(!dir.mkdirs()) throw new IOException("Falha ao criar diretorio " + this.diretorioBackup);
        }

        File arq = new File(this.arquivoListagem);
        if(!arq.exists()){
                if(!arq.createNewFile()) throw new IOException("Falha ao criar arquivo " + this.arquivoListagem);
        }

        this.processador = new Processador(this);
        this.disco = new Disco(this);
        this.teclado = new Teclado(this);
    }

    public void ligar(){
        this.rodando = true;
        this.processador.start();
        this.disco.start();
        this.teclado.start();
    }

    public void desligar(){
        this.rodando = false;
    }

    public boolean isRodando() {
        return rodando;
    }

    public String getDiretorioBackup() {
        return diretorioBackup;
    }

    public String getArquivoListagem() {
        return arquivoListagem;
    }

    public static void main(String[] args) {
        try {
            Computador computador = new Computador();
            computador.ligar();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
