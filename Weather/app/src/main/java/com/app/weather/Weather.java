//package com.app.weather;
//
//import android.os.AsyncTask;
//import android.widget.ArrayAdapter;
//import android.widget.ListAdapter;
//import android.widget.SimpleAdapter;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//
//import static com.app.weather.MainActivity.wList;
//
//public class Weather extends AsyncTask<String,Void,String> {
//    String result;
//    public ArrayList<WeatherData> wlist = new ArrayList<>();
//    @Override
//    protected String doInBackground(String... urls) {
//        result ="";
//        URL link;
//        HttpURLConnection myConnection = null;
//        try {
//            link = new URL(urls[0]);
//            myConnection = (HttpURLConnection)link.openConnection();
//            InputStream in = myConnection.getInputStream();
//            InputStreamReader myStreamReader = new InputStreamReader(in);
//            int data = myStreamReader.read();
//            while (data!=-1){
//                char current = (char)data;
//                result+=current;
//                data = myStreamReader.read();
//            }
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    @Override
//    protected void onPostExecute(String s){
//        super.onPostExecute(s);
//
//        try{
//            JSONObject myObject = new JSONObject(result);
//            JSONArray main = myObject.getJSONArray("list");
//            //int dateI = main.getInt("dt"); // get date
//            //String date = Integer.toString(dateI);
//            //JSONObject lisMain = new JSONObject(main.getString("main"));
//            //String temp = lisMain.getString("temp"); // get temp
//
//            String placeName = myObject.getString("name");
//
//            //JSONArray getArray = main.getJSONArray("JArray1");
//            ArrayList<String> friendList=new ArrayList<String>();
//
//            for (int i = 0; i < main.length(); i++) {
//                JSONObject objects = main.getJSONObject(i);
//                int dateI = objects.getInt("dt"); // get date
//                String date = Integer.toString(dateI);
//                JSONObject lisMain = new JSONObject(objects.getString("main"));
//                String temp = lisMain.getString("temp"); // get temp
//
//
//
//                //String[] elementNames = JSONObject.getNames(objectInArray);
//                // String location = objects.getString("name");
//                //  String temp =  objects.getString("temp");
//                // Object a = new WeatherData();
//                // friendList.add(temp);
//                // adapter = new ArrayAdapter<>(this, R.layout.w_item, R.id.w_temp, friendList);
//                // adapter.notifyDataSetChanged();
//                //taskList.setAdapter(adapter);
//
//
//                wlist.add(new WeatherData(date,temp));
//
//            }
//
//            ListAdapter adapter = new SimpleAdapter(this,wList,R.layout.w_item,new String[]{"w_date","w_temp"}, new int[]{R.id.w_date,R.id.w_temp});
//
//            // MainActivity.place.setText(placeName);
//            //MainActivity.temp.setText( );
//
//
//            // MainActivity.wList.
//        } catch (JSONException e){
//            e.printStackTrace();
//        }
//    }
//
//}