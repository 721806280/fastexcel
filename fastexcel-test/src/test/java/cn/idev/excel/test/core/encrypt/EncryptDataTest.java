package cn.idev.excel.test.core.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.idev.excel.test.core.simple.SimpleData;
import cn.idev.excel.test.util.TestFileUtil;
import cn.idev.excel.EasyExcel;
import cn.idev.excel.support.ExcelTypeEnum;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * @author Jiaju Zhuang
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
public class EncryptDataTest {

    private static File file07;
    private static File file03;
    private static File file07OutputStream;
    private static File file03OutputStream;

    @Test
    public void testformat() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        BigDecimal bigDecimal = new BigDecimal("0.105");

        System.out.println(decimalFormat.format(bigDecimal));
    }

    @BeforeAll
    public static void init() {
        file07 = TestFileUtil.createNewFile("encrypt07.xlsx");
        file03 = TestFileUtil.createNewFile("encrypt03.xls");
        file07OutputStream = TestFileUtil.createNewFile("encryptOutputStream07.xlsx");
        file03OutputStream = TestFileUtil.createNewFile("encryptOutputStream03.xls");
    }

    @Test
    public void t01ReadAndWrite07() {
        readAndWrite(file07);
    }

    @Test
    public void t02ReadAndWrite03() {
        readAndWrite(file03);
    }

    @Test
    public void t03ReadAndWriteStream07() throws Exception {
        readAndWriteStream(file07OutputStream, ExcelTypeEnum.XLSX);
    }

    @Test
    public void t04ReadAndWriteStream03() throws Exception {
        readAndWriteStream(file03OutputStream, ExcelTypeEnum.XLS);
    }

    private void readAndWrite(File file) {
        EasyExcel.write(file, EncryptData.class).password("123456").sheet().doWrite(data());
        EasyExcel.read(file, EncryptData.class, new EncryptDataListener()).password("123456").sheet().doRead();
    }

    private void readAndWriteStream(File file, ExcelTypeEnum excelType) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        EasyExcel.write(fileOutputStream, EncryptData.class).password("123456").excelType(excelType).sheet()
            .doWrite(data());
        fileOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream(file);
        EasyExcel.read(fileInputStream, EncryptData.class, new EncryptDataListener()).password("123456").sheet()
            .doRead();
    }

    private List<SimpleData> data() {
        List<SimpleData> list = new ArrayList<SimpleData>();
        for (int i = 0; i < 10; i++) {
            SimpleData simpleData = new SimpleData();
            simpleData.setName("姓名" + i);
            list.add(simpleData);
        }
        return list;
    }
}
