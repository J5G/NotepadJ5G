package eleni.b.vhta.gmail.com.notelpadj5g;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class View_Notes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__notes);

        Database db = new Database(this);
        BackGroundTask bgtask = new BackGroundTask(this);

        bgtask.execute("get_notes");

        FloatingActionButton newNote= findViewById(R.id.NewNote);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),Editor.class);
                startActivityForResult(intent,0);
            }
        });
        }





    }

