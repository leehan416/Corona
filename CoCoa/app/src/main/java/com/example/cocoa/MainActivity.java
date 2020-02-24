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
        DataLoad();
    }

    public void DataLoad() {
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
                Elements titles = doc.select("ul[class=s_listin_dot]").eq(0);
                for (Element e : titles) {
                    list = e.select("li").text().trim() + "\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            String[] tmp;
            tmp = list.split("명");
            Infected.setText(tmp[0]+"명");
            Dead.setText(tmp[1]+"명");
            Suspected.setText(tmp[2]+"명");
            Test.setText(tmp[3]+"명");
        }
    }
/*
    @Override
    public void onClick(View v){ }
*/
}
