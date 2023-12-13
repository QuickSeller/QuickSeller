package com.amplifyframework.datastore.generated.model;

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

/** This is an auto generated class representing the User type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Users", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class User implements Model {
  public static final QueryField ID = field("User", "id");
  public static final QueryField USERNAME = field("User", "username");
  public static final QueryField EMAIL = field("User", "email");
  public static final QueryField PHONE_NUMBER = field("User", "phoneNumber");
  public static final QueryField IMAGE = field("User", "image");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String username;
  private final @ModelField(targetType="String", isRequired = true) String email;
  private final @ModelField(targetType="String", isRequired = true) String phoneNumber;
  private final @ModelField(targetType="String") String image;
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
  
  public String getUsername() {
      return username;
  }

  public String getEmail() {
      return email;
  }

  public String getPhoneNumber() {
      return phoneNumber;
  }

  public String getImage() {
      return image;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private User(String id, String username, String email, String phoneNumber, String image) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.image = image;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      User user = (User) obj;
      return ObjectsCompat.equals(getId(), user.getId()) &&
              ObjectsCompat.equals(getUsername(), user.getUsername()) &&
              ObjectsCompat.equals(getEmail(), user.getEmail()) &&
              ObjectsCompat.equals(getPhoneNumber(), user.getPhoneNumber()) &&
              ObjectsCompat.equals(getImage(), user.getImage()) &&
              ObjectsCompat.equals(getCreatedAt(), user.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), user.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUsername())
      .append(getEmail())
      .append(getPhoneNumber())
      .append(getImage())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("User {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("username=" + String.valueOf(getUsername()) + ", ")
      .append("email=" + String.valueOf(getEmail()) + ", ")
      .append("phoneNumber=" + String.valueOf(getPhoneNumber()) + ", ")
      .append("image=" + String.valueOf(getImage()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static UsernameStep builder() {
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
  public static User justId(String id) {
    return new User(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      username,
      email,
      phoneNumber,
      image);
  }
  public interface UsernameStep {
    EmailStep username(String username);
  }


  public interface EmailStep {
    PhoneNumberStep email(String email);
  }


  public interface PhoneNumberStep {
    BuildStep phoneNumber(String phoneNumber);
  }
  

  public interface BuildStep {
    User build();
    BuildStep id(String id);
    BuildStep image(String image);
  }
  

  public static class Builder implements UsernameStep, EmailStep, PhoneNumberStep, BuildStep {
    private String id;
    private String username;
    private String email;
    private String phoneNumber;
    private String image;
    public Builder() {
      
    }
    
    private Builder(String id, String username, String email, String phoneNumber, String image) {
      this.id = id;
      this.username = username;
      this.email = email;
      this.phoneNumber = phoneNumber;
      this.image = image;
    }
    
    @Override
     public User build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new User(
          id,
          username,
          email,
          phoneNumber,
          image);
    }

    @Override
     public EmailStep username(String username) {
        Objects.requireNonNull(username);
        this.username = username;
        return this;
    }

    @Override
     public PhoneNumberStep email(String email) {
        Objects.requireNonNull(email);
        this.email = email;
        return this;
    }
    
    @Override
     public BuildStep phoneNumber(String phoneNumber) {
        Objects.requireNonNull(phoneNumber);
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
     public BuildStep image(String image) {
        this.image = image;
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
    private CopyOfBuilder(String id, String username, String email, String phoneNumber, String image) {
      super(id, username, email, phoneNumber, image);
      Objects.requireNonNull(username);
      Objects.requireNonNull(email);
      Objects.requireNonNull(phoneNumber);
    }

    @Override
     public CopyOfBuilder username(String username) {
      return (CopyOfBuilder) super.username(username);
    }
    
    @Override
     public CopyOfBuilder email(String email) {
      return (CopyOfBuilder) super.email(email);
    }
    
    @Override
     public CopyOfBuilder phoneNumber(String phoneNumber) {
      return (CopyOfBuilder) super.phoneNumber(phoneNumber);
    }

    @Override
     public CopyOfBuilder image(String image) {
      return (CopyOfBuilder) super.image(image);
    }
  }



  
}
