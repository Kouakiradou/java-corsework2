import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Model
{
    private ArrayList<Patient> patients;
    private String[] fields = {"ID", "BIRTHDATE","DEATHDATE","SSN","DRIVERS","PASSPORT","PREFIX","FIRST","LAST","SUFFIX","MAIDEN","MARITAL","RACE","ETHNICITY","GENDER","BIRTHPLACE","ADDRESS","CITY","STATE","ZIP"};

    public Model()
    {
        patients = new ArrayList<>();
    }

    public void readFile(String filename) throws IOException
    {
        patients = new ReadCSV().readFile(filename);
    }

    public void readJSON(String filename) throws IOException
    {
        patients = new ReadJSON().readfile(filename);
    }

    public String getPatientListJSON()
    {
        return new JSONFormatter().convertPatientList(patients);
    }

    public String getPatientJSON(int index)
    {
        return new JSONFormatter().convertPatient(patients.get(index));
    }

    public Map<String, String> getNames()
    {
        Map<String, String> names = new LinkedHashMap<>();
        for (int i = 0; i < patients.size(); i++)
        {
            names.put(patients.get(i).get("FIRST"), patients.get(i).get("LAST"));
        }
        return names;
    }

    public String[] getFields()
    {
        return fields;
    }

    public void saveAsJSON() throws IOException
    {
        String patientsJSON = this.getPatientListJSON();
        BufferedWriter writer = new BufferedWriter(new FileWriter("JSON.txt"));
        writer.write(patientsJSON);
        writer.close();
    }

    public String[] getProperty(String first, String last)
    {
        for (Patient patient : patients)
        {
            if (patient.get("FIRST") ==  first && patient.get("LAST") ==  last)
            {
                return patient.getValue();
            }
        }
        return null;
    }

    public String[] getFullname(String name)
    {
        for (Patient patient : patients)
        {
            if (patient.get("FIRST").indexOf(name) != -1 ||  patient.get("LAST").indexOf(name) != -1)
            {
                return new String[]{patient.get("FIRST"), patient.get("LAST")};
            }
        }
        return null;
    }


}
