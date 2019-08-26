[![Upflux](https://upflux.net/wp-content/uploads/2018/04/logo-upflux-108x47-1.png)](https://upflux.net/)

##### Versão: 0.0.1-SNAPSHOT
Projeto-teste para análise de codificação segundo especificações recebidas.

  1. __Execução do projeto__

Baixe o este [jar](https://github.com/armange/java-br-com-armange-backend-api-examples/raw/upflux/backend-api-examples-upf-api/src/assets/backend-api-examples-upf-api-all-0.0.1-SNAPSHOT.jar) para executar o projeto.

  2. __Configuração__
  
As seguintes opções de configuração podem ser utilizadas como propriedades do sistema:
  - "br.com.armange.http.server.port"
  - "br.com.armange.base.directory"
  - "br.com.armange.api.version"
     
Exemplo:
  - *__java -Dbr.com.armange.http.server.port=9090 -jar backend-api-examples-upf-api-all-0.0.1-SNAPSHOT.jar__*
       
  3. __Chamadas para os endpoints__
  
Endpoints:
- __*{host}/{apiVersion}/{id}/from*__
Recurso para o carregamento de um primeiro arquivo(versão original do arquivo) textual para posterior comparação. __*Utilize a chave [text-file] para enviar arquivos.*__
Exemplo:
```java
final FileDataBodyPart filePart = new FileDataBodyPart("text-file", file);
```
Ou através do postman:

[![Postman](https://github.com/armange/java-br-com-armange-backend-api-examples/blob/upflux/backend-api-examples-upf-api/src/assets/postman.png)](https://github.com/armange/java-br-com-armange-backend-api-examples/edit/upflux/README.md)
- __*{host}/{apiVersion}/{id}/to*__
Recurso para o carregamento de um segundo arquivo(versão revisada) textual para posterior comparação.
- __*{host}/{apiVersion}/{id}/diff*__
Recurso para obtenção da análise comparativa dos arquivos textuais carregados. Cas linhas alteradas serão precedidas pelos sinais (+, - e =), sinalizando adição de linha, remoção de linha e linha não alterada, reespectivamente.
