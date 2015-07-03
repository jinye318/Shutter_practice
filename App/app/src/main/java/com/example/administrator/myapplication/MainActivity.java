package com.example.administrator.myapplication;

import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    EditText text_word;
    TextView find;
    Button button_search;
    Dictionary dict;
    String word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_word = (EditText) findViewById(R.id.editText_word);
        button_search = (Button) findViewById(R.id.button_find);
        find = (TextView) findViewById(R.id.textView_find);

        Button.OnClickListener mClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                word = text_word.getText().toString();
                find.setText(dict.find(word));
            }
        };

        button_search.setOnClickListener(mClickListener);
        dict = new Dictionary("a.dic");
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
