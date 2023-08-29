package com.blog.controller;

import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create blog post rest api
    //http://localhost:8080/api/posts
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto,
                                              BindingResult result) {

        if( result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        PostDto dto = postService.createPost(postDto);
       return new ResponseEntity<>(dto, HttpStatus.CREATED);
   }

    // get all posts rest api
    //http://localhost:8080/api/posts?pageNo=1&pageSize=5&sortBy=title&sortDir=desc
   @GetMapping
      public PostResponse getAllPosts(
              @RequestParam(value = "pageNo" ,defaultValue = "0",required = false) int pageNo,
              @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
              @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
              @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

   ){
        PostResponse postResponse = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return postResponse;
      }

    // get post by id
    //http://localhost:8080/api/posts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostByID(@PathVariable("id") Long id){
     PostDto dto = postService.getPostById(id);
     return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    // update post by id rest api
    //http://localhost:8080/api/posts/{id}
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(
            @RequestBody PostDto postDto,
            @PathVariable("id") long id
    ){
      PostDto dto = postService.updatePost(postDto, id);

      return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // delete post rest api
    //http://localhost:8080/api/posts/{id}
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post is deleted", HttpStatus.OK);
    }
}