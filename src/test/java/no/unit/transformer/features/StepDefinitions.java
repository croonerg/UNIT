package no.unit.transformer.features;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import no.unit.transformer.Transformer;

import no.unit.transformer.utils.FileUtils.FILE_FORMAT;
import org.junit.jupiter.api.BeforeEach;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinitions extends TestWiring {

  final ByteArrayOutputStream out = new ByteArrayOutputStream();
  final ByteArrayOutputStream err = new ByteArrayOutputStream();

  private CommandLine cmd;
  private StringBuilder command;
  private String inputFile;
  private File outputFile;
  private FILE_FORMAT outputFormat;

  @Before
  public void setUp(Scenario scenario) {
    System.out.println("------------------------------");
    System.out.println("Starting - " + scenario.getName());
    System.out.println("------------------------------");

    command = new StringBuilder("transform");
  }

  @BeforeEach
  public void setUpStreams() {
    out.reset();
    err.reset();
  }

  @After
  public void tearDown(Scenario scenario) {
    System.out.println("------------------------------");
    System.out.println(scenario.getName() + " Status - " + scenario.getStatus());
    System.out.println("------------------------------");
  }

  // Background
  @Given("^the user has an application \"Transformer\" that has a command line interface$")
  public void theUserHasAnApplicationThatHasACommandLineInterface() {
    cmd = new CommandLine(new Transformer());
  }

  @And("\"Transformer\" has a flag \"--input\" that takes a single argument that is a filename")
  public void hasAFlagInputThatTakesASingleArgumentThatIsAFilename() {
    command.append(" --input=%s");
  }

  @And("\"Transformer\" has a flag \"--output\" that takes a single argument that is a filename")
  public void hasAFlagOutputThatTakesASingleArgumentThatIsAFilename() {
    command.append(" --output=%s");
  }

  @And(
      "\"Transformer\" has a flag \"--output-format\" that takes a single argument \"xml\" or \"json\"")
  public void theTransformerHasAFlagOutputFormatThatTakesASingleArgumentXmlOrJson() {
    command.append(" --output-format=%s");
  }

  @Given("the user has a file {string}")
  public void the_user_has_a_file(String fileName) {
    inputFile = fileName;
  }

  @When("the user transforms the file to {string}")
  public void the_user_transforms_the_file_to(String outputFormat) {
    cmd.execute(String.format(command.toString(), inputFile, outputFile, outputFormat).split(" "));
  }

  @Then("the user sees that the output file conforms to {string}")
  public void the_user_sees_that_the_output_file_conforms_to(String string) {
    assertEquals("expected output", out.toString());
  }

  @Then("the user sees an error message telling that the input file is badly formatted")
  public void the_user_sees_an_error_message_telling_that_the_input_file_is_badly_formatted() {
    assertEquals("expected output", err.toString());
  }
}
