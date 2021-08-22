package software.mathkids;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class ContarActivity extends AppCompatActivity {
    ImageView imgOpcion1, imgOpcion2, imgOpcion3, imgOpcion4, imgOpcion5, imgOpcion6, imgOpcion7, imgOpcion8;
    int cantidadImagenes;
    ImageView[] arrayImg;
    TextView txtPregunta, txtOpcion1, txtOpcion2, txtOpcion3, txtOpcion4;
    LinearLayout fondoPantalla;
    int[] aleatorios;
    int respuestaCorrecta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contar);

        fondoPantalla = (LinearLayout) findViewById(R.id.linearLFondoPantalla);

        imgOpcion1 = findViewById(R.id.imgOpcion1_actC);
        imgOpcion2 = findViewById(R.id.imgOpcion2_actC);
        imgOpcion3 = findViewById(R.id.imgOpcion3_actC);
        imgOpcion4 = findViewById(R.id.imgOpcion4_actC);
        imgOpcion5 = findViewById(R.id.imgOpcion5_actC);
        imgOpcion6 = findViewById(R.id.imgOpcion6_actC);
        imgOpcion7 = findViewById(R.id.imgOpcion7_actC);
        imgOpcion8 = findViewById(R.id.imgOpcion8_actC);

        aggImagenLista();
        cantidadImagenes = 8;
        aleatorios = new int[cantidadImagenes];
        generarAleatoriosAlArray(8);
        txtPregunta = findViewById(R.id.txtPregunta_actC);

        txtOpcion1 = findViewById(R.id.txtOpcion1_actC);
        txtOpcion2 = findViewById(R.id.txtOpcion2_actC);
        txtOpcion3 = findViewById(R.id.txtOpcion3_actC);
        txtOpcion4 = findViewById(R.id.txtOpcion4_actC);
    }

    @Override
    protected void onStart() {
        super.onStart();

        JSONArray preguntas = cargarJsonArray();

        //Elegir aleatoriamente una pregunta
        Random random = new Random();
        int index = random.nextInt(2);

        try {
            JSONObject pregunta = preguntas.getJSONObject(index);

            //cargar drawable para el fondo
            fondoPantalla.setBackground(getDrawable(
                    getResources().getIdentifier(pregunta.getString("landscape"), "drawable", getPackageName())
            ));

            //Establecer la pregunta
            txtPregunta.setText(pregunta.getString("pregunta"));

            //Obtener la respuesta
            String literalCorrecta = pregunta.getString("respuesta");
            JSONObject opciones = pregunta.getJSONObject("opciones");
            int opcion = opciones.getInt(literalCorrecta);
            respuestaCorrecta = opcion;

            //Ubicar los gráficos
            Uri imgUri;
            for (int i = 0; i < cantidadImagenes; i++){
                if(i < opcion){
                    imgUri = Uri.parse("android.resource://"+ getPackageName() +"/drawable/"+ pregunta.getString("objeto"));
                    System.out.println("Pintando objeto " + pregunta.getString("objeto"));
                }
                else{
                    imgUri = Uri.parse("android.resource://"+ getPackageName() +"/drawable/"+ pregunta.getString("objetoContrario"));
                    System.out.println("Pintando objeto contrario " + pregunta.getString("objetoContrario"));
                }
                arrayImg[aleatorios[i]].setImageURI(imgUri);
            }

            //Ubicar las alternativas
            txtOpcion1.setText(opciones.getString("a"));
            txtOpcion2.setText(opciones.getString("b"));
            txtOpcion3.setText(opciones.getString("c"));
            txtOpcion4.setText(opciones.getString("d"));

        } catch (JSONException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void ontouchOpcion(View view){
        TextView txtseleccionado = (TextView)view;
        if(txtseleccionado.getText().equals(String.valueOf(respuestaCorrecta)) ){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Felicitaciones")
                    .setMessage("Has contestado correctamente")
                    .setPositiveButton("Volver al inicio", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent puente = new Intent(getApplicationContext(), OpcionesActivity.class);
                            startActivity(puente);
                        }
                    })
                    .create()
                    .show();
        }
        else{
            System.out.println("Touch: " + txtseleccionado.getText() + ", correcta: " + respuestaCorrecta);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("¡Ups!")
                    .setMessage("No has respondido correctamente la pregunta")
                    .setPositiveButton("Intentarlo de nuevo", null)
                    .create()
                    .show();
        }
    }

    private JSONArray cargarJsonArray(){
        JSONArray jsonArray = new JSONArray();
        try{
            InputStream dataStream = getResources().openRawResource(R.raw.data);
            String jsonString = readTextFile( dataStream );
            jsonArray = new JSONArray(jsonString);
        }
        catch (Resources.NotFoundException | JSONException ex){
            System.out.println("Archivo no encontrado o json error, " + ex.getMessage());
        }
        return jsonArray;
    }

    private String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            } outputStream.close();
            inputStream.close();
        }
        catch (IOException e) { }
        return outputStream.toString();
    }

    private void aggImagenLista(){
        arrayImg = new ImageView[]{imgOpcion1, imgOpcion2, imgOpcion3, imgOpcion4, imgOpcion5, imgOpcion6, imgOpcion7, imgOpcion8};
    }

    private boolean aleatorioExiste(int num){
        for(int i = 0; i < aleatorios.length; i++){
            if(num == aleatorios[i]){
                return true;
            }
        }
        return false;
    }

    private void generarAleatoriosAlArray(int cantidad){
        Random random = new Random();
        System.out.print("Aleatorios: [");
        for (int i = 0; i < cantidad; i++){
            int aleatorio = random.nextInt(cantidad);
            while ( aleatorioExiste(aleatorio) ){
                aleatorio = random.nextInt(cantidad);
            }
            aleatorios[i] = aleatorio;
            System.out.print( aleatorio + ",");
        }
        System.out.println("]");
    }
}