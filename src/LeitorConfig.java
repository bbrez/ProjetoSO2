import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * LeitorConfig: Classe que ira ler o arquivo de configurações
 * eh tambem responsavel por montar o computador
 */


public class LeitorConfig {
    //Lê as variaveis do arquivo de configurações e monta o computador
    //Entrada: Nome do arquivo de configuração
    //Saida: Computador montado
    //Pre-condição: Nenhuma
    //Pos-condição: Variaveis lidas e computador montado
    public static Computador MontarComputador(String arquivo) throws IOException {
        String arquivoListagem = "./listagem.txt", diretorioBackup = "./backup";
        int tamMemoria = 30, maxInst = 10, instBackup = 5, maxBackup = 3, delayProcessador = 100, delayDisco = 1000;
        int[] initMemoria = null;

        if (arquivo != null) {
            try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
                while (leitor.ready()) { //Lê do arquivo enquando ele não terminou
                    String linha = leitor.readLine();
                    if (linha.charAt(0) == '#') continue; //Linhas começadas com # são consideradas comentário
                    String[] tokens = linha.split("="); //Separa as linhas em nome e valor

                    //System.out.println(Arrays.toString(tokens)); //somente para debug
                    double aux;
                    switch (tokens[0]) {
                        case "freqProcessador":
                            aux = 1 / Float.parseFloat(tokens[1]);
                            delayProcessador = (int) (aux * 1000);
                            break;
                        case "rpmDisco":
                            aux = 1 / (Float.parseFloat(tokens[1]) / 60);
                            delayDisco = (int) (aux * 1000);
                            break;
                        case "arquivoListagem":
                            arquivoListagem = tokens[1];
                            break;
                        case "diretorioBackup":
                            diretorioBackup = tokens[1];
                            break;
                        case "tamMemoria":
                            tamMemoria = Integer.parseInt(tokens[1]);
                            break;
                        case "maxInst":
                            maxInst = Integer.parseInt(tokens[1]);
                            break;
                        case "instBackup":
                            instBackup = Integer.parseInt(tokens[1]);
                            break;
                        case "maxBackup":
                            maxBackup = Integer.parseInt(tokens[1]);
                            break;
                        case "initMemoria":
                            initMemoria = Arrays.stream(tokens[1].split(",")).mapToInt(Integer::parseInt).toArray();
                            tamMemoria = initMemoria.length;
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + tokens[0]);
                    }
                }
            }
        }

        return new Computador(delayProcessador, delayDisco, arquivoListagem, diretorioBackup, tamMemoria, maxInst, instBackup, maxBackup, initMemoria);
    }
}
