package software.mathkids;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Hashtable;
import java.util.Random;
import java.util.Dictionary;

public class SumarActivity extends AppCompatActivity {

    TextView txtNumero1, txtNumero2, txtOpcion1_actS, txtOpcion2_actS, txtOpcion3_actS, txtOpcion4_actS;
    int respuesta, contador, num1, num2;
    Dictionary<Integer, String> msjFelicitacion;
    AlertDialog.Builder builder;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sumar);
        txtNumero1 = findViewById(R.id.txtNumero1);
        txtNumero2 = findViewById(R.id.txtNumero2);
        txtOpcion1_actS = findViewById(R.id.txtOpcion1_actS);
        txtOpcion2_actS = findViewById(R.id.txtOpcion2_actS);
        txtOpcion3_actS = findViewById(R.id.txtOpcion3_actS);
        txtOpcion4_actS = findViewById(R.id.txtOpcion4_actS);

        generarNivel(0);
        dibujarNivel();
        contador = 1;

        msjFelicitacion = new Hashtable<Integer, String>();
        msjFelicitacion.put(1, "Muy bien, vas por buen camino. Pasemos al nivel 2");
        msjFelicitacion.put(2, "Excelente, intentémoslo una vez más. Vamos al nivel 3");
        msjFelicitacion.put(3, "Genial, has completado todos los niveles");

        builder = new AlertDialog.Builder(this);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.mp3_pantalla_suma);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer.start();
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

    public void onClicOpcion(View view){
        TextView txtOpcion = (TextView) view;

        try{
            if(txtOpcion.getText().equals(String.valueOf(respuesta))){
                builder.setTitle("¡Felicitaciones!");
                builder.setMessage( msjFelicitacion.get(this.contador) );

                this.contador = this.contador + 1;

                if(contador <= msjFelicitacion.size()){
                    builder.setPositiveButton("Ir al nivel " + String.valueOf(contador), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            generarNivel(contador*4);
                            dibujarNivel();
                        }
                    });
                }
                else{
                    builder.setPositiveButton("", null);
                }
                builder.setNegativeButton("Volver al inicio", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        volverAlInicio();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else{
                builder.setTitle("¡Ups!");
                builder.setMessage("No has respondido bien");
                builder.setPositiveButton("Intentar de nuevo", null);
                builder.setNegativeButton("Volver al inicio", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        volverAlInicio();
                    }
                });
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

    private void generarNivel(int valorDeComplejidad){
        this.num1 = generarNumeroAlea() + valorDeComplejidad;
        this.num2 = generarNumeroAlea() + valorDeComplejidad;

        this.respuesta = num1 + num2;
    }

    private void dibujarNivel(){
        try{
            txtNumero1.setText(Integer.toString(num1));
            txtNumero2.setText(String.valueOf(num2));

            txtOpcion1_actS.setText(String.valueOf(respuesta - 2));
            txtOpcion2_actS.setText(String.valueOf(respuesta));
            txtOpcion3_actS.setText(String.valueOf(respuesta + 1));
            txtOpcion4_actS.setText(String.valueOf(respuesta + 3));
        }
        catch (NullPointerException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void volverAlInicio(){
        Intent puente = new Intent(getApplicationContext(), OpcionesActivity.class);
        this.finish();
        puente.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(puente);
    }
}