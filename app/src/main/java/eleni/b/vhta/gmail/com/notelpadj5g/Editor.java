package eleni.b.vhta.gmail.com.notelpadj5g;

import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class Editor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);


        final CheckBox CheckBoxBold = findViewById(R.id.CheckBoxBold);
        final CheckBox CheckBoxItalics = findViewById(R.id.CheckBoxItalics);
        final CheckBox CheckBoxUnderline = findViewById(R.id.CheckBoxUnderline);
        final EditText EditorTextBox = findViewById(R.id.EditorTextBox);
        final Controller cntlr = new Controller();

        CheckBoxBold.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                    if(CheckBoxBold.isChecked())
                        {
                            EditorTextBox.setTypeface(null, Typeface.BOLD);
                            cntlr.setBold(1);
                        }
                    else
                        {
                            EditorTextBox.setTypeface(null, Typeface.NORMAL);
                            cntlr.setBold(0);
                        }

            }
        });
        CheckBoxItalics.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(CheckBoxItalics.isChecked())
                {
                    EditorTextBox.setTypeface(null, Typeface.ITALIC);
                    cntlr.setItalics(1);
                }
                else
                {
                    EditorTextBox.setTypeface(null, Typeface.NORMAL);
                    cntlr.setItalics(0);
                }
            }
        });
        CheckBoxUnderline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(CheckBoxUnderline.isChecked())
                {
                    EditorTextBox.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                    cntlr.setUnderline(1);
                }
                else
                {
                    EditorTextBox.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
                    cntlr.setUnderline(0);
                }
            }
        });


    }
}
