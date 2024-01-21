package dev.randomcode.calculator.tool;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.List;

public class AstGenerator {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: AstGenerator.class <output directory>");
            System.exit(1);
        }

        String outputDir = args[0];
        defineAst(outputDir, "Expression", Arrays.asList(
                "Binary:Expression left, Token operator, Expression right",
                "Grouping:Expression expression",
                "Literal:Object value",
                "Call:Expression callee, Token parenthesis, Expression argument", // TODO: multi-argument functions
                "Unary:Token operator, Expression right"));
    }

    // Writes the base class and parses the subclass descriptions
    private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
        String path = outputDir + "/" + baseName + ".java";
        PrintWriter writer = new PrintWriter(path, "UTF-8");

        // Package, imports, and base class
        writer.println("package dev.randomcode.calculator;");
        writer.println();
        writer.println("import dev.randomcode.calculator.Token;");
        writer.println();
        writer.printf("abstract class %s {\n", baseName);

        // Visitor
        defineVisitor(writer, baseName, types);

        // AST classes
        for (String type : types) {
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();
            writer.println();
            defineType(writer, baseName, className, fields);
        }

        // accept method
        writer.println();
        writer.println("\tabstract <R> R accept(Visitor<R> visitor);");

        writer.println("}");
        writer.close();
    }

    // Writes a visitor interface
    private static void defineVisitor(PrintWriter writer, String baseName, List<String> types) {
        writer.println("\tinterface Visitor<R> {");

        for (String type : types) {
            String typeName = type.split(":")[0].trim();
            writer.printf("\t\tR visit%s%s(%s %s);\n", typeName, baseName, typeName, baseName.toLowerCase());
        }

        writer.println("\t}");
    }

    // Writes a subclass
    private static void defineType(PrintWriter writer, String baseName, String className, String fieldList) {
        // Beginning of the class's body
        writer.printf("\tstatic class %s extends %s {\n", className, baseName);

        // Fields
        String[] fields = fieldList.split(", ");
        for (String field : fields) {
            writer.printf("\t\tfinal %s;\n", field);
        }
        writer.println();

        // Constructor
        writer.printf("\t\t%s(%s) {\n", className, fieldList);
        for (String field : fields) {
            String name = field.split(" ")[1];
            writer.printf("\t\t\tthis.%s = %s;\n", name, name);
        }
        writer.println("\t\t}");

        // Visitor
        writer.println();
        writer.println("\t\t@Override");
        writer.println("\t\t<R> R accept(Visitor<R> visitor) {");
        writer.printf("\t\t\treturn visitor.visit%s%s(this);\n", className, baseName);
        writer.println("\t\t}");

        // End of class body
        writer.println("\t}");
    }
}
