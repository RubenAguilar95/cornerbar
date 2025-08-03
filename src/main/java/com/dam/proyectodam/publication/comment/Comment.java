package com.dam.proyectodam.publication.comment;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.dam.proyectodam.user.entity.UserEntity;

public class Comment {
	
	@DBRef
	private UserEntity owner;
	
	private String text;
	
	private Date commentDate;

	public UserEntity getOwner() {
		return owner;
	}

	public void setOwner(UserEntity owner) {
		this.owner = owner;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public Date getCommentDate() {
		return commentDate;
	}
	
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj instanceof Comment) {
			Comment comment = (Comment) obj;
			return this.commentDate.equals(comment.getCommentDate()) 
					&& this.text.equals(comment.getText())
					&& this.owner.getId().equals(comment.getOwner().getId());
		} else {
			return false;
		}
	}

}
