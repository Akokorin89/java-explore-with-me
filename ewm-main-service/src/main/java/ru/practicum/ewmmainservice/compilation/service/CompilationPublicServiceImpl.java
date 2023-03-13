package ru.practicum.ewmmainservice.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.compilation.dto.CompilationDto;
import ru.practicum.ewmmainservice.compilation.mapper.CompilationMapper;
import ru.practicum.ewmmainservice.compilation.model.Compilation;
import ru.practicum.ewmmainservice.compilation.repository.CompilationRepository;
import ru.practicum.ewmmainservice.event.dto.EventShortDto;
import ru.practicum.ewmmainservice.event.mapper.EventMapper;
import ru.practicum.ewmmainservice.event.model.Event;
import ru.practicum.ewmmainservice.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationPublicServiceImpl implements CompilationPublicService {
    private final CompilationRepository repository;
    private final CompilationMapper mapper;
    private final EventMapper eventMapper;

    @Override
    public List<CompilationDto> findAll(Boolean pinned, int from, int size) {
        return repository.findAllByPinned(pinned, PageRequest.of(from / size, size))
                .stream()
                .map(c -> mapper.toDto(c, findCompilationEvents(c)))
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto findById(Long compId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(() -> new NotFoundException("CompilationPublicService: Не найдена подборка событий " +
                        "с id=" + compId));
        return mapper.toDto(compilation, findCompilationEvents(compilation));
    }


    private List<EventShortDto> findCompilationEvents(Compilation compilation) {
        List<Event> eventList = new ArrayList<>(compilation.getEvents());
        return eventList.stream().map(eventMapper::toShortDto).collect(Collectors.toList());
    }
}
