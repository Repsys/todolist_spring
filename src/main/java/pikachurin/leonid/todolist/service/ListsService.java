package pikachurin.leonid.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;
import pikachurin.leonid.todolist.entity.*;
import pikachurin.leonid.todolist.exception.*;
import pikachurin.leonid.todolist.model.ListBody;
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
     * Получить списки с сортировкой и пагинацией
     * @param quantity - количество списков на странице
     * @param page - номер страницы
     * @param sort - параметр сортировки
     * @param isInvert - инвертировать порядок списков
     * @return массив списков
     */
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
    public ListEnt createList(ListBody listBody) throws MissingServletRequestParameterException
    {
        if (listBody.getName() == null)
            throw new MissingServletRequestParameterException("name", String.class.getTypeName());
        listBody.validate();

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
    public ListEnt modifyList(UUID id, ListBody listBody) {
        Optional<ListEnt> optionalList = listRepo.findById(id);
        if (optionalList.isEmpty())
            throw new ListNotFoundException(id);

        listBody.validate();
        ListEnt list = optionalList.get();

        if (listBody.getName() != null)
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
