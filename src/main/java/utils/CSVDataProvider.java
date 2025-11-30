package utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CSVDataProvider {
    private static final String DATA_DIR = Paths.get("resources").toString();

    public static Object[][] readDataLogin() {
        return readCSVFile("data_login_correct.csv");
    }

    public static Object[][] readDataLoginWrongUserName() {
        return readCSVFile("data_login_wrong_userName.csv");
    }

    public static Object[][] readDataLoginWrongPassword() {
        return readCSVFile("data_login_wrong_password.csv");
    }

    public static Object[][] readDataLoginBlankUserName() {
        return readCSVFile("data_login_blank_userName.csv");
    }

    public static Object[][] readDataLoginBlankPassword() {
        return readCSVFile("data_login_blank_password.csv");
    }

    private static Object[][] readCSVFile(String filename) {
        Path filePath = Paths.get(DATA_DIR, filename);

        // Verify the file exists.
        if (!Files.exists(filePath)) {
            throw new RuntimeException("File didn´t found: " + filePath);
        }

        // Verify is not empty
        try {
            if (Files.size(filePath) == 0) {
                throw new RuntimeException("Empty file: " + filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error verifying file size: " + filePath, e);
        }

        try (CSVReader reader = new CSVReader(new FileReader(filePath.toFile()))) {
            List<String[]> allData = reader.readAll()
                    .stream()
                    .filter(row -> !Arrays.stream(row).allMatch(String::isEmpty)) // Filter empty rows.
                    .collect(Collectors.toList());

            if (allData.isEmpty()) {
                throw new RuntimeException("There is not valid data in .CSV file: " + filePath);
            }

            // Determine if there are headers (we assume that the first row is a header if contains text).
            boolean hasHeader = Arrays.stream(allData.get(0))
                    .anyMatch(cell -> cell != null && !cell.trim().isEmpty() && cell.matches(".*[a-zA-Z].*"));

            int startRow = hasHeader ? 1 : 0;
            int rowCount = allData.size() - startRow;

            if (rowCount == 0) {
                throw new RuntimeException("Only there are headers in the .CSV file: " + filePath);
            }

            // Convert to a bidimensional array.
            Object[][] testData = new Object[rowCount][];
            for (int i = startRow; i < allData.size(); i++) {
                testData[i - startRow] = allData.get(i);
            }

            return testData;

        } catch (IOException | CsvException e) {
            throw new RuntimeException("Error reading .CSV file: " + filePath, e);
        }
    }
}