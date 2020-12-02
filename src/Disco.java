import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Disco extends Componente {
    private final Computador computador;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public Disco(Computador computador) {
        this.computador = computador;
    }

    protected void relatar(String mensagem) {
        super.relatar("Disco", mensagem);
    }

    public String toRemove() {

        String[] arquivos;
        File myD = new File(this.computador.getDiretorioBackup());

        FilenameFilter filter = (f, name) -> name.startsWith("arquivo");

        arquivos = myD.list(filter);
        assert arquivos != null;
        int len = arquivos.length;

        if(len >= 2){
            return arquivos[0];
        }
        else{
            return null;
        }

    }

    public void run() {
        while (computador.isRodando()) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.relatar("Dei uma volta");

            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(this.computador.getArquivoListagem(), true))){
                escritor.write("Timestamp:" + dtf.format(LocalDateTime.now()));
                escritor.write("Arquivos:");

                File diretorio = new File(this.computador.getDiretorioBackup());
                for(File f: Objects.requireNonNull(diretorio.listFiles())){
                    escritor.write(f.getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            
        }
    }
}
