package mockproject.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
/*handle config request of FE for user-management:
 *
 *
 *
 */
public class PageConfig {
    private String keyword;
    private int page;
    private int size;
    private String[] sort;


    public Pageable getPageableConfig() {
        String[] sort = getSort();

        String sortField = sort[0];
        String sortDirection = sort[1];

        Sort.Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort.Order order = new Sort.Order(direction, sortField);

        return PageRequest.of(getPage(), getSize(), Sort.by(order));
    }
}
