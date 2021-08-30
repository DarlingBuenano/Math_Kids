package software.mathkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.mp3_pantalla_principal);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer.start();
    }

    public void ontouchTocarPantalla(View view){
        Intent puente = new Intent(this.getApplicationContext(), OpcionesActivity.class);
        startActivity(puente);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        mediaPlayer.stop();
    }
}