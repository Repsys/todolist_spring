package pikachurin.leonid.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import pikachurin.leonid.todolist.entity.ListEnt;
import pikachurin.leonid.todolist.exception.*;
import pikachurin.leonid.todolist.model.ListRequestBody;
import pikachurin.leonid.todolist.repository.ListRepo;

import java.util.*;

@Service
public class ListsService {
    @Autowired
    private ListRepo listRepo;

    public List<ListEnt> getLists(Integer quantity, Integer page, String sort, Boolean isInvert)
    {
        if (quantity > 100 || quantity < 1) quantity = 10;

        String[] sortValues = {"name", "createDate", "modifyDate"};
        if (Arrays.stream(sortValues).noneMatch(sort::equals))
            throw new InvalidSortValueException(sort);

        Sort.Direction sortDir;
        if (isInvert) sortDir = Sort.Direction.DESC;
        else sortDir = Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, quantity, Sort.by(sortDir, sort));
        return listRepo.findAll(pageable).getContent();
    }

    public ListEnt getList(UUID id) {
        Optional<ListEnt> optionalList = listRepo.findById(id);
        if (optionalList.isEmpty())
            throw new ListNotFoundException(id);

        return optionalList.get();
    }

    public ListEnt createList(ListRequestBody listBody) {
        if (listBody.getName().isEmpty())
            throw new IncorrectNameException();

        ListEnt list = new ListEnt();
        list.setName(listBody.getName());
        list = listRepo.saveAndFlush(list);
        return list;
    }

    public ListEnt modifyList(UUID id, ListRequestBody listBody) {
        Optional<ListEnt> optionalList = listRepo.findById(id);
        if (optionalList.isEmpty())
            throw new ListNotFoundException(id);
        if (listBody.getName().isEmpty())
            throw new IncorrectNameException();

        ListEnt list = optionalList.get();
        list.setName(listBody.getName());
        listRepo.flush();
        return list;
    }

    public void removeList(UUID id) {
        Optional<ListEnt> optionalList = listRepo.findById(id);
        if (optionalList.isEmpty())
            throw new ListNotFoundException(id);

        ListEnt list = optionalList.get();
        listRepo.delete(list);
    }
}
