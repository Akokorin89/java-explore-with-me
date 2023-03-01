package com.example.ewmmainservice.compilation.service;

import com.example.ewmmainservice.compilation.dto.CompilationDto;
import com.example.ewmmainservice.compilation.dto.NewCompilationDto;
import com.example.ewmmainservice.compilation.mapper.CompilationMapper;
import com.example.ewmmainservice.compilation.model.Compilation;
import com.example.ewmmainservice.compilation.repository.CompilationRepository;
import com.example.ewmmainservice.event.dto.EventShortDto;
import com.example.ewmmainservice.event.mapper.EventMapper;
import com.example.ewmmainservice.event.model.Event;
import com.example.ewmmainservice.event.repository.EventRepository;
import com.example.ewmmainservice.exception.ForbiddenException;
import com.example.ewmmainservice.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationAdminServiceImpl implements CompilationAdminService {
    private final CompilationRepository repository;
    private final EventRepository eventRepository;
    private final CompilationMapper mapper;
    private final EventMapper eventMapper;

    @Transactional
    @Override
    public CompilationDto save(NewCompilationDto newCompilationDto) {
        Compilation compilation = mapper.toModel(newCompilationDto);
        Compilation saved = repository.save(compilation);
        log.info("CompilationAdminService: Сохранена подборка событий с id={}.", saved.getId());
        return mapper.toDto(saved, findCompilationEvents(saved));
    }

    @Transactional
    @Override
    public void delete(Long compId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(() ->
                        new NotFoundException("CompilationAdminService: Не найдена подборка событий с id=" +
                                compId));
        repository.delete(compilation);
        log.info("CompilationAdminService: Удалена информация о подборке событий №={}.", compId);
    }

    @Transactional
    @Override
    public void saveOrDeleteEventInCompilation(Long compId, Long eventId, boolean isDeleting) {
        Compilation compilation = findCompilationById(compId);
        Event event = findEventById(eventId);
        List<Event> events = findCompilationEvents(compilation)
                .stream().map(eventMapper::toModelFromShortDto).collect(Collectors.toList());
        if (isDeleting) {
            if (events.contains(event)) {
                compilation.getEvents().remove(event);
                log.info("CompilationAdminService: Удалено событие с id={} из подборки событий с id={}.",
                        eventId, compId);
            }
        } else {
            if (!events.contains(event)) {
                compilation.getEvents().add(event);
                log.info("CompilationAdminService: Добавлено событие с id={} в подборку событий с id={}.",
                        eventId, compId);
            }
        }
        repository.save(compilation);
    }

    @Transactional
    @Override
    public void changeCompilationPin(Long compId, boolean isPin) {
        Compilation compilation = findCompilationById(compId);
        boolean pinned = compilation.isPinned();
        if (isPin) {
            if (pinned) {
                repository.setCompilationPinned(false, compId);
            } else {
                throw new ForbiddenException(String.format("CompilationAdminService: Уже откреплена подборка с id=" +
                        compId));
            }
        } else {
            if (!pinned) {
                repository.setCompilationPinned(true, compId);
            } else {
                throw new ForbiddenException(String.format("CompilationAdminService: Уже закреплена подборка с id=" +
                        compId));
            }
        }
    }

    private Event findEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new NotFoundException("CompilationService: Не найдено событие с id=" + eventId));
    }

    private List<EventShortDto> findCompilationEvents(Compilation compilation) {
        return compilation.getEvents()
                .stream()
                .map(eventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    private Compilation findCompilationById(Long compilationId) {
        return repository.findById(compilationId)
                .orElseThrow(() ->
                        new NotFoundException("CompilationService: Не найдена подборка событий с id=" + compilationId));
    }
}
