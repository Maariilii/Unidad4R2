package mx.edu.utng.wscourse;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etclave;
    private EditText etTitle;
    private EditText etCredits;
    private EditText etDepartment;
    private EditText etEnrollment;
    private EditText etInstructors;


    private Button btnSave;
    private Button btnList;

    private Course course = null;

    final String NAMESPACE ="http://ws.utng.edu.mx";
    final SoapSerializationEnvelope envelope =
            new SoapSerializationEnvelope(SoapEnvelope.VER11);
    static String URL ="http://192.168.24.67:8080/WSCourse/services/CourseWS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }
    private void initComponents(){
        etclave = (EditText)findViewById(R.id.tv_clave);
        etTitle= (EditText)findViewById(R.id.tv_title);
        etCredits= (EditText)findViewById(R.id.tv_credits);
        etDepartment= (EditText)findViewById(R.id.tv_department);
        etEnrollment= (EditText)findViewById(R.id.tv_enrollments);
        etInstructors= (EditText)findViewById(R.id.tv_instructors);

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
            startActivity(new Intent(MainActivity.this, ListCourses.class));
        }
    }
    private class TaskWSInsert extends
            AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;
            final String METHOD_NAME = "addCourse";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            course = new Course();
            course.setProperty(0, 0);

            getData();

            PropertyInfo info = new PropertyInfo();
            info.setName("course");
            info.setValue(course);
            info.setType(course.getClass());
            request.addProperty(info);
            envelope.setOutputSoapObject(request);
            envelope.addMapping(NAMESPACE, "Course", Course.class);

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
        etclave.setText("");
        etTitle.setText("");
        etCredits.setText("");
        etDepartment.setText("");
        etEnrollment.setText("");
        etInstructors.setText("");

    }
    private class TaskWSUpdate extends
            AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            final String METHOD_NAME = "updateCourse";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            course = new Course();
            course.setProperty(0, getIntent().getExtras().getString("valor0"));
            getData();

            PropertyInfo info = new PropertyInfo();
            info.setName("course");
            info.setValue(course);
            info.setType(course.getClass());

            request.addProperty(info);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            envelope.addMapping(NAMESPACE, "Course", course.getClass());

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
        course.setProperty(1, etclave.getText().toString());
        course.setProperty(2, etTitle.getText().toString());
        course.setProperty(3, etCredits.getText().toString());
        course.setProperty(4, etDepartment.getText().toString());
        course.setProperty(5, etEnrollment.getText().toString());
        course.setProperty(6, etInstructors.getText().toString());


    }//

    @Override
    protected void onResume() {
        super.onResume();
        Bundle datosRegreso = this.getIntent().getExtras();
        try {
            Log.i("Dato", datosRegreso.getString("valor6"));

            etclave.setText(datosRegreso.getString("valor1"));
            etTitle.setText(datosRegreso.getString("valor2"));
            etCredits.setText(datosRegreso.getString("valor3"));
            etDepartment.setText(datosRegreso.getString("valor4"));
            etEnrollment.setText(datosRegreso.getString("valor5"));
            etInstructors.setText(datosRegreso.getString("valor6"));


        } catch (Exception e) {
            Log.e("Error al Recargar", e.toString());
        }
    }
}
