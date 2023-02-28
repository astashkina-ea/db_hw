import data.Curator;
import data.Group;
import data.Student;
import db.IDBExecutor;
import db.MySqlExecutor;
import tables.AbsTable;
import tables.CuratorTable;
import tables.GroupTable;
import tables.StudentTable;

import java.util.ArrayList;
import java.util.List;

public class Runner {

    public static void main(String[] args) throws Exception {

        IDBExecutor dbExecutor = new MySqlExecutor();
        try {
            AbsTable studentTable = new StudentTable(dbExecutor);
            AbsTable curatorTable = new CuratorTable(dbExecutor);
            AbsTable groupTable = new GroupTable(dbExecutor);

            List<String> columnsStudentTable = new ArrayList<>();
            columnsStudentTable.add("id INT PRIMARY KEY");
            columnsStudentTable.add("fio VARCHAR(50)");
            columnsStudentTable.add("sex VARCHAR(1)");
            columnsStudentTable.add("id_group INT");

            studentTable.create(columnsStudentTable);

            List<String> columnsGroupTable = new ArrayList<>();
            columnsGroupTable.add("id INT PRIMARY KEY");
            columnsGroupTable.add("name_group VARCHAR(50)");
            columnsGroupTable.add("id_curator INT");

            groupTable.create(columnsGroupTable);

            List<String> columnsCuratorTable = new ArrayList<>();
            columnsCuratorTable.add("id INT PRIMARY KEY");
            columnsCuratorTable.add("fio VARCHAR(50)");

            curatorTable.create(columnsCuratorTable);

            List<String> columnsStudent = new ArrayList<>();
            columnsStudent.add("id");
            columnsStudent.add("fio");
            columnsStudent.add("sex");
            columnsStudent.add("id_group");

            studentTable.insert(columnsStudent, new Student(1, "Thomas Wyatt", 'm', 1));
            studentTable.insert(columnsStudent, new Student(2, "William Shakespeare", 'm', 1));
            studentTable.insert(columnsStudent, new Student(3, "John Clare", 'm', 1));
            studentTable.insert(columnsStudent, new Student(4, "Robert Browning", 'm', 1));
            studentTable.insert(columnsStudent, new Student(5, "Thomas Hardy", 'm', 1));
            studentTable.insert(columnsStudent, new Student(6, "Charles Dickens", 'm', 2));
            studentTable.insert(columnsStudent, new Student(7, "Arthur Conan Doyle", 'm', 2));
            studentTable.insert(columnsStudent, new Student(8, "Agatha Christie", 'f', 2));
            studentTable.insert(columnsStudent, new Student(9, "John Tolkien", 'm', 2));
            studentTable.insert(columnsStudent, new Student(10, "George Orwell", 'm', 2));
            studentTable.insert(columnsStudent, new Student(11, "Joanne Rowling", 'f', 3));
            studentTable.insert(columnsStudent, new Student(12, "Geoffrey Chauce", 'm', 3));
            studentTable.insert(columnsStudent, new Student(13, "Stephen Edwin King", 'm', 3));
            studentTable.insert(columnsStudent, new Student(14, "Jack London", 'm', 3));
            studentTable.insert(columnsStudent, new Student(15, "Louisa May Alcott", 'f', 3));

            List<String> columnsGroup = new ArrayList<>();
            columnsGroup.add("id");
            columnsGroup.add("name_group");
            columnsGroup.add("id_curator");

            groupTable.insert(columnsGroup, new Group(1, "QA", 1));
            groupTable.insert(columnsGroup, new Group(2, "Analysts", 2));
            groupTable.insert(columnsGroup, new Group(3, "Designers", 3));

            List<String> columnsCurator = new ArrayList<>();
            columnsCurator.add("id");
            columnsCurator.add("fio");

            curatorTable.insert(columnsCurator, new Curator(1, "Pushkin A.S"));
            curatorTable.insert(columnsCurator, new Curator(2, "Lermontov M.Yu"));
            curatorTable.insert(columnsCurator, new Curator(3, "Tolstoy L.N"));
            curatorTable.insert(columnsCurator, new Curator(4, "Dostoevsky M.F"));

            System.out.println("*** Информация о студентах ***");
            studentTable.resultDisplay(studentTable.selectSimple("*"));
            System.out.println("*** Кол-во студентов ***");
            studentTable.resultDisplay(studentTable.count());
            System.out.println("*** Студентки ***");
            studentTable.resultDisplay(studentTable.selectWithWhere("fio", "sex", "f"));
            System.out.println("*** Обновить информацию о группе сменив куратора ***");
            System.out.println("ДО:");
            groupTable.resultDisplay(groupTable.selectWithWhere("*", "id", "2"));
            groupTable.update("id_curator", "4", "id", "2");
            System.out.println("ПОСЛЕ:");
            groupTable.resultDisplay(groupTable.selectWithWhere("*", "id", "2"));
            System.out.println("*** Группы их кураторы ***");
            groupTable.resultDisplay(groupTable.selectWithJoin("StudentGroup.name_group, Curator.fio", "id_curator", "curator", "id"));
            System.out.println("*** Студенты из определенной группы (по имени) ***");
            studentTable.resultDisplay(studentTable.selectWithSubRequest("fio", "id_group", "id", "StudentGroup", "name_group", "QA"));
        } finally {
            dbExecutor.close();
        }
    }
}