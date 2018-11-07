package eleni.b.vhta.gmail.com.notelpadj5g;

import java.sql.Blob;
import java.util.Date;

public class Controller
{
    public static final String tableName1 = "NOTES";
    public static final String columnID = "ID_NOTES";
    public static final String columnText = "TEXT";
    public static final String columnTitle = "TITLE";
    public static final String columnDate = "DATE";
    public static final String columnCoordinates = "COORDINATES";
    public static final String columnRecord = "RECORD";
    public static final String columnBold = "BOLD";
    public static final String columnItalics = "ITALICS";
    public static final String columnUnderline = "UNDERLINE";
    public static final String columnPhotograph = "PHOTOGRAPH";

    // Create Table Notes SQL query
    public static final String createNotesTable = "CREATE TABLE " + tableName1 + "(" + columnID +" INT IDENTITY(1,1), "+ columnTitle +" VARCHAR(60) NOT NULL, "+columnText+" TEXT NULL, "+columnDate+" DATE NOT NULL, "+columnCoordinates+" TEXT NULL, "+columnRecord + " BLOB NULL, "+ columnPhotograph + " BLOB NULL, " + columnBold + " INT NULL, " + columnItalics + " INT NULL, " +columnUnderline + " INT NULL, CONSTRAINT PK_NOTES PRIMARY KEY (ID_NOTES))";

    private int notesID;
    private String title;
    private String note;
    private Date date;
    private String coordinates;
    private Blob record;
    private int bold;
    private int italics;
    private int underline;
    private Blob photograph;

    public Controller()
    {

    }

    public Controller(int notesID, String title, String note, Date date, String coordinates, Blob record, int bold, int italics, int underline, Blob photograph)
    {
        this.notesID = notesID;
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

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
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

    public Blob getPhotograph()
    {
        return photograph;
    }

    public void setPhotograph(Blob photograph)
    {
        this.photograph = photograph;
    }
}
