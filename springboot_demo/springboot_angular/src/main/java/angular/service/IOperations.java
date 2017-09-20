package angular.service;

import org.springframework.data.domain.Page;

/**
 * Author: Johnny
 * Date: 2017/9/20
 * Time: 22:18
 */
public interface IOperations<T> {
    Page<T> findPaginated(final int page, final int size);
}
