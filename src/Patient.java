import java.util.LinkedHashMap;
import java.util.Map;

public class Patient
{
    private Map<String, String> patientInformation;
    private String[] fields = {"ID", "BIRTHDATE","DEATHDATE","SSN","DRIVERS","PASSPORT","PREFIX","FIRST","LAST","SUFFIX","MAIDEN","MARITAL","RACE","ETHNICITY","GENDER","BIRTHPLACE","ADDRESS","CITY","STATE","ZIP"};
    public Patient()
    {
        patientInformation = new LinkedHashMap();
    }

    public String get(String field)
    {
        return patientInformation.get(field);
    }

    public void set(String field, String value)
    {
        patientInformation.put(field, value);
    }

    public String[] getFields()
    {
        return patientInformation.keySet().toArray(new String[0]);
    }

    public String[] getValue()
    {
        return patientInformation.values().toArray(new String[0]);
    }
}
