public class Student {
    private String id;
    private String name;
    private int age;
    private String email;

    public Student(String id, String name, int age, String email){
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public int getAge(){
        return this.age;
    }
    public String getEmail(){
        return this.email;
    }

    @Override
    public String toString(){
        return String.format("Student ID: %s\nName: %s\nAge: %d\nEmail: %s\n",
                this.id,
                this.name,
                this.age,
                this.email);
    }
}
