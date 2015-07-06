package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity implements  View.OnClickListener {
    EditText text_word;
    TextView find;
    Button button_search;
    String word;

    SQLiteDatabase db;
    MySQLiteOpenHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_word = (EditText) findViewById(R.id.editText_word);
        button_search = (Button) findViewById(R.id.button_find);
        find = (TextView) findViewById(R.id.textView_find);

        button_search.setOnClickListener((View.OnClickListener)this);
        mHelper = new MySQLiteOpenHelper(this);
        //dict = new Dictionary("a.dic");
    }

    @Override
    public void onClick(View v){

        switch(v.getId()) {
            case R.id.button_find :
/*                word = text_word.getText().toString();
                String sql = "select meaning from Dictionary where word = '" + word + "';";
                //find_db(sql);
                if(find.equals(""))
                    find.setText("app��ã��");
                else
                    find.setText(dict.find(word));*/

                search();
                break;
        }
    }

    public void search() {
        db = mHelper.getReadableDatabase();
        Cursor cursor;
        word = text_word.getText().toString();

        String sql = "SELECT word, meaning from Dictionary where word='" + word + "'";
        cursor = db.rawQuery(sql, null);

        String result = "";
        while (cursor.moveToNext()) {
            String word = cursor.getString(0);
            String meaning = cursor.getString(1);
            result += (word + " : " + meaning + "\n");
        }

        if(result.length()==0)
            find.setText("not found");
        else
            find.setText(result);

        cursor.close();
        mHelper.close();
    }


    public void find_db(String sql) {
        db = mHelper.getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        String meaning = "";
        if (c.moveToFirst()) {
            do {
                meaning = c.getString(0);
//                find.setText(meaning);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
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
