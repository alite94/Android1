package com.example.jason.houseproject;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    //알림창
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this,  RegisterActivity.class);
//                이 로그인 액티비티에서 RegisterActivity로 이동을 하라는 Intent 명령어 지정

                LoginActivity.this.startActivity(registerIntent);
//                  해당 Intent 실행

            }
        });


        final EditText idText = findViewById(R.id.idText);
        final EditText passwordText = findViewById(R.id.passwordText);
        final Button loginButton = findViewById(R.id.loginButton);


        //로그인 버튼을 눌렀을 때 이벤트 처리
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            //해당 결과를 받아옴 ,
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            //로그인 성공 시
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("로그인 성공").setPositiveButton("확인", null).create();
                                dialog.show();

                                //인탠트를 통해 로그인 액티비티에서 메인액티비티로 넘어가 넘어가
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                LoginActivity.this.startActivity(intent);
                                finish();
                                //finish를 통해 현재 액티비티는 종료한다.
                            }

                            //로그인 실패 시
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("로그인에 실패하였습니다.").setNegativeButton("다시 시도", null).create();
                                dialog.show();
                            }
                        } //예외처리
                        catch (Exception e ) {

                            e.printStackTrace();



                        }
                    }
                };
                //실제 로그인을 보내는 부분
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);



            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

        //함부로 종료가 되지 않도록 하는 센스 . 사실 큰 의미 음슴
        if(dialog !=null) {
            dialog.dismiss();
            dialog=null;

        }
    }

}
