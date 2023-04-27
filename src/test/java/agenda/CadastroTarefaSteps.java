package agenda;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CadastroTarefaSteps {

    private Response response;

    // Feature: A API deve estar disponivel
    @Given("realizar uma requisição GET para a URL")
    public void realiza_uma_requisição_get_para_a_url() {
        response = RestAssured.get("http://localhost:8080/api/tasks");
    }
    @Then("a resposta deve ser 200")
    public void a_resposta_deve_ser_200() {
        assertThat(response.getStatusCode(), equalTo(200));
    }

    // Scenario: Verificar o usuario no corpo da requisição
    @Given("que eu realize uma requisição GET para a URL")
    public void que_eu_realize_uma_requisição_get_para_a_url() {
        response = RestAssured.get("http://localhost:8080/api/tasks");
    }
    @Then("o código de resposta deve ser 200")
    public void o_código_de_resposta_deve_ser() {
        assertThat(response.getStatusCode(), equalTo(200));
    }
    @And("o id usuario da primeira tarefa deve ser diferente de vazio")
    public void o_id_usuario_da_primeira_tarefa_deve_ser_diferente_de_vazio_() {
        response.then().assertThat().body("[0].user.name", not(equalTo("")));
    }

    //Scenario: Cadastrar tarefa
    @Given("que eu cadastre uma tarefa para o usuario de id {int}")
    public void que_eu_cadastre_uma_tarefa_para_o_usuario_de_id(Integer userId) {
        String jsonBody = "{ \"title\": \"Tarefa de Teste\", \"description\": \"Descrição da tarefa de teste abc\", \"closedAt\": \"2023-04-26\", \"status\": \"OPEN\", \"user\": { \"id\": "+userId+" } }";
        response = RestAssured.given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post("http://localhost:8080/api/tasks");
    }

    @Then("o código da resposta deve ser {int}")
    public void verificarCodigoResposta(Integer expectedStatusCode) {
        response.then().assertThat()
                .statusCode(expectedStatusCode);
    }

    //  Scenario: É possivel cadastrar com a descrição vazia
    @Given("que eu cadastre uma tarefa com a descrição vazia")
    public void que_eu_cadastre_uma_tarefa_com_a_descrição_vazia() {
        String jsonBody = "{ \"title\": \"Tarefa de Teste\", \"description\": \"\", \"closedAt\": \"2023-04-26\", \"status\": \"OPEN\" }";
        response = RestAssured.given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post("http://localhost:8080/api/tasks");
    }

    @Then("o código resposta deve ser {int}")
    public void o_código_resposta_deve_ser(Integer expectedStatusCode) {
        response.then().assertThat()
                .statusCode(expectedStatusCode);
    }

    //  Scenario: É possivel editar a descrição vazia
    @Given("que eu cadastre uma tarefa com a descrição vazia e depois a edite")
    public void cadastrarTarefaComDescricaoVazia() {
        // Cadastra uma tarefa com a descrição vazia
        String jsonBody = "{ \"title\": \"Tarefa de Teste\", \"description\": \"\", \"closedAt\": \"2023-04-30\", \"status\": \"OPEN\" }";
        response = RestAssured.given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post("http://localhost:8080/api/tasks");

        String taskId = response.then().extract().path("id").toString();

        // Edita a tarefa com uma nova descrição
        jsonBody = "{ \"title\": \"Tarefa de Teste\", \"description\": \"Nova descrição da tarefa de teste\", \"closedAt\": \"2023-04-30\", \"status\": \"OPEN\" }";
        response = RestAssured.given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .put("http://localhost:8080/api/tasks/" + taskId);
    }

    @Then("o código resposta deverá ser {int}")
    public void validarCodigoResposta(Integer statusCode) {
        response.then().assertThat()
                .statusCode(statusCode);
    }

    //  Scenario: Tarefa cadastrada deve vir com status OPEN
    @Given("que eu cadastre uma tarefa")
    public void que_eu_cadastre_uma_tarefa() {
        String jsonBody = "{ \"title\": \"Tarefa de Teste\", \"description\": \"Descrição da tarefa de teste\", \"closedAt\": \"2023-04-26\"}";
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post("http://localhost:8080/api/tasks");
    }

    @Then("verificar se o status é OPEN")
    public void verificar_se_o_status_é_open() {
        Response response = RestAssured.get("http://localhost:8080/api/tasks");
        response.then().assertThat()
                .statusCode(200)
                .body("[0].status", equalTo("OPEN"));
    }
}

