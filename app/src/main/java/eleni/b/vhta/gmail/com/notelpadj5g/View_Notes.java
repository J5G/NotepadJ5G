package eleni.b.vhta.gmail.com.notelpadj5g;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;



public class View_Notes extends AppCompatActivity {
    ArrayList<String> listItem;
    ArrayAdapter adapter;
    ListView list;
    AlertDialog alertDialog = null;
    Database db = new Database(this);
    String item;
    SwipeRefreshLayout swipeRefreshLayout;
    SearchView searchView;

    private static final String TAG ="View_Notes";
    private static final int ERROR_DIALOG_REQUEST=9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__notes);

        final Button ButtonByDate = findViewById(R.id.ButtonByDate);
        final Button ButtonByName = findViewById(R.id.ButtonByName);


        db = new Database(this);
        listItem = new ArrayList<>();
        list = findViewById(R.id.ListView);
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

        viewData();


        list.setOnItemClickListener(onListClick);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        swipeRefreshLayout.setRefreshing(false);
                        listItem = new ArrayList<>();
                        list = findViewById(R.id.ListView);
                        viewData();
                    }
                },2000);
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

        FloatingActionButton map= findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(servicesVersionCorrect())
                {
                    Intent intent = new Intent(View_Notes.this, Notes_On_Map.class);
                    startActivity(intent);
                }
            }
        });
    }

    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            item= list.getItemAtPosition(position).toString();
            edit_box();
        }
    };

    private void edit_box()
    {
        final View deleteView = getLayoutInflater().inflate(R.layout.delete_dialog,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("What do you wish to do?");
        builder.setView(deleteView);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("View Note or Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(View_Notes.this, EditorForUpdate.class);

                startActivity(intent);
            }
        });
        builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (item==null)
                {
                    return;
                }
                else
                {
                    db.delete(item);
                    item = null;
                    listItem = new ArrayList<>();
                    list = findViewById(R.id.ListView);
                    viewData();

                }
            }

        });
        builder.show();
    }

    public void viewData() {
        Cursor cursor= db.viewData();
        if (cursor.getCount()==0){
            Toast.makeText(this,"no data to show", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while (cursor.moveToNext()){
                String date=cursor.getString(0);
                String title=cursor.getString(1);
                String total = date + " - " + title;
                listItem.add(total);
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listItem);
        list.setAdapter(adapter);
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

    public boolean servicesVersionCorrect()
    {
        Log.d(TAG,"servicesVersionCorrect: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(View_Notes.this);
        if(available == ConnectionResult.SUCCESS)
        {
            Log.d(TAG,"servicesVersionCorrect: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available))
        {
            Log.d(TAG,"servicesVersionCorrect: An error occured be it can be solved");
            android.app.Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(View_Notes.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else
        {
            Toast.makeText(this,"we cannot make map requests",Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}

