Feature: Testar API
  Scenario: A API deve estar disponivel
    Given realizar uma requisição GET para a URL
    Then a resposta deve ser 200

  Scenario: Verificar o usuario no corpo da requisição
    Given que eu realize uma requisição GET para a URL
    Then o código de resposta deve ser 200
    And o id usuario da primeira tarefa deve ser diferente de vazio

