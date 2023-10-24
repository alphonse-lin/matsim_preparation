package Implement;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.ScenarioUtils;

import java.io.File;

public class runsinglematsim {
    public static void main(String[] args) {
        String configPath = args[0];
//        String configPath="D:\\Code\\114_temp\\008_CodeCollection\\005_java\\matsim_data_backup\\debug\\tq38_london_strategy\\static_waittodry\\config_001.xml";
        File file = new File(configPath);
        String parentPath = file.getParent();
        Config config = ConfigUtils.loadConfig(configPath);
        // Set the output directory
        config.controler().setOutputDirectory(parentPath+String.format("\\output_%s","traffic"));

        Scenario scenario = ScenarioUtils.loadScenario(config);
        Controler controler = new Controler(scenario);

        controler.run();
    }
}
