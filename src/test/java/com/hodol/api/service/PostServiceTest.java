package com.hodol.api.service;

import com.hodol.api.domain.Post;
import com.hodol.api.repository.PostRepository;
import com.hodol.api.request.PostCreate;
import com.hodol.api.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.data.domain.Sort.Direction.DESC;

@SpringBootTest
class PostServiceTest {


    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }


    // 게시글 생성 메서드
    private void createPost(){

        /*for(int i=0; i<30; i++){

            Post post = Post.builder()
                    .title("우히히" + i)
                    .content("재밌당" + i)
                    .build();

            postRepository.save(post);
        }*/

        List <Post> posts = IntStream.range(0, 30)
                .mapToObj(i -> {
                     return Post.builder()
                            .title("우히히" + i)
                            .content("재밌당" + i)
                            .build();
                })
                .collect(Collectors.toList());

        postRepository.saveAll(posts);

        /*postRepository.saveAll(List.of(
            Post.builder()
                    .title("우히히" )
                    .content("재밌당")
                    .build(),

                Post.builder()
                        .title("우히히" )
                        .content("재밌당")
                        .build()
        ));*/


    }



    @Test
    @DisplayName("글 작성")
    void test1(){
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // when
        postService.write(postCreate);

        // then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2(){
        //given
        Post requestPost = Post.builder()
                .title("123456789012345")
                .content("bar")
                .build();
        postRepository.save(requestPost);

        // 클라이언트 요구사항
            // JSON 응답에서 title값 길이를 최대 10글자로 해주세요.
            // 이런 처리은 클라이언트에서 하는게 좋다. (근데도 서버측에 요구한다면?)

        //when
        PostResponse response = postService.get(requestPost.getId());

        //then
        assertNotNull(response);
        assertEquals("foo", response.getTitle());
        assertEquals("bar", response.getContent());
    }

    /*@Test
    @DisplayName("글 전체 조회")
    void test3(){

        createPost();

        List<PostResponse> posts = postService.getAll();
        assertEquals(10, posts.size());

    }*/

    @Test
    @DisplayName("1페이지 글 조회")
    void test3(){
       createPost();

        Pageable pageable = PageRequest.of(0, 5, Sort.by(DESC, "id"));

        List<PostResponse> posts = postService.getAll(pageable);

        assertEquals(5, posts.size());
        assertEquals("우히히29", posts.get(0).getTitle());
        assertEquals("우히히25", posts.get(4).getTitle());

    }
}
























