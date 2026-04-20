package com.platform.core.data.providers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * DataProviderUtil — reads test data from CSV and JSON for TestNG @DataProvider methods.
 */
public final class DataProviderUtil {

    private static final Logger log    = LogManager.getLogger(DataProviderUtil.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    private DataProviderUtil() {}

    public static Object[][] readCsv(String resourcePath) {
        log.info("Reading CSV: {}", resourcePath);
        try (InputStream is = DataProviderUtil.class.getClassLoader().getResourceAsStream(resourcePath);
             CSVReader reader = new CSVReader(new InputStreamReader(is))) {
            List<String[]> rows = reader.readAll();
            rows.remove(0); // skip header row
            Object[][] data = new Object[rows.size()][];
            for (int i = 0; i < rows.size(); i++) data[i] = rows.get(i);
            log.info("Loaded {} rows from {}", data.length, resourcePath);
            return data;
        } catch (Exception e) {
            throw new RuntimeException("Failed to read CSV: " + resourcePath, e);
        }
    }

    public static <T> T readJson(String resourcePath, Class<T> clazz) {
        log.info("Reading JSON: {} as {}", resourcePath, clazz.getSimpleName());
        try (InputStream is = DataProviderUtil.class.getClassLoader().getResourceAsStream(resourcePath)) {
            return mapper.readValue(is, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON: " + resourcePath, e);
        }
    }
}
