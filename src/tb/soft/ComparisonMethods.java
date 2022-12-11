package tb.soft;

public class ComparisonMethods extends Person{

    public ComparisonMethods(Person person) throws PersonException{

        super(person.getFirstName(), person.getLastName());
        birthYear = person.getBirthYear();
        job = person.getJob();
    }

    @Override
    public boolean equals(Object object) {

        if (object == null) return false;

        if (this == object) return true;


        if (object instanceof Person) {

            Person otherPerson = (Person) object;

            return this.getFirstName().equals(otherPerson.getFirstName()) &&
                   this.getLastName().equals(otherPerson.getLastName()) &&
                   birthYear == otherPerson.getBirthYear();
        }

        return false;
    }

    @Override
    public int hashCode() {

        int outcome;
        outcome = 31 * this.getFirstName().hashCode();
        outcome = outcome + 31 * this.getLastName().hashCode();
        outcome = outcome + 31 * birthYear;

        return outcome;
    }
}
