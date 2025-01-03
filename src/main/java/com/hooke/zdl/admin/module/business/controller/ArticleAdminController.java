package com.hooke.zdl.admin.module.business.controller;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.module.business.entity.Article;
import com.hooke.zdl.admin.module.business.service.ArticleAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/article")
@Tag(name = "文章", description = "文章")
public class ArticleAdminController {

    @Autowired
    private ArticleAdminService articleAdminService;

    @Operation(summary = "新增文章")
    @PostMapping("/add")
    public ResponseDTO<String> addArticle(@RequestBody Article article) {
        articleAdminService.addArticle(article);
        return ResponseDTO.ok("成功");
    }

    @Operation(summary = "修改文章")
    @PostMapping("/edit")
    public ResponseDTO<String> editArticle(@RequestBody Article article) {
        articleAdminService.editArticle(article);
        return ResponseDTO.ok("成功");
    }

    @Operation(summary = "删除文章")
    @PostMapping("/remove")
    public ResponseDTO<String> removeArticle(@RequestBody Article article) {
        articleAdminService.removeArticle(article);
        return ResponseDTO.ok("成功");
    }

    @Operation(summary = "分页文章")
    @GetMapping("/page")
    public ResponseDTO<PageResult<Article>> pageArticle(Article article, PageParam pageParam) {
        PageResult<Article> page = articleAdminService.pageArticle(article, pageParam);
        return ResponseDTO.ok(page);
    }
}
