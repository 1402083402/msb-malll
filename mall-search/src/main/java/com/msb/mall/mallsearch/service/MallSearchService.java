package com.msb.mall.mallsearch.service;

import com.msb.mall.mallsearch.vo.SearchParam;
import com.msb.mall.mallsearch.vo.SearchResult;

/**
 * @author Jason
 * @date 2022/7/28
 * hello ashen one
 */
public interface MallSearchService {
    SearchResult search(SearchParam param);
}
