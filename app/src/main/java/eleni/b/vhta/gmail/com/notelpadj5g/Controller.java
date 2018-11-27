package eleni.b.vhta.gmail.com.notelpadj5g;

import android.net.Uri;

import java.sql.Blob;
import java.util.Date;

public class Controller
{
    public static final String tableName = "NOTES";
    public static final String columnId = "ID";
    public static final String columnText = "TEXT";
    public static final String columnTitle = "TITLE";
    public static final String columnDate = "DATE";
    public static final String columnCoordinates = "COORDINATES";
    public static final String columnBold = "BOLD";
    public static final String columnItalics = "ITALICS";
    public static final String columnUnderline = "UNDERLINE";
    public static final String columnPhotograph = "PHOTOGRAPH";
    public static final String columnRecord = "RECORD";

    public static final String createTable="CREATE TABLE NOTES ( ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, TEXT TEXT, DATE TEXT, COORDINATES TEXT, BOLD INT, ITALICS INT, UNDERLINE INT, RECORD BLOB, PHOTOGRAPH TEXT )";

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

    public Controller(int id, String title, String note,String date, String coordinates, Blob record, int bold, int italics, int underline, String photograph)
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
