package com.hodol.api.service;


import com.hodol.api.Exception.PostNotFound;
import com.hodol.api.domain.Post;
import com.hodol.api.domain.PostEditor;
import com.hodol.api.repository.PostRepository;
import com.hodol.api.request.PostCreate;
import com.hodol.api.request.PostEdit;
import com.hodol.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate){
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }

    // 엔티티 조회 하는 메서드
    public PostResponse get(Long id) {
        // 조회 해오기
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new); // 예외 던지기
        // 변환
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();


    }

    public List<PostResponse> getAll(Pageable pageable) {
        // 이건 수동이라서 필요없음ㅠ (페이지 int로 받을때ㅠ)
        // Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC,"id"));


        List<Post> posts = postRepository.findAll(pageable).getContent();
        List<PostResponse> responses = posts.stream().map(PostResponse::fromPost).toList();
        return responses;
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit){
        Post post = postRepository.findById(id).orElseThrow(PostNotFound::new);

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        // 이렇게 하면 변경하고 싶은 부분만 변경할 수 있나?
        PostEditor postEditor = editorBuilder.title(postEdit.getTitle())
                .content(postEdit.getContent()).build();

        post.edit(postEditor);

    }

    public void deletePost(Long id) {

        Post post = postRepository.findById(id).orElseThrow(PostNotFound::new);

        postRepository.delete(post);

    }
}


