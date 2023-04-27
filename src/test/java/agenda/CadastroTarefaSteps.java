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

    // A tarefa deve estar vinculada a um usuário

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
}

