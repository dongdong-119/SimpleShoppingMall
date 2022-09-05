package jpashop1_practice.practice.controller;

import jpashop1_practice.practice.controller.form.AlbumForm;
import jpashop1_practice.practice.controller.form.BookForm;
import jpashop1_practice.practice.controller.form.ItemForm;
import jpashop1_practice.practice.controller.form.MovieForm;
import jpashop1_practice.practice.domain.Category;
import jpashop1_practice.practice.domain.item.Album;
import jpashop1_practice.practice.domain.item.Book;
import jpashop1_practice.practice.domain.item.Item;
import jpashop1_practice.practice.domain.item.Movie;
import jpashop1_practice.practice.service.CategoryService;
import jpashop1_practice.practice.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final CategoryService categoryService;


    @GetMapping("/items/new")
    public String selectType(){

        return "items/selectItemType";
    }


    /**
     * Item 생성 폼으로 이동
     */
    @GetMapping("/items/book")
    public String createBook(Model model){

        //form
        model.addAttribute("BookForm", new BookForm());

        //Category option
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        //createBookForm;
        return "items/createBookForm";
    }

    @GetMapping("/items/album")
    public String createAlbum(Model model){

        //form
        model.addAttribute("AlbumForm", new AlbumForm());

        //Category option
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        return "items/createAlbumForm";
    }

    @GetMapping("/items/movie")
    public String createMovie(Model model){

        model.addAttribute("MovieForm", new MovieForm());

        //Category option
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        return "items/createMovieForm";
    }


    /**
     * Item 생성 폼
     */
    @PostMapping("/items/createBook")
    public String create(@ModelAttribute("BookForm") BookForm form,
                         @RequestParam("category") Long categoryId){

        Category category = categoryService.findOne(categoryId);

        //DTO 객체, 생성 메소드 이용해보기
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        //카테고리 추가
        category.addItem(book);

        itemService.saveItem(book);
        return "redirect:/items";
    }


    @PostMapping("/items/createAlbum")
    public String createAlbum(@ModelAttribute("AlbumForm") AlbumForm form,
                              @RequestParam("category") Long categoryId){

        Category category = categoryService.findOne(categoryId);

        Album album = new Album();
        album.setName(form.getName());
        album.setPrice(form.getPrice());
        album.setStockQuantity(form.getStockQuantity());
        album.setArtist(form.getArtist());
        album.setEtc(form.getEtc());


        //카테고리 추가
        category.addItem(album);

        itemService.saveItem(album);
        return "redirect:/items";
    }

    @PostMapping("/items/createMovie")
    public String createMovie(@ModelAttribute("MovieForm") MovieForm form,
                              @RequestParam("category") Long categoryId){

        Category category = categoryService.findOne(categoryId);


        Movie movie = new Movie();
        movie.setName(form.getName());
        movie.setPrice(form.getPrice());
        movie.setStockQuantity(form.getStockQuantity());
        movie.setDirector(form.getDirector());
        movie.setActor((form.getActor()));

        //카테고리 추가
        category.addItem(movie);

        itemService.saveItem(movie);
        return "redirect:/items";
    }



    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);

        return "items/itemList";
    }

    // 수정 폼으로 이동(단, 상품의 타입을 구분해서 각 상품에 맞는 수정 페이지로 이동)
    // 엔티티 -> form 메소드 필요(각 Item type 별로)
    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){

        Item item = itemService.findOne(itemId);


        if(item instanceof Book){


            Book book = (Book)item;

            BookForm form = new BookForm();
            form.setId(book.getId());
            form.setPrice(book.getPrice());
            form.setName(book.getName());
            form.setStockQuantity(book.getStockQuantity());
            form.setAuthor(book.getAuthor());
            form.setIsbn(book.getIsbn());


            model.addAttribute("form", form);

            // Book 수정 페이지로 이동
            return "items/updateBookForm";


        } else if(item instanceof Album){

            Album album = (Album)item;

            AlbumForm form = new AlbumForm();
            form.setId(album.getId());
            form.setName(album.getName());
            form.setPrice(album.getPrice());
            form.setStockQuantity(album.getStockQuantity());
            form.setArtist(album.getArtist());
            form.setEtc(album.getEtc());

            model.addAttribute("form", form);

            // Album 수정 페이지로 이동
            return "items/updateAlbumForm";

        } else if(item instanceof Movie){

            Movie movie = (Movie)item;

            MovieForm form = new MovieForm();
            form.setId(movie.getId());
            form.setName(movie.getName());
            form.setPrice(movie.getPrice());
            form.setStockQuantity(movie.getStockQuantity());
            form.setDirector(movie.getDirector());
            form.setActor(movie.getActor());

            model.addAttribute("form", form);

            // Movie 수정 페이지로 이동
            return "items/updateMovieForm";
        }
        return "asdf";
    }



    // Update 메소드 (Book, Album, Movie)
    // 물품별로 Post 구현 --> 리팩터링 필요
    @PostMapping("items/updateBook")
    public String updateBook(@ModelAttribute("form") BookForm form){


        Book book = new Book();

        BookForm bookForm = (BookForm)form;
        log.info(bookForm.getName());

        book.setId(bookForm.getId());
        book.setName(bookForm.getName());
        book.setPrice(bookForm.getPrice());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setAuthor(bookForm.getAuthor());
        book.setIsbn(bookForm.getIsbn());

        itemService.saveItem(book);

        return "redirect:/items";

    }

    @PostMapping("items/updateAlbum")
    public String updateAlbum(@ModelAttribute("form") AlbumForm form){

        Album album = new Album();

        AlbumForm albumForm = (AlbumForm)form;

        album.setId(albumForm.getId());
        album.setName(albumForm.getName());
        album.setPrice(albumForm.getPrice());
        album.setStockQuantity(albumForm.getStockQuantity());
        album.setArtist(albumForm.getArtist());
        album.setEtc(albumForm.getEtc());

        itemService.saveItem(album);

        return "redirect:/items";

    }

    @PostMapping("items/updateMovie")
    public String updateMovie(@ModelAttribute("form") MovieForm form){

        Movie movie = new Movie();

        MovieForm movieForm = (MovieForm)form;

        movie.setId(movieForm.getId());
        movie.setName(movieForm.getName());
        movie.setPrice(movieForm.getPrice());
        movie.setStockQuantity(movieForm.getStockQuantity());
        movie.setDirector(movieForm.getDirector());
        movie.setActor(movieForm.getActor());

        itemService.saveItem(movie);

        return "redirect:/items";
    }


    // Item 삭제 컨트롤러(고민 필요)
    //@GetMapping("items/{itemId}/delete")
    //public String deleteItem(@PathVariable("itemId") Long itemId){
    //
    //    Item item = itemService.findOne(itemId);
    //    itemService.deleteItem(item);
    //
    //    return "redirect:/items";
    //}




}
