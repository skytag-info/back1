package info.skytag.apiserver.common.util.querydsl;

/**
 * 정렬 조건에 대한 인터페이스
 */
public interface Sortable {
    String getField();
    String getDirection();
}
