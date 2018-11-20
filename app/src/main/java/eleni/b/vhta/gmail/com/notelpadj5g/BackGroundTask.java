package eleni.b.vhta.gmail.com.notelpadj5g;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

public class BackGroundTask extends AsyncTask<String, Controller, String>
{
    Context con;
    Notes_Adapter adpt;
    Activity activity;
    ListView listView;

    BackGroundTask(Context con)
    {
        this.con = con;
        activity = (Activity) con;
    }


    @Override
    protected String doInBackground(String... params)
    {
        String method = params[0];
        if (method.equals("get_notes"))
        {
            listView = activity.findViewById(R.id.ListView);
            Database db = new Database(con);
            SQLiteDatabase sqldb = db.getReadableDatabase();

            Cursor cursor= db.getNotes(sqldb);
            adpt = new Notes_Adapter(con,R.layout.display_notes_row);
            String title, text, coordinates;
            int id, bold, italics,underline;

            while (cursor.moveToNext())
            {
                id = cursor.getInt(cursor.getColumnIndex(Controller.columnId));
                title = cursor.getString(cursor.getColumnIndex(Controller.columnTitle));
                text = cursor.getString(cursor.getColumnIndex(Controller.columnText));
                coordinates = cursor.getString(cursor.getColumnIndex(Controller.columnCoordinates));
                bold = cursor.getInt(cursor.getColumnIndex(Controller.columnBold));
                italics = cursor.getInt(cursor.getColumnIndex(Controller.columnItalics));
                underline = cursor.getInt(cursor.getColumnIndex(Controller.columnUnderline));

                Controller cntrlr = new Controller(id,title,text,coordinates,bold,italics,underline);
                publishProgress(cntrlr);
            }
            return "get_notes";
        }
        return null;
    }


    @Override
    protected void onProgressUpdate(Controller... values)
    {
        adpt.add(values[0]);
    }
    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result)
    {
        if(result.equals("get_notes"))
        {
            listView.setAdapter(adpt);
        }
        else
        {
            Toast.makeText(con,result, Toast.LENGTH_LONG).show();
        }
    }
}
