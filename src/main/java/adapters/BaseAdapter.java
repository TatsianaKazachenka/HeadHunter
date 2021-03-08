package adapters;

import com.google.gson.Gson;
import constants.ICommonConstants;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.protocol.HTTP;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseAdapter implements ICommonConstants {

    Gson converter = new Gson();

    public String get(String url){
        return
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                .when()
                        .get(String.format("%s%s",BASE_API_HH_URL, url))
                .then()
                        .log().all()
                        .extract().body().asString();
    }

    public String getWithParams(String url, Map<String, String> params){
        return
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .queryParams(params)
                .when()
                        .get(String.format("%s%s",BASE_API_HH_URL, url))
                .then()
                        .log().all()
                        .extract().body().asString();
    }

    public Response post(String url, String body){
        return
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .body(body)
                .when()
                        .post(BASE_API_HH_URL + url)
                .then()
                        .log().all()
                        .extract().response();
    }

    public void delete(String url){
        given()
                .header(HTTP.CONTENT_TYPE, ContentType.JSON)
        .when()
                .delete(BASE_API_HH_URL + url)
        .then()
                .log().all();
    }
}
