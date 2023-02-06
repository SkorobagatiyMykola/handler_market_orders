package ua.skorobahatyi;

import ua.skorobahatyi.utils.CreationHttpPage;
import ua.skorobahatyi.utils.Handler;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    private static final String fileInput = "input.txt";
    private static final String fileOutput = "output.txt";
    private static final String fileError = "error.txt";

    public static void main(String[] args) throws IOException {
        System.out.println("=====  The task is to analyze and build an order book =====");
        Handler handler = new Handler();

        int countErr = 0;

        try (FileReader fr = new FileReader(fileInput);
             PrintWriter out = new PrintWriter(fileOutput)) {

            Scanner scan = new Scanner(fr);
            int lineInsex = 0;
            PrintWriter errorFile = null;
            String formatErrorMassage = "Error in %d line: %s";

            while (scan.hasNextLine()) {
                try {
                    String line = scan.nextLine();
                    lineInsex++;
                    String[] strArray = line.split(",");
                    handler.handlerLine(strArray, out);
                } catch (Exception ex) {
                    countErr++;
                    if (countErr == 1) {
                        errorFile = new PrintWriter(fileError);
                    }
                    String errorMassage = String.format(formatErrorMassage, lineInsex, ex.getMessage());
                    errorFile.println(errorMassage);
                    System.err.println(errorMassage);
                }
            }

            if (errorFile != null) {
                errorFile.close();
            }
        }

        getResultReportOrErrorFile(handler, countErr);
    }

    private static void getResultReportOrErrorFile(Handler handler, int countErr) throws IOException {

        if (countErr == 0) {
            System.out.println("===================================");
            System.out.println("THE REPORT WAS CREATED SUCCESSFULLY");
            CreationHttpPage.generateOrderBookForHtml(handler.getOrderBook());
            System.out.println("===================================");

        } else {
            System.err.println(String.format("Fix errors in the %s file.", fileInput));
            System.err.println(String.format("You can see errors in the %s file.", fileError));
        }
    }
}
