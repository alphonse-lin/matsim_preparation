import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.ScenarioUtils;
import java.io.*;

public class runmatsim {
    public static void main(String[] args) throws IOException {
//        String configPath = "src/main/resources/debug/tq38_london_strategy/dynamic_12_00_pm/config_001.xml";
//        String eventDir = "src/main/resources/debug/tq38_london_strategy/dynamic_12_00_pm/eventXML";
//        String eventPath="src/main/resources/debug/tq38_london_strategy/dynamic_12_00_pm/networkChangeEvents.xml";
        String configPath = "src/main/resources/debug/tq38_london_strategy/static_waittodry/config_001.xml";
        String eventDir = "src/main/resources/debug/tq38_london_strategy/static_waittodry/eventXML";
        String eventPath="src/main/resources/debug/tq38_london_strategy/static_waittodry/networkChangeEvents.xml";
        File file = new File(eventDir);
        File[] fs = file.listFiles();
        for(File f:fs) {
            String tempEventPath=f.getAbsolutePath();
            File tempfile = new File(tempEventPath);
            File checkfile = new File(eventPath);
            // 检查文件是否存在，如果文件存在，删除文件
            if (checkfile.exists()) {checkfile.delete();}
            //将原文件更改为f:\a\b.xlsx，其中路径是必要的。注意
            tempfile.renameTo(new File(eventPath));

            Config config = ConfigUtils.loadConfig(configPath);

            String outputTag=GetName(f);
            // Set the output directory
            config.controler().setOutputDirectory(String.format("./output/static_waittodry/no_event_output_%s",outputTag));

            Scenario scenario = ScenarioUtils.loadScenario(config);
            Controler controler = new Controler(scenario);

            controler.run();
        }
    }

    public static String GetName(File f){
        String filename=f.getName();
        int firstIndex = filename.indexOf("_");  // 找到第一个"_"的位置
        int pos = filename.indexOf("_", firstIndex + 1);
        pos = filename.lastIndexOf("_", pos + 1);  // 查找倒数第二个"_"的位置

        // 使用substring函数从倒数第二个"_"之后的位置开始截取，直到字符串末尾
        String desiredPart = filename.substring(pos + 1, filename.length() - 4);  // -4是为了去掉".csv"
        return desiredPart;
    }
}
