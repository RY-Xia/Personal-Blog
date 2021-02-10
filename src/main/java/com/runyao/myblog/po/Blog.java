package com.runyao.myblog.po;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Runyao Xia
 * @date 2021/1/27
 */

@Entity
@Table(name = "t_blog")
public class Blog {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;
    private String headPic;
    private String flag;
    private Integer viewNumber;
    private boolean likeOrNot;
    private boolean shareOrNot;
    private boolean commentOrNot;
    private boolean postOrNot;
    private boolean recommend;
    private String description;
    // 不存入数据库
    @Transient
    private String tagIds;


    @ManyToOne
    private Type type;

    @ManyToMany(cascade = CascadeType.MERGE, fetch=FetchType.EAGER)
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    public Blog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViewNumber() {
        return viewNumber;
    }

    public void setViewNumber(Integer viewNumber) {
        this.viewNumber = viewNumber;
    }

    public boolean isLikeOrNot() {
        return likeOrNot;
    }

    public void setLikeOrNot(boolean likeOrNot) {
        this.likeOrNot = likeOrNot;
    }

    public boolean isShareOrNot() {
        return shareOrNot;
    }

    public void setShareOrNot(boolean shareOrNot) {
        this.shareOrNot = shareOrNot;
    }

    public boolean isCommentOrNot() {
        return commentOrNot;
    }

    public void setCommentOrNot(boolean commentOrNot) {
        this.commentOrNot = commentOrNot;
    }

    public boolean isPostOrNot() {
        return postOrNot;
    }

    public void setPostOrNot(boolean postOrNot) {
        this.postOrNot = postOrNot;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void init() {
        this.tagIds = tagsToIds(this.getTags());

    }
    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                }else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }
        return tagIds;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", headPic='" + headPic + '\'' +
                ", flag='" + flag + '\'' +
                ", viewNumber=" + viewNumber +
                ", likeOrNot=" + likeOrNot +
                ", shareOrNot=" + shareOrNot +
                ", commentOrNot=" + commentOrNot +
                ", postOrNot=" + postOrNot +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", description" + description+
                '}';
    }

}
