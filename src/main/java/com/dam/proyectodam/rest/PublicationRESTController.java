package com.dam.proyectodam.rest;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dam.proyectodam.contacs.facade.ContactFacade;
import com.dam.proyectodam.publication.comment.Comment;
import com.dam.proyectodam.publication.content.Content;
import com.dam.proyectodam.publication.content.ContentType;
import com.dam.proyectodam.publication.entity.PublicationEntity;
import com.dam.proyectodam.publication.facade.PublicationFacade;
import com.dam.proyectodam.rest.wrappers.CommentWrapper;
import com.dam.proyectodam.rest.wrappers.LikeWrapper;
import com.dam.proyectodam.rest.wrappers.PublicationContext;
import com.dam.proyectodam.user.entity.UserEntity;
import com.dam.proyectodam.user.facade.UserFacade;

@RestController
public class PublicationRESTController {
	
	
	@Autowired
	@Qualifier("publicationFacade")
	private PublicationFacade publicationFacade;
	
	@Autowired
	@Qualifier("userFacade")
	private UserFacade userFacade;
	
	@Autowired
	@Qualifier("contactFacade")
	private ContactFacade contactFacade;
	
	
	@RequestMapping(value="/publish", method = RequestMethod.POST)
	public @ResponseBody List<PublicationEntity> publish(@RequestBody PublicationContext publicationContext) {
		
		PublicationEntity publication = publicationContext.getPublication();
		
		Content contentPub = publication.getContent();
		
		if(contentPub.getImagen() == null || contentPub.getImagen().equals("")) {
			contentPub.setType(ContentType.TXT);
		} else {
			contentPub.setType(ContentType.IMG);
		}
		
		contentPub.setText(contentPub.getText().replaceAll("\\n", "<br/>"));
		
		publication.setPublishDate(Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid")).getTime());
		
		UserEntity publisher = publicationContext.getPublisher();
		String idUserWall = publicationContext.getIdUserWall();
		
		if(null == publication.getOwner()) {
			publication.setOwner(publisher);
		}else {
			publication.setId(null);
			publication.setComments(null);
			publication.setLikes(null);
			publication.setSharer(publisher);
		}
		publication.setIdUserWall(new ObjectId(idUserWall));
		
		if(publicationFacade.addPublication(publication)) {
//			return publicationFacade.getPublicationsFromUser(userFacade.findById(idUserWall));
			return publicationFacade.getHomePublications(publisher);
		}else{
			return null;
		}
	}
	
	@RequestMapping(value="/getPublicationsFromUser/{username}", method = RequestMethod.GET)
	public @ResponseBody List<PublicationEntity> getPublicationsFromUser(@PathVariable String username) {
		
		return publicationFacade.getPublicationsFromUser(userFacade.findByUsername(username));
	}
	
	@RequestMapping(value="/getAllPublications")
	public @ResponseBody List<PublicationEntity> getAllPublications() {
		return publicationFacade.getAllPublications();
	}
	
	@RequestMapping(value="/comment", method = RequestMethod.POST)
	public @ResponseBody List<Comment> comment(@RequestBody CommentWrapper commentWrapper) {
		String idPublication = commentWrapper.getIdPublication();
		Comment newComment = commentWrapper.getComment();
		
		newComment.setText(newComment.getText().replaceAll("\\n", "<br/>"));
		newComment.setCommentDate(Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid")).getTime());
		
		return publicationFacade.comment(idPublication, newComment);
	}
	
	@RequestMapping(value="/deleteComment", method = RequestMethod.POST)
	public @ResponseBody List<Comment> deleteComment(@RequestBody CommentWrapper commentWrapper) {
		String idPublication = commentWrapper.getIdPublication();
		Comment comment = commentWrapper.getComment();
		
		return publicationFacade.deleteComment(idPublication, comment);
	}
	
	@RequestMapping(value="/like", method = RequestMethod.POST)
	public @ResponseBody List<UserEntity> like(@RequestBody LikeWrapper likeWrapper) {
		String idPub = likeWrapper.getIdPublication();
		String username = likeWrapper.getUsername();
		
		return publicationFacade.like(idPub, username);
	}
	
	@RequestMapping(value="getHomePublications", method = RequestMethod.POST)
	public @ResponseBody List<PublicationEntity> getHomePublications(@RequestBody UserEntity user) {
		UserEntity entity = userFacade.findById(user.getId());
		if(entity == null) {
			return null;
		}
		entity.setContacts(contactFacade.getContactsFromUser(entity));
		
		return publicationFacade.getHomePublications(entity);
		
	}

}
