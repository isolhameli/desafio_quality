# Desafio Quality - Israel Solha

Projeto do Bootcamp da DigitalHouse para implementação de Testes Unitários e de Integração

## Documentação da API no Swagger

http://localhost:8080/swagger-ui.html#/

## Informações sobre os testes

 ### Testes Unitários

 Foram implementados Testes Unitários nas três camadas da aplicação, voltados para analisar o comportamento
 individual de cada uma das camadas, sem interferências externas.
 
Comportamentos testados em cada Camada:

### Controller
 - Verificar se o output (mockado) do Service está sendo serializado de forma correta e a resposta do endpoint vem como esperado.
- Verificar a integridade do JSON de entrada
 - Verificar as validações do @Valid, mostrando todas as mensagens requeridas de forma organizada por cada
campo analisado
- Verificar se todos os campos estão recebendo a classe correta para serialização. Ex: O campo "rooms" está recebendo uma lista?
- Verificar se todos a classe da entidade "nested" de uma lista equivale ao esperado. Ex: O primeiro elemento do campo "rooms" corresponde ao esperado no DTO?

### Service

 - Testando o resultado de cada um dos métodos de cada service, sempre mockando as dependências

### Repository

 - Testando o comportamento de retorno de entidades a partir do Repositório

   

### Testes de Integração

Analisa o comportamento da aplicação como um todo. Simulando uma requisição HTTP, verifica-se a integridade
e assertividade do resultado provido pela API.

### Testes Realizados

 - Verificar se o resultado de um input perfeitamente válido bate com o esperado
 - Verificar se passando um bairro inexistente é lançada a exceção apropriada


### Comentários extras

Os testes de validação poderiam ter sido feitos em testes de integração, porém, como espera-se que
o Controller seja o responsável por essa validação, optou-se por fazer apenas nesta camada, visto que assim
pode-se confirmar que todo o tratamento não só está funcionando, como está sendo executado na camada esperada da aplicação.
Essa estratégia evita falsos positivos de testes que passam, embora o processamento não tenha sido feito na camada esperada, e sim em outra.


    