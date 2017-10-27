package au.edu.federation.myapplication;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
/**
 * Created by Victor on 16/10/2017.
 */

public class UploadDataAsyncTask extends AsyncTask<String, Void, String> {

    private static final String dburl = "http://192.168.1.6:80/post_test.php";

    private static final Context appContext = Global.getInstance().appContext;

    private static String androidId = Settings.Secure.getString(appContext.getContentResolver(), Settings.Secure.ANDROID_ID);

    private static final String loginSuccess = "Login Successful!";
    private static final String loginFailure = "Login Failed!";

    private static final int readTimeout = 5000 /* milliseconds */;
    private static final int connectTimeout = 5000 /* milliseconds */;

    private String todo;

    protected void onPreExecute(){}

    protected String doInBackground(String... arg0) {

        todo = arg0[0];

        if(todo.equals("login"))
        {
            if(processLogin(arg0[1], arg0[2]))
            {
                return "success";
            }
            else
            {
                return "failed";
            }

        }

        if(todo.equals("upload"))
        {
            processWellbeingUploadData();
            return "";
        }

        return "failed";

    }

    @Override
    protected void onPostExecute(String result) {

        if(todo.equals("login"))
        {
            if (result.equals("success")) {
                displayInformationToast("Login Success!!!");
                Intent launchMain = new Intent(LoginActivity.context, MainActivity.class);
                LoginActivity.context.startActivity(launchMain);
            }
            else
            {
                displayInformationToast("Login Failed!!!");
            }
        }
    }


    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    public Boolean sendData(JSONObject dataToSend)
    {
        try {

            URL url = new URL(dburl); // here is your URL path

            Log.e("params",dataToSend.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(readTimeout /* milliseconds */);
            conn.setConnectTimeout(connectTimeout /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(dataToSend));

            writer.flush();
            writer.close();
            os.close();

            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in=new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                if(todo.equals("login"))
                {
                    //Read data as JSON text
                    String data = in.readLine();
                    Log.d("login - readLine()", data);

                    //Convert text to Json object and read data
                    JsonElement jelement = new JsonParser().parse(data);
                    JsonObject  jobject = jelement.getAsJsonObject();

                    String success = jobject.get("success").toString();
                    Log.d("login - success", success);
                    if (removeQuotes(success).equals("true")) {
                        Log.d("login - success", success);

                        int userID = Integer.parseInt(removeQuotes(jobject.get("userID").toString()));
                        Log.d("login - userID", Integer.toString(userID));

                        String name = removeQuotes(jobject.get("name").toString());
                        Log.d("login - name", name);

                        String gender = removeQuotes(jobject.get("gender").toString());
                        Log.d("login - gender", gender);

                        //Insert user data into database
                        int achievementPoints = 0;
                        Control.setUser(appContext,  userID, name, achievementPoints, gender);


                        in.close();
                        return true;
                    }
                    else
                    {
                        Log.d("login - fail", data);
                        in.close();
                        return false;
                    }
                }

                if(todo.equals("upload"))
                {
                    String success = in.readLine();
                    Log.d("upload - success", success);
                    if (success == "true")
                    {

                        in.close();
                        return true;
                    }
                    in.close();
                    return false;
                }

                in.close();
                return false;

            }
            else {
                return false;
            }
        }
        catch(Exception e){
            return false;
        }
    }

    public Boolean processLogin(String userID, String password)
    {
        try
        {
            JSONObject postDataParams = new JSONObject();

            postDataParams.put("todo", "login");
            postDataParams.put("userID", userID);
            postDataParams.put("password", password);
            postDataParams.put("deviceID", androidId);

            Boolean success = sendData(postDataParams);

            return success;

        }
        catch(Exception e)
        {
            return false;
        }
    }

    public void processWellbeingUploadData()
    {
        try{
            JSONObject postDataParams = new JSONObject();

            List<Wellbeing> wellbeingData = Control.getWellbeingTable(appContext);
            for(Wellbeing row: wellbeingData)
            {
                if(Time.isBeforeCurrentDateTime(row.getDate()))
                {
                    postDataParams = new JSONObject();

                    //add object to dataToSend array
                    postDataParams.put("todo", "upload");
                    postDataParams.put("table", "wellbeing");
                    postDataParams.put("userID", row.getUserID());
                    postDataParams.put("question1", Integer.toString(row.getQuestion1()));
                    postDataParams.put("question2", Integer.toString(row.getQuestion2()));
                    postDataParams.put("question3", Integer.toString(row.getQuestion3()));
                    postDataParams.put("question4", Integer.toString(row.getQuestion4()));
                    postDataParams.put("question5", Integer.toString(row.getQuestion5()));
                    postDataParams.put("date", row.getDate());

                    //send data to server and store return value which indicates success
                    Boolean success = sendData(postDataParams);
                    Log.d("upload success", success.toString());

                    //Delete uploaded data from database if server returns successful
                    if(success)
                    {
                        Control.deleteWellbeingRow(appContext, row.getDate());
                            Log.d("delete row - wellbeing", row.getDate());
                    }

                }
            }
        }
        catch(Exception e)
        {
            Log.d("UploadData", "Error uploading data");
        }
    }



    public void displayInformationToast(String information)
    {
        Context context = LoginActivity.context;
        CharSequence text = information;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public String removeQuotes(String data)
    {
        String newData = data.substring(1, (data.length() - 1));
        return newData;
    }
}

