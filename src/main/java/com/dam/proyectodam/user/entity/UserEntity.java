package com.dam.proyectodam.user.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dam.proyectodam.contacs.dto.ContactDTO;
import com.dam.proyectodam.login.entity.UserCredentialsEntity;

@Document(collection = "users")
public class UserEntity {
	
	@Id
	private String id;
	
	@DBRef
	private UserCredentialsEntity userCredentials;
	
//	private Wall wall;
	
//	/**
//	 * Usernames 
//	 */
//	private List<String> follows;
//	
//	@Transient
//	private List<UserEntity> userFollows;
	
	@Transient
	private List<ContactDTO> contacts;
	
	private String name;
	private String surname1;
	private String surname2;
	private Integer age;
	private Date birthDate;
	private String phone;
	private String city;
	private String address;
	private String email;
	private String nameProfileImage;
	
	public UserEntity(String name, String surname1, String surname2, Integer age, Date birthDate,
			String phone, String city, String address, String email) {
		this.name = name;
		this.surname1 = surname1;
		this.surname2 = surname2;
		this.age = age;
		this.birthDate = birthDate;
		this.phone = phone;
		this.city = city;
		this.address = address;
		this.email = email;
	}
	
	public UserEntity() {
		
	}
	
	public String getId() {
		return id;
	}
//
//	public String getUsername() {
//		return userCredentials.getUsername(); ??
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
	
	public UserCredentialsEntity getUserCredentials() {
		return userCredentials;
	}

	public void setUserCredentials(UserCredentialsEntity userCredentials) {
		this.userCredentials = userCredentials;
	}
	
	public List<ContactDTO> getContacts() {
		return contacts;
	}
	
	public void setContacts(List<ContactDTO> contacts) {
		this.contacts = contacts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname1() {
		return surname1;
	}

	public void setSurname1(String surname1) {
		this.surname1 = surname1;
	}

	public String getSurname2() {
		return surname2;
	}

	public void setSurname2(String surname2) {
		this.surname2 = surname2;
	}
	
	/**
	 * Obligatorio 1 apellido. El resto se añade al apellido 2
	 * @param surnames
	 */
	public void setSurnames(String surnames) {
		String[] parts = surnames.split(" ");
		setSurname1(surnames.split(" ")[0]);
		if(parts.length > 1) {
			String surname2 = "";
			for(int i = 1; i< parts.length; i++) {
				surname2 += parts[i] + " ";
			}
			setSurname2(surname2.trim());
		}
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}

//	public void setBirthDate(String birthDate) {
//		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//		try {
//			this.birthDate = formatter.parse(birthDate);
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(this.birthDate);
//			
//			LocalDate localBD = new LocalDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
//			LocalDate now = new LocalDate();
//			Years y = Years.yearsBetween(localBD, now);			
//			
//			this.age = Integer.valueOf(y.getYears());
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * A través del año de nacimiento establecemos su edad con el uso de la libreria jodatime
	 * @param birthDate
	 */
	
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.birthDate);
		
		LocalDate localBD = new LocalDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
		LocalDate now = new LocalDate();
		Years y = Years.yearsBetween(localBD, now);			
		
		this.age = Integer.valueOf(y.getYears());
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNameProfileImage() {
		return nameProfileImage;
	}
	
	public void setNameProfileImage(String nameProfileImage) {
		this.nameProfileImage = nameProfileImage;
	}
	
//	public List<String> getFollows() {
//		return follows;
//	}
//	
//	public void setFollows(List<String> follows) {
//		this.follows = follows;
//	}
//	
//	public List<UserEntity> getUserFollows() {
//		return userFollows;
//	}
//	
//	public void setUserFollows(List<UserEntity> userFollows) {
//		this.userFollows = userFollows;
//	}
	
//	public List<UserEntity> getFollows() {
//		return follows;
//	}
//	
//	public void setFollows(List<UserEntity> follows) {
//		this.follows = follows;
//	}

//	public Wall getWall() {
//		return wall;
//	}
//
//	public void setWall(Wall wall) {
//		this.wall = wall;
//	}

	@Override
	public String toString() {
		return "UserEntity [username=" + userCredentials.getUsername() + ", name=" + name + ", surname1=" + surname1 + ", surname2="
				+ surname2 + ", age=" + age + ", birthDate=" + birthDate + ", phone=" + phone + ", city=" + city
				+ ", address=" + address + ", email=" + email + "]";
	}
	
	// Necesario para usar List.contains
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj instanceof UserEntity) {
			UserEntity entity = (UserEntity) obj;
			return this.userCredentials.getUsername().equals(entity.getUserCredentials().getUsername())
					&& this.userCredentials.getPassword().equals(entity.getUserCredentials().getPassword())
					&& this.name.equals(entity.getName());
		} else {
			return false;
		}
	}
	
	

}
