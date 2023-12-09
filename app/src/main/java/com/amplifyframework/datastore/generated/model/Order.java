package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.HasMany;
import com.amplifyframework.core.model.annotations.BelongsTo;

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

/** This is an auto generated class representing the Order type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Orders", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byUser", fields = {"userId","orderDate"})
public final class Order implements Model {
  public static final QueryField ID = field("Order", "id");
  public static final QueryField ORDER_DATE = field("Order", "orderDate");
  public static final QueryField ORDER_STATUS = field("Order", "orderStatus");
  public static final QueryField USER = field("Order", "userId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime orderDate;
  private final @ModelField(targetType="OrderStatusEnum") OrderStatusEnum orderStatus;
  private final @ModelField(targetType="OrderProduct") @HasMany(associatedWith = "order", type = OrderProduct.class) List<OrderProduct> products = null;
  private final @ModelField(targetType="User") @BelongsTo(targetName = "userId", type = User.class) User user;
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
  
  public Temporal.DateTime getOrderDate() {
      return orderDate;
  }
  
  public OrderStatusEnum getOrderStatus() {
      return orderStatus;
  }
  
  public List<OrderProduct> getProducts() {
      return products;
  }
  
  public User getUser() {
      return user;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Order(String id, Temporal.DateTime orderDate, OrderStatusEnum orderStatus, User user) {
    this.id = id;
    this.orderDate = orderDate;
    this.orderStatus = orderStatus;
    this.user = user;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Order order = (Order) obj;
      return ObjectsCompat.equals(getId(), order.getId()) &&
              ObjectsCompat.equals(getOrderDate(), order.getOrderDate()) &&
              ObjectsCompat.equals(getOrderStatus(), order.getOrderStatus()) &&
              ObjectsCompat.equals(getUser(), order.getUser()) &&
              ObjectsCompat.equals(getCreatedAt(), order.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), order.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getOrderDate())
      .append(getOrderStatus())
      .append(getUser())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Order {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("orderDate=" + String.valueOf(getOrderDate()) + ", ")
      .append("orderStatus=" + String.valueOf(getOrderStatus()) + ", ")
      .append("user=" + String.valueOf(getUser()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
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
  public static Order justId(String id) {
    return new Order(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      orderDate,
      orderStatus,
      user);
  }
  public interface BuildStep {
    Order build();
    BuildStep id(String id);
    BuildStep orderDate(Temporal.DateTime orderDate);
    BuildStep orderStatus(OrderStatusEnum orderStatus);
    BuildStep user(User user);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private Temporal.DateTime orderDate;
    private OrderStatusEnum orderStatus;
    private User user;
    public Builder() {
      
    }
    
    private Builder(String id, Temporal.DateTime orderDate, OrderStatusEnum orderStatus, User user) {
      this.id = id;
      this.orderDate = orderDate;
      this.orderStatus = orderStatus;
      this.user = user;
    }
    
    @Override
     public Order build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Order(
          id,
          orderDate,
          orderStatus,
          user);
    }
    
    @Override
     public BuildStep orderDate(Temporal.DateTime orderDate) {
        this.orderDate = orderDate;
        return this;
    }
    
    @Override
     public BuildStep orderStatus(OrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }
    
    @Override
     public BuildStep user(User user) {
        this.user = user;
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
    private CopyOfBuilder(String id, Temporal.DateTime orderDate, OrderStatusEnum orderStatus, User user) {
      super(id, orderDate, orderStatus, user);
      
    }
    
    @Override
     public CopyOfBuilder orderDate(Temporal.DateTime orderDate) {
      return (CopyOfBuilder) super.orderDate(orderDate);
    }
    
    @Override
     public CopyOfBuilder orderStatus(OrderStatusEnum orderStatus) {
      return (CopyOfBuilder) super.orderStatus(orderStatus);
    }
    
    @Override
     public CopyOfBuilder user(User user) {
      return (CopyOfBuilder) super.user(user);
    }
  }

  
}
