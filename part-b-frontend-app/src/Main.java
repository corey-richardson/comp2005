import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Main
{
    private static final double RATIO = 1.618; // Golden Ratio
    private static final int WIDTH = 400;
    private static final int HEIGHT = (int) (WIDTH * RATIO);

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(Main::gui);
    }


    private static void gui() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton fetchButton = new JButton("Fetch Patients Readmitted Within 7 Days");

        String[] columns = {"Patient ID", "NHS Number", "Forename", "Surname"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable resultsTable = new JTable(tableModel);

        // Alternating lines of White - Light Gray
        resultsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value,
                                                           boolean isSelected,
                                                           boolean hasFocus,
                                                           int row,
                                                           int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(240, 240, 240));
                    }
                }
                return c;
            }
        });

        panel.add(fetchButton, BorderLayout.NORTH);
        panel.add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        fetchButton.addActionListener(e -> {
            // fetchData(tableModel);
            fetchMockData(tableModel);
            resultsTable.setVisible(true);
        });

        frame.add(panel);
        frame.setVisible(true);
    }


    private static void fetchData(DefaultTableModel tableModel) {
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
                       displayPatients(patients, tableModel);
                   });
                })
                .exceptionally(e -> {
                    return null;
                });
    }


    private static List<PatientModel> parsePatients(String jsonResponse) {
        Gson gson = new Gson();
        Type patientListType = new TypeToken<List<PatientModel>>() {}.getType();
        return gson.fromJson(jsonResponse, patientListType);
    }


    private static void displayPatients(List<PatientModel> patients, DefaultTableModel tableModel) {
        tableModel.setRowCount(0);

        for (PatientModel patient : patients) {
            Object[] rowData = {
                    patient.getId(),
                    patient.getNhsNumber(),
                    patient.getForename(),
                    patient.getSurname()
            };
            tableModel.addRow(rowData);
        }
    }
}
