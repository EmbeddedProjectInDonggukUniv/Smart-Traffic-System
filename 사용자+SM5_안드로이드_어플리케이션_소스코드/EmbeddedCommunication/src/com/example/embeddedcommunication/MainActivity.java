package com.example.embeddedcommunication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView input;
	String speedText = "",  accidentText = "";

	InputSpeed inputSpeed;
	InputAccident inputAccident;
	UpdateAccident updateAccident;
	
	String mSdPath;
	
	Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		input = (TextView)findViewById(R.id.input);
		
		long todaytime;
        SimpleDateFormat day, time;
        String Day, Time;
        
        todaytime = System.currentTimeMillis();
        day = new SimpleDateFormat("yyyy-MM-dd");
        time = new SimpleDateFormat("HH:mm:ss");
        
        Day = day.format(new Date(todaytime));
        Time = time.format(new Date(todaytime));

        StringTokenizer st1 = new StringTokenizer(Day);
        StringTokenizer st2 = new StringTokenizer(Time);

        String year = st1.nextToken("-");
        String month = st1.nextToken("-");
        String dayData = st1.nextToken("-");

        String hour = st2.nextToken(":");
        String min = st2.nextToken(":");
		
		inputSpeed = new InputSpeed("35.00",year,month,dayData,hour,min);
		inputSpeed.execute();
		
		String ext = Environment.getExternalStorageState();
		if (ext.equals(Environment.MEDIA_MOUNTED)) {
			mSdPath = Environment.getExternalStorageDirectory()
					.getAbsolutePath();
		} else {
			mSdPath = Environment.MEDIA_UNMOUNTED;
		}
		

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

                            StringTokenizer st1 = new StringTokenizer(Day);
                            StringTokenizer st2 = new StringTokenizer(Time);

                            String year = st1.nextToken("-");
                            String month = st1.nextToken("-");
                            String dayData = st1.nextToken("-");

                            String hour = st2.nextToken(":");
                            String min = st2.nextToken(":");
							
							
							FileInputStream fisSpeed = new FileInputStream(mSdPath
									+ "/dir/speed.txt");
							byte[] dataSpeed = new byte[fisSpeed.available()];
							while (fisSpeed.read(dataSpeed) != -1) {
								;
							}
							fisSpeed.close();
							Log.i("speed", "speed : " + new String(dataSpeed));
							if (!new String(dataSpeed).equals("no")) {
								//Log.i("checkSpeed", "ok!");
								inputSpeed = new InputSpeed(new String(dataSpeed),year,month,dayData,hour,min);
								inputSpeed.execute();
							}
							File dirSpeed = new File(mSdPath + "/dir");
							
							dirSpeed.mkdir();
							File fileSpeed = new File(mSdPath + "/dir/speed.txt");

							FileOutputStream fosSpeed = new FileOutputStream(fileSpeed);
							String st = "no";
							fosSpeed.write(st.getBytes());
							
							
							
							FileInputStream fisAccident = new FileInputStream(mSdPath
									+ "/dir/accident.txt");
							byte[] dataAccident = new byte[fisAccident.available()];
							while (fisAccident.read(dataAccident) != -1) {
								;
							}
							fisAccident.close();
							Log.i("accident", "accident : " + new String(dataAccident));
							if (!new String(dataAccident).equals("no")) {
								Log.i("checkAccident", "ok!");
								inputAccident = new InputAccident(new String(dataAccident));
								inputAccident.execute();
								
								updateAccident = new UpdateAccident("yes");
								updateAccident.execute();
								
							}
							File dirAccident = new File(mSdPath + "/dir");
							dirAccident.mkdir();
							File fileAccident = new File(mSdPath + "/dir/accident.txt");

							FileOutputStream fosAccident = new FileOutputStream(fileAccident);
							String sa = "no";
							fosAccident.write(sa.getBytes());
							
							
							fosSpeed.close();
							fosAccident.close();
							

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				handler.post(updater);
			}
		};
		Timer timer = new Timer();

		timer.schedule(timerTask, 0, 2000);
		

	}
	
	

	private class InputSpeed extends AsyncTask<Void, String, Void> {

		String speed;
		String year;
		String month;
		String day;
		String hour;
		String min;		

		public InputSpeed(String speed, String year, String month, String day,
				String hour, String min) {
			super();
			this.speed = speed;
			this.year = year;
			this.month = month;
			this.day = day;
			this.hour = hour;
			this.min = min;
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			insertData(speed,year,month,day,hour,min);
			return null;
		}

		protected void insertData(String speed,String year, String month, String day, String hour,String min) {
			try {

				HttpClient client = new DefaultHttpClient();

				String postURL = "http://embedded.freehost.kr/testServers/insertTraffic.jsp";

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("speed", speed));
				params.add(new BasicNameValuePair("year", year));
				params.add(new BasicNameValuePair("month", month));
				params.add(new BasicNameValuePair("day", day));
				params.add(new BasicNameValuePair("hour", hour));
				params.add(new BasicNameValuePair("min", min));

				UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,
						HTTP.UTF_8);

				HttpPost post = new HttpPost(postURL);

				post.setEntity(ent);

				HttpResponse responsePOST = client.execute(post);

				HttpEntity resEntity = responsePOST.getEntity();

				if (resEntity != null)

				{

					Log.i("RESPONSE", EntityUtils.toString(resEntity));

				}

			} catch (IOException e) {

				e.printStackTrace();

			}
		}

	}
	
	private class UpdateAccident extends AsyncTask<Void,String,Void> {
		String accident;
		
		public UpdateAccident(String accident) {
			super();
			this.accident = accident;
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			updateData(accident);
			return null;
		}
		
		protected void updateData(String accident) {
			try {

				HttpClient client = new DefaultHttpClient();

				String postURL = "http://embedded.freehost.kr/testServers/update.jsp";

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("accident_status", accident));

				UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,
						HTTP.UTF_8);

				HttpPost post = new HttpPost(postURL);

				post.setEntity(ent);

				HttpResponse responsePOST = client.execute(post);

				HttpEntity resEntity = responsePOST.getEntity();

				if (resEntity != null)

				{

					Log.i("RESPONSE", EntityUtils.toString(resEntity));

				}

			} catch (IOException e) {

				e.printStackTrace();

			}
		}
		
		
	}

	private class InputAccident extends AsyncTask<Void, String, Void> {

		String accident;

		public InputAccident(String accident) {
			super();
			this.accident = accident;
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			insertData(accident);
			return null;
		}

		protected void insertData(String accident) {
			try {

				HttpClient client = new DefaultHttpClient();

				String postURL = "http://embedded.freehost.kr/testServers/insertAccident.jsp";

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("accident", accident));

				UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,
						HTTP.UTF_8);

				HttpPost post = new HttpPost(postURL);

				post.setEntity(ent);

				HttpResponse responsePOST = client.execute(post);

				HttpEntity resEntity = responsePOST.getEntity();

				if (resEntity != null)

				{

					Log.i("RESPONSE", EntityUtils.toString(resEntity));

				}

			} catch (IOException e) {

				e.printStackTrace();

			}
		}

	}

}
