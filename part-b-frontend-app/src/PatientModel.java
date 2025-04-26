public class PatientModel
{
    private int id;
    private String forename;
    private String surname;
    private String nhsNumber;

    public int getId() {
        return id;
    }

    public String getFullName() {
        return forename + " " + surname;
    }

    public String getNhsNumber() {
        return nhsNumber;
    }
}
