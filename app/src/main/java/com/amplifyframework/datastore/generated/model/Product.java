package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.annotations.HasMany;

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

/** This is an auto generated class representing the Product type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Products", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byUser", fields = {"userId","name"})
public final class Product implements Model {
  public static final QueryField ID = field("Product", "id");
  public static final QueryField NAME = field("Product", "name");
  public static final QueryField DESCRIPTION = field("Product", "description");
  public static final QueryField DATE_CREATED = field("Product", "dateCreated");
  public static final QueryField PRODUCT_CATEGORY = field("Product", "productCategory");
  public static final QueryField PRODUCT_IMAGE_S3_KEY = field("Product", "productImageS3Key");
  public static final QueryField PRODUCT_LATITUDE = field("Product", "productLatitude");
  public static final QueryField PRODUCT_LONGITUDE = field("Product", "productLongitude");
  public static final QueryField USER_PERSON = field("Product", "userId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime dateCreated;
  private final @ModelField(targetType="ProductCategoryEnum") ProductCategoryEnum productCategory;
  private final @ModelField(targetType="String") String productImageS3Key;
  private final @ModelField(targetType="String") String productLatitude;
  private final @ModelField(targetType="String") String productLongitude;
  private final @ModelField(targetType="User") @BelongsTo(targetName = "userId", type = User.class) User userPerson;
  private final @ModelField(targetType="OrderProduct") @HasMany(associatedWith = "product", type = OrderProduct.class) List<OrderProduct> orders = null;
  private final @ModelField(targetType="Comment") @HasMany(associatedWith = "product", type = Comment.class) List<Comment> comments = null;
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
  
  public String getName() {
      return name;
  }
  
  public String getDescription() {
      return description;
  }
  
  public Temporal.DateTime getDateCreated() {
      return dateCreated;
  }
  
  public ProductCategoryEnum getProductCategory() {
      return productCategory;
  }
  
  public String getProductImageS3Key() {
      return productImageS3Key;
  }
  
  public String getProductLatitude() {
      return productLatitude;
  }
  
  public String getProductLongitude() {
      return productLongitude;
  }
  
  public User getUserPerson() {
      return userPerson;
  }
  
  public List<OrderProduct> getOrders() {
      return orders;
  }
  
  public List<Comment> getComments() {
      return comments;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Product(String id, String name, String description, Temporal.DateTime dateCreated, ProductCategoryEnum productCategory, String productImageS3Key, String productLatitude, String productLongitude, User userPerson) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.dateCreated = dateCreated;
    this.productCategory = productCategory;
    this.productImageS3Key = productImageS3Key;
    this.productLatitude = productLatitude;
    this.productLongitude = productLongitude;
    this.userPerson = userPerson;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Product product = (Product) obj;
      return ObjectsCompat.equals(getId(), product.getId()) &&
              ObjectsCompat.equals(getName(), product.getName()) &&
              ObjectsCompat.equals(getDescription(), product.getDescription()) &&
              ObjectsCompat.equals(getDateCreated(), product.getDateCreated()) &&
              ObjectsCompat.equals(getProductCategory(), product.getProductCategory()) &&
              ObjectsCompat.equals(getProductImageS3Key(), product.getProductImageS3Key()) &&
              ObjectsCompat.equals(getProductLatitude(), product.getProductLatitude()) &&
              ObjectsCompat.equals(getProductLongitude(), product.getProductLongitude()) &&
              ObjectsCompat.equals(getUserPerson(), product.getUserPerson()) &&
              ObjectsCompat.equals(getCreatedAt(), product.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), product.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getDescription())
      .append(getDateCreated())
      .append(getProductCategory())
      .append(getProductImageS3Key())
      .append(getProductLatitude())
      .append(getProductLongitude())
      .append(getUserPerson())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Product {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("dateCreated=" + String.valueOf(getDateCreated()) + ", ")
      .append("productCategory=" + String.valueOf(getProductCategory()) + ", ")
      .append("productImageS3Key=" + String.valueOf(getProductImageS3Key()) + ", ")
      .append("productLatitude=" + String.valueOf(getProductLatitude()) + ", ")
      .append("productLongitude=" + String.valueOf(getProductLongitude()) + ", ")
      .append("userPerson=" + String.valueOf(getUserPerson()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
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
  public static Product justId(String id) {
    return new Product(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      description,
      dateCreated,
      productCategory,
      productImageS3Key,
      productLatitude,
      productLongitude,
      userPerson);
  }
  public interface NameStep {
    BuildStep name(String name);
  }
  

  public interface BuildStep {
    Product build();
    BuildStep id(String id);
    BuildStep description(String description);
    BuildStep dateCreated(Temporal.DateTime dateCreated);
    BuildStep productCategory(ProductCategoryEnum productCategory);
    BuildStep productImageS3Key(String productImageS3Key);
    BuildStep productLatitude(String productLatitude);
    BuildStep productLongitude(String productLongitude);
    BuildStep userPerson(User userPerson);
  }
  

  public static class Builder implements NameStep, BuildStep {
    private String id;
    private String name;
    private String description;
    private Temporal.DateTime dateCreated;
    private ProductCategoryEnum productCategory;
    private String productImageS3Key;
    private String productLatitude;
    private String productLongitude;
    private User userPerson;
    public Builder() {
      
    }
    
    private Builder(String id, String name, String description, Temporal.DateTime dateCreated, ProductCategoryEnum productCategory, String productImageS3Key, String productLatitude, String productLongitude, User userPerson) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.dateCreated = dateCreated;
      this.productCategory = productCategory;
      this.productImageS3Key = productImageS3Key;
      this.productLatitude = productLatitude;
      this.productLongitude = productLongitude;
      this.userPerson = userPerson;
    }
    
    @Override
     public Product build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Product(
          id,
          name,
          description,
          dateCreated,
          productCategory,
          productImageS3Key,
          productLatitude,
          productLongitude,
          userPerson);
    }
    
    @Override
     public BuildStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep dateCreated(Temporal.DateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }
    
    @Override
     public BuildStep productCategory(ProductCategoryEnum productCategory) {
        this.productCategory = productCategory;
        return this;
    }
    
    @Override
     public BuildStep productImageS3Key(String productImageS3Key) {
        this.productImageS3Key = productImageS3Key;
        return this;
    }
    
    @Override
     public BuildStep productLatitude(String productLatitude) {
        this.productLatitude = productLatitude;
        return this;
    }
    
    @Override
     public BuildStep productLongitude(String productLongitude) {
        this.productLongitude = productLongitude;
        return this;
    }
    
    @Override
     public BuildStep userPerson(User userPerson) {
        this.userPerson = userPerson;
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
    private CopyOfBuilder(String id, String name, String description, Temporal.DateTime dateCreated, ProductCategoryEnum productCategory, String productImageS3Key, String productLatitude, String productLongitude, User userPerson) {
      super(id, name, description, dateCreated, productCategory, productImageS3Key, productLatitude, productLongitude, userPerson);
      Objects.requireNonNull(name);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder dateCreated(Temporal.DateTime dateCreated) {
      return (CopyOfBuilder) super.dateCreated(dateCreated);
    }
    
    @Override
     public CopyOfBuilder productCategory(ProductCategoryEnum productCategory) {
      return (CopyOfBuilder) super.productCategory(productCategory);
    }
    
    @Override
     public CopyOfBuilder productImageS3Key(String productImageS3Key) {
      return (CopyOfBuilder) super.productImageS3Key(productImageS3Key);
    }
    
    @Override
     public CopyOfBuilder productLatitude(String productLatitude) {
      return (CopyOfBuilder) super.productLatitude(productLatitude);
    }
    
    @Override
     public CopyOfBuilder productLongitude(String productLongitude) {
      return (CopyOfBuilder) super.productLongitude(productLongitude);
    }
    
    @Override
     public CopyOfBuilder userPerson(User userPerson) {
      return (CopyOfBuilder) super.userPerson(userPerson);
    }
  }
  
}
