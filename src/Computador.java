import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Computador: classe principal que junta os componentes para simular um computador,
 * eh tambem responsavel por executar o programa.
 */
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

    //Construtor do computador, alem de inicializar os componentes, a pasta e o arquivo que contem a lista da pasta
    //Entrada: Nenhuma
    //Saida: Nenhuma
    //Pre-condição: Arquivo de configuração lido com sucesso
    //Pos-condição: Computador criado, e componentes, pasta e arquivo incializados
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

    //Executa os componentes e liga a thread
    //Entrada: Nenhuma
    //Saida: Nenhuma
    //Pre-condição: Componentes e computador devem existir
    //Pos-condição: Componentes executados
    public void ligar(){
        this.rodando = true;
        this.processador.start();
        this.disco.start();
        this.teclado.start();
    }

    //Desliga a thread
    //Entrada: Nenhuma
    //Saida: Nenhuma
    //Pre-condição: Thread ligada
    //Pos-condição: Thread desligada
    public void desligar(){
        this.rodando = false;
    }

    //Realiza a leitura da memoria em um endereço especifico
    //Entrada: Endereço da memoria
    //Saida: Memoria lida
    //Pre-condição: Endereço valido
    //Pos-condição: Memoria lida
    public int lerMemoria(int endereco){
        return this.memoria[endereco];
    }

    //Verifica se a thread esta rodando
    //Entrada: Nenhuma
    //Saida: True se estiver rodando e False se não estiver
    //Pre-condição: Computador deve existir
    //Pos-condição: Verificação realizada
    public boolean isRodando() {
        return rodando;
    }

    //Retorna uma string contendo o diretorio da pasta de backup
    //Entrada: Nenhuma
    //Saida: String contendo o diretorio
    //Pre-condição: Nenhuma
    //Pos-condição: Diretorio retornado
    public String getDiretorioBackup() {
        return diretorioBackup;
    }

    //Retorna uma string contendo o nome do arquivo com a lista da pasta
    //Entrada: Nenhuma
    //Saida: String contendo o nome do arquivo
    //Pre-condição: Arquivo deve existir
    //Pos-condição: Nome do arquivo retornado    
    public String getArquivoListagem() {
        return arquivoListagem;
    }

    //Retorna o disco
    //Entrada: Nenhuma
    //Saida: O disco presente no computador
    //Pre-condição: Disco deve existir
    //Pos-condição: Disco retornado
    public Disco getDisco() {
        return disco;
    }

    //Retorna o processador
    //Entrada: Nenhuma
    //Saida: O processador presente no computador
    //Pre-condição: Processador deve existir
    //Pos-condição: Processador retornado    
    public Processador getProcessador() {
        return processador;
    }

    //Retorna o teclado
    //Entrada: Nenhuma
    //Saida: O teclado presente no computador
    //Pre-condição: Teclado deve existir
    //Pos-condição: Teclado retornado
    public Teclado getTeclado() {
        return teclado;
    }

    //Retorna a instrução necessaria para realizar o backup
    //Entrada: Nenhuma
    //Saida: Instrução necessaria para realizar o backup
    //Pre-condição: Nenhuma
    //Pos-condição: Instrução retornada
    public int getInstBackup() {
        return instBackup;
    }

    //Retorna o tamanho da memoria
    //Entrada: Nenhuma
    //Saida: Inteiro contendo o tamanho da memoria
    //Pre-condição: Nenhuma
    //Pos-condição: Tamanho da memoria retornado
    public int getTamMemoria() {
        return tamMemoria;
    }

    //Roda o programa principal
    //Entrada: Nenhuma
    //Saida: Nenhuma
    //Pre-condição: Nenhuma
    //Pos-condição: Programa principal executado
    public static void main(String[] args) {
        try {
            Computador computador = new Computador();
            computador.ligar();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //Retorna a quantidade maxima de backups na pasta
    //Entrada: Nenhuma
    //Saida: Inteiro com a quantidade maxima de backups
    //Pre-condição: Pasta deve existir
    //Pos-condição: Quantidade maxima de backups retornada
    public int getMaxBackup() {
        return maxBackup;
    }
}
