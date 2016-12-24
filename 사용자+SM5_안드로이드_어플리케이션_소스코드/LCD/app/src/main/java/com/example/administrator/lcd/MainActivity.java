package com.example.administrator.lcd;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private TextView todayTime;
    private TextView speed;
    private TextView accident;

    String result;

    ViewTrafficSituation viewTrafficSituation;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        todayTime = (TextView) findViewById(R.id.time);
        speed = (TextView) findViewById(R.id.speed);
        accident = (TextView) findViewById(R.id.accident);


        TimerTask timerTask = new TimerTask() {

            public void run() {
                Runnable updater = new Runnable() {
                    public void run() {
                        try {

                            long todaytime;
                            SimpleDateFormat day, time;
                            String Day, Time;


                            todaytime = System.currentTimeMillis();
                            day = new SimpleDateFormat("yyyy-MM-dd");
                            time = new SimpleDateFormat("HH:mm:ss");



                            Day = day.format(new Date(todaytime));
                            Time = time.format(new Date(todaytime));

                            todayTime.setText(Day+" "+Time);

                            StringTokenizer st1 = new StringTokenizer(Day);
                            StringTokenizer st2 = new StringTokenizer(Time);

                            String year = st1.nextToken("-");
                            String month = st1.nextToken("-");
                            String dayData = st1.nextToken("-");

                            String hour = st2.nextToken(":");
                            String min = st2.nextToken(":");

                            viewTrafficSituation = new ViewTrafficSituation(year,month,dayData,hour,min);
                            result = viewTrafficSituation.execute().get();
                            StringTokenizer st = new StringTokenizer(result);
                            String count = st.nextToken();
                            String speedData = st.nextToken();
                            float speedFloat = Float.parseFloat(speedData);
                            String inputSpeed = String.format("%.2f", speedFloat);


                            accident.setText(count + " 회");
                            speed.setText(inputSpeed + " km/h");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                handler.post(updater);
            }
        };
        Timer timer = new Timer();

        timer.schedule(timerTask, 0, 1000);
    }

    private class ViewTrafficSituation extends AsyncTask<Void, Void, String> {

        String year;
        String month;
        String day;
        String hour;
        String min;

        public ViewTrafficSituation(String year, String month, String day, String hour, String min){
            this.year =year;
            this.month=month;
            this.day = day;
            this.hour = hour;
            this.min =min;
        }

        @Override
        protected String doInBackground(Void... voids) {

            String result = loadData();
            String[][] parsedData = jsonParserList(result);


            if (parsedData == null) {
                return "0 0";
            }

            if (parsedData[0][0].equals("null")){
                return "0" + " " + parsedData[0][1];
            }

            if (parsedData[0][1].equals("null")){
                return parsedData[0][0] + " " + "0";
            }

            return parsedData[0][0] + " " + parsedData[0][1];
        }

        protected String loadData() {

            HttpURLConnection conn = null;

            try {
                URL url = new URL("http://embedded.freehost.kr/testServers/selectTraffic.jsp"+"?year="+year+"&month="+month+"&day="+day+"&hour="+hour+"&min="+min);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setConnectTimeout(60);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8")); //캐릭터셋 설정


                writer.flush();
                writer.close();
                os.close();

                conn.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); //캐릭터셋 설정

                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (sb.length() > 0) {
                        sb.append("\n");
                    }
                    sb.append(line);
                }

                Log.i("LoadData", "data=" + sb.toString());

                return sb.toString();


            } catch (Exception e) {

                e.printStackTrace();
                return "";

            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }

        private String[][] jsonParserList(String pRecvServerPage) {

            Log.i("pRecvServerPage", "pRecvServerPage=" + pRecvServerPage);

            try {

                JSONArray jArray = new JSONArray(pRecvServerPage);

                String[] jsonName = {"count", "speed"};
                String[][] parsedData = new String[jArray.length()][jsonName.length];

                JSONObject json = null;

                for (int i = 0; i < jArray.length(); i++) {
                    json = jArray.getJSONObject(i);
                    if (json != null) {
                        for (int j = 0; j < jsonName.length; j++) {
                            parsedData[i][j] = json.getString(jsonName[j]);
                        }
                    }
                }

                for (int i = 0; i < parsedData.length; i++) {
                    Log.i("here", parsedData[i][0]);
                    Log.i("here", parsedData[i][1]);
                }
                // Log.i("here", "here");
                return parsedData;

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

        }


    }


}
