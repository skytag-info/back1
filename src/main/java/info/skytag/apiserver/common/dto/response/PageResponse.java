package info.skytag.apiserver.common.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PageResponse<T> {
    private List<T> content;
    @Builder.Default
    private int totalPages=0;
    @Builder.Default
    private long totalElements=0;
    @Builder.Default
    private int currentPage=0;
    @Builder.Default
    private int pageSize=0;

    public boolean hasNext(){
        return currentPage<totalPages;
    }
}