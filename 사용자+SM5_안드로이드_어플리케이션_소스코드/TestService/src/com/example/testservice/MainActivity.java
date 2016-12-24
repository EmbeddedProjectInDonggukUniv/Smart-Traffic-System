package com.example.testservice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	UpdateAccident updateAccident;
	ViewTrafficSituation viewTrafficSituation;
	
	String result;

	Handler handler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TimerTask timerTask = new TimerTask() {

			public void run() {
				Runnable updater = new Runnable() {
					public void run() {
						try {
							
							viewTrafficSituation = new ViewTrafficSituation();
							result =viewTrafficSituation.execute().get();
							if(result.equals("yes")){
								startService(new Intent("android.intent.action.service"));
								updateAccident = new UpdateAccident("no");
								updateAccident.execute();
							}
							
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
/*
		Button btn_play = (Button) findViewById(R.id.btn_play);
		btn_play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startService(new Intent("android.intent.action.service"));
			}
		});

		Button btn_stop = (Button) findViewById(R.id.btn_stop);
		btn_stop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				stopService(new Intent("android.intent.action.service"));
			}
		});
*/
	}

	private class UpdateAccident extends AsyncTask<Void, String, Void> {
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

	private class ViewTrafficSituation extends AsyncTask<Void, Void, String> {

		public ViewTrafficSituation() {

		}

		@Override
		protected String doInBackground(Void... voids) {

			String result = loadData();
			String[][] parsedData = jsonParserList(result);

			if (parsedData == null) {
				return "0";
			}

			return parsedData[0][0];
		}

		protected String loadData() {

			HttpURLConnection conn = null;

			try {
				URL url = new URL(
						"http://embedded.freehost.kr/testServers/selectFireman.jsp");
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");

				conn.setDoInput(true);
				conn.setDoOutput(true);

				conn.setConnectTimeout(60);

				OutputStream os = conn.getOutputStream();
				BufferedWriter writer = new BufferedWriter(
						new OutputStreamWriter(os, "UTF-8")); // 캐릭터셋 설정

				writer.flush();
				writer.close();
				os.close();

				conn.connect();

				BufferedReader br = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "UTF-8")); // 캐릭터셋 설정

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

				String[] jsonName = { "accident_status" };
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
