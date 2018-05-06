package kr.ac.gnu.yg398.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ItemView extends AppCompatActivity{
    private TextView textViewSubject;
    private TextView textViewNick;
    private TextView textViewDate;
    private TextView textViewHits;
    private TextView textViewContent;

    ArrayList<HashMap<String,String>> itemArrElement;

    JSONArray list = null;
    String myJson;

    private static final String TAG_RESULT = "result";
    private static final String TAG_SUBJECT = "sub";
    private static final String TAG_WRITER = "writer";
    private static final String TAG_DESCRIPTION = "desc";
    private static final String TAG_HITS = "hits";
    private static final String TAG_DATE = "date";

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_view);

        TextView itemContentView = (TextView)findViewById(R.id.textViewContent);
        itemArrElement = new ArrayList<HashMap<String, String>>();
        getData("http://35.194.105.42/itemView.php");
    }

    private void getData(String url) {
        class GetDataJson extends AsyncTask<String, Void, String>
        {
            @Override
            protected String doInBackground(String... params)
            {
                String strUrl = params[0];

                BufferedReader bufferedReader = null;

                try {
                    URL url = new URL(strUrl);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;

                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
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
    protected  void showList()
    {
        String sub=null;
        String nick=null;
        String contents=null;
        String hits=null;
        String strDate=null;
        try
        {
            JSONObject jsonObj = new JSONObject(myJson);
            list = jsonObj.getJSONArray(TAG_RESULT);

            for(int i=0;i<list.length();i++)
            {
                JSONObject c = list.getJSONObject(i);
                sub = c.getString(TAG_SUBJECT);
                nick = c.getString(TAG_WRITER);
                contents=c.getString(TAG_DESCRIPTION);
                hits=c.getString(TAG_HITS);
                strDate = c.getString(TAG_DATE);

                HashMap<String,String> itemElement = new HashMap<String,String>();

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = df.parse(strDate);
                strDate = new SimpleDateFormat("MM-dd").format(date);

                itemElement.put(TAG_SUBJECT, sub);
                itemElement.put(TAG_WRITER,nick);
                itemElement.put(TAG_DATE,strDate);
                itemElement.put(TAG_HITS,hits);
                itemElement.put(TAG_DESCRIPTION,contents);

                itemArrElement.add(itemElement);
            }
            textViewSubject=(TextView)findViewById(R.id.textViewSub);
            textViewNick = (TextView)findViewById(R.id.textViewNIck);
            textViewDate = (TextView)findViewById(R.id.textViewDate);
            textViewHits = (TextView)findViewById(R.id.textViewHits);
            textViewContent = (TextView)findViewById(R.id.textViewContent);

            sub = itemArrElement.get(0).get(TAG_SUBJECT);
            nick = itemArrElement.get(0).get(TAG_WRITER);
            strDate = itemArrElement.get(0).get(TAG_DATE);
            hits = itemArrElement.get(0).get(TAG_HITS);
            contents = itemArrElement.get(0).get(TAG_DESCRIPTION);

            textViewSubject.setText(sub);
            textViewNick.setText(nick);
            textViewContent.setText(contents);
            textViewHits.setText(hits);
            textViewDate.setText(strDate);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
