package io.github.mehranmirkhan.common.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T extends Serializable> implements Serializable {
  @Builder.Default
  protected List<T> content = Collections.emptyList();

  protected int pageNumber; // The current page number (zero-based index).
  protected int pageSize; // The number of elements per page.
  protected int pageElements; // The number of elements in the current page.
  protected int totalPages; // The total number of pages.
  protected long totalElements; // The total number of elements.

  protected boolean isEmpty; // Indicates if the page is empty.
  protected boolean isFirst; // Indicates if this is the first page.
  protected boolean isLast; // Indicates if this is the last page.
  protected boolean hasNext; // Indicates if there is a next page.
  protected boolean hasPrevious; // Indicates if there is a previous page.

  public static <T extends Serializable> PageDto<T> empty() {
    return PageDto.<T>builder().content(Collections.emptyList()).pageNumber(0).pageSize(0)
        .pageElements(0).totalPages(0).totalElements(0).isEmpty(true).isFirst(true).isLast(true)
        .hasNext(false).hasPrevious(false).build();
  }

  public static <T extends Serializable> PageDto<T> of(Page<T> page) {
    return PageDto.<T>builder().content(page.getContent()).pageNumber(page.getNumber())
        .pageSize(page.getSize()).pageElements(page.getNumberOfElements())
        .totalPages(page.getTotalPages()).totalElements(page.getTotalElements())
        .isEmpty(page.isEmpty()).isFirst(page.isFirst()).isLast(page.isLast())
        .hasNext(page.hasNext()).hasPrevious(page.hasPrevious()).build();
  }
}
