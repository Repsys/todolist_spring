package pikachurin.leonid.todolist.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pikachurin.leonid.todolist.entity.*;
import pikachurin.leonid.todolist.exception.*;
import pikachurin.leonid.todolist.model.*;
import pikachurin.leonid.todolist.repository.ListRepo;

import java.sql.Timestamp;
import java.util.*;

@Api(description = "Операции со списками задач")
@RestController
@RequestMapping("lists")
public class ListsController {
    @Autowired
    private ListRepo listRepo;

    @ApiOperation("Получить списки с сортировкой и фильтрацией")
    @GetMapping
    public ResponseEntity getLists(
            @RequestParam(required = false, defaultValue = "10") Integer quantity,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "createDate") String sort,
            @RequestParam(required = false, defaultValue = "false") Boolean isInvert,
            @RequestParam(required = false) Optional<String> name,
            @RequestParam(required = false) Optional<Timestamp> createDate,
            @RequestParam(required = false) Optional<Timestamp> modifyDate)
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

    @ApiOperation("Получить список по его id")
    @GetMapping("/{id}")
    public ResponseEntity getList(
            @PathVariable("id") UUID id)
    {
        Optional<ListEnt> optionalList = listRepo.findById(id);
        if (optionalList.isEmpty())
            throw new ListNotFoundException(id);

        return new ResponseEntity(optionalList.get(), HttpStatus.OK);
    }

    @ApiOperation("Создать новый список")
    @PostMapping
    public ResponseEntity createList(
            @RequestBody ListRequestBody listBody)
    {
        if (listBody.getName().isEmpty())
            throw new IncorrectNameException();

        ListEnt list = new ListEnt();
        list.setName(listBody.getName());
        list = listRepo.saveAndFlush(list);

        return new ResponseEntity(list, HttpStatus.CREATED);
    }

    @ApiOperation("Изменить список")
    @PutMapping("/{id}")
    public ResponseEntity modifyList(
            @PathVariable("id") UUID id,
            @RequestBody ListRequestBody listBody)
    {
        Optional<ListEnt> optionalList = listRepo.findById(id);
        if (optionalList.isEmpty())
            throw new ListNotFoundException(id);
        if (listBody.getName().isEmpty())
            throw new IncorrectNameException();

        ListEnt list = optionalList.get();
        list.setName(listBody.getName());
        listRepo.flush();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @ApiOperation("Удалить список")
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

