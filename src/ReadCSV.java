import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadCSV
{
    public ArrayList<Patient> readFile(String filename) throws IOException
    {
        BufferedReader fin = new BufferedReader(new FileReader(filename));
        ArrayList<Patient> patients = new ArrayList<>();
        String[] titles = fin.readLine().split(",");
        while(fin.ready())
        {
            Patient thisPatient = new Patient();
            String thisString = fin.readLine();
            String[] splitedString = thisString.split(",");
            for (int i = 0; i < splitedString.length; i++)
            {
                thisPatient.set(titles[i], splitedString[i]);
            }
            patients.add(thisPatient);
        }
        return patients;
    }

}
