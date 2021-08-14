package software.mathkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void ontouchTocarPantalla(View view){
        Toast.makeText(getApplicationContext(), "Tocaste la pantalla", Toast.LENGTH_SHORT).show();

        //Aqui programar para ir a la siguiente pantalla
        Intent puente = new Intent(this.getApplicationContext(), OpcionesActivity.class);
        startActivity(puente);
    }
}