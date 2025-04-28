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
    private static final int WIDTH = 600;
    private static final int HEIGHT = (int) (WIDTH * RATIO);

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(Main::gui);
    }


    private static void gui() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        frame.setTitle("Maternity Readmissions");
        JLabel titleLabel = new JLabel("Maternity Ward Patients Readmitted Within 7 Days");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

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

        // Font sizes
        Font titleFont = new Font(null, Font.BOLD, 16);
        Font largerFont = new Font(null, Font.PLAIN, 14);
        titleLabel.setFont(titleFont);
        fetchButton.setFont(largerFont);
        resultsTable.setFont(largerFont);
        resultsTable.getTableHeader().setFont(largerFont);

        titlePanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(fetchButton, BorderLayout.NORTH);
        contentPanel.add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        fetchButton.addActionListener(e -> {
            // fetchData(tableModel);
            fetchMockData(tableModel);
            resultsTable.setVisible(true);
        });

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(contentPanel, BorderLayout.CENTER);
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


    private static void fetchMockData(DefaultTableModel tableModel) {
        List<PatientModel> mockPatients = List.of(
                new PatientModel(1, "1234567890", "Corey", "Richardson"),
                new PatientModel(2, "0987654321", "Kelly", "Margot"),
                new PatientModel(3, "1122334455", "Daryl", "Matthews"),
                new PatientModel(5, "1243152453", "Trevor", "Locke"),
                new PatientModel(6, "0982359823", "Cheryl", "McDonald"),
                new PatientModel(7, "3259874359", "Parker", "Fewings"),
                new PatientModel(8, "2455215933", "Maria", "King"),
                new PatientModel(9, "9183276522", "Leah", "Simpson"),
                new PatientModel(10, "0439683871", "Anna", "Johnson")
        );

        SwingUtilities.invokeLater(() -> {
            displayPatients(mockPatients, tableModel);
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
