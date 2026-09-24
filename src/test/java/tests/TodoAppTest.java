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

        // Замените на package вашего собранного TODO-приложения
        options.setAppPackage("com.example.android.architecture.blueprints.todoapp");

        // Activity приложения
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

        // Шаг 1. Нажимаем кнопку добавления задачи
        WebElement addTaskButton = driver.findElement(
                AppiumBy.accessibilityId("Add task")
        );

        addTaskButton.click();

        // Шаг 2. Вводим название задачи
        WebElement titleInput = driver.findElement(
                AppiumBy.className("android.widget.EditText")
        );

        String taskTitle = "Изучить Appium";

        titleInput.sendKeys(taskTitle);

        // Шаг 3. Сохраняем задачу
        WebElement saveButton = driver.findElement(
                AppiumBy.accessibilityId("Save")
        );

        saveButton.click();

        // Шаг 4. Проверяем, что задача появилась в списке
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
