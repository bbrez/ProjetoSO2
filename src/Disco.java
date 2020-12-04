import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Disco: classe que simula um disco,
 * eh tambem responsavel por criar e manter a pasta de backup.
 */
public class Disco extends Componente {
    private final Computador computador; //Referência ao computador dono do disco

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); //Formatador para data e hora usado no log do backup
    private int semaforo = 0; //Semáforo do disco, se for maior que 0, executa um backup e decrementa
    private final int delay;

    //Construtor do disco
    //Entrada: Referencia pro computador que possui o componente
    //Saida: Nenhuma
    //Pre-condição: Nenhuma
    //Pos-condição: Disco criado
    public Disco(Computador computador, int delayDisco) {
        this.setName("Thread-Disco");
        this.computador = computador;
        this.delay = delayDisco;
    }

    //Relata o que o componente esta realizando
    //Entrada: Mensagem que sera impressa
    //Saida: Nenhuma
    //Pre-condição: String valida
    //Pos-condição: Relato realizado    
    protected void relatar(String mensagem) {
        super.relatar(this.getName(), mensagem);
    }

    //Retorna o nome do arquivo que sera removido da pasta
    //Entrada: Nenhuma
    //Saida: String com o nome do arquivo
    //Pre-condição: Deve existir a pasta no diretorio
    //Pos-condição: Nome do arquivo retornado    
    private String aRemover() {

        String[] arquivos;
        File diretorio = new File(this.computador.getDiretorioBackup());

        FilenameFilter filtro = (f, name) -> name.startsWith("arquivo");

        arquivos = diretorio.list(filtro);
        assert arquivos != null;
        int len = arquivos.length;

        if (len > this.computador.getMaxBackup()) {
            return arquivos[0];
        } else {
            return null;
        }

    }

    //Retorna o nome do arquivo que sera inserido na pasta
    //Entrada: Nenhuma
    //Saida: String com o nome do arquivo
    //Pre-condição: Deve existir a pasta no diretorio
    //Pos-condição: Nome do arquivo retornado
    private String aInserir() {

        File diretorio = new File(this.computador.getDiretorioBackup());

        FilenameFilter filtro = (f, name) -> name.startsWith("arquivo");

        String[] arquivos;
        arquivos = diretorio.list(filtro);
        assert arquivos != null;

        String insert;
        if (arquivos.length > 0) {
            insert = arquivos[arquivos.length - 1];
            insert = insert.replaceAll("\\D+", "");
            int n = Integer.parseInt(insert);
            n += 1;
            insert = "arquivo" + n + ".txt";
        } else {
            insert = "arquivo1.txt";
        }

        return insert;
    }

    //Função que atualiza o semaforo para fazer um pedido de backup
    //Entrada: Nenhuma
    //Saida: Nenhuma
    //Pre-condição: Thread em execução
    //Pos-condição: Semaforo atualizado
    public synchronized void execBackup() {
        ++this.semaforo;
        notifyAll();
    }

    //Executa o disco, que ira aguardar ate o processador solicitar o backup
    //Entrada: Nenhuma
    //Saida: Nenhuma
    //Pre-condição: Deve existir um disco
    //Pos-condição: Disco executado       
    public synchronized void run() {
        while (computador.isRodando()) {
            if(semaforo > 0){
                --this.semaforo;
            }

            while (this.semaforo == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            this.relatar("Comecei uma volta");

            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(this.computador.getArquivoListagem(), true))) {
                escritor.write("Timestamp:" + dtf.format(LocalDateTime.now()) + "\n");
                escritor.write("Arquivos:" + "\n");

                File diretorio = new File(this.computador.getDiretorioBackup());
                for (File f : Objects.requireNonNull(diretorio.listFiles())) {
                    escritor.write("    " + f.getName() + "\n");
                }
                escritor.write("------------------------------\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Files.copy(Path.of(this.computador.getArquivoListagem()), Path.of(this.computador.getDiretorioBackup() + "/" + this.aInserir()));

                String remover = this.aRemover();
                if (remover != null) {
                    Files.delete(Path.of(this.computador.getDiretorioBackup() + "/" + remover));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.relatar("Terminei a volta");

        }
    }
}
