package info.skytag.apiserver.common.dto.request;

import info.skytag.apiserver.common.util.querydsl.Sortable;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SortCriteria implements Sortable {
    private String field;
    private String direction;  // "ASC" 또는 "DESC"

    public boolean isAscending() {
        return "ASC".equalsIgnoreCase(direction);
    }
}
