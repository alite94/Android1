package com.example.jason.houseproject;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jason on 2018-04-02.
 */

public class RegisterRequest extends StringRequest {
    //RegisterRequest 를 이용하여 php에 userId 등의 정보를 보내서 저장을 요청하는 것

    final static private String URL = "http://cir112.cafe24.com/UserRegister.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userGender, String userMajor, String userEmail, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userGender", userGender);
        parameters.put("userMajor", userMajor);
        parameters.put("userEmail", userEmail);


    }

    public Map<String, String> getParams() {

        return parameters;
    }
}
