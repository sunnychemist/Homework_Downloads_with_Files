package com.dashaqaguru;

import com.codeborne.xlstest.XLS;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DownloadTest {

    @Test
    @DisplayName("Скачивание текстового файла")
    void downloadTXT() throws IOException {
        open("http://testingtask.online/test.html");
        File download = $("#txt").download();
        System.out.println(download.getAbsolutePath());
        assertThat(download.exists()).isEqualTo(true);
        String firstLine = Files.readLines(download, Charsets.UTF_8).get(0);
        assertThat(firstLine).isEqualTo("This is text file");
    }

    @Test
    @DisplayName("Скачивание PDF файла")
    void downloadPDF () throws FileNotFoundException {
        open("http://testingtask.online/test.html");
        File download = $("#pdf").download();
        assertThat(download.exists()).isEqualTo(true);
    }

    @Test
    @DisplayName("Скачивание XLS файла")
    void downloadXLS() throws IOException {
        open("http://testingtask.online/test.html");
        File file = $("#xls").download();
        assertThat(file.exists()).isEqualTo(true);
        XLS parsedXls = new XLS(file);
        boolean checkPassed = parsedXls.excel
                .getSheetAt(0)
                .getRow(0)
                .getCell(0)
                .getStringCellValue()
                .contains("Test file xls");

        assertTrue(checkPassed);
    }
}
