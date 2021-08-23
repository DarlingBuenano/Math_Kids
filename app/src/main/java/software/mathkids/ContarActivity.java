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
import java.util.ArrayList;
import java.util.Random;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ContarActivity extends AppCompatActivity {
    ImageView imgOpcion1, imgOpcion2, imgOpcion3, imgOpcion4, imgOpcion5, imgOpcion6, imgOpcion7, imgOpcion8;
    int cantidadImagenes;
    ImageView[] arrayImg;
    TextView txtPregunta, txtOpcion1, txtOpcion2, txtOpcion3, txtOpcion4;
    LinearLayout fondoPantalla;
    Integer[] valores;
    List<Integer> listValores;
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
        valores = new Integer[cantidadImagenes];
        llenarArray(valores);
        listValores = desordenarArray(valores);
        txtPregunta = findViewById(R.id.txtPregunta_actC);

        txtOpcion1 = findViewById(R.id.txtOpcion1_actC);
        txtOpcion2 = findViewById(R.id.txtOpcion2_actC);
        txtOpcion3 = findViewById(R.id.txtOpcion3_actC);
        txtOpcion4 = findViewById(R.id.txtOpcion4_actC);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                System.out.println("Img ("+ i + "): " + imgUri.getPath());
                System.out.println(listValores.get(i));
                arrayImg[listValores.get(i) - 1].setImageURI(imgUri);
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
            builder.setTitle("Felicitaciones");
            builder.setMessage("Has contestado correctamente");
            builder.setPositiveButton("Volver al inicio", new DialogInterface.OnClickListener() {
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
            builder.setMessage("No has respondido correctamente la pregunta");
            builder.setPositiveButton("Intentarlo de nuevo", null);
            AlertDialog dialog = builder.create();
            dialog.show();
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

    private void llenarArray(Integer[] array){
        for(int i = 0; i < array.length; i++){
            array[i] = i + 1;
        }
    }

    private List<Integer> desordenarArray(Integer[] array){
        List<Integer> array2 = Arrays.asList(array);
        Collections.shuffle(array2);
        return array2;
    }
}