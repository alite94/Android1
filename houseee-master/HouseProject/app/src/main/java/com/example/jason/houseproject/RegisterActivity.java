package com.example.jason.houseproject;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner spinner;
    private String userID;
    private String userPassword;
    private String userGender;
    private String userMajor;
    private String userEmail;

    //알림창을 보여주는 AlertDialog
    private AlertDialog dialog;

    //회원 가입이 가능한지 체크하는 변수
    private boolean validate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinner = findViewById(R.id.majorSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.major, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        //초기화 진행
        final EditText idText = findViewById(R.id.idText);
        final EditText passWordText = findViewById(R.id.passwordText);
        final EditText emailText = findViewById(R.id.emailText);

        RadioGroup genderGroup= (RadioGroup)findViewById(R.id.genderGroup);
        int genderGroupID = genderGroup.getCheckedRadioButtonId();
        userGender = ((RadioButton)findViewById(genderGroupID)).getText().toString();

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton genderButton = (RadioButton) findViewById(i);
                userGender = genderButton.getText().toString();


            }
        });

        final Button validateButton = (Button) findViewById(R.id.valadateButton);

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                if(validate){
                    return;
                }
                if(userID.length()!=10){
                    return;
                }

                if(userID.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("학번은 빈 칸 일 수 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용 가능한 학번입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                idText.setEnabled(false);
                                validate = true;
                                idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            }
                               else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    dialog = builder.setMessage("사용 할 수 없는 학번입니다.")
                                            .setNegativeButton("확인", null)
                                            .create();
                                    dialog.show();
                                }



                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);


            }
        });


        //회원가입버튼
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                String userPassword = passWordText.getText().toString();
                String userMajor = spinner.getSelectedItem().toString();
                String userEmail = emailText.getText().toString();

                if(!validate) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("중복체크가 필요합니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;


                }
                if(userID.equals("") || userPassword.equals("") || userMajor.equals("") || userEmail.equals("") || userGender.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("빈 칸 없이 입력하세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;

                }



                //회원 가입 성공 시
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("회원가입이 완료되었습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                finish();

                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("회원 등록 실패")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }



                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };


                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userGender, userMajor, userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);


            }
        });

    }
    @Override
    protected void onStop() {
        super.onStop();
        if(dialog!=null ) {
            dialog.dismiss();
            dialog=null;

        }


    }



}
