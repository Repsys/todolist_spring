package pikachurin.leonid.todolist.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pikachurin.leonid.todolist.entity.*;
import pikachurin.leonid.todolist.exception.*;
import pikachurin.leonid.todolist.model.*;
import pikachurin.leonid.todolist.service.ListsService;

import java.sql.Timestamp;
import java.util.*;

@Api(description = "Операции со списками задач")
@RestController
@RequestMapping("lists")
public class ListsController {
    @Autowired
    private ListsService listsService;

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
        List<ListEnt> lists = listsService.getLists(quantity, page, sort, isInvert);
        return new ResponseEntity(lists, HttpStatus.OK);
    }

    @ApiOperation("Получить список по его id")
    @GetMapping("/{id}")
    public ResponseEntity getList(
            @PathVariable("id") UUID id)
    {
        ListEnt list = listsService.getList(id);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @ApiOperation("Создать новый список")
    @PostMapping
    public ResponseEntity createList(
            @RequestBody ListRequestBody listBody)
    {
        ListEnt list = listsService.createList(listBody);
        return new ResponseEntity(list, HttpStatus.CREATED);
    }

    @ApiOperation("Изменить список")
    @PutMapping("/{id}")
    public ResponseEntity modifyList(
            @PathVariable("id") UUID id,
            @RequestBody ListRequestBody listBody)
    {
        ListEnt list = listsService.modifyList(id, listBody);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @ApiOperation("Удалить список")
    @DeleteMapping("/{id}")
    public ResponseEntity removeList(
            @PathVariable("id") UUID id)
    {
        listsService.removeList(id);
        return new ResponseEntity(new MyResponse("Список успешно удален", HttpStatus.OK), HttpStatus.OK);
    }
}

