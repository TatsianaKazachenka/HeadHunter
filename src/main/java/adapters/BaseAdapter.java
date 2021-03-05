package adapters;

import com.google.gson.Gson;
import constants.IPageConstants;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BaseAdapter implements IPageConstants {

    Gson converter = new Gson();

    public String get(String url){
        return
                given()
                        .header("Content-Type", "application/json")
                .when()
                        .get(URL + url)
                .then()
                        .log().all()
                        .extract().body().asString();
    }

    public Response post(String url, String body){
        return
                given()
                        .header("Content-Type", "application/json")
                        .body(body)
                .when()
                        .post(URL + url)
                .then()
                        .log().all()
                        .extract().response();
    }

    public void delete(String url){
        given()
                .header("Content-Type", "application/json")
        .when()
                .delete(URL + url)
        .then()
                .log().all();
    }
}
