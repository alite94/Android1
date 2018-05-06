package kr.ac.gnu.yg398.myapplication;

import android.app.ProgressDialog;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yg398 on 2018-04-25.
 */

public class write extends AppCompatActivity {
    private static String TAG="phptest_write";

    private EditText mEditTextAge;
    private EditText mEditTextName;
    private EditText mEditTextAddress;
    private TextView mTextViewResult;
    private String [] info = new String [3];

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write);

        mEditTextAge = (EditText)findViewById(R.id.editTextAge);
        mEditTextName = (EditText)findViewById(R.id.editTextName);
        mEditTextAddress = (EditText)findViewById(R.id.editTextAdd);
        mTextViewResult = (TextView)findViewById(R.id.txtResult);

        Button btnCommit = (Button)findViewById(R.id.btnCommit);

        btnCommit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                info[0] = mEditTextAge.getText().toString();
                info[1] = mEditTextName.getText().toString();
                info[2] = mEditTextAddress.getText().toString();


                InsertData task = new InsertData();
                task.execute(info);

                mEditTextAge.setText("");
                mEditTextName.setText("");
                mEditTextAddress.setText("");
            }
        });

    }

    class InsertData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = ProgressDialog.show(write.this,"Please Wait",null,true,true);
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG,"POST response - "+result);
        }

        @Override
        protected String doInBackground(String... params){
            String age = (String)params[0];
            String name = (String)params[1];
            String address = (String)params[2];

            String strUrl = "http://35.194.105.42/insertInfo.php";
            String postParams = "age="+age+"&name="+name+"&address="+address;

            try{
                URL url=new URL(strUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParams.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - "+responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode==HttpURLConnection.HTTP_OK){
                    inputStream = httpURLConnection.getInputStream();
                }else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line=bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString();
            }catch (Exception e){
                Log.d(TAG,"InsertData : Error", e);

                return new String("Error: "+e.getMessage());
            }
        }

    }
}