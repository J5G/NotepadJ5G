package eleni.b.vhta.gmail.com.notelpadj5g;

import android.net.Uri;

import java.sql.Blob;
import java.util.Date;

public class Controller
{
    private int notesID;
    private String title;
    private String note;
    private String date;
    private String coordinates;
    private Blob record;
    private int bold;
    private int italics;
    private int underline;
    private String photograph;

    public Controller()
    {

    }

    public Controller(int id, String title)
    {
        this.notesID = id;
        this.title = title;
        this.note = note;
        this.date = date;
        this.coordinates = coordinates;
        this.record = record;
        this.bold = bold;
        this.italics = italics;
        this.underline = underline;
        this.photograph = photograph;
    }

    public int getNotesID()
    {
        return notesID;
    }

    public void setNotesID(int notesID)
    {
        this.notesID = notesID;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getDate()
    {
        return date;
    }

    public String getCoordinates()
    {
        return coordinates;
    }

    public void setCoordinates(String coordinates)
    {
        this.coordinates = coordinates;
    }

    public Blob getRecord()
    {
        return record;
    }

    public void setRecord(Blob record)
    {
        this.record = record;
    }

    public int getBold()
    {
        return bold;
    }

    public void setBold(int bold)
    {
        this.bold = bold;
    }

    public int getItalics()
    {
        return italics;
    }

    public void setItalics(int italics)
    {
        this.italics = italics;
    }

    public int getUnderline()
    {
        return underline;
    }

    public void setUnderline(int underline)
    {
        this.underline = underline;
    }

    public String getPhotograph()
    {
        return photograph;
    }

    public void setPhotograph(String photograph)
    {
        this.photograph = photograph;
    }
}
