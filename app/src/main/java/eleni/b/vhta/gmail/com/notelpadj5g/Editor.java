package eleni.b.vhta.gmail.com.notelpadj5g;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Editor extends AppCompatActivity {

    public static final int REQUEST_CODE = 20;
    public static final int IMAGE_GALLERY_REQUEST = REQUEST_CODE;
    private ImageView imgPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);


        final CheckBox CheckBoxBold = findViewById(R.id.CheckBoxBold);
        final CheckBox CheckBoxItalics = findViewById(R.id.CheckBoxItalics);
        final CheckBox CheckBoxUnderline = findViewById(R.id.CheckBoxUnderline);
        final EditText EditorTextBox = findViewById(R.id.EditorTextBox);
        final FloatingActionButton ButtonSave= findViewById(R.id.ButtonSave);
        final Controller cntlr = new Controller();
        final Database db= new Database(this);

        imgPicture = (ImageView) findViewById(R.id.imageView2);


        ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = db.insertNote(cntlr.getTitle(),EditorTextBox.getText().toString(),cntlr.getCoordinates(),cntlr.getBold(),cntlr.getItalics(),cntlr.getUnderline(),null,null);
                System.out.println(id);
                cntlr.setNote(EditorTextBox.getText().toString());
            }
        });

        CheckBoxBold.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (CheckBoxBold.isChecked()) {
                    EditorTextBox.setTypeface(null, Typeface.BOLD);
                    cntlr.setBold(1);
                } else {
                    EditorTextBox.setTypeface(null, Typeface.NORMAL);
                    cntlr.setBold(0);
                }

            }
        });
        CheckBoxItalics.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (CheckBoxItalics.isChecked()) {
                    EditorTextBox.setTypeface(null, Typeface.ITALIC);
                    cntlr.setItalics(1);
                } else {
                    EditorTextBox.setTypeface(null, Typeface.NORMAL);
                    cntlr.setItalics(0);
                }
            }
        });
        CheckBoxUnderline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (CheckBoxUnderline.isChecked()) {
                    EditorTextBox.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                    cntlr.setUnderline(1);
                } else {
                    EditorTextBox.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
                    cntlr.setUnderline(0);
                }
            }
        });
        }
        /**
         * Invoke onImageGalleryClick when user clicks button Images
         * @param v
         */
        public void onImageGalleryClick(View v)
        {
            //Invoke Image Gallery
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

            //Where we want to find the data(pictures)?
            File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String photoPath = pictureDirectory.getPath();

            //URI representation
            Uri pdata = Uri.parse(photoPath);

            //Get all image types
            photoPickerIntent.setDataAndType(pdata, "image/*");
            startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
        {
            if(resultCode==RESULT_OK)
            {
                // We are here only if everything processed successfully
                if(requestCode==IMAGE_GALLERY_REQUEST)
                {
                    // If we are here we access image gallery
                    // The address of the image on the SD card
                    Uri imageUri = data.getData();
                    // Declare a stream to read data from SD card
                    InputStream inputStream;

                    try {
                        // Get an input stream based on the URI of the image
                        inputStream = getContentResolver().openInputStream(imageUri);
                        // Get bitmap from stream
                        Bitmap bitmapImage = BitmapFactory.decodeStream(inputStream);
                        // Show image to the user
                        imgPicture.setImageBitmap(bitmapImage);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        // Show message to the user that the picture is unavailable
                        Toast.makeText(this, "Image Unavailable", Toast.LENGTH_LONG).show();
                    }
                }
            }


    }
}
