package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

//1. db에서 단어를 가져와 list에 저장한다.
//2. 검색 시 단어의 의미를 db에서 가져온다.
//3. 리스트뷰 setSelectionFromTop


public class MainActivity extends Activity implements  View.OnClickListener {
    EditText textWord;
    TextView meaning;
    Button buttonSearch;
    String word;

    // DB관련
    SQLiteDatabase db;
    MySQLiteOpenHelper mHelper;

    // 리스트 관련
    ArrayList<String> words;
    ArrayAdapter<String> adapter;
    ListView wordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 초기화
        words = new ArrayList<String>();

        // layout 연결
        textWord = (EditText) findViewById(R.id.editText_word);
        buttonSearch = (Button) findViewById(R.id.button_find);
        meaning = (TextView) findViewById(R.id.textView_find);
        wordList = (ListView) findViewById(R.id.listView_wordList);

        // 리스너 등록
        buttonSearch.setOnClickListener((View.OnClickListener) this);

        // db
        mHelper = new MySQLiteOpenHelper(this);
        // list
        initListView();
    }

    public void initListView() {
        // data
        db = mHelper.getReadableDatabase();
        Cursor cursor;

        String sql = "SELECT word from Dictionary";
        cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            String word = cursor.getString(0);
            words.add(word);
        }

        // adapter
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, words);

        // adapter연결
        wordList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v){

        switch(v.getId()) {
            case R.id.button_find :
                search();
                break;
        }
    }

    public void search() {
        db = mHelper.getReadableDatabase();
        Cursor cursor;
        word = textWord.getText().toString();

        String sql = "SELECT * from Dictionary where word like '" + word + "%'";
        cursor = db.rawQuery(sql, null);

    //    String result = "";
        cursor.moveToFirst();

        int _id = cursor.getInt(0);
        String word = cursor.getString(1);
        String result = cursor.getString(2);
/*
        while (cursor.moveToNext()) {
            _id = cursor.getInt(0);
            String word = cursor.getString(1);
            String meaning = cursor.getString(2);
            result += (meaning + "\n");
        }*/
/*
        if(result.length()==0)
            meaning.setText("not found");
        else
            meaning.setText(result);
*/
        wordList.setSelection(_id-1);
        meaning.setText(result);
        cursor.close();
        mHelper.close();
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
