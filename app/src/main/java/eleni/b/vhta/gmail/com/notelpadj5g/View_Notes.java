package eleni.b.vhta.gmail.com.notelpadj5g;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class View_Notes extends AppCompatActivity {

    Database db;
    ArrayList<String> listItem;
    ArrayAdapter adapter;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__notes);

        db = new Database(this);
        listItem = new ArrayList<>();
        list = findViewById(R.id.ListView);

        viewData();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text= list.getItemAtPosition(1).toString();
                Toast.makeText(View_Notes.this, "" +text, Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton newNote= findViewById(R.id.NewNote);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),Editor.class);
                startActivityForResult(intent,0);
            }
        });
        }

    public void viewData() {
        Cursor cursor= db.viewData();
        if (cursor.getCount()==0){
            Toast.makeText(this,"no data to show", Toast.LENGTH_SHORT).show();
        }
        else
            {
            while (cursor.moveToNext()){
                listItem.add(cursor.getString(1));
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listItem);
        list.setAdapter(adapter);
    }


}

