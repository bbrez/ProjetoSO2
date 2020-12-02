import java.io.IOException;

public class Teclado extends Componente {
    private final Computador computador;

    public Teclado(Computador computador){
        this.computador = computador;
    }

    protected void relatar(String mensagem){
        super.relatar("Teclado", mensagem);
    }

    public void run() {
        while(computador.isRodando()){
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.relatar("Usuário digitou algo");
        }
    }
}
