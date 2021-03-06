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
                        .header(CONTENT_TYPE, APPLICATION_JSON)
                .when()
                        .get(BASE_API_HH_URL + url)
                .then()
                        .log().all()
                        .extract().body().asString();
    }

    public Response post(String url, String body){
        return
                given()
                        .header(CONTENT_TYPE, APPLICATION_JSON)
                        .body(body)
                .when()
                        .post(BASE_API_HH_URL + url)
                .then()
                        .log().all()
                        .extract().response();
    }

    public void delete(String url){
        given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
        .when()
                .delete(BASE_API_HH_URL + url)
        .then()
                .log().all();
    }
}
