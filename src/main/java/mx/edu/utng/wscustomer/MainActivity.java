package mx.edu.utng.wscustomer;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etCompanyName;
    private EditText etContactName;
    private EditText etContactType;
    private EditText etContactTitle;
    private EditText etAddress;
    private EditText etCity;
    private EditText etRegion;
    private EditText etPostalCode;
    private EditText etCountry;
    private EditText etPhone;
    private EditText etFax;



    private Button btnSave;
    private Button btnList;

    private Customer customer = null;

    final String NAMESPACE ="http://ws.utng.edu.mx";
    final SoapSerializationEnvelope envelope =
            new SoapSerializationEnvelope(SoapEnvelope.VER11);
    static String URL ="http://192.168.24.175:8080/WSCustomer/services/CustomerWS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }
    private void initComponents(){
        etCompanyName = (EditText)findViewById(R.id.tv_company);
        etContactName= (EditText)findViewById(R.id.tv_contactn);
        etContactType= (EditText)findViewById(R.id.tv_contactt);
        etContactTitle= (EditText)findViewById(R.id.tv_contactty);
        etAddress= (EditText)findViewById(R.id.tv_address);
        etCity= (EditText)findViewById(R.id.tv_city);
        etRegion= (EditText)findViewById(R.id.tv_region);
        etPostalCode= (EditText)findViewById(R.id.tv_code);
        etCountry= (EditText)findViewById(R.id.tv_country);
        etPhone= (EditText)findViewById(R.id.tv_phone);
        etFax= (EditText)findViewById(R.id.tv_fax);

        btnSave = (Button) findViewById(R.id.bt_save);
        btnList = (Button)findViewById(R.id.btn_list);
        btnSave.setOnClickListener(this);
        btnList.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_consume_w, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== btnSave.getId()){
            try {
                if (getIntent().getExtras().getString("accion")
                        .equals("modificar")) {
                    TaskWSUpdate tarea = new TaskWSUpdate();
                    tarea.execute();
                }

            } catch (Exception e) {
                //Cuando no se haya mandado una accion por defecto es insertar.
                TaskWSInsert tarea = new TaskWSInsert();
                tarea.execute();
            }
        }
        if (btnList.getId() == v.getId()) {
            startActivity(new Intent(MainActivity.this, ListCustomers.class));
        }
    }
    private class TaskWSInsert extends
            AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;
            final String METHOD_NAME = "addCustomer";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            customer = new Customer();
            customer.setProperty(0, 0);

            getData();

            PropertyInfo info = new PropertyInfo();
            info.setName("customer");
            info.setValue(customer);
            info.setType(customer.getClass());
            request.addProperty(info);
            envelope.setOutputSoapObject(request);
            envelope.addMapping(NAMESPACE, "Course", Customer.class);

            /* Para serializar flotantes y otros tipos no cadenas o enteros*/
            MarshalFloat mf = new MarshalFloat();
            mf.register(envelope);

            HttpTransportSE transporte = new HttpTransportSE(URL);
            try {
                transporte.call(SOAP_ACTION, envelope);
                SoapPrimitive response =
                        (SoapPrimitive) envelope.getResponse();
                String res = response.toString();
                if (!res.equals("1")) {
                    result = false;
                }

            } catch (Exception e) {
                Log.e("Error ", e.getMessage());
                result = false;
            }


            return result;
        }


        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                Toast.makeText(getApplicationContext(),
                        "Registro exitoso.",
                        Toast.LENGTH_SHORT).show();
                cleanBox();

            }else {
                Toast.makeText(getApplicationContext(),
                        "Error al insertar.",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }//
    private void cleanBox(){
        etCompanyName.setText("");
        etContactName.setText("");
        etContactType.setText("");
        etContactTitle.setText("");
        etAddress.setText("");
        etCity.setText("");
        etRegion.setText("");
        etPostalCode.setText("");
        etCountry.setText("");
        etPhone.setText("");
        etFax.setText("");

    }
    private class TaskWSUpdate extends
            AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            final String METHOD_NAME = "updateCustomer";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            customer = new Customer();
            customer.setProperty(0, getIntent().getExtras().getString("valor0"));
            getData();

            PropertyInfo info = new PropertyInfo();
            info.setName("customer");
            info.setValue(customer);
            info.setType(customer.getClass());

            request.addProperty(info);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            envelope.addMapping(NAMESPACE, "Customer", customer.getClass());

            MarshalFloat mf = new MarshalFloat();
            mf.register(envelope);

            HttpTransportSE transporte = new HttpTransportSE(URL);

            try {
                transporte.call(SOAP_ACTION,envelope);
                SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
                String res = resultado_xml.toString();

                if (!res.equals("1")) {
                    result = false;
                }

            } catch (HttpResponseException e) {
                Log.e("Error HTTP", e.toString());
            } catch (IOException e) {
                Log.e("Error IO", e.toString());
            } catch (XmlPullParserException e) {
                Log.e("Error XmlPullParser", e.toString());
            }


            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(getApplicationContext(), "Actualizado OK",
                        Toast.LENGTH_SHORT).show();
                cleanBox();
                startActivity(new Intent(MainActivity.this, MainActivity.class));

            } else {
                Toast.makeText(getApplicationContext(), "Error al actualizar",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }//
    private void getData() {
        customer.setProperty(1, etCompanyName.getText().toString());
        customer.setProperty(2, etContactName.getText().toString());
        customer.setProperty(3, etContactType.getText().toString());
        customer.setProperty(4, etContactTitle.getText().toString());
        customer.setProperty(5, etAddress.getText().toString());
        customer.setProperty(6, etCity.getText().toString());
        customer.setProperty(7, etRegion.getText().toString());
        customer.setProperty(8, etPostalCode.getText().toString());
        customer.setProperty(9, etCountry.getText().toString());
        customer.setProperty(10, etPhone.getText().toString());
        customer.setProperty(11, etFax.getText().toString());


    }//

    @Override
    protected void onResume() {
        super.onResume();
        Bundle datosRegreso = this.getIntent().getExtras();
        try {
            Log.i("Dato", datosRegreso.getString("valor11"));

            etCompanyName.setText(datosRegreso.getString("valor1"));
            etContactName.setText(datosRegreso.getString("valor2"));
            etContactType.setText(datosRegreso.getString("valor3"));
            etContactTitle.setText(datosRegreso.getString("valor4"));
            etAddress.setText(datosRegreso.getString("valor5"));
            etCity.setText(datosRegreso.getString("valor6"));
            etRegion.setText(datosRegreso.getString("valor7"));
            etPostalCode.setText(datosRegreso.getString("valor8"));
            etCountry.setText(datosRegreso.getString("valor9"));
            etPhone.setText(datosRegreso.getString("valor10"));
            etFax.setText(datosRegreso.getString("valor11"));


        } catch (Exception e) {
            Log.e("Error al Recargar", e.toString());
        }
    }
}
