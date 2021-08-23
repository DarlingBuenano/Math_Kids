package software.mathkids;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class SumarActivity extends AppCompatActivity {

    TextView txtNumero1, txtNumero2, txtOpcion1_actS, txtOpcion2_actS, txtOpcion3_actS, txtOpcion4_actS;
    int respuesta;
    int[] opciones;
    int[] aleatorios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sumar);

        txtNumero1 = (TextView) findViewById(R.id.txtNumero1);
        txtNumero2 = (TextView) findViewById(R.id.txtNumero2);

        txtOpcion1_actS = findViewById(R.id.txtOpcion1_actS);
        txtOpcion2_actS = findViewById(R.id.txtOpcion2_actS);
        txtOpcion3_actS = findViewById(R.id.txtOpcion3_actS);
        txtOpcion4_actS = findViewById(R.id.txtOpcion4_actS);
    }

    @Override
    protected void onStart() {
        super.onStart();

        int num1 = generarNumeroAlea();
        int num2 = generarNumeroAlea();

        respuesta = num1 + num2;

        txtNumero1.setText(String.valueOf(num1));
        txtNumero2.setText(String.valueOf(num2));

        opciones = new int[4];
        txtOpcion1_actS.setText(String.valueOf(respuesta - 2));
        txtOpcion2_actS.setText(String.valueOf(respuesta));
        txtOpcion3_actS.setText(String.valueOf(respuesta + 1));
        txtOpcion4_actS.setText(String.valueOf(respuesta + 3));
    }

    public void ontouchOpcion(View view){
        TextView txtOpcion = (TextView) view;

        try{
            //Respondió bien
            if(txtOpcion.getText().equals(String.valueOf(respuesta))){
                AlertDialog.Builder builder= new AlertDialog.Builder(this);
                builder.setTitle("Felicitaciones");
                builder.setMessage("Has respondido bien");
                builder.setPositiveButton("Ir al inicio", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent puente = new Intent(getApplicationContext(), OpcionesActivity.class);
                        puente.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(puente);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("¡Ups!");
                builder.setMessage("No has respondido bien");
                builder.setNegativeButton("Intentar de nuevo", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
        catch (IllegalStateException ex){
            System.out.println("Error -> " + ex.getMessage());
        }
    }

    private int generarNumeroAlea(){
        Random random = new Random();
        return random.nextInt(10);
    }
}