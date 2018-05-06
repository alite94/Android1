package kr.ac.gnu.yg398.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{

    String myJson;

    private static final String TAG_RESULT = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADD = "address";



    JSONArray people = null;

    ArrayList<HashMap<String,String>> personList;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String, String>>();
        getData("http://35.194.105.42/PHP_connection.php");
        Button btnWrite = (Button)findViewById(R.id.btnWrite);
        Button btnBoard = (Button)findViewById(R.id.btnBoard);

        btnWrite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), write.class);
                startActivity(intent);
            }
        });
        btnBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), boardList.class);
                startActivity(intent);
            }
        });

    }


    protected void showList()
    {
        try
        {
            JSONObject jsonObj = new JSONObject(myJson);
            people = jsonObj.getJSONArray(TAG_RESULT);

            for(int i=0;i<people.length();i++)
            {
                JSONObject c = people.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String address = c.getString(TAG_ADD);

                HashMap<String,String> persons = new HashMap<String, String>();

                persons.put(TAG_ID,id);
                persons.put(TAG_NAME,name);
                persons.put(TAG_ADD,address);

                personList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter
                    (
                    MainActivity.this,
                    personList,
                    R.layout.list_item,
                    new String[]{TAG_ID,TAG_NAME,TAG_ADD},
                    new int[]{R.id.age, R.id.name, R.id.address}
                    );

            list.setAdapter(adapter);
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void getData(String url)
    {
        class GetDataJson extends AsyncTask<String, Void, String>
        {
            @Override
            protected String doInBackground (String... params)
            {
                String strUrl = params[0];

                BufferedReader bufferedReader = null;

                try
                {
                    URL url = new URL(strUrl);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;

                    while((json = bufferedReader.readLine())!=null)
                    {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                }catch (Exception e)
                {
                    return null;
                }
            }
            protected void onPostExecute(String result)
            {
                myJson = result;
                showList();
            }
        }
        GetDataJson g = new GetDataJson();
        g.execute(url);
    }
}
