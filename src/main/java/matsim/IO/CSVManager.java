package matsim.IO;

import com.opencsv.*;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import matsim.basic.peopleCalc.SinglePeople;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class CSVManager {
    public static void Write(String filePath, List<String[]>entries) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             OutputStreamWriter osw = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8);
             CSVWriter writer = new CSVWriter(osw)) {
            writer.writeAll(entries);
        }
    }

    public static void Write(String filePath, List<String[]>entries, String[] header) throws IOException {
        entries.add(0,header);
        try (FileOutputStream fos = new FileOutputStream(filePath);
             OutputStreamWriter osw = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8);
             CSVWriter writer = new CSVWriter(osw)) {
            writer.writeAll(entries);
        }
    }

    public static List<String[]> Read(String fileName){
        List<String[]> result=new ArrayList<>();
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(fileName), "utf-8");
            CSVParser csvParser = new CSVParserBuilder().withSeparator('\t').build();
            CSVReader reader = new CSVReaderBuilder(is).withCSVParser(csvParser).build();
            result = reader.readAll();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
