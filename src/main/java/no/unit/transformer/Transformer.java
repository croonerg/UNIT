package no.unit.transformer;

import java.io.FileWriter;
import java.io.PrintWriter;

import no.unit.transformer.utils.FileUtils;
import no.unit.transformer.utils.FileUtils.FILE_FORMAT;
import org.apache.commons.io.FilenameUtils;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.concurrent.Callable;

@Command(
    name = "transform",
    version = "transform 0.0.1",
    description =
        "Converts a XML or JSON file on <serialization-a> schema to XML or JSON accordingly or given"
            + " with content transformed to <serialization-b> schema",
    mixinStandardHelpOptions = true,
    exitCodeListHeading = "Exit Codes:%n",
    exitCodeList = {
      " 0:Successful program execution",
      " 2:Invalid or missing input",
      " 3:Input file and/or output file missing",
    })
public class Transformer implements Callable<Integer> {

  private static final int EXIT_CODE_SUCCESS = 0;
  private static final int EXIT_CODE_MISSING_INPUT_ERROR = 3;

  @Spec private CommandSpec spec;

  @Option(
      names = {"-i", "--input"},
      description = "The file to transform")
  private File optionInputFile;

  @Option(
      names = {"-o", "--output"},
      description = "The final transformed file")
  private File optionOutputFile;

  @Option(
      names = {"-of", "--output-format"},
      defaultValue = "UNKNOWN",
      fallbackValue = "UNKNOWN",
      description = "XML, JSON")
  private FILE_FORMAT optionOutputFormat;

  public static void main(String... args) {
    int exitCode = new CommandLine(new Transformer()).execute(args);
    System.exit(exitCode); // NOPMD
  }

  @Override
  public Integer call() throws Exception {
    if (optionInputFile == null || optionOutputFile == null) {
      return EXIT_CODE_MISSING_INPUT_ERROR;
    }

    FILE_FORMAT inputFormat =
        FileUtils.getFileFormat(FilenameUtils.getExtension(optionInputFile.getName()));

    Parser parser;
    if (inputFormat == FILE_FORMAT.XML) {
      parser = new XMLParser();
    } else if (inputFormat == FILE_FORMAT.JSON) {
      parser = new JSONParser();
    } else {
      throw new IllegalArgumentException();
    }

    byte[] fileContents = Files.readAllBytes(optionInputFile.toPath());
    String inputString = new String(fileContents, StandardCharsets.UTF_8);
    FILE_FORMAT outputFormat =
        optionOutputFormat != FILE_FORMAT.UNKNOWN ? optionOutputFormat : inputFormat;
    String output = "";
    if (outputFormat == FILE_FORMAT.XML) {
      output = parser.toXML(inputString);
    } else if (outputFormat == FILE_FORMAT.JSON) {
      output = parser.toJSON(inputString);
    }

    FileWriter fileWriter = new FileWriter(optionOutputFile); // NOPMD
    PrintWriter printWriter = new PrintWriter(fileWriter);
    try {
      printWriter.printf(output);
    } finally {
      fileWriter.close();
      printWriter.close();
    }

    // print to Out stream too for testing purpose or user wanting to pipe result
    spec.commandLine().getOut().println(output);

    // TODO: print error if input file is badly formatted
    spec.commandLine().getErr().println("error message");

    return EXIT_CODE_SUCCESS;
  }
}
