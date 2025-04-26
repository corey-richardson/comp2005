import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(Main::gui);
    }


    private static void gui() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JButton fetchButton = new JButton("Fetch Patients Readmitted Within 7 Days");
        JTextArea resultSection = new JTextArea();
        resultSection.setEditable(false);

        panel.add(fetchButton, BorderLayout.NORTH);
        panel.add(new JScrollPane(resultSection), BorderLayout.CENTER);

        fetchButton.addActionListener(e -> {
            fetchData(resultSection);
        });

        frame.add(panel);
        frame.setVisible(true);
    }


    private static void fetchData(JTextArea resultsSection) {
        // https://openjdk.org/groups/net/httpclient/intro.html
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/Patients/readmitted-within-7-days"))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(body -> {
                   List<PatientModel> patients = parsePatients(body) ;
                   SwingUtilities.invokeLater(() -> {
                       displayPatients(patients, resultsSection);
                   });
                })
                .exceptionally(e -> {
                    SwingUtilities.invokeLater(() -> {
                        resultsSection.setText("Error fetching patient data.");
                    });
                    return null;
                });
    }


    private static List<PatientModel> parsePatients(String jsonResponse) {
        Gson gson = new Gson();
        Type patientListType = new TypeToken<List<PatientModel>>() {}.getType();
        return gson.fromJson(jsonResponse, patientListType);
    }


    private static void displayPatients(List<PatientModel> patients, JTextArea resultsSection) {
        if (patients == null || patients.isEmpty()) {
            resultsSection.setText("No patients readmitted within 7 days have been found.");
            return;
        }

        // https://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html
        StringBuilder sb = new StringBuilder();
        for (PatientModel patient : patients) {
            sb.append("ID: ")
                    .append(patient.getId())
                    .append(" - ")
                    .append(patient.getFullName())
                    .append(" : ")
                    .append(patient.getNhsNumber())
                    .append("\n");
        }

        resultsSection.setText(sb.toString());
    }
}
