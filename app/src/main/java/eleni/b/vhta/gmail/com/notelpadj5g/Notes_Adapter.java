package eleni.b.vhta.gmail.com.notelpadj5g;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Notes_Adapter extends ArrayAdapter
{
    List list = new ArrayList();

    public Notes_Adapter(Context context, int resource)
    {
        super(context, resource);
    }

    public void add (Controller object)
    {
        list.add(object);
        super.add(object);
    }

    @Override
    public  int getCount()
    {
        return  list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        NotesHolder notesHolder;
        if(row==null)
        {
            LayoutInflater layoutflater =(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutflater.inflate(R.layout.display_notes_row,parent,false);
            notesHolder = new NotesHolder();
            notesHolder.date = row.findViewById(R.id.date);
            notesHolder.title = row.findViewById(R.id.title);
            row.setTag(notesHolder);
        }
        else
        {
            notesHolder =(NotesHolder) row.getTag();
        }
        Controller controller = (Controller) getItem(position);
        //notesHolder.date.setText(controller.getDate().toString);
        notesHolder.title.setText(controller.getTitle().toString());
        return row;
    }

    static  class NotesHolder
    {
        TextView date,title;
    }
}
