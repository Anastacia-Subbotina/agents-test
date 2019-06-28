import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class Agents {

    @Test
    public void agents_test() throws  Exception {
        System.setProperty("webdriver.chrome.driver", "/ChromeDriver/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--test-type");
        options.addArguments("--headless");
        options.addArguments("--disable-extensions"); //to disable browser extension popup
        /*options.addArguments("--no-sandbox");
        options.addArguments("--profile.default_content_setting_values.automatic_downloads=1"); */

        /*Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.directory_upgrade", true);
        prefs.put("safebrowsing.enabled", true);
        prefs.put("safebrowsing.disable_download_protection", true);*/

       /* options.setExperimentalOption("prefs", prefs);*/

        ChromeDriverService driverService = ChromeDriverService.createDefaultService();
        ChromeDriver driver = new ChromeDriver(driverService, options);

        Map<String, Object> commandParams = new HashMap<>();
        commandParams.put("cmd", "Page.setDownloadBehavior");
        Map<String, String> params = new HashMap<>();
        params.put("behavior", "allow");
        params.put("downloadPath", "C:\\Users\\subbotina\\Downloadsss");
        commandParams.put("params", params);
        ObjectMapper objectMapper = new ObjectMapper();
        HttpClient httpClient = HttpClientBuilder.create().build();
        String command = objectMapper.writeValueAsString(commandParams);
        String u = driverService.getUrl().toString() + "/session/" + driver.getSessionId() + "/chromium/send_command";
        HttpPost request = new HttpPost(u);
        request.addHeader("content-type", "application/json");
        request.setEntity(new StringEntity(command));
        System.out.println(httpClient.execute(request).getStatusLine().getStatusCode());
        driver.get("https://www.seleniumhq.org/download/");
        driver.findElement(By.linkText("32 bit Windows IE")).click();
    }
}
