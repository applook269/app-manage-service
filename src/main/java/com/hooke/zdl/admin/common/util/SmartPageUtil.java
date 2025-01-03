package com.hooke.zdl.admin.common.util;


import com.mybatisflex.core.paginate.Page;
import com.google.common.collect.Lists;
import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 */
@Slf4j
public class SmartPageUtil {

    /**
     * 转换为查询参数
     */
    public static <T> Page<T> convert2PageQuery(PageParam pageParam) {
        Page<T> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());

//        if (pageParam.getSearchCount() != null) {
//            page.setSearchCount(pageParam.getSearchCount());
//        }
//
//        List<PageParam.SortItem> sortItemList = pageParam.getSortItemList();
//        if (CollectionUtils.isEmpty(sortItemList)) {
//            return page;
//        }
//
//        // 设置排序字段并检测是否含有sql注入
//        List<OrderItem> orderItemList = new ArrayList<>();
//        for (PageParam.SortItem sortItem : sortItemList) {
//
//            if (SmartStringUtil.isEmpty(sortItem.getColumn())) {
//                continue;
//            }
//
//            if (SqlInjectionUtils.check(sortItem.getColumn())) {
//                log.error("《存在SQL注入：》 : {}", sortItem.getColumn());
//                throw new BusinessException("存在SQL注入风险，请联系技术工作人员！");
//            }
//
//            OrderItem orderItem = new OrderItem();
//            orderItem.setColumn(sortItem.getColumn());
//            orderItem.setAsc(sortItem.getIsAsc());
//            orderItemList.add(orderItem);
//        }
//        page.setOrders(orderItemList);
        return page;
    }

    /**
     * 转换为 PageResult 对象
     */
    public static <T, E> PageResult<T> convert2PageResult(Page<?> page, List<E> sourceList, Class<T> targetClazz) {
        return convert2PageResult(page, SmartBeanUtil.copyList(sourceList, targetClazz));
    }

    /**
     * 转换为 PageResult 对象
     */
    public static <E> PageResult<E> convert2PageResult(Page<?> page, List<E> sourceList) {
        PageResult<E> pageResult = new PageResult<>();
        pageResult.setPageNum(page.getPageNumber());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setTotal(page.getTotalRow());
        pageResult.setPages(page.getTotalPage());
        pageResult.setList(sourceList);
        pageResult.setEmptyFlag(CollectionUtils.isEmpty(sourceList));
        return pageResult;
    }

    /**
     * 转换分页结果对象
     */
    public static <E, T> PageResult<T> convert2PageResult(PageResult<E> pageResult, Class<T> targetClazz) {
        PageResult<T> newPageResult = new PageResult<>();
        newPageResult.setPageNum(pageResult.getPageNum());
        newPageResult.setPageSize(pageResult.getPageSize());
        newPageResult.setTotal(pageResult.getTotal());
        newPageResult.setPages(pageResult.getPages());
        newPageResult.setEmptyFlag(pageResult.getEmptyFlag());
        newPageResult.setList(SmartBeanUtil.copyList(pageResult.getList(), targetClazz));
        return newPageResult;
    }

    public static <T> PageResult<T> subListPage(Integer pageNum, Integer pageSize, List<T> list) {
        PageResult<T> pageRet = new PageResult<T>();
        //总条数
        int count = list.size();
        int pages = count % pageSize == 0 ? count / pageSize : (count / pageSize + 1);
        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = Math.min(pageNum * pageSize, count);

        if (pageNum > pages) {
            pageRet.setList(Lists.newLinkedList());
            pageRet.setPageNum(pageNum.longValue());
            pageRet.setPages((long) pages);
            pageRet.setTotal((long) count);
            return pageRet;
        }
        List<T> pageList = list.subList(fromIndex, toIndex);
        pageRet.setList(pageList);
        pageRet.setPageNum(pageNum.longValue());
        pageRet.setPages((long) pages);
        pageRet.setTotal((long) count);
        return pageRet;
    }
}
