import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadJSON
{

    public ArrayList<Patient> readfile(String filename) throws IOException
    {
        BufferedReader fin = new BufferedReader(new FileReader(filename));
        ArrayList<Patient> patients = new ArrayList<>();
        while(fin.read() != '[')
        {
        }
        while(fin.ready())
        {
            char thischar = (char) fin.read();
            if (thischar == '{')
            {
                String patientString = "";
                while(true)
                {
                    char read = (char) fin.read();
                    if (read != '}')
                    {
                        patientString += read;
                    }
                    else
                    {
                        break;
                    }
                }
                String[] fields = patientString.split(",");
                Patient thisPatient = new Patient();
                for (String str : fields)
                {
                    String[] pair = str.split(":");
                    pair[0] = pair[0].substring(pair[0].indexOf("\"") + 1);
                    pair[0] = pair[0].substring(0, pair[0].indexOf("\""));
                    if (pair[1].charAt(pair[1].indexOf("\"") + 1) == '\"')
                    {
                        pair[1] = "";
                    }
                    else
                    {
                        pair[1] = pair[1].substring(pair[1].indexOf("\"") + 1);
                        pair[1] = pair[1].substring(0, pair[1].indexOf("\""));
                    }
                    thisPatient.set(pair[0], pair[1]);
                  //  System.out.println(pair[0] + " " + pair[1]);
                }
                patients.add(thisPatient);
            }
        }
        return patients;
    }
}
