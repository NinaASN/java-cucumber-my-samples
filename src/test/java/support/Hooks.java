package support;

import io.cucumber.java.After;

public class Hooks {
    @After
    public void after(){
      DriverFactory.quitDriver();
    }
}
