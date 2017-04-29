package mx.edu.utng.wscustomer;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Marili Arevalo on 28/04/2017.
 */

public class Customer implements KvmSerializable {

    private int id;
    private String companyName;
    private String contactName;
    private String contactType;
    private String contactTitle;
    private String address;
    private String city;
    private String region;
    private String postalCode;
    private String country;
    private String phone;
    private String fax;


    public Customer(int id, String companyName, String contactName, String contactType,
                    String contactTitle, String address, String city, String region,
                    String postalCode, String country, String phone, String fax) {
        this.id = id;
        this.companyName = companyName;
        this.contactName = contactName;
        this.contactType = contactType;
        this.contactTitle = contactTitle;
        this.address = address;
        this.city = city;
        this.region = region;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;
        this.fax = fax;

    }

    public Customer() {
        this(0,"", "", "", "", "", "","","","","","");
    }


    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return id;
            case 1:
                return companyName;
            case 2:
                return contactName;
            case 3:
                return contactType;
            case 4:
                return contactTitle;
            case 5:
                return address;
            case 6:
                return city;
            case 7:
                return region;
            case 8:
                return postalCode;
            case 9:
                return country;
            case 10:
                return phone;
            case 11:
                return fax;

        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 12;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i) {
            case 0:
                id = Integer.parseInt(o.toString());
                break;
            case 1:
                companyName = o.toString();
                break;
            case 2:
                contactName = o.toString();
                break;
            case 3:
                contactType = o.toString();
                break;
            case 4:
                contactTitle = o.toString();
                break;
            case 5:
                address = o.toString();
            case 6:
                city = o.toString();
                break;
            case 7:
                region = o.toString();
                break;
            case 8:
                postalCode = o.toString();
                break;
            case 9:
                country = o.toString();
                break;
            case 10:
                phone = o.toString();
                break;
            case 11:
                fax = o.toString();
                break;
            default:
                break;
        }

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

        switch (i) {
            case 0:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "id";
                break;
            case 1:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "companyName";
                break;

            case 2:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "contactName";
                break;
            case 3:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "contactType";
                break;
            case 4:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "contactTitle";
                break;
            case 5:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "address";
                break;
            case 6:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "city";
                break;
            case 7:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "region";
                break;
            case 8:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "postalCode";
                break;
            case 9:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "country";
                break;
            case 10:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "phone";
                break;
            case 11:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "fax";
                break;
            default:
                break;
        }

    }
}
