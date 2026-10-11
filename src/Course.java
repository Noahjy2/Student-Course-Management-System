public class Course {
    private String id;
    private String name;
    private int credit;

    public Course(String id, String name, int credit){
        this.id = id;
        this.name = name;
        this.credit = credit;
    }

    public String getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public int getCredit(){
        return this.credit;
    }

    @Override
    public String toString(){
    return String.format("Course ID: %s\nCourse Name: %s\nCredit: %d\n", this.id, this.name, this.credit);
    }


}
