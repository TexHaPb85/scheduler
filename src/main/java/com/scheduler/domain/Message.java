package com.scheduler.domain;

import javax.persistence.*;


@Entity
public class Message {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String text;
    private String tag;


    /**
     * вказуємо як саме автор повитет зберігатися в базі даних
     */
    @ManyToOne(fetch = FetchType.EAGER)//одному користувачу відповідає багато повідомлень
    @JoinColumn(name = "user_id")
    private User author;

    private String filename;

    /**
     * обов'язково треба створити пустий конструктор інакше спрінг не зможе створити класс
     */
    public Message() {

    }

    public Message(String text, String tag, User user) {
        this.author = user;
        this.text = text;
        this.tag = tag;
    }


    /**
     * не всі повідомлення будуть мати автора
     * @return автора
     */
    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
