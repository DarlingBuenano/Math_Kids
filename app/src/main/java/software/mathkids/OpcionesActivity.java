package software.mathkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OpcionesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void ontouchSumar(View view){

    }

    public void ontouchContar(View view){
        Intent puente = new Intent(this.getApplicationContext(), ContarActivity.class);
        startActivity(puente);
    }
}