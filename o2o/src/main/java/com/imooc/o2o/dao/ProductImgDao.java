package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ProductImg;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("productImgDao")
public interface ProductImgDao {

    int batchInsertProductImg(List<ProductImg> productImgs);

    int deleteProductImgByProductId(Long productId);

    List<ProductImg> queryProductImgList(Long productId);
}
