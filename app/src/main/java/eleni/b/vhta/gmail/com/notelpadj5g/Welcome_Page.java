package eleni.b.vhta.gmail.com.notelpadj5g;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Welcome_Page extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome__page);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent viewNotesIntent = new Intent( Welcome_Page.this, View_Notes.class );
                startActivity(viewNotesIntent);
            }
        },SPLASH_TIME_OUT);
    }
}
