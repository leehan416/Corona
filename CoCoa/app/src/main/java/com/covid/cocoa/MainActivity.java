package com.covid.cocoa;

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

    String Url2 = "http://ncov.mohw.go.kr/bdBoardList_Real.do?brdId=1&brdGubun=13&ncvContSeq=&contSeq=&board_id=&gubun=";

    String list = "";
    String list2 = "";

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

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(Url).get();
                Elements titles = doc.select("table.num").eq(0).select("tbody");//.select("td[class=w_bold]");

                for (Element e : titles) {
                    list += e.select("td").text().trim() + "\n";
                }

                doc = Jsoup.connect(Url2).get();
                //titles = doc.select("div[class=data_table tbl_scrl_mini2 mgt24]").select("tr[class=sumline]").select("td[class=number]").eq(0);
                titles = doc.select("div[class=data_table mgt24]").select("tr[class=sumline]").select("td[class=number]").eq(0);
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
            String[] tmp = {"",};
            String s = "";

            try {
                tmp = list.split("명");
                tmp[0] = tmp[0].substring(5, tmp[3].length()).trim();
                tmp[1] = tmp[1].substring(9, tmp[1].length()).trim();
                tmp[2] = tmp[2].substring(4, tmp[2].length()).trim();
                tmp[3] = tmp[3].substring(5, tmp[3].length()).trim();
            } catch (Exception e){
            }

            Infected.setText("확진환자\n"+ tmp[0] +"명");
            Dead.setText("격리해제\n"+ tmp[1] +"명");
            Suspected.setText("사망자\n"+tmp[2] +"명");
            Test.setText("검사진행\n" + tmp[3] +"명");


            boolean b = false;
            String temp = "";
            Today_1.setText("\n최근 추가 확진자는");
            Today.setText(list2+"명 입니다.");
            /*
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
                    s += temp;
                }
            }
            */
            //Today.setText(s+"명 입니다.");
        }
    }
}
