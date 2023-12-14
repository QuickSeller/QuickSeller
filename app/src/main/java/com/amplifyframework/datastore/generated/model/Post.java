package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.temporal.Temporal;


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

/** This is an auto generated class representing the Post type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Posts",authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Post implements Model {
  public static final QueryField ID = field("Post", "id");
  public static final QueryField USER = field("Post", "userId");
  public static final QueryField CITY = field("Post", "city");
  public static final QueryField TITLE = field("Post", "title");
  public static final QueryField DESCRIPTION = field("Post", "description");
  public static final QueryField PRICE = field("Post", "price");
  public static final QueryField PRODUCT_CATEGORY = field("Post", "productCategory");
  public static final QueryField IMAGES = field("Post", "images");
  public static final QueryField CREATED_AT = field("Post", "createdAt");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="User", isRequired = true) @BelongsTo(targetName = "userId",  type = User.class) User user;
  private final @ModelField(targetType="String", isRequired = true) String city;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="String", isRequired = true) String price;
  private final @ModelField(targetType="ProductCategoryEnum", isRequired = true) ProductCategoryEnum productCategory;
  private final @ModelField(targetType="String") List<String> images;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  /** @deprecated This API is internal to Amplify and should not be used. */
  @Deprecated
   public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public User getUser() {
      return user;
  }
  
  public String getCity() {
      return city;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getDescription() {
      return description;
  }
  
  public String getPrice() {
      return price;
  }
  
  public ProductCategoryEnum getProductCategory() {
      return productCategory;
  }
  
  public List<String> getImages() {
      return images;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Post(String id, User user, String city, String title, String description, String price, ProductCategoryEnum productCategory, List<String> images, Temporal.DateTime createdAt) {
    this.id = id;
    this.user = user;
    this.city = city;
    this.title = title;
    this.description = description;
    this.price = price;
    this.productCategory = productCategory;
    this.images = images;
    this.createdAt = createdAt;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Post post = (Post) obj;
      return ObjectsCompat.equals(getId(), post.getId()) &&
              ObjectsCompat.equals(getUser(), post.getUser()) &&
              ObjectsCompat.equals(getCity(), post.getCity()) &&
              ObjectsCompat.equals(getTitle(), post.getTitle()) &&
              ObjectsCompat.equals(getDescription(), post.getDescription()) &&
              ObjectsCompat.equals(getPrice(), post.getPrice()) &&
              ObjectsCompat.equals(getProductCategory(), post.getProductCategory()) &&
              ObjectsCompat.equals(getImages(), post.getImages()) &&
              ObjectsCompat.equals(getCreatedAt(), post.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), post.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUser())
      .append(getCity())
      .append(getTitle())
      .append(getDescription())
      .append(getPrice())
      .append(getProductCategory())
      .append(getImages())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Post {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("user=" + String.valueOf(getUser()) + ", ")
      .append("city=" + String.valueOf(getCity()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("price=" + String.valueOf(getPrice()) + ", ")
      .append("productCategory=" + String.valueOf(getProductCategory()) + ", ")
      .append("images=" + String.valueOf(getImages()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static UserStep builder() {
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
  public static Post justId(String id) {
    return new Post(
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
      user,
      city,
      title,
      description,
      price,
      productCategory,
      images,
      createdAt);
  }
  public interface UserStep {
    CityStep user(User user);
  }
  

  public interface CityStep {
    TitleStep city(String city);
  }
  

  public interface TitleStep {
    PriceStep title(String title);
  }
  

  public interface PriceStep {
    ProductCategoryStep price(String price);
  }
  

  public interface ProductCategoryStep {
    CreatedAtStep productCategory(ProductCategoryEnum productCategory);
  }
  

  public interface CreatedAtStep {
    BuildStep createdAt(Temporal.DateTime createdAt);
  }
  

  public interface BuildStep {
    Post build();
    BuildStep id(String id);
    BuildStep description(String description);
    BuildStep images(List<String> images);
  }
  

  public static class Builder implements UserStep, CityStep, TitleStep, PriceStep, ProductCategoryStep, CreatedAtStep, BuildStep {
    private String id;
    private User user;
    private String city;
    private String title;
    private String price;
    private ProductCategoryEnum productCategory;
    private Temporal.DateTime createdAt;
    private String description;
    private List<String> images;
    public Builder() {
      
    }
    
    private Builder(String id, User user, String city, String title, String description, String price, ProductCategoryEnum productCategory, List<String> images, Temporal.DateTime createdAt) {
      this.id = id;
      this.user = user;
      this.city = city;
      this.title = title;
      this.description = description;
      this.price = price;
      this.productCategory = productCategory;
      this.images = images;
      this.createdAt = createdAt;
    }
    
    @Override
     public Post build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Post(
          id,
          user,
          city,
          title,
          description,
          price,
          productCategory,
          images,
          createdAt);
    }
    
    @Override
     public CityStep user(User user) {
        Objects.requireNonNull(user);
        this.user = user;
        return this;
    }
    
    @Override
     public TitleStep city(String city) {
        Objects.requireNonNull(city);
        this.city = city;
        return this;
    }
    
    @Override
     public PriceStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public ProductCategoryStep price(String price) {
        Objects.requireNonNull(price);
        this.price = price;
        return this;
    }
    
    @Override
     public CreatedAtStep productCategory(ProductCategoryEnum productCategory) {
        Objects.requireNonNull(productCategory);
        this.productCategory = productCategory;
        return this;
    }
    
    @Override
     public BuildStep createdAt(Temporal.DateTime createdAt) {
        Objects.requireNonNull(createdAt);
        this.createdAt = createdAt;
        return this;
    }
    
    @Override
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep images(List<String> images) {
        this.images = images;
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
    private CopyOfBuilder(String id, User user, String city, String title, String description, String price, ProductCategoryEnum productCategory, List<String> images, Temporal.DateTime createdAt) {
      super(id, user, city, title, description, price, productCategory, images, createdAt);
      Objects.requireNonNull(user);
      Objects.requireNonNull(city);
      Objects.requireNonNull(title);
      Objects.requireNonNull(price);
      Objects.requireNonNull(productCategory);
      Objects.requireNonNull(createdAt);
    }
    
    @Override
     public CopyOfBuilder user(User user) {
      return (CopyOfBuilder) super.user(user);
    }
    
    @Override
     public CopyOfBuilder city(String city) {
      return (CopyOfBuilder) super.city(city);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder price(String price) {
      return (CopyOfBuilder) super.price(price);
    }
    
    @Override
     public CopyOfBuilder productCategory(ProductCategoryEnum productCategory) {
      return (CopyOfBuilder) super.productCategory(productCategory);
    }
    
    @Override
     public CopyOfBuilder createdAt(Temporal.DateTime createdAt) {
      return (CopyOfBuilder) super.createdAt(createdAt);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder images(List<String> images) {
      return (CopyOfBuilder) super.images(images);
    }
  }

}
