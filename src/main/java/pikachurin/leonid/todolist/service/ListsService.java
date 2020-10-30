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

/**
 * Сервис по операциям со Списками задач
 */
@Service
public class ListsService {
    @Autowired
    private ListRepo listRepo;

    /**
     * Получить списки с сортировкой и фильтрацией
     * @param pageable - параметры пагинации и сортировки
     * @return массив списков
     */
    public List<ListEnt> getLists(Pageable pageable)
    {
        return listRepo.findAll(pageable).getContent();
    }

    /**
     * Получить список по его id
     * @param id - id списка
     * @return список
     */
    public ListEnt getList(UUID id) {
        Optional<ListEnt> optionalList = listRepo.findById(id);
        if (optionalList.isEmpty())
            throw new ListNotFoundException(id);

        return optionalList.get();
    }

    /**
     * Создать новый список
     * @param listBody - параметры нового списка
     * @return созданный список
     */
    public ListEnt createList(ListRequestBody listBody) {
        if (listBody.getName().isEmpty())
            throw new IncorrectNameException();

        ListEnt list = new ListEnt();
        list.setName(listBody.getName());
        list = listRepo.saveAndFlush(list);
        return list;
    }

    /**
     * Изменить список
     * @param id - id списка
     * @param listBody - параметры изменённого списка
     * @return изменённый список
     */
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

    /**
     * Удалить список
     * @param id - id списка
     */
    public void removeList(UUID id) {
        Optional<ListEnt> optionalList = listRepo.findById(id);
        if (optionalList.isEmpty())
            throw new ListNotFoundException(id);

        ListEnt list = optionalList.get();
        listRepo.delete(list);
    }
}
