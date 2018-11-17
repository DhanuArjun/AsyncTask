package com.example.android.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.asynctask.services.MyIntentService;

public class MainActivity extends AppCompatActivity {

    TextView output;
    ScrollView scrollView;
    private SimpleClass mTask;
    private boolean mTaskRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView = findViewById(R.id.scrollView);
        output =  findViewById(R.id.output);


        displayMessage("");

        SimpleClass simpleCl = new SimpleClass();
        simpleCl.execute("Hello ", "Dude", "this is the asyncTask");
        Log.i("Main:: ", "before delay");


    }

    public void runClickHandler(View view) {

        //this button start the async task if you press the button again the task i cancels
        if(mTaskRunning && mTask != null){
            mTask.cancel(true);
            mTaskRunning = false;
        }else {
            mTask = new SimpleClass();
            mTask.execute("R ", "G", "B");
            mTaskRunning = true;
        }

    }
    public void runClickHandler2(View view){

        MyIntentService.startActionFoo(this, "This is ", "intent service ");
    }
    public void clearClickHandler(View view) {

        output.setText("");

    }

    private void displayMessage(String on){

        output.append(on + "\n");

    }

    /* 1st string is input type
     * 2nd is progress bar
     * 3rd output */
    private class SimpleClass extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... strings) {

            for (String value :
                    strings) {
                if (isCancelled()){
                    publishProgress("cancelled");
                    break;
                }
                Log.i("Async ", "doInBackground  " + value);
                publishProgress(value);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            return "async is completed";
        }

        @Override
        protected void onProgressUpdate(String... values ){
            displayMessage(values[0]);

        }

        @Override
        protected void onPostExecute(String s) {
            displayMessage(s);
        }

        @Override
        protected void onCancelled() {
            displayMessage(" task is cancelled");
        }
    }
}
