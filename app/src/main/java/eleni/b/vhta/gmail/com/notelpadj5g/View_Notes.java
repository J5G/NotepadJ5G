package eleni.b.vhta.gmail.com.notelpadj5g;

import android.app.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;



public class View_Notes extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "notepad";
    //our view from layout
    private ListView list;
    private FloatingActionButton newNote;
    private ArrayAdapter<String> adapter;
    private Cursor notes;
    private Database db ;
    private ArrayList<String> titles;
    private ArrayList listItem;
    private ArrayList<Controller> items;
    //will contain the position of clicked item in listview
    private int position =0;

    SwipeRefreshLayout swipeRefreshLayout;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__notes);

        final Button ButtonByDate = findViewById(R.id.ButtonByDate);
        final Button ButtonByName = findViewById(R.id.ButtonByName);
        list = findViewById(R.id.ListView);
        db = new Database(this);
        listItem = new ArrayList<>();
        searchView= findViewById(R.id.SearchNote);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        setNotes();
        this.registerForContextMenu(list);


        //the Button new note
        newNote= findViewById(R.id.NewNote);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Editor.class));
            }
        });

        ButtonByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewSortTitleData();
            }
        });

        ButtonByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               viewSortDateData();
            }
        });

    }

    public void setNotes(){
        titles= new ArrayList<String>();
        items= new ArrayList<Controller>();

        //getting readable database
        SQLiteDatabase dbase= db.getReadableDatabase();
        notes=db.getNotes2(dbase);

        startManagingCursor(notes);
        dbase.close();

        if (notes.moveToFirst()){
            do{
                items.add(new Controller(notes.getShort(0),notes.getString(1)));
            }while (notes.moveToNext());
        }
        for (Controller i : items){
            titles.add(i.getTitle());
        }
        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titles);
        list.setAdapter(adapter);
        list.setOnItemClickListener((AdapterView.OnItemClickListener) this);

    }

    @Override
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
    super.onCreateContextMenu(menu,v,menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        position= info.position;
        menu.setHeaderTitle(getResources().getString(R.string.CtxMenuHeader));
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_menu,menu);

    }

    //method id called when user clicks on context menu item
    @Override
    public boolean onContextItemSelected(MenuItem item){
        TextView tv = (TextView) list.getChildAt(position);
        //getting the title of this textView
        String title = tv.getText().toString();
        //performing one of actions, depending on the user's choice
        switch (item.getItemId()){
            case R.id.editNote:
                Intent i = new Intent(this,EditorForUpdate.class);
                i.putExtra("ID", items.get(position).getNotesID());
                Log.d(TAG,title);
                i.putExtra("isEdit",true);
                startActivity(i);
                break;
            case R.id.removeNote:
                //removing this note
                db.removeNote(items.get(position).getNotesID());
                setNotes();
                break;
        }
        return false;
    }

    /*
    @Override
    public void onItemClick ( AdapterView<?> arg0, View arg1, int arg2, long arg3)
    {
       TextView tv = (TextView) arg1;
       String title = tv.getText().toString();
       Intent mIntent = new Intent(this,EditorForUpdate.class);
       mIntent.putExtra("title",title);
       mIntent.putExtra("ID", items.get(arg2).getNotesID());
       startActivity(mIntent);

    }
    */

    @Override

    protected void onResume() {

        super.onResume();

        setNotes();

    }



    public void viewSortTitleData() {
        listItem = new ArrayList<>();
        Cursor cursor = db.viewSortTitleData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "no data to show", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String date = cursor.getString(0);
                String title = cursor.getString(1);
                String total = date + " - " + title;
                listItem.add(total);
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
        list.setAdapter(adapter);
    }

    public void viewSortDateData() {
        listItem = new ArrayList<>();
        Cursor cursor = db.viewSortDateData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "no data to show", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String date = cursor.getString(0);
                String title = cursor.getString(1);
                String total = date + " - " + title;
                listItem.add(total);
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
        list.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

