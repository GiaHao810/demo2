package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.example.demo.util.ImageUtil.*;

@Controller
@RequestMapping("/product")
public class ProductController {

    private Logger log = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public String showProductList(Model model){
        List<Product> listProduct = productService.getListAllProduct();
        model.addAttribute("listProduct", listProduct);
        log.info("Direct to product Manager Page");
        return "product/ProductManager";
    }

    @GetMapping("/add")
    public String getAddPage(Model model){
        model.addAttribute("product", new Product());
        log.info("Direct to Add product Page");
        return "product/AddProductPage";
    }

    @PostMapping("/add")
    public String saveProduct(@ModelAttribute Product product,@RequestParam("image") MultipartFile image) throws IOException {
        String fileName = saveImage(image);

        product.setImageURL(fileName);

        productService.save(product);

        log.info("product Saved");
        return "redirect:/product/all";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("productID") String id){
        Optional<Product> existed = productService.findbyId(id);

        if(existed.isEmpty()){
            log.warn("product Non-Exist");
        } else {
            log.info("product Deleted");
            deleteImage(existed.get().getImageURL());
            productService.deletProductbyId(id);

        }
        return "redirect:/product/all";
    }

    @GetMapping("/update/{productid}")
    public String getUpdatePage(@PathVariable("productid") String productid, Model model){
        model.addAttribute("product", productService.findById(productid));
        log.info("Direct to Update product Page");
        return "product/UpdateProductPage";
    }

    @PostMapping("/update/{productid}")
    public String updateProduct(@ModelAttribute Product product, @PathVariable String productid, @RequestParam("image") MultipartFile image) throws IOException{
        Optional<Product> existed = productService.findbyId(productid);
        if(existed.isPresent()){
            String fileName = saveImage(image);

            existed.get().setName(product.getName());
            existed.get().setPrice(product.getPrice());
            existed.get().setCategory(product.getCategory());
            deleteImage(existed.get().getImageURL());
            existed.get().setImageURL(fileName);

            productService.save(existed.get());
            log.info("Update product Successful");
        } else {
            log.info("Update product Failed");
        }
        return "redirect:/product/all";
    }

    @GetMapping("")
    public String getHomePage(Model model){
        model.addAttribute("products", productService.getListAllProduct());
        log.info("Direct to product Home Page");
        return "main/HomePage";
    }

    @PostMapping("/buy")
    public String buyProductHandler(@RequestParam("productID") String id){
        Optional<Product> existed = productService.findbyId(id);

        return "redirect:/product";
    }
}
