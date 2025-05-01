package com.example.lotteon.repository.product;

import com.example.lotteon.entity.product.Product;
import com.example.lotteon.entity.product.ProductOptions;
import com.example.lotteon.entity.product.QProduct;
import com.example.lotteon.entity.product.QProductOptions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

  private final JPAQueryFactory query;
  private final QProduct product = QProduct.product;
  private final QProductOptions options = QProductOptions.productOptions;

  @Override
  public Page<Product> findAll(Pageable pageable) {
    List<Product> products = query.selectFrom(product).fetch();
    return new PageImpl<>(products, pageable, products.size());
  }

  @Override
  public Page<Product> findById(int id, Pageable pageable) {
    List<Product> products = query.selectFrom(product)
        .where(product.id.eq(id))
        .fetch();
    return new PageImpl<>(products, pageable, products.size());
  }

  @Override
  public Page<Product> findByName(String name, Pageable pageable) {
    List<Product> products = query.selectFrom(product)
        .where(product.name.eq(name))
        .fetch();
    return new PageImpl<>(products, pageable, products.size());
  }

  @Override
  public Page<Product> findByCompany(String company, Pageable pageable) {
    List<Product> products = query.selectFrom(product)
        .where(product.seller.companyName.eq(company))
        .fetch();
    return new PageImpl<>(products, pageable, products.size());
  }

  @Override
  @Transactional
  public void updateStatusById(int id, String status) {
    query.update(product)
        .set(product.status, status)
        .where(product.id.eq(id))
        .execute();
  }

  @Override
  @Transactional
  public void updateById(int id, Product product) {
    // TODO: Update product Image's locations
    query.update(this.product)
        .set(this.product.category.id, product.getCategory().getId())
        .set(this.product.subCategory.id, product.getSubCategory().getId())
        .set(this.product.name, product.getName())
        .set(this.product.description, product.getDescription())
        .set(this.product.price, product.getPrice())
        .set(this.product.discountRate, product.getDiscountRate())
        .set(this.product.point, product.getPoint())
        .set(this.product.stock, product.getStock())
        .set(this.product.deliveryFee, product.getDeliveryFee())
        .set(this.product.isVatFree, product.isVatFree())
        .set(this.product.receiptIssuable, product.isReceiptIssuable())
        .set(this.product.businessClassification, product.getBusinessClassification())
        .set(this.product.origin, product.getOrigin())
        .where(this.product.id.eq(id))
        .execute();
  }

  @Override
  @Transactional
  public void deleteById(int id) {
    query.delete(product)
        .where(product.id.eq(id))
        .execute();
  }

  @Override
  public List<ProductOptions> findOptionsByProductId(int productId) {
    return query.select(options)
        .from(product)
        .join(options)
        .on(options.product.id.eq(productId))
        .fetch();
  }
}
