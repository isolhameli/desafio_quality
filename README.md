# Desafio Quality - Israel Solha

Projeto do Bootcamp da DigitalHouse para implementação de Testes Unitários e de Integração

## Documentação da API no Swagger

```
http://localhost:8080/swagger-ui.html#/
```

### Estrutura de Input

```
{
  "prop_district": "string",
  "prop_name": "string",
  "rooms": [
    {
      "room_length": 0,
      "room_name": "string",
      "room_width": 0
    }
  ]
}
```

### Estrutura do Output

```
{
  "largest_room": {
    "area_m2": 0,
    "room_name": "string"
  },
  "prop_district": "string",
  "prop_name": "string",
  "prop_value": 0,
  "rooms": [
    {
      "area_m2": 0,
      "room_name": "string"
    }
  ],
  "total_area_m2": 0
}
```

## Informações sobre os testes

 ### Testes Unitários

 Foram implementados Testes Unitários nas três camadas da aplicação, voltados para analisar o comportamento
 individual de cada uma das camadas, sem interferências externas.
 
Comportamentos testados em cada Camada:

### Property Controller

- <b>testControllerDTOValidationsAreWorking</b>: Verifica as validações do @Valid, mostrando todas as mensagens requeridas de forma organizada por cada
  campo analisado
 - <b>testControllerBehavesWellAfterGettingResultFromService</b>: Verifica se o output (mockado) do Service está sendo serializado de forma correta e a resposta do endpoint vem como esperado.
- <b>testControllerHandlesExceptionWhenProvidingInvalidJson</b>: Verifica a integridade do JSON de entrada
- <b>testControllerHandlesExceptionWhenProvidingWrongDataForRoomsList</b>: Verifica se todos os campos estão recebendo a classe correta para serialização. Ex: O campo "rooms" está recebendo uma lista?
- <b>testControllerHandlesExceptionWhenProvidingWrongDataForRoomsElement</b>: Verifica se todas as classes da entidade "nested" de uma lista equivale ao esperado. Ex: O primeiro elemento do campo "rooms" corresponde ao esperado no DTO?
- <b>testErrorWhenRoomsAreEmpty</b>: Verifica se um erro acontece quando a lista de Rooms é vazia
### Service

### District Service
- <b>testFindByNameReturnsDistrict</b>: Verifica se o Service retorna a Entidade da maneira esperada 
- <b>testFindByNameThrowsExceptionWhenDistrictNotFound</b>: Verifica se o Service faz o throw da Exceção <b>DistrictNotFoundException</b> quando o Repository retorna null
  
### Property Service

- <b>testCalculatePriceGivesRightResult</b>: Verifica se o método <b>calculatePropertyPrice</b> retorna o resultado esperado
- <b>testGetPropertyInfo</b>: Verifica se o retorno da compilação dos resultados (mockados) condiz com o esperado

### Room Service

- <b>testCalculateRoomAreaWorksWithRoom</b>: Verifica se o método <b>calculateRoomArea</b> funciona passando uma lista de ENTIDADES
- <b>testCalculateRoomAreaWorksWithRoomRequest</b>: Verifica se o método <b>calculateRoomArea</b> funciona passando uma lista de DTOS
- <b>testGetRoomsWithAreaWorksWithRoomRequest</b>: Verifica se o método <b>getRoomsWithArea</b> funciona passando uma lista de DTOS
- <b>testGetRoomsWithAreaWorksWithRooms</b>: Verifica se o método <b>getRoomsWithArea</b> funciona passando uma lista de ENTIDADES
- <b>testGetLargestRoom</b>: Verifica se o método <b>getLargestRoom</b> retorna a Room esperada
- <b>testCalculateTotalArea</b>: Verifica se o método <b>calculateTotalArea</b> retorna o valor esperado

### District Repository

 - <b>testRepositoryReturnsDistrict</b>: Verifica se o repositório retorna a entidade de maneira correta quando a mesma existe
- <b>testRepositoryReturnsDistrict</b>: Verifica se o repositório retorna nulo quando a entidade relacionada não existe

   

### Testes de Integração

Analisa o comportamento da aplicação como um todo. Simulando uma requisição HTTP, verifica-se a integridade
e assertividade do resultado provido pela API.

### Testes Realizados

 - <b>testRightOutput</b>: Verifica se o resultado de um input perfeitamente válido bate com o esperado
 - <b>testDistrictNotFoundException</b>: Verifica se ao passar um bairro inexistente é lançada a exceção apropriada


### Bairros já cadastrados

| Nome | Preço |
| ---- | ----- |
| BAIRRO DOS ESTADOS | 450.0 |
| TORRE | 300.0 |
| CABO BRANCO | 500.0 |
| MANAIRA | 700.0 |
| BANACRIOS | 400.0 |


### Comentários extras

Os testes de validação poderiam ter sido feitos em testes de integração, porém, como espera-se que
o Controller seja o responsável por essa validação, optou-se por fazer apenas nesta camada, visto que assim
pode-se confirmar que todo o tratamento não só está funcionando, como está sendo executado na camada esperada da aplicação.
Essa estratégia evita falsos positivos de testes que passam, embora o processamento não tenha sido feito na camada esperada, e sim em outra.


    