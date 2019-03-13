import java.util.ArrayList;

public class JSONFormatter
{
    /*
      this method is for construct JSON of single patient in a list, so it will be slightly different
      from the other convert patient.
     */

    public String convertPatientList(ArrayList<Patient> patients)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"patients\":");
        builder.append("\n[");
        for (Patient patient : patients)
        {
            builder.append(convertPatient(patient));
            builder.append(",\n");
        }
        builder.deleteCharAt(builder.length() - 2);
        builder.insert(builder.length() - 1, "]");
       builder.append("}");
        return builder.toString();
    }

    public String convertPatient(Patient patient)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        for (String field : patient.getFields())
        {
            builder.append("    ");
            builder.append("\"");
            builder.append(field);
            builder.append("\"");
            builder.append(": ");
            builder.append("\"");
            builder.append(patient.get(field));
            builder.append("\"");
            builder.append(",\n");
        }
        builder.deleteCharAt(builder.length() - 2);
        builder.append("}");
        return builder.toString();
    }
}
