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


    public Disco(Computador computador) {
        this.setName("Thread-Disco");
        this.computador = computador;
    }

    protected void relatar(String mensagem) {
        super.relatar(this.getName(), mensagem);
    }

    public String aRemover() {

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

    public String aInserir() {

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

    public void run() {
        while (computador.isRodando()) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.relatar("Dei uma volta");

            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(this.computador.getArquivoListagem(), true))) {
                escritor.write("Timestamp:" + dtf.format(LocalDateTime.now()) + "\n");
                escritor.write("Arquivos:" + "\n");

                File diretorio = new File(this.computador.getDiretorioBackup());
                for (File f : Objects.requireNonNull(diretorio.listFiles())) {
                    escritor.write(f.getName() + "\n");
                }
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

        }
    }
}
