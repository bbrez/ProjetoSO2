import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Computador {
    private boolean rodando;

    private final Processador processador;
    private final Disco disco;
    private final Teclado teclado;
    private final int[] memoria;

    private final String arquivoListagem = "./listagem.txt"; //Arquivo que lista o conteudo do diretorio de backup
    private final String diretorioBackup = "./backup"; //Diretorio onde será feita a copia do arquivo de listagem
    private final int tamMemoria = 30; //Tamanho da memória do computador
    private final int[] initMemoria = null;  //Valores utilizados para inicializar a memoria, caso seja null, utiliza valores aleatorios
    private final int maxInst = 10; //Limite superior para as instruções, não inclusivo
    private final int instBackup = 5; //Qual instrução deve fazer o disco executar um backup
    private final int maxBackup = 3; //Numero máximo de arquivos na pasta backup

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

        if(this.initMemoria == null){
            Random r = new Random();
            memoria = new int[this.tamMemoria];
            for(int i = 0; i < memoria.length ; ++i){
                memoria[i] = r.nextInt(this.maxInst); //Gera valores aleatórios para memória
            }
        } else {
            this.memoria = this.initMemoria;
        }
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

    public int lerMemoria(int endereco){
        return this.memoria[endereco];
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

    public Disco getDisco() {
        return disco;
    }

    public Processador getProcessador() {
        return processador;
    }

    public Teclado getTeclado() {
        return teclado;
    }

    public int getInstBackup() {
        return instBackup;
    }

    public int getTamMemoria() {
        return tamMemoria;
    }

    public static void main(String[] args) {
        try {
            Computador computador = new Computador();
            computador.ligar();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public int getMaxBackup() {
        return maxBackup;
    }
}
