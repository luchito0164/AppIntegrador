package luislopez.appintegrador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {

    final String NAMESPACE = "http://servicios/";
    final String URL="http://192.168.1.10:21298/WS-Integrador/WSIntegrador?wsdl";
    final String METHOD_NAME = "buscarNumero";
    final String SOAP_ACTION = "http://servicios/buscarNumero";

    Suministro suministro;
    private boolean boolres = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void consumir(View view) {


        new consumirAsyc().execute();
    }

    private class consumirAsyc extends android.os.AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            boolres = invoceWS();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (boolres) {

                System.out.println("Actualizado..");

            Intent intent=new Intent(MainActivity.this,SecondActivity.class);
            intent.putExtra("CLIENTE",suministro.getCliente());
            intent.putExtra("DIRECCION",suministro.getDireccion());
            intent.putExtra("NUMERO",suministro.getNumeroSumi());
            startActivity(intent);

            }else{
                Toast.makeText(getApplicationContext(), "El numero de Suministro no existe", Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), "Descargando..", Toast.LENGTH_SHORT).show();
        }


    }

    public boolean invoceWS() {
        boolean res = false;

        EditText texto = (EditText) findViewById(R.id.txtNum1);


        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("numero", texto.getText().toString());


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE ht = new HttpTransportSE(URL);
        try {
            ht.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.getResponse();
            suministro=new Suministro();
            suministro.setDireccion(response.getProperty(0).toString());
            suministro.setCliente(response.getProperty(1).toString());
            suministro.setNumeroSumi(response.getProperty(2).toString());

            res=true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return res;
    }
}
