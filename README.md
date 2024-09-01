# Registro de Pessoa com Deleção Lógica e Testes de Integração

Este projeto visa demonstrar a importância de testes de integração em sistemas Java, especialmente em cenários onde o
uso de mocks pode resultar em falsos positivos. Desenvolvido em **Java 17** utilizando **Spring Boot**, o sistema
gerencia registros de pessoas que podem desempenhar múltiplos papéis, como **instrutor** (Pessoa Física ou Jurídica) e *
*instituição** (Estadual, Nacional ou Municipal).

## Funcionalidades Principais

- **Registro de Pessoa**:
    - Cada pessoa registrada pode ter múltiplos papéis, permitindo uma relação N para N.

- **Deleção Lógica de Instrutores**:
    - Um método para deletar logicamente um instrutor é implementado, garantindo que a operação só seja realizada se não
      houver papéis superiores ou ordens de serviço em aberto.

- **Métodos de Verificação**:
    - Métodos como `isInstrutor`, `isEstadual`, `isMunicipal`, `isNacional` são utilizados para verificar os papéis
      associados a cada pessoa.

## Testes de Integração

- **Cenários de Deleção**:
    - Testes foram escritos para validar a deleção lógica de um instrutor em diferentes cenários:
        1. **Deleção bem-sucedida**: Quando o deletante tem permissão e não há ordens de serviço em aberto.
        2. **Deleção impedida**: Quando há ordens de serviço em aberto.
        3. **Deleção impedida**: Quando o deletante possui apenas o papel de instrutor.

- **Banco de Dados em Memória**:
    - Utilizando **H2** para testes de integração, o sistema simula a persistência real de dados, garantindo que as
      consultas e operações reflitam o comportamento esperado em produção.

## Demonstração da Ineficácia dos Mocks

Este projeto também inclui exemplos de como testes utilizando mocks podem falhar em capturar cenários reais, como quando
as consultas com `JOIN FETCH` não são realizadas adequadamente, levando a resultados imprecisos. Isso destaca a
importância de realizar testes de integração completos, que interajam diretamente com o banco de dados para validar a
lógica de negócios.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.3.3**
    - Spring Data JPA
    - Spring Web
- **Serenity BDD 3.9.0**
    - serenity-core
    - serenity-junit5
    - serenity-spring
- **H2 Database**
- **Lombok**
- **Maven**
- **Selenium 4.4.0**

## Como o Projeto Ajuda o Gestor a Entender os Cenários dos Testes com o Serenity

Este projeto utiliza o Serenity BDD para fornecer relatórios detalhados dos testes de integração, ajudando o gestor a
entender os diferentes cenários de testes de forma clara e visual. O Serenity BDD gera relatórios que incluem:

- **Resumo dos Testes**: Mostra o número total de testes, quantos passaram, falharam ou foram ignorados.
- **Detalhes dos Testes**: Fornece informações detalhadas sobre cada teste, incluindo os passos executados, os dados de
  entrada e os resultados esperados e reais.
- **Histórico de Execuções**: Permite visualizar o histórico das execuções dos testes, facilitando a identificação de
  padrões e tendências.

## Instruções para Gerar os Testes e Relatórios com o Serenity

1. **Executar os Testes**: Para executar os testes e gerar os relatórios do Serenity, utilize o seguinte comando Maven:

   ```bash
   mvn clean verify
   ```
   Este comando irá compilar o projeto, executar os testes e gerar os relatórios do Serenity na pasta
   `target/site/serenity`.

2. **Abrir os Relatórios**: Após a execução dos testes, os relatórios do Serenity estarão disponíveis no seguinte
   caminho:

   ```
   target/site/serenity/index.html
   ```

   Abra o arquivo `index.html` em um navegador para visualizar os relatórios detalhados dos testes de integração.