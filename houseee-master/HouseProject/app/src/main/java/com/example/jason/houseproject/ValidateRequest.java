package com.example.jason.houseproject;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jason on 2018-04-02.
 */

public class ValidateRequest extends StringRequest {
    //가입이 가능한지 검사한다.

    final static private String URL = "http://cir112.cafe24.com/UserValidate.php";
    private Map<String, String> parameters;

    public ValidateRequest(String userID,  Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);

    }

    public Map<String, String> getParams() {

        return parameters;
    }
}
