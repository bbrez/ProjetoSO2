public class Disco extends Componente {
    private final Computador computador;

    public Disco(Computador computador){
        this.computador = computador;
    }

    protected void report(String mensagem){
        super.report("Disco", mensagem);
    }

    public void run(){
        while(computador.isRodando()){
            try {
                sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            this.report("Dei uma volta");
        }
    }
}
