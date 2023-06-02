package hr.fer.oprpp1.jmbag0036531975;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class StudentDB {

    private static void ispisiLiniju(int jmbag, int firstName, int lastName) {

        System.out.print("+");
        for (int i = 0; i < jmbag + 2; i++) {
            System.out.print("=");
        }
        System.out.print("+");
        for (int i = 0; i < lastName + 2; i++) {
            System.out.print("=");
        }
        System.out.print("+");
        for (int i = 0; i < firstName + 2; i++) {
            System.out.print("=");
        }
        System.out.print("+");
        for (int i = 0; i < 3; i++) {
            System.out.print("=");
        }
        System.out.println("+");
    }

    private static void ispisiRazmake(int razmaci) {
        for (int i = 0; i < razmaci; i++) {
            System.out.print(" ");
        }
    }

    private static void ispisi(List<StudentRecord> list) {
        if (list.isEmpty()) return;
        int jmbag = 0, firstName = 0, lastName = 0;
        for (StudentRecord record : list) {
            if (record.getJmbag().length() > jmbag) {
                jmbag = record.getJmbag().length();
            }
            if (record.getFirstName().length() > firstName) {
                firstName = record.getFirstName().length();
            }
            if (record.getLastName().length() > lastName) {
                lastName = record.getLastName().length();
            }
        }
        ispisiLiniju(jmbag, firstName, lastName);
        for (StudentRecord student : list) {
            System.out.print("| " + student.getJmbag());
            ispisiRazmake(jmbag - student.getJmbag().length() + 1);
            System.out.print("| " + student.getLastName());
            ispisiRazmake(lastName - student.getLastName().length() + 1);
            System.out.print("| " + student.getFirstName());
            ispisiRazmake(firstName - student.getFirstName().length() + 1);
            System.out.print("| " + student.getFinalGrade());
            ispisiRazmake(jmbag - student.getJmbag().length() + 1);
            System.out.println("|");
        }
        ispisiLiniju(jmbag, firstName, lastName);

    }

    private static String first(String s) {
        int index = 0;
        String izlaz = "";
        while (index < s.length() &&s.charAt(index) != ' ' && s.charAt(index) != '\n' && s.charAt(index) != '\t') {
            izlaz += s.charAt(index++);
        }
        return izlaz;
    }

    private static String naredba(String s) {
        boolean puni = false;
        String text = "";
        for (int i = 0; i < s.length(); i++) {
            if (!puni) {
                if (s.charAt(i) == ' ' || s.charAt(i) == '\n' || s.charAt(i) == '\t') {
                    puni = true;
                }
            } else {
                text += s.charAt(i);
            }
        }
        return text;

    }

    public static void main(String[] args) {
        try {
            String filePath = "src/main/resources/database.txt";

            List<String> ulaz =
                    Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
            StudentDatabase studentDatabase = new StudentDatabase(ulaz);

            Scanner scanner = new Scanner(System.in);
            String action;
            while (!first((action = scanner.nextLine())).equals("exit")) {
                if (!first(action).equals("query")) {
                    System.out.println("Krivi unos naredbe, pokusajte ponovno.");
                } else {
                    String text = naredba(action);
                    QueryParser parser = new QueryParser(text);
                    List<StudentRecord> list = new LinkedList<>();
                    if (parser.isDirectQuery()) {
                        StudentRecord sr = studentDatabase.forJMBAG(parser.getQueriedJMBAG());
                        if (sr != null) {
                            list.add(sr);
                        }
                        System.out.println("Using index for record retrival");
                    } else {
                        for (StudentRecord record : studentDatabase.filter(new QueryFilter(parser.getQuery()))) {
                            list.add(record);
                        }
                    }
                    ispisi(list);
                    System.out.println("Records selected: " + list.size());
                }
            }
            System.out.println("Goodbye!");
            scanner.close();

        } catch (IOException exception) {
            System.out.println("Problem with reading the file.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}