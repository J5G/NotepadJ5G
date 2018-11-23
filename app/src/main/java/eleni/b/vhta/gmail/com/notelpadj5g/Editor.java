package eleni.b.vhta.gmail.com.notelpadj5g;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Calendar;

public class Editor extends AppCompatActivity implements Dialog.DialogListener {

    public static final int REQUEST_CODE = 20;
    public static final int IMAGE_GALLERY_REQUEST = REQUEST_CODE;
    private ImageView imgPicture;
    private TextView dateTimeView;
    private static final String TAG ="Editor";
    private static final int ERROR_DIALOG_REQUEST=9001;
    final Controller cntlr = new Controller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);



        final CheckBox CheckBoxBold = findViewById(R.id.CheckBoxBold);
        final CheckBox CheckBoxItalics = findViewById(R.id.CheckBoxItalics);
        final CheckBox CheckBoxUnderline = findViewById(R.id.CheckBoxUnderline);
        final EditText EditorTextBox = findViewById(R.id.EditorTextBox);
        final FloatingActionButton ButtonSave= findViewById(R.id.ButtonSave);
        final Button ButtonCoordinates = findViewById(R.id.ButtonCoordinates);
        FloatingActionButton ButtonBack= findViewById(R.id.ButtonBack);
        final Database db= new Database(this);


        imgPicture = (ImageView) findViewById(R.id.imageView2);

        dateTimeView = (TextView) findViewById(R.id.textViewDate);
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        dateTimeView.setText(currentDate);


        ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                OpenDialog();
                long id = db.insertNote(cntlr.getTitle(), EditorTextBox.getText().toString(), cntlr.getCoordinates(), cntlr.getBold(), cntlr.getItalics(), cntlr.getUnderline(), null, cntlr.getPhotograph());
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
        ButtonCoordinates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(servicesVersionCorrect())
                {
                    Intent intent = new Intent(Editor.this, Map.class);
                    startActivity(intent);
                }
            }
        });
        ButtonBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(v.getContext(),View_Notes.class);
                startActivityForResult(intent,0);
            }
        });
        }

        public void OpenDialog()
        {
            Dialog dialog = new Dialog();
            dialog.show(getSupportFragmentManager(), "dialog");

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
                        String imgPictureString = imageViewEncodeToString(imgPicture);
                        cntlr.setPhotograph(imgPictureString);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        // Show message to the user that the picture is unavailable
                        Toast.makeText(this, "Image Unavailable", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }

    private String imageViewEncodeToString(ImageView image)
    {
        Bitmap bitmapImage2 = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapImage2.compress(Bitmap.CompressFormat.PNG,50,stream);
        byte[] byteArray = stream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.URL_SAFE);
    }

    @Override
    public void applyText(String title) {
        cntlr.setTitle(title);
        // testing for giving title
        // it works
        System.out.println(title);
    }

    //Συνάρτηση που ελέγχει αν το κινητό έχει την απαραίτητη έκδοση της υπηρεσίας Google Services
    public boolean servicesVersionCorrect()
    {
        Log.d(TAG,"servicesVersionCorrect: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Editor.this);
        if(available == ConnectionResult.SUCCESS)
        {
            Log.d(TAG,"servicesVersionCorrect: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available))
        {
            Log.d(TAG,"servicesVersionCorrect: An error occured be it can be solved");
            android.app.Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Editor.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else
        {
            Toast.makeText(this,"we cannot make map requests",Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
