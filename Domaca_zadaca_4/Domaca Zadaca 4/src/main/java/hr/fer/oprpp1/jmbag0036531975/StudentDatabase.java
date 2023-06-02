package hr.fer.oprpp1.jmbag0036531975;

import java.util.*;

public class StudentDatabase {
    private List<StudentRecord> studentRecords;
    private Map<String, StudentRecord> indexMap;
    public StudentDatabase(List<String> ulaz) {
        studentRecords = new LinkedList<>();
        indexMap = new HashMap<>();
        Set<String> jmbagSet = new HashSet<>(ulaz.size());
        for(var str : ulaz) {
            String[] podatak = str.split("\t");
            String jmbag = podatak[0], lastName = podatak[1], firstName = podatak[2], grade = podatak[3];
            if (jmbagSet.contains(jmbag))
                throw new IllegalArgumentException("Already exists jmbag" + jmbag);
            if (!(grade.equals("1") || grade.equals("2") || grade.equals("3") || grade.equals("4") || grade.equals("5")))
                throw new IllegalArgumentException("Grade must be between 1 and 5");
            jmbagSet.add(jmbag);
            StudentRecord studentRecord = new StudentRecord(jmbag, lastName, firstName, grade);
            studentRecords.add(studentRecord);
            indexMap.put(jmbag, studentRecord);
        }
    }

    public StudentRecord forJMBAG(String jmbag) {
        return indexMap.get(jmbag);
    }

    public List<StudentRecord> filter(IFilter filter) {
        List<StudentRecord> tmpList = new LinkedList<>();
        for(var studentRecord : studentRecords) {
            if (filter.accepts(studentRecord)) {
                tmpList.add(studentRecord);
            }
        }
        return tmpList;
    }

}
