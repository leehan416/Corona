package com.example.cocoa;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String Url = "http://ncov.mohw.go.kr/bdBoardList_Real.do?brdId=&brdGubun=&ncvContSeq=&contSeq=&board_id=&gubun=";

    String Url2 = "https://www.coronanow.kr/";

    String list = "";

    TextView Today;
    TextView Today_1;
    TextView Infected;
    TextView Dead;
    TextView Suspected;
    TextView Test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Infected = (TextView)findViewById(R.id.infected);
        Today = (TextView)findViewById(R.id.today);
        Today_1 = (TextView)findViewById(R.id.today_1);
        Dead = (TextView)findViewById(R.id.dead);
        Suspected = (TextView)findViewById(R.id.suspect);
        Test = (TextView)findViewById(R.id.test);
        try {
            DataLoad();
        } catch (Exception e){
        }
    }

    public void DataLoad() {
        try {
        list = "";
        list2 = "";
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
        } catch (Exception p){
            Today_1.setText("Error :(");
        }

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
                Elements titles = doc.select("table[class=num]").eq(0);
                for (Element e : titles) {
                    list = e.text().trim() + "\n";
                }

                doc = Jsoup.connect(Url2).get();
                titles = doc.select("div[class=card-body2]").eq(1);
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


            tmp = list.split("명");

            tmp[0] = tmp[0].substring(49, tmp[0].length()).trim();
            tmp[1] = tmp[1].substring(11, tmp[1].length()).trim();
            tmp[2] = tmp[2].substring(5, tmp[2].length()).trim();
            tmp[3] = tmp[3].substring(6, tmp[3].length()).trim();
            tmp[4] = ""; // 이거가 오늘 숫자

            Infected.setText("확진환자\n"+ tmp[0] +"명");
            Dead.setText("격리해제\n"+ tmp[1] +"명");
            Suspected.setText("사망자\n"+tmp[2] +"명");
            Test.setText("검사진행\n" + tmp[3] +"명");

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
            Today_1.setText("\n최근 추가 확진자는");
            Today.setText(tmp[4]+"명 입니다.");
        }
    }
}
