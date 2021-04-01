package br.com.brienze.desafio.dock.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/features", 
		tags = { "@CadastroTest" },
		plugin = "pretty")
public class CucumberRunnerTest {

}
