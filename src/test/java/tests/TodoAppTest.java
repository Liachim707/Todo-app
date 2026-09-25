package tests;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.*;

import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TodoAppTest {

    private AndroidDriver driver;

    @BeforeEach
    void setUp() throws MalformedURLException {

        var options = new io.appium.java_client.android.options
                .UiAutomator2Options();

        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");

        options.setAppPackage("com.example.android.architecture.blueprints.main");

        options.setAppActivity(
                "com.example.android.architecture.blueprints.todoapp.TodoActivity"
        );

        options.setDeviceName("Android Emulator");

        driver = new AndroidDriver(
                new URL("http://127.0.0.1:4723"),
                options
        );
    }

    @Test
    @DisplayName("Создание новой задачи")
    void shouldCreateNewTask() {

        WebElement addTaskButton = driver.findElement(
                AppiumBy.accessibilityId("New Task")
        );

        addTaskButton.click();

        WebElement titleInput = driver.findElement(
                AppiumBy.className("android.widget.EditText")
        );

        String taskTitle = "Изучить Appium";

        titleInput.sendKeys(taskTitle);

        WebElement saveButton = driver.findElement(
                AppiumBy.accessibilityId("Save")
        );

        saveButton.click();

        WebElement createdTask = driver.findElement(
                AppiumBy.xpath(
                        "//*[@text='" + taskTitle + "']"
                )
        );

        assertTrue(
                createdTask.isDisplayed(),
                "Созданная задача не отображается в списке"
        );
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
