package com.shadmaan.prapti.medihelpkuet;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public class fetchData extends AsyncTask<String, String,String> {
        String data="";
        String dataParsed = "";
        String singleParsed = "";
        @Override

        protected String doInBackground(String...strings)
        {
            try {
                URL url = new URL("https://api.myjson.com/bins/1csyin");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream= httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line!=null)
                {
                    line = bufferedReader.readLine();
                    data = data + line;
                }
                JSONObject jo=new JSONObject(data);
                JSONArray JA =jo.getJSONArray("results");
                StringBuffer sb=new StringBuffer();
                for (int i=0; i<JA.length();i++)
                {
                    JSONObject JO =JA.getJSONObject(i);
                    sb.append("Name : "+JO.getString("Name")+"\n"+"Designation :"+JO.getString("Designation")+"\n"+"Visiting period :"+JO.getString("Visiting period")+"\n"+"Mobile : "+JO.getString("Mobile")+"\n");
                    sb.append("\n");
                }
                String ans=sb.toString();
                Log.d("ans",ans);
                publishProgress(ans);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } //catch (JSONException e) {
            catch (JSONException e) {
                e.printStackTrace();
            }
            //e.printStackTrace();
            //}
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
         //   super.onProgressUpdate(values);
            TextView t1=(TextView) findViewById(R.id.fetcheddata);
            t1.setText(values[0]);

        }
    }

    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.b1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData process = new fetchData();
                process.execute();

            }
        });
    }
}
