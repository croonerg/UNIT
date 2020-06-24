package no.unit.transformer;

import java.io.FileWriter;
import java.io.PrintWriter;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

import java.io.File;
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

  public enum SUPPORTED_FILE_FORMAT {
    XML,
    JSON
  }

  @Spec private CommandSpec spec;

  @Option(
      names = {"-i", "--input"},
      description = "The file to transform")
  private File inputFile;

  @Option(
      names = {"-o", "--output"},
      description = "The final transformed file")
  private File outputFile;

  @Option(
      names = {"-if", "--input-format"},
      description = "XML, JSON")
  private String inputFormat; // NOPMD

  @Option(
      names = {"-of", "--output-format"},
      description = "XML, JSON")
  private String outputFormat; // NOPMD

  public static void main(String... args) {
    int exitCode = new CommandLine(new Transformer()).execute(args);
    System.exit(exitCode); // NOPMD
  }

  @Override
  public Integer call() throws Exception {
    if (inputFile == null || outputFile == null) {
      return EXIT_CODE_MISSING_INPUT_ERROR;
    }

    byte[] fileContents = Files.readAllBytes(inputFile.toPath()); // NOPMD

    // TODO: detect format on input file if output format is not given
    SUPPORTED_FILE_FORMAT inputFileFormat = SUPPORTED_FILE_FORMAT.XML; // NOPMD

    // TODO: parse output and save as inputFormat or inputFileFormat
    String output = "this is the parsed output";

    FileWriter fileWriter = new FileWriter(outputFile); // NOPMD
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
