package pikachurin.leonid.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pikachurin.leonid.todolist.entity.*;
import pikachurin.leonid.todolist.exception.*;
import pikachurin.leonid.todolist.repository.ListRepo;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("lists")
public class ListsController {
    @Autowired
    private ListRepo listRepo;

    @GetMapping
    public ResponseEntity getLists(
            @RequestParam(required = false, defaultValue = "10") Integer quantity,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "createDate") String sort,
            @RequestParam(required = false, defaultValue = "false") Boolean isInvert,
            @RequestParam Optional<String> name,
            @RequestParam Optional<Timestamp> createDate,
            @RequestParam Optional<Timestamp> modifyDate)
    {
        if (quantity > 100 || quantity < 1) quantity = 10;

        String[] sortValues = {"name", "createDate", "modifyDate"};
        if (Arrays.stream(sortValues).noneMatch(sort::equals))
            throw new InvalidSortValueException(sort);

        Sort.Direction sortDir;
        if (isInvert) sortDir = Sort.Direction.DESC;
        else sortDir = Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, quantity, Sort.by(sortDir, sort));
        return new ResponseEntity(listRepo.findAll(pageable).getContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getList(
            @PathVariable("id") UUID id)
    {
        Optional<ListEnt> optionalList = listRepo.findById(id);
        if (optionalList.isEmpty())
            throw new ListNotFoundException(id);

        return new ResponseEntity(optionalList.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createList(
            @RequestParam String name)
    {
        if (name.isEmpty())
            throw new IncorrectNameException();

        ListEnt list = new ListEnt();
        list.setName(name);
        list = listRepo.saveAndFlush(list);

        return new ResponseEntity(list, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity modifyList(
            @PathVariable("id") UUID id,
            @RequestParam Optional<String> name)
    {
        Optional<ListEnt> optionalList = listRepo.findById(id);
        if (optionalList.isEmpty())
            throw new ListNotFoundException(id);

        ListEnt list = optionalList.get();
        name.ifPresent(list::setName);
        listRepo.flush();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeList(
            @PathVariable("id") UUID id)
    {
        Optional<ListEnt> optionalList = listRepo.findById(id);
        if (optionalList.isEmpty())
            throw new ListNotFoundException(id);

        ListEnt list = optionalList.get();
        listRepo.delete(list);
        return new ResponseEntity(new MyResponse("Список успешно удален", HttpStatus.OK), HttpStatus.OK);
    }
}

