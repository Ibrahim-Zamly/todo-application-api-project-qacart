package com.qacart.todo.testcases;

import com.qacart.todo.apis.TodoApi;
import com.qacart.todo.apis.UserApi;
import com.qacart.todo.data.ErrorMessages;
import com.qacart.todo.models.Error;
import com.qacart.todo.models.Todo;
import com.qacart.todo.steps.TodoSteps;
import com.qacart.todo.steps.UserSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
@Feature("Todo Feature")
public class TodoTest {
    String token = UserSteps.getUserToken();
    @Story("Should Be Able To Add Todo")
    @Test (description = "Should Be Able To Add Todo")
    public void shouldBeAbleToAddTodo(){
       Todo toDo = TodoSteps.generateTodo();
        Response response = TodoApi.addTodo(toDo,token);
        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(response.statusCode(),equalTo(201));
        assertThat(returnedTodo.getItem(),equalTo(toDo.getItem()));
        assertThat(returnedTodo.isCompleted(),equalTo(toDo.isCompleted()));
    }
    @Story("Should Not Be Able To Add Todo If Is Completed Is Missing")
    @Test (description = "Should Not Be Able To Add Todo If Is Completed Is Missing")
    public void shouldNotBeAbleToAddTodoIfIsCompletedIsMissing(){
        Todo toDo = new Todo("Learn Appium");
        Response response = TodoApi.addTodo(toDo,token);
        Error returnedError = response.body().as(Error.class);

        assertThat(response.statusCode(),equalTo(400));
        assertThat(returnedError.getMessage(),equalTo(ErrorMessages.IS_COMPLETED_IS_REQUIRED));
    }
    @Story("Should Be Able To Get A Todo By Id")
    @Test (description = "Should Be Able To Get A Todo By Id")
    public void shouldBeAbleToGetATodoById(){
        Todo todo = TodoSteps.generateTodo();
        String todoID = TodoSteps.getTodoID(todo,token);
        Response response = TodoApi.getTodo(token,todoID);

        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnedTodo.getItem(),equalTo(todo.getItem()));
        assertThat(returnedTodo.isCompleted(),equalTo(false));
    }
    @Story("Should Be Able To Delete A Todo")
    @Test (description = "Should Be Able To Delete A Todo")
    public void shouldBeAbleToDeleteATodo(){
        //String taskId = "6964c7b839bdca001522089a";
        Todo todo = TodoSteps.generateTodo();
        String todoID = TodoSteps.getTodoID(todo,token);
        Response response = TodoApi.deleteTodo(token,todoID);
        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnedTodo.getItem(),equalTo(todo.getItem()));
        assertThat(returnedTodo.isCompleted(),equalTo(false));

    }

}
