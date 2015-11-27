package com.fly.hello;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;


public class MainActivity extends Activity {

    Vector<String> words = new Vector<String>();
    Vector<String> means = new Vector<String>();
    int index;
    boolean hideMean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        index = 0;
        hideMean = false;

        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            new AlertDialog.Builder(this.getApplicationContext()).setMessage("SD card can't be recognized")
                    .setPositiveButton("OK",null).show();
        }
        String sdpath = Environment.getExternalStorageDirectory()
                .getAbsolutePath();
        File file = new File(sdpath,"words.txt");

        Scanner in;
        String str ;
        String str2 = "";

        try {
            in = new Scanner(file);
            str = in.nextLine();
            str = str.replaceFirst("\\*", "");
            str = str.replaceFirst("\\*", "\n|");
            str = str.replaceFirst("\\*","|");
            words.add(str);
            while (in.hasNextLine()) {
                str = in.nextLine();

                if(str.contains("*")){
                    means.add(str2);
                    str2 = "";
                    str = str.replaceFirst("\\*", "");
                    str = str.replaceFirst("\\*", "\n|");
                    str = str.replaceFirst("\\*", "|");
                    words.add(str);
                }else
                    str2 += str;
                if(!in.hasNextLine())
                    means.add(str2);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

                    final EditText editText;
                    final LinearLayout login = (LinearLayout) getLayoutInflater().inflate(R.layout.alert_dialog, null);
                    editText = (EditText) login.findViewById(R.id.editTextIndex);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("自定义");
                    builder.setView(login);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            String str = editText.getText().toString();
                            index = Integer.valueOf(str);
                            TextView text2 = (TextView)findViewById(R.id.text2);
                            TextView text4 = (TextView)findViewById(R.id.text4);
                            text2.setText(words.get(index));
                            if(!hideMean)
                                text4.setText(means.get(index));
                            else
                                text4.setText("");
//                            Log.i("MainActivity", str);
//                            Toast.makeText(login.getContext(), str, Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.create().show();
            return true;
        }
        if(id == R.id.action_hideMean){
            hideMean = !hideMean;
            if(hideMean) {
                item.setTitle("showMean");
                TextView text4 = (TextView)findViewById(R.id.text4);
                text4.setText("");
            }
            else {
                item.setTitle("hideMean");
                TextView text4 = (TextView)findViewById(R.id.text4);
                text4.setText(means.get(index));
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void nextOnClick(View view) {

        TextView text2 = (TextView)findViewById(R.id.text2);
        TextView text4 = (TextView)findViewById(R.id.text4);
        if(text2.getText().toString().contains("Hello"))
            index = 0;
        else
            index ++;

        text2.setText(words.get(index));

        if(!hideMean)
            text4.setText(means.get(index));
        else
            text4.setText("");

    }

    public void previousOnClick(View view) {
        if(index > 0)
            index--;
        TextView text2 = (TextView)findViewById(R.id.text2);
        TextView text4 = (TextView)findViewById(R.id.text4);
        //     char[] text = new char[](words[index]);
        text2.setText(words.get(index));
        if(!hideMean)
            text4.setText(means.get(index));
        else
            text4.setText("");

    }


}
