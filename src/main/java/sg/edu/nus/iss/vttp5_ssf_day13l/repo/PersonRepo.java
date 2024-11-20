package sg.edu.nus.iss.vttp5_ssf_day13l.repo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.vttp5_ssf_day13l.model.Person;

@Repository
public class PersonRepo {
    
    private List<Person> persons = new ArrayList<>();

    public PersonRepo() throws ParseException {
       String birthDate = "1988-12-01";
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       Date birthday = sdf.parse(birthDate);
       persons.add(new Person("Daniel", "Loo", "danielloo@ial.edu.sg", 12000, birthday, "91234567", 123456));
    }

    public List<Person> findAll() {
        return persons;
    }

    public Person findById(String personId) {
        Person foundPerson = persons.stream().filter(p -> p.getId().equals(personId)).findFirst().get();
        return foundPerson;
    }

    public Boolean create(Person person) {
        persons.add(person);
        return true;
    }

    public Boolean delete(Person person) {
        int iFoundPerson = persons.indexOf(person);
        
        if (iFoundPerson >= 0) {
            persons.remove(person);
            return true;
        }
        return false;
    }

    public Boolean update(Person person) {
        List<Person> filteredPerson = persons.stream().filter(p -> p.getId().equals(person.getId())).collect(Collectors.toList());
        
        if (filteredPerson.size() > 0) {
            persons.remove(filteredPerson.getFirst());
            persons.add(person);
            return true;
        }
        return false;
    }
}
