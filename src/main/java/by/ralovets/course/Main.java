package by.ralovets.course;

import by.ralovets.course.entity.Course;
import by.ralovets.course.entity.Group;
import by.ralovets.course.entity.RecordBook;
import by.ralovets.course.entity.Student;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class Main {

    public static SessionFactory sessionFactory = null;

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().build();
        sessionFactory = new MetadataSources(registry)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(RecordBook.class)
                .addAnnotatedClass(Group.class)
                .addAnnotatedClass(Course.class)
                .buildMetadata()
                .buildSessionFactory();

        selectStudents();

        sessionFactory.close();
    }

    public static void selectStudents() {
        final List<Student> students = sessionFactory.fromSession(session -> {
            final Query<Student> query = session.createQuery("FROM Student s JOIN FETCH s.group LEFT JOIN FETCH s.courses", Student.class);
            return query.list();
        });

        for (Student student : students) {
            final Double rating = student.getRecordBook().getRating();
            System.out.printf("Student: %s %s, Group: %s, Rating: %f\n",
                    student.getName(), student.getLastName(), student.getGroup().getGroupNumber(), rating);

            List<Course> courses = student.getCourses();
            if (courses != null && !courses.isEmpty()) {
                System.out.println("Courses:");
                for (Course course : courses) {
                    System.out.println("- " + course.getCourseName());
                }
            }
        }
    }
}