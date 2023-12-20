package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
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

/** This is an auto generated class representing the Comment type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Comments", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Comment implements Model {
  public static final QueryField ID = field("Comment", "id");
  public static final QueryField CONTENT = field("Comment", "content");
  public static final QueryField CREATED_AT = field("Comment", "createdAt");
  public static final QueryField POST = field("Comment", "postId");
  public static final QueryField USER = field("Comment", "userId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String content;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime createdAt;
  private final @ModelField(targetType="Post", isRequired = true) @BelongsTo(targetName = "postId", type = Post.class) Post post;
  private final @ModelField(targetType="User", isRequired = true) @BelongsTo(targetName = "userId", type = User.class) User user;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  /** @deprecated This API is internal to Amplify and should not be used. */
  @Deprecated
   public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public String getContent() {
      return content;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Post getPost() {
      return post;
  }
  
  public User getUser() {
      return user;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Comment(String id, String content, Temporal.DateTime createdAt, Post post, User user) {
    this.id = id;
    this.content = content;
    this.createdAt = createdAt;
    this.post = post;
    this.user = user;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Comment comment = (Comment) obj;
      return ObjectsCompat.equals(getId(), comment.getId()) &&
              ObjectsCompat.equals(getContent(), comment.getContent()) &&
              ObjectsCompat.equals(getCreatedAt(), comment.getCreatedAt()) &&
              ObjectsCompat.equals(getPost(), comment.getPost()) &&
              ObjectsCompat.equals(getUser(), comment.getUser()) &&
              ObjectsCompat.equals(getUpdatedAt(), comment.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getContent())
      .append(getCreatedAt())
      .append(getPost())
      .append(getUser())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Comment {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("content=" + String.valueOf(getContent()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("post=" + String.valueOf(getPost()) + ", ")
      .append("user=" + String.valueOf(getUser()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static ContentStep builder() {
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
  public static Comment justId(String id) {
    return new Comment(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      content,
      createdAt,
      post,
      user);
  }
  public interface ContentStep {
    CreatedAtStep content(String content);
  }
  

  public interface CreatedAtStep {
    PostStep createdAt(Temporal.DateTime createdAt);
  }
  

  public interface PostStep {
    UserStep post(Post post);
  }
  

  public interface UserStep {
    BuildStep user(User user);
  }
  

  public interface BuildStep {
    Comment build();
    BuildStep id(String id);
  }
  

  public static class Builder implements ContentStep, CreatedAtStep, PostStep, UserStep, BuildStep {
    private String id;
    private String content;
    private Temporal.DateTime createdAt;
    private Post post;
    private User user;
    public Builder() {
      
    }
    
    private Builder(String id, String content, Temporal.DateTime createdAt, Post post, User user) {
      this.id = id;
      this.content = content;
      this.createdAt = createdAt;
      this.post = post;
      this.user = user;
    }
    
    @Override
     public Comment build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Comment(
          id,
          content,
          createdAt,
          post,
          user);
    }
    
    @Override
     public CreatedAtStep content(String content) {
        Objects.requireNonNull(content);
        this.content = content;
        return this;
    }
    
    @Override
     public PostStep createdAt(Temporal.DateTime createdAt) {
        Objects.requireNonNull(createdAt);
        this.createdAt = createdAt;
        return this;
    }
    
    @Override
     public UserStep post(Post post) {
        Objects.requireNonNull(post);
        this.post = post;
        return this;
    }
    
    @Override
     public BuildStep user(User user) {
        Objects.requireNonNull(user);
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
    private CopyOfBuilder(String id, String content, Temporal.DateTime createdAt, Post post, User user) {
      super(id, content, createdAt, post, user);
      Objects.requireNonNull(content);
      Objects.requireNonNull(createdAt);
      Objects.requireNonNull(post);
      Objects.requireNonNull(user);
    }
    
    @Override
     public CopyOfBuilder content(String content) {
      return (CopyOfBuilder) super.content(content);
    }
    
    @Override
     public CopyOfBuilder createdAt(Temporal.DateTime createdAt) {
      return (CopyOfBuilder) super.createdAt(createdAt);
    }
    
    @Override
     public CopyOfBuilder post(Post post) {
      return (CopyOfBuilder) super.post(post);
    }
    
    @Override
     public CopyOfBuilder user(User user) {
      return (CopyOfBuilder) super.user(user);
    }
  }

  
}
