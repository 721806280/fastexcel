package cn.idev.excel.test.core.encrypt;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiaju Zhuang
 */
public class EncryptDataListener extends AnalysisEventListener<EncryptData> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptDataListener.class);
    
    List<EncryptData> list = new ArrayList<EncryptData>();
    
    @Override
    public void invoke(EncryptData data, AnalysisContext context) {
        list.add(data);
    }
    
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assertions.assertEquals(list.size(), 10);
        Assertions.assertEquals(list.get(0).getName(), "姓名0");
        Assertions.assertEquals((int) (context.readSheetHolder().getSheetNo()), 0);
        Assertions.assertEquals(
                context.readSheetHolder().getExcelReadHeadProperty().getHeadMap().get(0).getHeadNameList().get(0),
                "姓名");
        LOGGER.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
