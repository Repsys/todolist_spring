package pikachurin.leonid.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pikachurin.leonid.todolist.entity.ListEnt;
import pikachurin.leonid.todolist.repository.ListRepo;

@RestController
@RequestMapping("lists")
public class ListsController {
    @Autowired
    private ListRepo listRepo;

    @GetMapping
    public void getLists()
    {

    }

    @PostMapping
    public ListEnt createList(@RequestParam(required = true) String name)
    {
        ListEnt list = new ListEnt();
        list.setName(name);
        listRepo.save(list);
        return listRepo.getOne(list.getId());
    }

    @PutMapping("/{id}")
    public void modifyList(@PathVariable("id") int id)
    {

    }

    @DeleteMapping("/{id}")
    public void removeList(@PathVariable("id") int id)
    {

    }
}
