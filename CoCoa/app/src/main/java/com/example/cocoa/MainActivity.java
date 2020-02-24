package com.example.cocoa;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String Url = "http://ncov.mohw.go.kr/bdBoardList_Real.do?brdId=&brdGubun=&ncvContSeq=&contSeq=&board_id=&gubun=";
    String Sdata1;
    String Sdata2;
    String Sdata3;
    String Sdata4;

    TextView Infected;
    TextView Dead;
    TextView Suspected;
    TextView Test;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Infected = (TextView)findViewById(R.id.infected);
        Dead = (TextView)findViewById(R.id.dead);
        Suspected = (TextView)findViewById(R.id.suspected);
        Test = (TextView)findViewById(R.id.test);

    }

    public void CalltheMeal() {
        //Call the Meal 고침
        list = "";
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
    }

    String list = "";

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(Url).get();
                Elements titles = doc.select("ul[s_listin_dot]");

                for (Element e : titles) {
                    list += e.select("li").text().trim() + "\n";
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Sdata1 = list;
        }
    }


    @Override
    public void onClick(View v){


    }

}
