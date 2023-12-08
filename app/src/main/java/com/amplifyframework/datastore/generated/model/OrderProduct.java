package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.ModelIdentifier;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the OrderProduct type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "OrderProducts", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byOrder", fields = {"orderId","id"})
@Index(name = "byProduct", fields = {"productId","id"})
public final class OrderProduct implements Model {
  public static final QueryField ID = field("OrderProduct", "id");
  public static final QueryField ORDER = field("OrderProduct", "orderId");
  public static final QueryField PRODUCT = field("OrderProduct", "productId");
  public static final QueryField QUANTITY = field("OrderProduct", "quantity");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Order") @BelongsTo(targetName = "orderId", targetNames = {"orderId"}, type = Order.class) Order order;
  private final @ModelField(targetType="Product") @BelongsTo(targetName = "productId", targetNames = {"productId"}, type = Product.class) Product product;
  private final @ModelField(targetType="Int", isRequired = true) Integer quantity;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  /** @deprecated This API is internal to Amplify and should not be used. */
  @Deprecated
   public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public Order getOrder() {
      return order;
  }
  
  public Product getProduct() {
      return product;
  }
  
  public Integer getQuantity() {
      return quantity;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private OrderProduct(String id, Order order, Product product, Integer quantity) {
    this.id = id;
    this.order = order;
    this.product = product;
    this.quantity = quantity;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      OrderProduct orderProduct = (OrderProduct) obj;
      return ObjectsCompat.equals(getId(), orderProduct.getId()) &&
              ObjectsCompat.equals(getOrder(), orderProduct.getOrder()) &&
              ObjectsCompat.equals(getProduct(), orderProduct.getProduct()) &&
              ObjectsCompat.equals(getQuantity(), orderProduct.getQuantity()) &&
              ObjectsCompat.equals(getCreatedAt(), orderProduct.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), orderProduct.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getOrder())
      .append(getProduct())
      .append(getQuantity())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("OrderProduct {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("order=" + String.valueOf(getOrder()) + ", ")
      .append("product=" + String.valueOf(getProduct()) + ", ")
      .append("quantity=" + String.valueOf(getQuantity()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static QuantityStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static OrderProduct justId(String id) {
    return new OrderProduct(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      order,
      product,
      quantity);
  }
  public interface QuantityStep {
    BuildStep quantity(Integer quantity);
  }
  

  public interface BuildStep {
    OrderProduct build();
    BuildStep id(String id);
    BuildStep order(Order order);
    BuildStep product(Product product);
  }
  

  public static class Builder implements QuantityStep, BuildStep {
    private String id;
    private Integer quantity;
    private Order order;
    private Product product;
    public Builder() {
      
    }
    
    private Builder(String id, Order order, Product product, Integer quantity) {
      this.id = id;
      this.order = order;
      this.product = product;
      this.quantity = quantity;
    }
    
    @Override
     public OrderProduct build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new OrderProduct(
          id,
          order,
          product,
          quantity);
    }
    
    @Override
     public BuildStep quantity(Integer quantity) {
        Objects.requireNonNull(quantity);
        this.quantity = quantity;
        return this;
    }
    
    @Override
     public BuildStep order(Order order) {
        this.order = order;
        return this;
    }
    
    @Override
     public BuildStep product(Product product) {
        this.product = product;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, Order order, Product product, Integer quantity) {
      super(id, order, product, quantity);
      Objects.requireNonNull(quantity);
    }
    
    @Override
     public CopyOfBuilder quantity(Integer quantity) {
      return (CopyOfBuilder) super.quantity(quantity);
    }
    
    @Override
     public CopyOfBuilder order(Order order) {
      return (CopyOfBuilder) super.order(order);
    }
    
    @Override
     public CopyOfBuilder product(Product product) {
      return (CopyOfBuilder) super.product(product);
    }
  }
  

  public static class OrderProductIdentifier extends ModelIdentifier<OrderProduct> {
    private static final long serialVersionUID = 1L;
    public OrderProductIdentifier(String id) {
      super(id);
    }
  }
  
}
