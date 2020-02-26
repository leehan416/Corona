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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String Url = "http://ncov.mohw.go.kr/bdBoardList_Real.do?brdId=&brdGubun=&ncvContSeq=&contSeq=&board_id=&gubun=";

    String list = "";

    String Url2 = "https://www.coronanow.kr/";


    TextView Today = null;
    TextView Infected= null;
    TextView Dead= null;
    TextView Suspected= null;
    TextView Test= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Infected = (TextView)findViewById(R.id.infected);
        Today = (TextView)findViewById(R.id.today);
        Dead = (TextView)findViewById(R.id.dead);
        Suspected = (TextView)findViewById(R.id.suspect);
        Test = (TextView)findViewById(R.id.test);
        try {
            DataLoad();
        }
        catch (Exception e){
        }
    }

    public void DataLoad() {
        list = "";
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
    }


    String list2 = "";
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
                doc = Jsoup.connect(Url2).get();
                titles = doc.select("div[class=card-body2]") .eq(1)   ;
                for (Element e : titles) {
                    list2 += e.text().trim();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            String[] tmp = new String[5];
            tmp[0].substring(7, tmp[0].length()-1);
            tmp[1].substring(12, tmp[1].length()-1);
            tmp[2].substring(6, tmp[2].length()-1);
            tmp[3].substring(7, tmp[3].length()-1);

            tmp = list.split("명");
            Infected.setText("확진자\n"+ tmp[0] +"명");
            Dead.setText("사망자\n"+ tmp[1] +"명");
            Suspected.setText("의심환자\n"+tmp[2] +"명");
            Test.setText("검사중\n" + tmp[3] +"명");
            tmp[4] = ""; // 이거가 오늘 숫자
            boolean b = false;
            String temp = "";
            for( int i = 0; i < list2.length(); i++){
                temp = list2.substring(i, i+1);
                if ( temp.equals("+") ){
                    b = true;
                    continue;
                }
                if ( b ) {
                    if (temp.equals(")")) {
                        break;
                    }
                    tmp[4] += temp;
                }
            }
            Today.setText("오늘의 추가 확진자는\n" + tmp[4]+" 명 입니다.");
        }
    }
}
