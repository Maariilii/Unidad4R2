package mx.edu.utng.wscourse;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Marili Arevalo on 26/04/2017.
 */

public class Course implements KvmSerializable {

    private int id;
    private int clave;
    private String title;
    private String credits;
    private String department;
    private String enrollment;
    private String instructors;

    public Course(int id, int clave, String title, String credits,String department, String enrollment, String instructors) {
        this.id = id;
        this.clave = clave;
        this.title = title;
        this.credits = credits;
        this.department = department;
        this.enrollment = enrollment;
        this.instructors = instructors;

    }

    public Course() {this(0,0,"","","","","");
    }


    @Override
    public Object getProperty(int i) {
        switch (i){
            case 0:
                return  id;
            case 1:
                return  clave;
            case 2:
                return  title;
            case 3:
                return  credits;
            case 4:
                return  department;
            case 5:
                return enrollment;
            case 6:
                return instructors;

        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 7;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i){
            case 0:
                id=Integer.parseInt(o.toString());
                break;
            case 1:
                clave=Integer.parseInt(o.toString());
                break;
            case 2:
                title=o.toString();
                break;
            case 3:
                credits=o.toString();
                break;
            case 4:
                department=o.toString();
                break;
            case 5:
                enrollment=o.toString();
            case 6:
                instructors=o.toString();
                break;
            default:
                break;
        }

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

        switch (i){
            case 0:
                propertyInfo.type= PropertyInfo.INTEGER_CLASS;
                propertyInfo.name="id";
                break;
            case 1:
                propertyInfo.type=PropertyInfo.INTEGER_CLASS;
                propertyInfo.name="clave";
                break;

            case 2:
                propertyInfo.type= PropertyInfo.STRING_CLASS;
                propertyInfo.name="title";
                break;
            case 3:
                propertyInfo.type= PropertyInfo.STRING_CLASS;
                propertyInfo.name="credits";
                break;
            case 4:
                propertyInfo.type= PropertyInfo.STRING_CLASS;
                propertyInfo.name="department";
                break;
            case 5:
                propertyInfo.type= PropertyInfo.STRING_CLASS;
                propertyInfo.name="enrollments";
                break;
            case 6:
                propertyInfo.type= PropertyInfo.STRING_CLASS;
                propertyInfo.name="instructors";
                break;
            default:
                break;
        }


    }
}
