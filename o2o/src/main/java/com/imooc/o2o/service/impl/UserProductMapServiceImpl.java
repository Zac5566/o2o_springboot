package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dao.UserProductMapDao;
import com.imooc.o2o.dao.UserShopMapDao;
import com.imooc.o2o.dto.UserProductMapExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.UserProductMap;
import com.imooc.o2o.entity.UserShopMap;
import com.imooc.o2o.enums.UserProductMapStateEnum;
import com.imooc.o2o.exceptions.UserProductMapOperationException;
import com.imooc.o2o.service.UserProductMapService;
import com.imooc.o2o.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("userProductMapService")
public class UserProductMapServiceImpl implements UserProductMapService {

    @Autowired
    private UserProductMapDao userProductMapDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserShopMapDao userShopMapDao;

    @Override
    public UserProductMapExecution listUserProductMap(UserProductMap userProductMapCondition, Integer pageIndex, Integer pageSize) {
        if (userProductMapCondition != null && pageIndex != null && pageSize != null) {
            int rowIndex = PageUtil.pageIndexToRowIndex(pageIndex, pageSize);
            List<UserProductMap> productMapList = userProductMapDao.queryUserProductMapList(
                    userProductMapCondition, rowIndex, pageSize);
            int count = userProductMapDao.queryProductMapCount(userProductMapCondition);
            UserProductMapExecution ue = new UserProductMapExecution();
            ue.setUserProductMapList(productMapList);
            ue.setCount(count);
            return ue;
        } else {
            return new UserProductMapExecution(UserProductMapStateEnum.NULL_USERPRODUCT_INFO);
        }
    }

    @Override
    @Transactional
    public UserProductMapExecution insertUserPurchaseRecord(UserProductMap userProductMapCondition)
            throws UserProductMapOperationException {
        //從數據庫中獲取商品與其點數，並裝入userProductMapCondition
        Long productId = userProductMapCondition.getProduct().getProductId();
        Product productFromDB = productDao.queryProductById(productId);
        userProductMapCondition.setProduct(productFromDB);
        userProductMapCondition.setPoint(productFromDB.getPoint());
        userProductMapCondition.setCreateTime(new Date());
        userProductMapCondition.setShop(productFromDB.getShop());
        //封裝userShopMapCondition
        UserShopMap userShopMapCondition = new UserShopMap();
        userShopMapCondition.setShop(productFromDB.getShop());
        userShopMapCondition.setUser(userProductMapCondition.getUser());
        userShopMapCondition.setCreateTime(new Date());
        try {
            int validUserProductRecord = userProductMapDao.insertUserProductMap(userProductMapCondition);
            List<UserShopMap> isUserPurchaseInShopBefore = userShopMapDao.queryUserShopMap(
                    userShopMapCondition,0,999);
            int validUserShopRecord = 0;
            //判斷用戶在該店有無購買紀錄
            if ( isUserPurchaseInShopBefore.size() == 1) {
                //有紀錄
                //  提出先前點數加上新得到的點數
                int point = isUserPurchaseInShopBefore.get(0).getPoint() + productFromDB.getPoint();
                userShopMapCondition.setPoint(point);
                validUserShopRecord = userShopMapDao.updateUserPoint(userShopMapCondition);
            }else if(isUserPurchaseInShopBefore.size() == 0){
                //無紀錄
                //  直接添加
                userShopMapCondition.setPoint(productFromDB.getPoint());
                validUserShopRecord =userShopMapDao.insertUserPoint(userShopMapCondition);
            }else {
                throw new UserProductMapOperationException("購買失敗");
            }
            //數據有成功添加到兩張表才算成功，失敗則回滾
            if (validUserProductRecord == 1 && validUserShopRecord == 1) {
                return new UserProductMapExecution(UserProductMapStateEnum.SUCCESS);
            } else {
                throw new UserProductMapOperationException("購買失敗");
            }
        } catch (Exception e) {
            throw new UserProductMapOperationException("購買失敗" + e.getMessage());
        }
    }

}
