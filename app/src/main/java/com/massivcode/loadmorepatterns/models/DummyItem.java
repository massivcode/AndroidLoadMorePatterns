package com.massivcode.loadmorepatterns.models;

/**
 * Created by massivcode@gmail.com on 2017. 10. 24. 09:40
 */

public class DummyItem {
  private String title, contents, author, profileImageUrl;

  public DummyItem(String title, String contents, String author, String profileImageUrl) {
    this.title = title;
    this.contents = contents;
    this.author = author;
    this.profileImageUrl = profileImageUrl;
  }

  public String getTitle() {
    return title;
  }

  public String getContents() {
    return contents;
  }

  public String getAuthor() {
    return author;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("DummyItem{");
    sb.append("title='").append(title).append('\'');
    sb.append(", contents='").append(contents).append('\'');
    sb.append(", author='").append(author).append('\'');
    sb.append(", profileImageUrl='").append(profileImageUrl).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
