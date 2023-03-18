package ru.practicum.ewmmainservice.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.compilation.dto.CompilationCreateDto;
import ru.practicum.ewmmainservice.compilation.dto.CompilationDto;
import ru.practicum.ewmmainservice.compilation.dto.CompilationUpdateDto;
import ru.practicum.ewmmainservice.compilation.model.Compilation;
import ru.practicum.ewmmainservice.compilation.repository.CompilationRepository;
import ru.practicum.ewmmainservice.event.model.Event;
import ru.practicum.ewmmainservice.event.repository.EventRepository;
import ru.practicum.ewmmainservice.exception.NotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.practicum.ewmmainservice.compilation.mapper.CompilationMapper.*;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public CompilationDto saveCompilation(CompilationCreateDto compilationCreateDto) {
        Compilation compilation = toCompilation(compilationCreateDto);
        Set<Event> events = eventRepository.findEventsByIds(compilationCreateDto.getEvents());
        compilation.setEvents(events);
        log.info("CompilationService: Сохранена подборка событий с id={}.", compilation.getId());
        return toCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    public CompilationDto updateCompilation(Long compId, CompilationUpdateDto compilationUpdateDto) {
        Compilation compilationToUpdate = getCompilation(compId);

        if (compilationToUpdate.getEvents() != null) {
            Set<Event> events = new HashSet<>();
            if (compilationUpdateDto.getEvents().size() != 0) {
                events = eventRepository.findEventsByIds(compilationUpdateDto.getEvents());
            }
            compilationToUpdate.setEvents(events);
        }
        compilationToUpdate.setPinned(compilationUpdateDto.getPinned());
        compilationToUpdate.setTitle(compilationUpdateDto.getTitle());

        log.info("CompilationService: Обновлена подборка событий с id={}.", compilationToUpdate.getId());
        return toCompilationDto(compilationRepository.save(compilationToUpdate));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompilationDto> getAllCompilations(Boolean pinned, Integer from, Integer size) {
        if (pinned == null) {
            return toCompilationsDto(compilationRepository.findAll(PageRequest.of(from / size, size)));
        }
        log.info("CompilationService: Получен список всех подборок.");
        return toCompilationsDto(compilationRepository.findAllByPinned(true, PageRequest.of(from / size, size)));
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationDto getCompilationById(Long compId) {
        return toCompilationDto(getCompilation(compId));
    }

    @Override
    public void deleteCompilation(Long compId) {
        getCompilation(compId);
        log.info("CompilationService: Удалена информация о подборке событий №={}.", compId);
        compilationRepository.deleteById(compId);
    }

    private Compilation getCompilation(Long compId) {
        return compilationRepository.findById(compId).orElseThrow(() ->
                new NotFoundException("CompilationService: Неверный ID подборки."));
    }
}
