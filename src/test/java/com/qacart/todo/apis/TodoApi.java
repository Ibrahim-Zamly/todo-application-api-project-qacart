package com.qacart.todo.apis;

import com.qacart.todo.base.Specs;
import com.qacart.todo.data.Route;
import com.qacart.todo.models.Todo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoApi {
    public static Response addTodo(Todo toDo, String token){
        return given()
                .baseUri("http://qacart-todo.herokuapp.com/")
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(token)
                .body(toDo)
                .when()
                .post("/api/v1/tasks")
                .then()
                .log()
                .all().extract().response();
    }

    public static Response getTodo(String token,String taskId){
        return given().spec(Specs.getRequestSpec())
                .auth()
                .oauth2(token)
                .when()
                .get(Route.TODOS_ROUTE + taskId)
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    public static Response deleteTodo(String token,String taskId){
        return given()
                .spec(Specs.getRequestSpec())
                .auth()
                .oauth2(token)
                .when()
                .delete(Route.TODOS_ROUTE + taskId)
                .then()
                .log()
                .all()
                .extract()
                .response();
    }
}
