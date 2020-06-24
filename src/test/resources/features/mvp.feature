Feature:
  User transforms file

  Background:
    Given the user has an application "Transformer" that has a command line interface
    And "Transformer" has a flag "--input" that takes a single argument that is a filename
    And "Transformer" has a flag "--output" that takes a single argument that is a filename
    And "Transformer" has a flag "--output-format" that takes a single argument "xml" or "json"

  Scenario Outline: User transforms file
    Given the user has a file <file>
    When the user transforms the file to <output-format>
    Then the user sees that the output file conforms to <schema>

    Examples:
      -------------------------------------------------------------
      | file                  | output-format     | schema        |

      | "input.json"          | ""                | "schema.json" |
      | "input.json"          | "json"            | "schema.json" |
      | "input.json"          | "xml"             | "schema.xsd"  |

      | "single_object.json"  | ""                | "schema.json" |
      | "single_object.json"  | "json"            | "schema.json" |
      | "single_object.json"  | "xml"             | "schema.xsd"  |

      | "input.xml"           | ""                | "schema.xsd"  |
      | "input.xml"           | "xml"             | "schema.xsd"  |
      | "input.xml"           | "json"            | "schema.json" |

      | "single_item.xml"     | ""                | "schema.xsd"  |
      | "single_item.xml"     | "xml"             | "schema.xsd"  |
      | "single_item.xml"     | "json"            | "schema.json" |


  Scenario: User attempts to transform badly formatted file
    Given the user has a file "badly_formatted.json"
    When the user transforms the data
    Then the user sees an error message telling that the input file is badly formatted