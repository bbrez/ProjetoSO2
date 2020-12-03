import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Disco extends Componente {
    private final Computador computador; //Referência ao computador dono do disco

    private final int maxBackup = 3; //Numero maximo de arquivos na pasta backup
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); //Formatador para data e hora usado no log do backup
    private boolean execOperacao = false; //Semáforo do disco, é verdadeiro quando está executando alguma operação


    public Disco(Computador computador) {
        this.setName("Thread-Disco");
        this.computador = computador;
    }

    protected void relatar(String mensagem) {
        super.relatar(this.getName(), mensagem);
    }

    private String aRemover() {

        String[] arquivos;
        File diretorio = new File(this.computador.getDiretorioBackup());

        FilenameFilter filtro = (f, name) -> name.startsWith("arquivo");

        arquivos = diretorio.list(filtro);
        assert arquivos != null;
        int len = arquivos.length;

        if (len > this.maxBackup) {
            return arquivos[0];
        } else {
            return null;
        }

    }

    private String aInserir() {

        File diretorio = new File(this.computador.getDiretorioBackup());

        FilenameFilter filtro = (f, name) -> name.startsWith("arquivo");

        String[] arquivos;
        arquivos = diretorio.list(filtro);
        assert arquivos != null;

        String insert = null;
        if(arquivos.length > 0){
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

    public synchronized void execBackup(){
        if(this.execOperacao){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.execOperacao = true;
        notifyAll();
    }

    public synchronized void run() {
        while (computador.isRodando()) {
            this.execOperacao = false;
            notifyAll();

            while(!this.execOperacao){
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
                if(remover != null){
                    Files.delete(Path.of(this.computador.getDiretorioBackup() + "/" + remover));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.relatar("Terminei a volta");

        }
    }
}
