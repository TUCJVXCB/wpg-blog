package com.wpg.wpgblog.controller;

import com.github.pagehelper.PageInfo;
import com.wpg.wpgblog.common.ApiResponse;
import com.wpg.wpgblog.common.Status;
import com.wpg.wpgblog.dao.CollectDao;
import com.wpg.wpgblog.dao.LikedDao;
import com.wpg.wpgblog.dao.UserDao;
import com.wpg.wpgblog.entity.Article;
import com.wpg.wpgblog.entity.User;
import com.wpg.wpgblog.form.LikeForm;
import com.wpg.wpgblog.form.ScoreForm;
import com.wpg.wpgblog.service.ArticleService;
import com.wpg.wpgblog.util.JwtUtil;
import com.wpg.wpgblog.vo.ArticleListVO;
import com.wpg.wpgblog.vo.ArticleVOListVO;
import com.wpg.wpgblog.vo.ArticleVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LikedDao likedDao;

    @Autowired
    private CollectDao collectDao;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add")
    @ApiOperation("添加文章")
    public ApiResponse addArticle(@RequestBody Article article, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        User user = userDao.findByEmail(jwtUtil.parseJWT(token).getId());
        article.setUserId(user.getId());
        int result = articleService.addArticle(article);
        if (result > 0) {
            return ApiResponse.ofSuccess();
        }else {
            return ApiResponse.ofStatus(Status.ERROR);
        }
    }

    @PutMapping("/edit")
    @ApiOperation("编辑文章")
    public ApiResponse editArticle(@RequestBody Article article) {
        int result = articleService.updateArticle(article);
        if (result > 0) {
            return ApiResponse.ofSuccess();
        }else {
            return ApiResponse.ofStatus(Status.ERROR);
        }
    }

    @GetMapping("/delete/{id}")
    @ApiOperation("根据Id删除文章")
    public ApiResponse deleteUser(@PathVariable("id") Integer id) {
        int result = articleService.deleteArticle(id);
        if (result > 0) {
            return ApiResponse.ofSuccess();
        }else {
            return ApiResponse.ofStatus(Status.ERROR);
        }
    }

    /**
     * 查询指定User的所有文章
     * @param
     * @return
     */
    @GetMapping("get/user")
    @ApiOperation("根据UserId获得文章")
    public ApiResponse getArticleById(@RequestParam(value = "pageNum", required = true, defaultValue = "1") int pageNum,
                                      @RequestParam(value = "pageSize", required = true, defaultValue = "5") int pageSize,
                                      HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        User user = userDao.findByEmail(jwtUtil.parseJWT(token).getId());

        PageInfo<Article> articlePageInfo = articleService.getMySelfArticle(user.getId(), pageNum, pageSize);

        List<Article> articleList = articlePageInfo.getList();
        int total = (int) articlePageInfo.getTotal();
        List<ArticleVO> articleVOList = new ArrayList<>();

        for (Article article : articleList) {
            ArticleVO articleVO = new ArticleVO();
            BeanUtils.copyProperties(article, articleVO);

            Integer userId = article.getUserId();
            User author = userDao.findById(userId);
            articleVO.setUsername(author.getUsername());
            articleVO.setAvatar(author.getAvatar());

            Boolean likedResult = likedDao.getByUserIdAndArticleId(user.getId(), articleVO.getId());
            Boolean collectedResult = collectDao.getByUserIdAndArticleId(user.getId(), articleVO.getId());

            if (null == likedResult || !likedResult) {
                articleVO.setLiked(false);
            }else {
                articleVO.setLiked(true);
            }

            if (null == collectedResult || !collectedResult) {
                articleVO.setCollected(false);
            }else {
                articleVO.setCollected(true);
            }
            articleVOList.add(articleVO);
        }
        return ApiResponse.ofSuccess(new ArticleVOListVO(articleVOList, total));
    }

    @GetMapping("/list")
    @ApiOperation("获得所有文章")
    public ApiResponse getAllArticle(@RequestParam(value = "pageNum", required = true, defaultValue = "1") int pageNum,
                                     @RequestParam(value = "pageSize", required = true, defaultValue = "5") int pageSize) {
        PageInfo<Article> allArticles = articleService.getAllArticles(pageNum, pageSize);

        return ApiResponse.ofSuccess(allArticles.getList());
    }

    @GetMapping("/list/normal")
    @ApiOperation("获得所有正常文章")
    public ApiResponse getAllNormalArticles(@RequestParam(value = "pageNum", required = true, defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", required = true, defaultValue = "5") int pageSize,
                                            @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                            HttpServletRequest request) {

        PageInfo<Article> articlePageInfo = articleService.getAllNormalArticles(categoryId, pageNum, pageSize);

        List<Article> articleList = articlePageInfo.getList();
        List<ArticleVO> articleVOList = new ArrayList<>();

        String token = request.getHeader("Authorization");
        if (token == null) {
            for (Article article : articleList) {
                ArticleVO articleVO = new ArticleVO();
                BeanUtils.copyProperties(article, articleVO);
                User user = userDao.findById(article.getUserId());
                articleVO.setUsername(user.getUsername());
                articleVO.setAvatar(user.getAvatar());
                articleVOList.add(articleVO);
            }
        }else {
            String email = jwtUtil.parseJWT(token).getId();
            User LoggedUser = userDao.findByEmail(email);

            for (Article article : articleList) {
                ArticleVO articleVO = new ArticleVO();

                BeanUtils.copyProperties(article, articleVO);
                User user = userDao.findById(article.getUserId());
                articleVO.setUsername(user.getUsername());
                articleVO.setAvatar(user.getAvatar());

                //判断是否用户点过赞
                Boolean result = likedDao.getByUserIdAndArticleId(LoggedUser.getId(), article.getId());
                if (null == result || !result) {
                    articleVO.setLiked(false);
                }else {
                    articleVO.setLiked(true);
                }

                articleVOList.add(articleVO);
            }
        }


        ArticleVOListVO articleVOListVO = new ArticleVOListVO(articleVOList, (int) articlePageInfo.getTotal());
        return ApiResponse.ofSuccess(articleVOListVO);
    }

    @GetMapping("get/{id}")
    @ApiOperation("获得文章详情")
    public ApiResponse getArticleByArticleId(@PathVariable("id") Integer id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        ArticleVO articleVO = articleService.getByArticleId(id);

        if (token != null) {
            String email = jwtUtil.parseJWT(token).getId();
            User user = userDao.findByEmail(email);
            Boolean likedResult = likedDao.getByUserIdAndArticleId(user.getId(), articleVO.getId());
            Boolean collectedResult = collectDao.getByUserIdAndArticleId(user.getId(), articleVO.getId());

            if (null == likedResult || !likedResult) {
                articleVO.setLiked(false);
            }else {
                articleVO.setLiked(true);
            }

            if (null == collectedResult || !collectedResult) {
                articleVO.setCollected(false);
            }else {
                articleVO.setCollected(true);
            }
        }

        if (!ObjectUtils.isEmpty(articleVO)) {
            return ApiResponse.ofSuccess(articleVO);
        }else {
            return ApiResponse.ofStatus(Status.ERROR);
        }
    }

    @PostMapping("/like")
    @ApiOperation("点赞")
    public ApiResponse like(@RequestBody LikeForm likeForm, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            return ApiResponse.ofStatus(Status.TOKEN_EXPIRED);
        }
        String email = jwtUtil.parseJWT(token).getId();
        User user = userDao.findByEmail(email);
        Integer id = user.getId();


        Boolean result = likedDao.getByUserIdAndArticleId(id, likeForm.getArticleId());

        if (result == null) {
            likedDao.addLiked(id, likeForm.getArticleId());
        }else if (result) {
            likedDao.updateLiked(0, id, likeForm.getArticleId());
        }else {
            likedDao.updateLiked(1, id, likeForm.getArticleId());
        }
        return ApiResponse.ofSuccess();
    }


    @PostMapping("/collect")
    @ApiOperation("点击收藏")
    public ApiResponse collect(@RequestBody LikeForm likeForm, HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        String email = jwtUtil.parseJWT(token).getId();
        User user = userDao.findByEmail(email);
        Integer id = user.getId();

        Boolean result = collectDao.getByUserIdAndArticleId(id, likeForm.getArticleId());

        if (result == null) {
            collectDao.addLiked(id, likeForm.getArticleId());
        }else if (result) {
            collectDao.updateLiked(0, id, likeForm.getArticleId());
        }else {
            collectDao.updateLiked(1, id, likeForm.getArticleId());
        }
        return ApiResponse.ofSuccess();
    }

    @GetMapping("/get/collected")
    @ApiOperation("获得所有收藏文章")
    public ApiResponse getCollected(@RequestParam(value = "pageNum", required = true, defaultValue = "1") int pageNum,
                                    @RequestParam(value = "pageSize", required = true, defaultValue = "5") int pageSize,
                                    HttpServletRequest request) {
        String email = jwtUtil.parseJWT(request.getHeader("Authorization")).getId();
        User LoggedUser = userDao.findByEmail(email);
        List<Integer> ids = collectDao.getCollectedArticleIdByUserId(LoggedUser.getId());
        PageInfo<Article> articlePageInfo = articleService.getByArticleIds(ids, pageNum, pageSize);
        List<Article> articleList = articlePageInfo.getList();

        List<ArticleVO> articleVOList = new ArrayList<>();

        for (Article article : articleList) {
            ArticleVO articleVO = new ArticleVO();

            BeanUtils.copyProperties(article, articleVO);
            User user = userDao.findById(article.getUserId());
            articleVO.setUsername(user.getUsername());
            articleVO.setAvatar(user.getAvatar());
            articleVO.setCollected(true);

            //判断是否用户点过赞
            Boolean result = likedDao.getByUserIdAndArticleId(LoggedUser.getId(), article.getId());
            if (null == result || !result) {
                articleVO.setLiked(false);
            }else {
                articleVO.setLiked(true);
            }

            articleVOList.add(articleVO);
        }

        ArticleVOListVO articleVOListVO = new ArticleVOListVO(articleVOList, (int) articlePageInfo.getTotal());
        return ApiResponse.ofSuccess(articleVOListVO);
    }

    @GetMapping("/list/noApproved")
    @ApiOperation("获得所有未审核的文章")
    public ApiResponse getNoApproved() {
        return ApiResponse.ofSuccess(articleService.getNoApprovedArticle());
    }

    @PutMapping("/score")
    @ApiOperation("给文章打分")
    public ApiResponse score(@RequestBody ScoreForm scoreForm) {
        int result = articleService.score(scoreForm.getArticleId(), scoreForm.getScore());
        if (result > 0) {
            return ApiResponse.ofSuccess();
        }else {
            return ApiResponse.ofStatus(Status.ERROR);
        }
    }
}
