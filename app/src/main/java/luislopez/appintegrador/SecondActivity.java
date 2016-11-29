package luislopez.appintegrador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SecondActivity extends AppCompatActivity {
TextView cliente;
    TextView direccion;
    TextView numeroSuministro;

    private boolean boolres = false;
    final String NAMESPACE = "http://servicios/";
    final String URL="http://192.168.1.10:21298/WS-Integrador/WSIntegrador?wsdl";
    final String METHOD_NAME = "almacenarCola";
    final String SOAP_ACTION = "http://servicios/almacenarCola";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        cliente = (TextView) findViewById(R.id.lblcliente);
        direccion = (TextView) findViewById(R.id.lbldireccion);
        numeroSuministro = (TextView) findViewById(R.id.lblnumSumi);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        cliente.setText(extras.getString("CLIENTE"));
        direccion.setText(extras.getString("DIRECCION"));
        numeroSuministro.setText(extras.getString("NUMERO"));


    }
    public void guardar(View view) {




        new consumirAsyc2().execute();
    }

    private class consumirAsyc2 extends android.os.AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            boolres = invoceWS();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (boolres) {

                System.out.println("Actualizado..");


                Intent intent=new Intent(SecondActivity.this,MainActivity.class);
                startActivity(intent);

            }else{
                Toast.makeText(getApplicationContext(), "Fallo", Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), "Guardando..", Toast.LENGTH_SHORT).show();
        }


    }

    public boolean invoceWS() {
        boolean res = false;

        EditText texto = (EditText) findViewById(R.id.txtwatts);



        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("cliente",cliente.getText());
        request.addProperty("numeroSumi",numeroSuministro.getText());
        request.addProperty("consumo", texto.getText().toString());

        request.addProperty("fecha","12/20/2014");


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE ht = new HttpTransportSE(URL);
        try {
            ht.call(SOAP_ACTION, envelope);


            res=true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return res;
    }}



