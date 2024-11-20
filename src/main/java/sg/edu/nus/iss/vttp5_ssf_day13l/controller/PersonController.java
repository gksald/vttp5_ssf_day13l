package sg.edu.nus.iss.vttp5_ssf_day13l.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import sg.edu.nus.iss.vttp5_ssf_day13l.model.Person;
import sg.edu.nus.iss.vttp5_ssf_day13l.service.PersonService;

@Controller
@RequestMapping("/persons")
public class PersonController {
    
    @Autowired
    PersonService personService;    // if no service then can map directly to repo. in this case actl service is redundant as only got 1 object

    @GetMapping()
    public String personListing(Model model) {

        List<Person> persons = personService.findAll();
        model.addAttribute("persons", persons);
        return "personlist";
    }

    @GetMapping(path = "/create")
    public String createForm(Model model) {
        Person p = new Person();
        model.addAttribute("person", p);
        return "personcreate";
    }

    @PostMapping(path = "/create")
    public String postCreateForm(@Valid @ModelAttribute("person") Person person, BindingResult result, Model model) {
        if(result.hasErrors())
            return "personcreate";
        
        Person p = new Person(person.getFirstName(), person.getLastName(), person.getEmail(), person.getSalary(), person.getDob(), person.getTelephone(), person.getPostalCode());
            personService.create(p);
            return "redirect:/persons";
    }

    @GetMapping("/delete/{person-id}")
    public String deletePerson(@PathVariable("person-id") String personId) {
        Person p = personService.findById(personId);
        personService.delete(p);
                return "redirect:/persons";
    }

    @GetMapping("/update/{person-id}")
    public String updatePerson(@PathVariable("person-id") String personId, Model model) {
        Person p = personService.findById(personId);
        model.addAttribute("personId", p);
                return "personupdate";
    }

    @PostMapping(path = "/create")
    public String postUpdatedForm(@Valid @ModelAttribute("person") Person person, BindingResult result, Model model) {
        if(result.hasErrors())
            return "personupdate";
        
        personService.update(person);
        
        return "redirect:/persons";
    }


    

    

}
