Como executar o programa
    1. Montar um projeto na IDE Java preferida (Recomendado IntelliJ IDEA)
    2. Compilar todos os arquivos na pasta src para serem executados com o main presente na classe Computador.java (jar ou execução pela IDE)
    3. Se desejado, configurar o programa com o arquivo config.cfg, que deve estar na pasta onde o programa será executado (working directory)
        a. O arquivo config.cfg padrão utiliza os valores recomendados e pode ser mudado de acordo com as necessidades
        b. Qualquer propriedade não encontrada utiliará o valor recomendado
        c. Se o arquivo não for encontrado todas as propriedades utiliam o valor recomendado
    4. O programa gera um arquivo e uma pasta novos na pasta onde está sendo executado
        a. O arquivo (nome recomendado listagem.txt) é a listagem de arquivos de backup na pasta
        b. A pasta (nome recomendado backup) é para onde é copiado o arquivo listagem para backup
    5. Para parar o programa um sinal SIGINT deve ser enviado a ele (ctrl+c em terminais unix), o programa captura o sinal e tenta encerrar de forma correta