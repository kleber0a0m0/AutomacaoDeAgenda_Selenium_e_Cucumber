Feature: Testar API
  Scenario: A API deve estar disponivel
    Given realizar uma requisição GET para a URL
    Then a resposta deve ser 200

  Scenario: Verificar o usuario no corpo da requisição
    Given que eu realize uma requisição GET para a URL
    Then o código de resposta deve ser 200
    And o id usuario da primeira tarefa deve ser diferente de vazio

  Scenario: Cadastrar tarefa
    Given que eu cadastre uma tarefa para o usuario de id 1
    Then o código da resposta deve ser 201

  Scenario: É possivel cadastrar com a descrição vazia
    Given que eu cadastre uma tarefa com a descrição vazia
    Then o código resposta deve ser 201

  Scenario: É possivel editar a descrição vazia
    Given que eu cadastre uma tarefa com a descrição vazia e depois a edite
    Then o código resposta deverá ser 200

  Scenario: Tarefa cadastrada deve vir com status OPEN
    Given que eu cadastre uma tarefa
    Then verificar se o status é OPEN