package software.mathkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class OpcionesActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.mp3_pantalla_opciones);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer.start();
    }

    public void ontouchSumar(View view){
        Intent puente = new Intent(this.getApplicationContext(), SumarActivity.class);
        puente.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(puente);
    }

    public void ontouchContar(View view){
        Intent puente = new Intent(this.getApplicationContext(), ContarActivity.class);
        puente.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
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