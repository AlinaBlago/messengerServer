package com.finalproject.server.serverResponse;

import java.net.http.HttpResponse;

public class AuthorizationResponse {
    private HttpResponse<String> response;
    private HttpResponse<Integer> responseStatusCode;

    public String getResponse() {
        return response.body();
    }

    public void setResponse(HttpResponse<String> response) {
        this.response = response;
    }

    public int getResponseStatusCode() {
        return responseStatusCode.statusCode();
    }

    public void setResponseStatusCode(HttpResponse<Integer> responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
    }

    //    private int responseID;
//    // 0 - все ок
//    // 1 - ошибка неизвестная
//    // 2 - такой пользователь уже есть
//    // 3 - неверный логин или парль
//    private String responseMessage;
//
//    public int getResponseID() {
//        return responseID;
//    }
//
//    public void setResponseID(int responseID) {
//        if(responseID > 3 || responseID < 0) return;
//        this.responseID = responseID;
//    }
//
//    public String getResponseMessage() {
//        return responseMessage;
//    }
//
//    public void setResponseMessage(String responseMessage) {
//        this.responseMessage = responseMessage;
//    }
}
