package mx.edu.utng.wscourse;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class ListCourses extends ListActivity {

    final String NAMESPACE = "http://ws.utng.edu.mx";

    final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
            SoapEnvelope.VER11);

    private ArrayList<Course> courses = new ArrayList<Course>();
    private int idSelected;
    private int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TaskWSSelect select=new TaskWSSelect();
        select.execute();
        registerForContextMenu(getListView());

    }//


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_modificar:

                Course course = courses.get(selectedPosition);
                Bundle bundleCourse = new Bundle();
                for (int i = 0; i < course.getPropertyCount(); i++) {
                    bundleCourse.putString("valor" + i, course.getProperty(i)
                            .toString());
                }
                bundleCourse.putString("accion", "modificar");
                Intent intent = new Intent(ListCourses.this, MainActivity.class);
                intent.putExtras(bundleCourse);
                startActivity(intent);

                return true;
            case R.id.item_eliminar:
                TaskWSDelete eliminar = new TaskWSDelete();
                eliminar.execute();

                return true;
            default:
                return super.onContextItemSelected(item);
        }


    }


    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_regresar:
                startActivity(new Intent(ListCourses.this, MainActivity.class));
                break;
            default:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }//
    private class TaskWSSelect extends AsyncTask<String, Integer, Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean result = true;

            final String METHOD_NAME = "getCourse";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            courses.clear();
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            envelope.setOutputSoapObject(request);

            HttpTransportSE transporte = new HttpTransportSE(MainActivity.URL);

            try {
                transporte.call(SOAP_ACTION, envelope);

                Vector<SoapObject> response = (Vector<SoapObject>) envelope.getResponse();

                if (response != null) {

                    for (SoapObject objSoap : response) {
                        Course course = new Course();

                        course.setProperty(0, Integer.parseInt(objSoap
                                .getProperty("id").toString()));
                        course.setProperty(1, Integer.parseInt(objSoap.getProperty("clave").toString()));

                        course.setProperty(2, objSoap.getProperty("title")
                                .toString());
                        course.setProperty(3, objSoap.getProperty("credits")
                                .toString());
                        course.setProperty(4, objSoap.getProperty("department")
                                .toString());

                        course.setProperty(5, objSoap.getProperty("enrollments")
                                .toString());
                        course.setProperty(6, objSoap.getProperty("instructors")
                                .toString());

                        courses.add(course);
                    }
                }

            } catch (XmlPullParserException e) {
                Log.e("Error XMLPullParser", e.toString());
                result = false;
            } catch (HttpResponseException e) {
                Log.e("Error HTTP", e.toString());
                result = false;
            } catch (IOException e) {
                Log.e("Error IO", e.toString());
                result = false;
            } catch (ClassCastException e) {

                //Enviará aquí cuando exista un solo registro en la base.
                try {
                    SoapObject objSoap = (SoapObject) envelope.getResponse();
                    Course course  = new Course();
                    course.setProperty(0, Integer.parseInt(objSoap.getProperty(
                            "id").toString()));
                    course.setProperty(1, Integer.parseInt(objSoap.getProperty(
                            "clave").toString()));
                    course.setProperty(2, objSoap.getProperty("title")
                            .toString());
                    course.setProperty(3, objSoap.getProperty("credits")
                            .toString());
                    course.setProperty(4, Integer.parseInt(objSoap.getProperty(
                            "department").toString()));
                    course.setProperty(5, objSoap.getProperty("enrollments")
                            .toString());
                    course.setProperty(6, objSoap.getProperty("instructors")
                            .toString());

                    courses.add(course);
                } catch (SoapFault e1) {
                    Log.e("Error SoapFault", e.toString());
                    result = false;
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                final String[] datos = new String[courses.size()];
                for (int i = 0; i < courses.size(); i++) {
                    datos[i] = courses.get(i).getProperty(0) + " - "
                            + courses.get(i).getProperty(1)+ " - "
                            + courses.get(i).getProperty(2)+ " - "
                            + courses.get(i).getProperty(3)+ " - "
                            + courses.get(i).getProperty(4)+ " - "
                            + courses.get(i).getProperty(5)+ " - "
                            + courses.get(i).getProperty(6);
                }


//////////////////////////////////este layout
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                        ListCourses.this,
                        android.R.layout.simple_list_item_1, datos);
                setListAdapter(adaptador);
            } else {
                Toast.makeText(getApplicationContext(), "No se encontraron datos.",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }//

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        menu.setHeaderTitle(getListView().getAdapter().getItem(info.position)
                .toString());
        idSelected = (Integer) courses.get(info.position).getProperty(0);
        selectedPosition = info.position;

        inflater.inflate(R.menu.menu_contextual, menu);

    }//
    private class TaskWSDelete extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            final String METHOD_NAME = "removeCourse";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("id", idSelected);

            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(MainActivity.URL);
            try {
                transporte.call(SOAP_ACTION, envelope);
                SoapPrimitive resultado_xml = (SoapPrimitive) envelope
                        .getResponse();
                String res = resultado_xml.toString();

                if (!res.equals("0")){
                    result = true;}

            } catch (Exception e) {
                Log.e("Error", e.toString());
                result = false;
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(getApplicationContext(),
                        "Eliminado", Toast.LENGTH_SHORT).show();
                TaskWSSelect consulta = new TaskWSSelect();
                consulta.execute();
            } else {
                Toast.makeText(getApplicationContext(), "Error al eliminar",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }

}
