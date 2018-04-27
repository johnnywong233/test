package demo1.entity;

import org.springframework.data.repository.CrudRepository;

import javax.naming.Name;

/**
 * Created by Johnny on 2018/2/3.
 */
public interface PersonRepository extends CrudRepository<Person, Name> {
}