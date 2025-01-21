package com.hooke.zdl.admin.module.business.service;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.ArticleMapper;
import com.hooke.zdl.admin.module.business.entity.Article;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import static com.hooke.zdl.admin.module.business.entity.table.ArticleTableDef.ARTICLE;

@Service
public class ArticleAdminService extends ServiceImpl<ArticleMapper, Article> {
    public void addArticle(Article article) {
        save(article);
    }

    public void editArticle(Article article) {
        updateById(article);
    }

    public void removeArticle(Article article) {
        removeById(article.getId());
    }

    public PageResult<Article> pageArticle(Article article, PageParam pageParam) {
        Page<Article> page = SmartPageUtil.convert2PageQuery(pageParam);
        Page<Article> walletPage = QueryChain.of(Article.class)
                .where(ARTICLE.TITLE.like(article.getTitle()))
                .page(page);
        return SmartPageUtil.convert2PageResult(page, walletPage.getRecords(), Article.class);
    }
}
