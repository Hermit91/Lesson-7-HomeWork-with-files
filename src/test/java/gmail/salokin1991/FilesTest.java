package gmail.salokin1991;


import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@DisplayName("File tests")
public class FilesTest extends TestBase {

    @Test
    @DisplayName("Downloading .txt file, than checking its contents")
    void downloadSimpleTextFileTest() throws IOException {
        open("https://filesamples.com/formats/txt");

        File download = $("a[href*='sample3.txt'").download();

        String fileContent = IOUtils.toString(new FileReader(download));
        Assertions.assertTrue(fileContent.contains("Cum enim fertur"));
    }

    @Test
    @DisplayName("Downloading.pdf file, then checking its content")
    void pdfFileDownloadTest() throws IOException {
        open("https://ies-drives.ru/download/");

        File pdf = $("a[href*='upload/iblock/71b/ACS55_en.pdf'").download();

        PDF parsedPdf = new PDF(pdf);
        Assertions.assertEquals(8, parsedPdf.numberOfPages);
        Assertions.assertEquals("untitled", parsedPdf.title);
        Assertions.assertNull(parsedPdf.author);
    }

    @Test
    @DisplayName("Downloading .xls file, then checking contents of the cell")
    void downloadXLSFile() throws IOException {
        open("http://begformula.ru/prajs-listy/");

        File file = $(byText("Пеналы и папки")).parent().download();

        XLS parsedXls = new XLS(file);
        boolean checkPassed = parsedXls.excel
                .getSheetAt(0)
                .getRow(100)
                .getCell(2)
                .getStringCellValue()
                .contains("ПКК 02- 5 жвт Пуся (36)");

        Assertions.assertTrue(checkPassed);
    }

}
