package com.example.fabianfleischer.thevocabmaster;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.R;

/**
 * BlubberBlubber
 * Created by fabian.fleischer on 22.11.2014.
 */
public class ListeActivity extends ListActivity implements View.OnClickListener {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste);

        Button addBtn;
        Button delBtn;
        addBtn = (Button) findViewById(R.id.addBtn);
        delBtn = (Button) findViewById(R.id.delBtn);
        addBtn.setOnClickListener(this);
        delBtn.setOnClickListener(this);

    }

    public void addWord(){

    }

    public void deleteFirstWord() {

    }

    protected void onResume() {

    }

    protected void onPause() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) { //Checkt welches Element geklickt wurde.
            case R.id.addBtn:
                addWord();
                break;
            case R.id.delBtn:
                deleteFirstWord();
                break;
        }
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(ListeActivity.this, MenuActivity.class));
        finish();

    }
}
