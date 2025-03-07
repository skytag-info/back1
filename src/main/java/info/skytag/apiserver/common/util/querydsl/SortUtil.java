package info.skytag.apiserver.common.util.querydsl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import info.skytag.apiserver.common.dto.request.SortCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SortUtil {
    public static final String ASC = "asc";
    public static final String DESC = "desc";

    public static final String DEFAULT_SORT_ORDER = ASC;

    public static <T extends Comparable<? super T>> void sort(Pageable pageable, JPAQuery<?> query, Class<?> entityClass, String alias) {
        Sort sort = pageable.getSort();
        if (sort != null) {
            PathBuilder<?> pathBuilder = new PathBuilder<>(entityClass, alias);
            sort.forEach(order -> {
                Order direction = order.isAscending() ? Order.ASC : Order.DESC;
                // 필요한 경우 실제 필드의 타입 정보를 구한 후 올바른 클래스를 사용하세요.
                Field field = null;
                try {
                    field = entityClass.getDeclaredField(order.getProperty());
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException("해당 필드가 존재하지 않습니다: " + order.getProperty(), e);
                }
                Class<T> fieldType = (Class<T>) field.getType();

                Expression<T> expression = pathBuilder.get(order.getProperty(), fieldType);
                query.orderBy(new OrderSpecifier<>(direction, expression));
            });
        }
    }

    /**
     * Pageable 객체에서 정렬 조건을 추출하여 OrderSpecifier 배열로 변환
     *
     * @param pageable      페이징 및 정렬 정보를 포함한 Pageable 객체
     * @param entityClass   엔티티 클래스
     * @param entityPath    QueryDSL 엔티티 경로 (예: QEntity.entity)
     * @param defaultField  기본 정렬 필드 (정렬 조건이 없을 경우 사용)
     * @param defaultOrder  기본 정렬 방향 (정렬 조건이 없을 경우 사용)
     * @return OrderSpecifier 배열
     */
    public static <T> OrderSpecifier<?>[] getOrderSpecifiers(
            Pageable pageable,
            Class<T> entityClass,
            EntityPathBase<?> entityPath,
            String defaultField,
            Order defaultOrder) {

        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        // 경로 빌더 생성 (동적 필드 접근용)
        PathBuilder<T> pathBuilder = new PathBuilder<>(entityClass, entityPath.getMetadata().getName());

        if (pageable == null || pageable.getSort().isEmpty()) {
            // 기본 정렬 조건 추가
            orderSpecifiers.add(getOrderSpecifier(defaultOrder, pathBuilder, defaultField));
        } else {
            // Pageable의 Sort를 OrderSpecifier로 변환
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.isAscending() ? Order.ASC : Order.DESC;
                orderSpecifiers.add(getOrderSpecifier(direction, pathBuilder, order.getProperty()));
            }
        }

        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }

    /**
     * DTO에서 정렬 정보를 추출하여 OrderSpecifier 배열로 변환
     *
     * @param sortCriterias   정렬 조건 목록
     * @param entityClass     엔티티 클래스
     * @param entityPath      QueryDSL 엔티티 경로 (예: QEntity.entity)
     * @param defaultField    기본 정렬 필드 (정렬 조건이 없을 경우 사용)
     * @param defaultOrder    기본 정렬 방향 (정렬 조건이 없을 경우 사용)
     * @return OrderSpecifier 배열
     */
    public static <T, S extends SortCriteria> OrderSpecifier<?>[] getOrderSpecifiers(
            List<S> sortCriterias,
            Class<T> entityClass,
            EntityPathBase<?> entityPath,
            String defaultField,
            Order defaultOrder) {

        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        // 경로 빌더 생성 (동적 필드 접근용)
        PathBuilder<T> pathBuilder = new PathBuilder<>(entityClass, entityPath.getMetadata().getName());

        if (sortCriterias == null || sortCriterias.isEmpty()) {
            // 기본 정렬 조건 추가
            orderSpecifiers.add(getOrderSpecifier(defaultOrder, pathBuilder, defaultField));
        } else {
            // DTO의 정렬 조건을 OrderSpecifier로 변환
            for (S sortCriteria : sortCriterias) {
                Order direction = "ASC".equalsIgnoreCase(sortCriteria.getDirection()) ? Order.ASC : Order.DESC;
                orderSpecifiers.add(getOrderSpecifier(direction, pathBuilder, sortCriteria.getField()));
            }
        }

        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }

    /**
     * 단일 정렬 조건에 대한 OrderSpecifier 생성
     */
    private static <T> OrderSpecifier<?> getOrderSpecifier(Order order, PathBuilder<T> pathBuilder, String property) {
        // 명시적인 타입 파라미터를 사용하여 OrderSpecifier 생성
        return new OrderSpecifier(order, pathBuilder.get(property));
    }

}
