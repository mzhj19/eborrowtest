package com.mzhj19.eborrow.controller;

import com.mzhj19.eborrow.constant.ResponseMessageConstants;
import com.mzhj19.eborrow.constant.WebApiUrlConstants;
import com.mzhj19.eborrow.dto.ResponseErrorData;
import com.mzhj19.eborrow.dto.ResponseSuccessData;
import com.mzhj19.eborrow.dto.ProductDto;
import com.mzhj19.eborrow.model.Product;
import com.mzhj19.eborrow.model.ProductCategory;
import com.mzhj19.eborrow.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping(WebApiUrlConstants.API_URI_PREFIX + "/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> register(@Valid @RequestBody ProductDto productDto, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
            return new ResponseEntity<>(new ResponseErrorData<>(HttpStatus.BAD_REQUEST.value(), errors), HttpStatus.BAD_REQUEST);
        }
        Product savedProduct = productService.save(productDto);
        if(savedProduct == null)  {
            return new ResponseEntity<>(new ResponseErrorData<>(HttpStatus.NOT_FOUND.value(), ResponseMessageConstants.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ResponseSuccessData<>(ResponseMessageConstants.SAVE_SUCCESS, savedProduct), HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllProduct", method = RequestMethod.GET)
    public ResponseEntity<?> getAllProduct(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size/*,
                                           @RequestParam(value = "sort", defaultValue = "id") String sort*/) throws Exception {

        Pageable pageable = PageRequest.of(page, size/*, Sort.by(sort)*/);
        Page<Product> products = productService.getAllProduct(pageable);

        if (products.isEmpty()) {
            return new ResponseEntity<>(new ResponseErrorData<>(HttpStatus.NOT_FOUND.value(), ResponseMessageConstants.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseSuccessData<>(ResponseMessageConstants.DATA_FOUND, products), HttpStatus.OK);
    }


    @RequestMapping(value = "/getProductByCategoryId", method = RequestMethod.GET)
    public ResponseEntity<?> getProductByCategoryId(@RequestParam("id") String id,
                                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                                    @RequestParam(value = "size", defaultValue = "10") int size
                                                    /*@RequestParam(value = "sort", defaultValue = "id") String sort*/) throws Exception {

        Pageable pageable = PageRequest.of(page, size /*Sort.by(sort)*/);
        Page<Product> products = productService.getProductByCategoryId(Long.valueOf(id), pageable);

        if (products.isEmpty()) {
            return new ResponseEntity<>(new ResponseErrorData<>(HttpStatus.NOT_FOUND.value(), ResponseMessageConstants.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseSuccessData<>(ResponseMessageConstants.DATA_FOUND, products), HttpStatus.OK);
    }



    @RequestMapping(value = "/getProductById", method = RequestMethod.GET)
    public ResponseEntity<?> getProductById(@RequestParam("id") String id) throws Exception {
        Product product = productService.getProductById(Long.valueOf(id));
        if (product == null) {
            return new ResponseEntity<>(new ResponseErrorData<>(HttpStatus.NOT_FOUND.value(), ResponseMessageConstants.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseSuccessData<>(ResponseMessageConstants.DATA_FOUND, product), HttpStatus.OK);
    }


    @RequestMapping(value = "/getProductByOwner", method = RequestMethod.GET)
    public ResponseEntity<?> getProductByOwner() throws Exception {
        String email = "mzhj19@gmail.com";
        List<Product> product = productService.findProductByOwner(email);

        return new ResponseEntity<>(new ResponseSuccessData<>(ResponseMessageConstants.SAVE_SUCCESS, product), HttpStatus.OK);
    }


    @RequestMapping(value = "/update" + WebApiUrlConstants.PATH_VAR_ID, method = RequestMethod.PUT)
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
            return new ResponseEntity<>(new ResponseErrorData<>(HttpStatus.BAD_REQUEST.value(), errors), HttpStatus.BAD_REQUEST);
        }

        Product updatedProduct = productService.updateProduct(id, productDto);

        if (updatedProduct == null) {
            return new ResponseEntity<>(new ResponseErrorData<>(HttpStatus.NOT_FOUND.value(), ResponseMessageConstants.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ResponseSuccessData<>(ResponseMessageConstants.UPDATE_SUCCESS, updatedProduct), HttpStatus.OK);
    }


    @RequestMapping(value = "/delete" + WebApiUrlConstants.PATH_VAR_ID, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) throws Exception {

        Product existingProduct = (Product) productService.getProductById(id);
        if (existingProduct == null) {
            return new ResponseEntity<>(new ResponseErrorData<>(HttpStatus.NOT_FOUND.value(), ResponseMessageConstants.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        productService.deleteProduct(id);

        return new ResponseEntity<>(new ResponseSuccessData<>(ResponseMessageConstants.DELETE_SUCCESS), HttpStatus.OK);
    }


/*    @RequestMapping(value = "/getAllProductByCategoryId", method = RequestMethod.GET)
    public ResponseEntity<?> getAllProductByCategoryId(@RequestParam("id") String id,
                                                       @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                       @RequestParam(value = "sort", defaultValue = "id") String sort) throws Exception {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sort));
        Page<ProductCategory> productCategory = productService.getAllProductByCategoryId(Long.valueOf(id), pageable);

        if (productCategory.isEmpty()) {
            return new ResponseEntity<>(new ResponseErrorData<>(HttpStatus.NOT_FOUND.value(), ResponseMessageConstants.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseSuccessData<>(ResponseMessageConstants.DATA_FOUND, productCategory), HttpStatus.OK);
    }*/


    @RequestMapping(value = "/getProductCategory", method = RequestMethod.GET)
    public ResponseEntity<?> getProductCategory() throws Exception {
        List<ProductCategory> productCategory = productService.getProductCategory();

        if (productCategory.isEmpty()) {
            return new ResponseEntity<>(new ResponseErrorData<>(HttpStatus.NOT_FOUND.value(), ResponseMessageConstants.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseSuccessData<>(ResponseMessageConstants.DATA_FOUND, productCategory), HttpStatus.OK);
    }

    @RequestMapping("/test")
    public String test()    {
        return "demo test from spring boot";
    }


}
