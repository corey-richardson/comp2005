public class PatientModel
{
    private int id;
    private String forename;
    private String surname;
    private String nhsNumber;

    public PatientModel(int id, String nhsNumber, String forename, String surname) {
        this.id = id;
        this.nhsNumber = nhsNumber;
        this.forename = forename;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }

    public String getFullName() {
        return forename + " " + surname;
    }

    public String getNhsNumber() {
        return nhsNumber;
    }
}
