package by.ralovets.course.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "group_name")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "group_number", nullable = false, length = 6)
    private String groupNumber;


    @Transient
    public Long getNumberOfGroup() {
        return id;
    }

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Student> students;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }


    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Group() {
    }

    public Group(Long id, String groupNumber, List<Student> students) {
        this.id = id;
        this.groupNumber = groupNumber;
        this.students = students;
    }
}
