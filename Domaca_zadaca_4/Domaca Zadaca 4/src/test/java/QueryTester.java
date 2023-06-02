import hr.fer.oprpp1.jmbag0036531975.QueryFilter;
import hr.fer.oprpp1.jmbag0036531975.QueryParser;
import hr.fer.oprpp1.jmbag0036531975.StudentDatabase;
import hr.fer.oprpp1.jmbag0036531975.StudentRecord;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QueryTester {

    @Test
    public void nullTextException() {
        assertThrows(NullPointerException.class, () -> new QueryParser(null));
    }

    @Test
    public void parseDirectJmbagTest() {
        List<String> list = new ArrayList<>();
        list.add("0000000001\tAkšamović\tMarin\t2");
        list.add("0000000002\tJuric\tMarin\t3");
        StudentDatabase sb = new StudentDatabase(list);
        QueryParser queryParser = new QueryParser("jmbag=\"0000000001\"");
        assertEquals(queryParser.getQueriedJMBAG(), "0000000001");
    }

    @Test
    public void isDirectQueryTest() {
        List<String> list = new ArrayList<>();
        list.add("0000000001\tAkšamović\tMarin\t2");
        list.add("0000000002\tJuric\tMarin\t3");
        StudentDatabase sb = new StudentDatabase(list);
        QueryParser queryParser = new QueryParser("jmbag=\"0000000001\"");
        assertTrue(queryParser.isDirectQuery());
    }

    @Test
    public void isDirectJmbag() {
        List<String> list = new ArrayList<>();
        list.add("0000000001\tAkšamović\tMarin\t2");
        list.add("0000000002\tJuric\tMarin\t3");
        StudentDatabase sb = new StudentDatabase(list);
        QueryParser queryParser = new QueryParser("jmbag=\"0000000001\"");
        assertEquals(new StudentRecord("0000000001", "Akšamović", "Marin", "2"),
                sb.forJMBAG(queryParser.getQueriedJMBAG()));
    }

    @Test
    public void isGreaterTest() {
        List<String> list = new ArrayList<>();
        list.add("0000000001\tAkšamović\tMarin\t2");
        list.add("0000000002\tJuric\tMarin\t3");
        StudentDatabase sb = new StudentDatabase(list);
        QueryParser queryParser = new QueryParser("jmbag>\"0000000001\"");
        List<StudentRecord> recordList = new ArrayList<>();
        recordList.add(new StudentRecord("0000000002", "Juric", "Marin", "3"));
        assertEquals(recordList, sb.filter(new QueryFilter(queryParser.getQuery())));
    }

    @Test
    public void isGreaterOrEqualTest() {
        List<String> list = new ArrayList<>();
        list.add("0000000001\tAkšamović\tMarin\t2");
        list.add("0000000002\tJuric\tMarin\t3");
        StudentDatabase sb = new StudentDatabase(list);
        QueryParser queryParser = new QueryParser("jmbag>=\"0000000001\"");
        List<StudentRecord> recordList = new ArrayList<>();
        recordList.add(new StudentRecord("0000000001", "Akšamović", "Marin", "2"));
        recordList.add(new StudentRecord("0000000002", "Juric", "Marin", "3"));
        assertEquals(recordList, sb.filter(new QueryFilter(queryParser.getQuery())));
    }

    @Test
    public void isLesserTest() {
        List<String> list = new ArrayList<>();
        list.add("0000000001\tAkšamović\tMarin\t2");
        list.add("0000000002\tJuric\tMarin\t3");
        StudentDatabase sb = new StudentDatabase(list);
        QueryParser queryParser = new QueryParser("jmbag<\"0000000001\"");
        List<StudentRecord> recordList = new ArrayList<>();
        assertEquals(recordList, sb.filter(new QueryFilter(queryParser.getQuery())));
    }

    @Test
    public void isLesserOrEqualTest() {
        List<String> list = new ArrayList<>();
        list.add("0000000001\tAkšamović\tMarin\t2");
        list.add("0000000002\tJuric\tMarin\t3");
        StudentDatabase sb = new StudentDatabase(list);
        QueryParser queryParser = new QueryParser("jmbag<=\"0000000001\"");
        List<StudentRecord> recordList = new ArrayList<>();
        recordList.add(new StudentRecord("0000000001", "Akšamović", "Marin", "2"));
        assertEquals(recordList, sb.filter(new QueryFilter(queryParser.getQuery())));
    }

    @Test
    public void isEqualTest() {
        List<String> list = new ArrayList<>();
        list.add("0000000001\tAkšamović\tMarin\t2");
        list.add("0000000002\tJuric\tMarin\t3");
        StudentDatabase sb = new StudentDatabase(list);
        QueryParser queryParser = new QueryParser("jmbag=\"0000000001\"and firstName=\"Marin\"");
        List<StudentRecord> recordList = new ArrayList<>();
        recordList.add(new StudentRecord("0000000001", "Akšamović", "Marin", "2"));
        assertEquals(recordList, sb.filter(new QueryFilter(queryParser.getQuery())));
    }

    @Test
    public void isNotEqualTest() {
        List<String> list = new ArrayList<>();
        list.add("0000000001\tAkšamović\tMarin\t2");
        list.add("0000000002\tJuric\tMarin\t3");
        StudentDatabase sb = new StudentDatabase(list);
        QueryParser queryParser = new QueryParser("jmbag!=\"0000000001\"");
        List<StudentRecord> recordList = new ArrayList<>();
        recordList.add(new StudentRecord("0000000002", "Juric", "Marin", "3"));
        assertEquals(recordList, sb.filter(new QueryFilter(queryParser.getQuery())));
    }

    @Test
    public void isLIKETest() {
        List<String> list = new ArrayList<>();
        list.add("0000000001\tAkšamović\tMarin\t2");
        list.add("0000000002\tJuric\tMarin\t3");
        StudentDatabase sb = new StudentDatabase(list);
        QueryParser queryParser = new QueryParser("jmbagLIKE\"000000000*\"");
        List<StudentRecord> recordList = new ArrayList<>();
        recordList.add(new StudentRecord("0000000001", "Akšamović", "Marin", "2"));
        recordList.add(new StudentRecord("0000000002", "Juric", "Marin", "3"));
        assertEquals(recordList, sb.filter(new QueryFilter(queryParser.getQuery())));
    }
}
