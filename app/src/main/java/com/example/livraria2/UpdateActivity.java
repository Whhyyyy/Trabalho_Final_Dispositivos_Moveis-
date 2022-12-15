package com.example.livraria2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, author_input, pages_input,link2;
    Button update_button, delete_button,link_button;

    String id, title, author, pages, link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        link2 = findViewById(R.id.link2);
        link_button = findViewById(R.id.link_button);
        title_input = findViewById(R.id.title_input2);
        author_input = findViewById(R.id.author_input2);
        pages_input = findViewById(R.id.pages_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        //First we call this
        getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }
        /*link_button.setBottom(new View.OnClickListener() {
            @Override
        public void onClick(View view) {
                Uri webpage = Uri.parse(link2.getText().toString());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }

        });*/
         link_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse(link2.getText().toString());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }

        });
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //And only then we call this
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                title = title_input.getText().toString().trim();
                author = author_input.getText().toString().trim();
                pages = pages_input.getText().toString().trim();
                link = link2.getText().toString().trim();
                myDB.updateData(id, title, author, pages,link);
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }



    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("Titulo") &&
                getIntent().hasExtra("Autor") && getIntent().hasExtra("Paginas")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("Titulo");
            author = getIntent().getStringExtra("Autor");
            pages = getIntent().getStringExtra("Paginas");
            link = getIntent().getStringExtra("Link");

            //Setting Intent Data
            title_input.setText(title);
            author_input.setText(author);
            pages_input.setText(pages);
            link2.setText(link);
            Log.d("Felipe", title+" "+author+" "+pages );
        }else{
            Toast.makeText(this, "Sem Dados.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Apagar " + title + " ?");
        builder.setMessage("Tem Certeza que Voce quer Apagar " + title + " ?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}