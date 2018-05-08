package com.example.jason.houseproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noticeListView = (ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        noticeList.add(new Notice("공지사항", "운영진", "2018-05-06"));
        adapter = new NoticeListAdapter(getApplicationContext(), noticeList);
        noticeListView.setAdapter(adapter);

        final Button noticeButton = (Button) findViewById(R.id.noticeButton);
        final Button mainButton = (Button) findViewById(R.id.mainButton);
        final Button angelButton = (Button) findViewById(R.id.angelButton);
        final LinearLayout notice = (LinearLayout) findViewById(R.id.notice);


        noticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noticeButton.setVisibility(View.GONE);
                noticeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                mainButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                angelButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManager = getSupportFragmentManager();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noticeButton.setVisibility(View.GONE);
                noticeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mainButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                angelButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        angelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noticeButton.setVisibility(View.GONE);
                noticeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mainButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                angelButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>{
        String target;

        protected void onPreExecute(){
            target = "http://cir112.cafe24.com/NoticeList.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuffer stringBuffer = new StringBuffer();
                while ((temp = bufferedReader.readLine())!=null)
                {
                    stringBuffer.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();;
                return stringBuffer.toString().trim();
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public  void onProgressUpdate(Void...values){
            super.onProgressUpdate();
        }

        @Override
        public  void onPostExecute(String result){
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String noticeContent, noticeName, noticeDate;
                while (count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");
                    Notice notice = new Notice(noticeContent, noticeName, noticeDate);
                    noticeList.add(notice);
                    count++;
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
}