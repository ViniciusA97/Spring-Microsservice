package learning.microservice.core.repository.note;

import learning.microservice.core.model.note.Note;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<Note,Long> {
     Note getById(long id);
}
