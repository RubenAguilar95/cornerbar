package com.dam.proyectodam.rest.wrappers;

import com.dam.proyectodam.publication.comment.Comment;

public class CommentWrapper {

	private String  idPublication;
	private Comment comment;
	
	public String getIdPublication() {
		return idPublication;
	}
	
	public void setIdPublication(String idPublication) {
		this.idPublication = idPublication;
	}
	
	public Comment getComment() {
		return comment;
	}
	
	public void setComment(Comment newComment) {
		this.comment = newComment;
	}
	
}
