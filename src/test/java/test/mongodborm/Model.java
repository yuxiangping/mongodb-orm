package test.mongodborm;

import java.util.List;
import java.util.Map;

/**
 * Sample Model for test.
 * 
 * @author yy
 */
public class Model {

  private String id;
  private String name;
  private Integer status;
  private Long time;

  private Parent parent;
  private List<Child> childrens;

  /**
   * bean data
   */
  private Map<String, Object> data;

  /**
   * new property
   */
  private String newProperty;

  /**
   * Partent Model
   */
  public static class Parent {
    private String name;
    private Child child;
    private Map<String, Object> data;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Child getChild() {
      return child;
    }

    public void setChild(Child child) {
      this.child = child;
    }

    public Map<String, Object> getData() {
      return data;
    }

    public void setData(Map<String, Object> data) {
      this.data = data;
    }
  }

  public static class Child {

    private String name;
    private Long time;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Long getTime() {
      return time;
    }

    public void setTime(Long time) {
      this.time = time;
    }
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Long getTime() {
    return time;
  }

  public void setTime(Long time) {
    this.time = time;
  }

  public Parent getParent() {
    return parent;
  }

  public void setParent(Parent parent) {
    this.parent = parent;
  }

  public List<Child> getChildrens() {
    return childrens;
  }

  public void setChildrens(List<Child> childrens) {
    this.childrens = childrens;
  }

  public Map<String, Object> getData() {
    return data;
  }

  public void setData(Map<String, Object> data) {
    this.data = data;
  }

  public String getNewProperty() {
    return newProperty;
  }

  public void setNewProperty(String newProperty) {
    this.newProperty = newProperty;
  }
}
