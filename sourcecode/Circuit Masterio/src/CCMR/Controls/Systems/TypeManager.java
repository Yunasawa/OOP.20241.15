package CCMR.Controls.Systems;
import CCMR.Models.Types.JSON.Circuit;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;

public class TypeManager {
    public static void main(String[] args) {
        Circuit.Component[] components = {
                new Circuit.Component("R1", "Resistor", "10KOm"),
                new Circuit.Component("C1", "Capacitor", "100F"),
                new Circuit.Component("V1", "Voltage", "5V")
        };

        ObjectMapper objectMapper = new ObjectMapper();

        try{
            String jsonString = objectMapper.writeValueAsString(components);

            String FilePath = "Circuit.ccmr";

            try(FileWriter fileWriter = new FileWriter(FilePath)) {
                fileWriter.write(jsonString);
                System.out.println("da luu");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
