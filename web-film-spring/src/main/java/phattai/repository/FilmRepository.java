package phattai.repository;

import org.springframework.data.repository.CrudRepository;

import phattai.model.Film;

public interface FilmRepository extends CrudRepository<Film, Long> {

}
