import java.io.IOException;
import java.util.Scanner;

public class Teclado extends Componente {
    private final Computador computador;

    public Teclado(Computador computador){
        this.computador = computador;
    }

    protected void report(String mensagem){
        super.report("Teclado", mensagem);
    }

    public void run() {
        while(computador.isRodando()){
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.report("Usuário digitou algo");
        }
    }
}
