package com.fresco.faculty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fresco.faculty.dto.LoginDTO;
import com.fresco.faculty.model.FacultyModel;
import com.fresco.faculty.model.RoleModel;
import com.fresco.faculty.model.UserModel;
import com.fresco.faculty.repository.RoleRepository;
import com.fresco.faculty.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.out;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class FacultyApplicationTests {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	private MockMvc mockMvc;

	public static final String TOKEN_USER_1 = "token_user_1";
	public static final String TOKEN_ADMIN_1 = "token_admin_1";
	public static final String TOKEN_ADMIN_2 = "token_admin_2";
	public static final String ID_USER_1 = "id_user_1";
	public static final String ID_USER_2 ="id_user_2";
	public static final String ID_FACULTY_1 = "id_faculty_1";
	public static final String ID_FACULTY_2 = "id_faculty_2";

	@Autowired
	WebApplicationContext context;

	@BeforeEach
	void setMockMvc(){
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}



	@Test
	void a_testInitialDataIsLoaded() throws Exception {

		ArrayList<UserModel> users= new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		assert users.size()==3;

		//removing the USER  from the users
		users.removeIf(u->u.getRole().getRolename().equals("USER"));

		//Checking two users data is same or not
		UserModel user1 = users.get(0);
		UserModel user2 = users.get(1);
		assert !user1.getEmail().equals(user2.getEmail());
		assert user1.getId()!=user2.getId();

		//Saving the ID of the users for future use in test cases
		saveDataToFileSystem(ID_USER_1,users.get(0).getId());
		saveDataToFileSystem(ID_USER_2,users.get(1).getId());

		ArrayList<RoleModel> roles = new ArrayList<>();
		roleRepository.findAll().forEach(roles::add);
		assert roles.size() == 2;


	}

	@Test
	void b_testUnauthorizedAccessCode() throws Exception {
		//None of the request has token in Authorization header so expecting unauthorized
		//No User Data passed so no bad request expected and no authorization on login endpoint
		mockMvc.perform(post("/user/login")).andExpect(status().isBadRequest());
		mockMvc.perform(post("/faculty/add")).andExpect(status().isUnauthorized());
		mockMvc.perform(get("/faculty/list")).andExpect(status().isUnauthorized());
		mockMvc.perform(patch("/faculty/update/")).andExpect(status().isUnauthorized());
		mockMvc.perform(delete("/faculty/delete/")).andExpect(status().isUnauthorized());

	}

	@Test
	void c_testFailedLoginAttempt() throws  Exception{

		//wrong
		LoginDTO loginData = new LoginDTO("adminemail@gmail.com","wrongpassword");
		mockMvc.perform(post("/user/login")
				.content(toJson(loginData)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();
	}


	@Test
	void d_testSuccessLoginAttemptAdmin() throws  Exception{

		//admin1
		LoginDTO loginData = new LoginDTO("adminemail@gmail.com","admin123$");
		MvcResult result = mockMvc.perform(post("/user/login")
				.content(toJson(loginData)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		JSONObject obj = new JSONObject(result.getResponse().getContentAsString());
		assert obj.has("jwt");
		assert obj.getInt("status")==200;
		saveDataToFileSystem(TOKEN_ADMIN_1,obj.getString("jwt"));


		//admin2
		LoginDTO loginData1 = new LoginDTO("adminemail2@gmail.com","admin789$");
		MvcResult result1 = mockMvc.perform(post("/user/login")
				.content(toJson(loginData1)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		JSONObject obj1 = new JSONObject(result1.getResponse().getContentAsString());
		assert obj1.has("jwt");
		assert obj1.getInt("status")==200;
		saveDataToFileSystem(TOKEN_ADMIN_2,obj1.getString("jwt"));
	}


	@Test
	void e_checkSuccessLoginAttemptUsers() throws  Exception {

		//user
		LoginDTO loginData = new LoginDTO("useremail@gmail.com","user123$");
		MvcResult result = mockMvc.perform(post("/user/login")
				.content(toJson(loginData)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		JSONObject jsonUser1Response = new JSONObject(result.getResponse().getContentAsString());
		assert jsonUser1Response.has("jwt");
		assert jsonUser1Response.getInt("status")==200;
		saveDataToFileSystem(TOKEN_USER_1,jsonUser1Response.getString("jwt"));

	}

	@Test
	void f_checkFacultyAdding() throws  Exception {


		//faculty1
		FacultyModel facultyModel = new FacultyModel("facultyOne","B.E","facultyOne@gmail.com","male","ECE",2);
		MvcResult result = mockMvc.perform(post("/faculty/add")
				.content(toJson(facultyModel))
				.header("Authorization","Bearer " + getDataFromFileSystem(TOKEN_ADMIN_1))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201)).andReturn();
		print(result.getResponse().getContentAsString());


		//faculty2
		FacultyModel facultyModel1 = new FacultyModel("facultyTwo","M.E","facultyTwo@gmail.com","female","CSE",3);
		MvcResult result2 = mockMvc.perform(post("/faculty/add")
				.content(toJson(facultyModel1))
				.header("Authorization","Bearer " + getDataFromFileSystem(TOKEN_ADMIN_1))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201)).andReturn();

		print(result2.getResponse().getContentAsString());

		//faculty1
		JSONObject response = new JSONObject(result.getResponse().getContentAsString());
		assert response.has("id");
		//checking Strings are same
		assert Objects.equals(response.getString("faculty_name"), "facultyOne");
		assert Objects.equals(response.getString("qualification"), "B.E");
		assert Objects.equals(response.getString("email"), "facultyOne@gmail.com");
		assert Objects.equals(response.getString("gender"), "male");
		assert Objects.equals(response.getString("department"), "ECE");
		assert Objects.equals(response.getInt("experience"), 2);

		//faculty2
		JSONObject response2 = new JSONObject(result2.getResponse().getContentAsString());
		assert response2.has("id");
		//checking Strings are same
		assert Objects.equals(response2.getString("faculty_name"), "facultyTwo");
		assert Objects.equals(response2.getString("qualification"), "M.E");
		assert Objects.equals(response2.getString("email"), "facultyTwo@gmail.com");
		assert Objects.equals(response2.getString("gender"), "female");
		assert Objects.equals(response2.getString("department"), "CSE");
		assert Objects.equals(response2.getInt("experience"), 3);


		saveDataToFileSystem(ID_FACULTY_1,response.getInt("id"));
		saveDataToFileSystem(ID_FACULTY_2,response2.getInt("id"));


		//Check unauthorized access
		mockMvc.perform(post("/faculty/add")
				.content(toJson(facultyModel))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized()).andReturn();

		//check forbidden access
		mockMvc.perform(post("/faculty/add")
				.content(toJson(facultyModel))
				.header("Authorization","Bearer " + getDataFromFileSystem(TOKEN_USER_1))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
	}

	@Test
	void g_getFacultyCheckTest() throws  Exception {

		mockMvc.perform(get("/faculty/list?experience=2").contentType(MediaType.APPLICATION_JSON_VALUE)
						.header("Authorization","Bearer "+ getDataFromFileSystem(TOKEN_USER_1)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.[0].id",Matchers.is(1)))
				.andExpect(jsonPath("$.[0].faculty_name", containsStringIgnoringCase("facultyOne")))
				.andExpect(jsonPath("$.[0].qualification", containsStringIgnoringCase("B.E")))
				.andExpect(jsonPath("$.[0].email", containsStringIgnoringCase("facultyOne@gmail.com")))
				.andExpect(jsonPath("$.[0].gender", containsStringIgnoringCase("male")))
				.andExpect(jsonPath("$.[0].department", containsStringIgnoringCase("ECE")))
				.andExpect(jsonPath("$.[0].experience", Matchers.is(2)));

		mockMvc.perform(get("/faculty/list?department=CSE").contentType(MediaType.APPLICATION_JSON_VALUE)
						.header("Authorization","Bearer "+ getDataFromFileSystem(TOKEN_ADMIN_1)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.[0].id",Matchers.is(2)))
				.andExpect(jsonPath("$.[0].faculty_name", containsStringIgnoringCase("facultyTwo")))
				.andExpect(jsonPath("$.[0].qualification", containsStringIgnoringCase("M.E")))
				.andExpect(jsonPath("$.[0].email", containsStringIgnoringCase("facultyTwo@gmail.com")))
				.andExpect(jsonPath("$.[0].gender", containsStringIgnoringCase("female")))
				.andExpect(jsonPath("$.[0].department", containsStringIgnoringCase("CSE")))
				.andExpect(jsonPath("$.[0].experience", Matchers.is(3)));

	}

	@Test
	void h_updateFacultywithDetailsCheck() throws  Exception {

		FacultyModel facultyModel = new FacultyModel("PhD","IT",5);
		MvcResult result = mockMvc.perform(patch("/faculty/update/"+getDataFromFileSystem(ID_FACULTY_1))
				.content(toJson(facultyModel))
				.header("Authorization", "Bearer " + getDataFromFileSystem(TOKEN_ADMIN_1))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(200)).andReturn();

		JSONObject response = new JSONObject(result.getResponse().getContentAsString());
		assert response.has("id");
		assert Objects.equals(response.getString("faculty_name"), "facultyOne");
		assert Objects.equals(response.getString("qualification"), "PhD");
		assert Objects.equals(response.getString("email"), "facultyOne@gmail.com");
		assert Objects.equals(response.getString("gender"), "male");
		assert Objects.equals(response.getString("department"), "IT");
		assert Objects.equals(response.getInt("experience"), 5);

		//USER cannot update
		MvcResult result1 = mockMvc.perform(patch("/faculty/update/"+getDataFromFileSystem(ID_FACULTY_2))
				.content(toJson(facultyModel))
				.header("Authorization", "Bearer " + getDataFromFileSystem(TOKEN_USER_1))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(403)).andReturn();

		//bad id
		MvcResult result2 = mockMvc.perform(patch("/faculty/update/8")
				.content(toJson(facultyModel))
				.header("Authorization", "Bearer " + getDataFromFileSystem(TOKEN_ADMIN_1))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400)).andReturn();

	}

	@Test
	void i_deleteFacultyWithNoAccess() throws  Exception {

		//user can't delete //forbidden
		mockMvc.perform(delete("/faculty/delete/"+getDataFromFileSystem(ID_FACULTY_1))
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization","Bearer "+ getDataFromFileSystem(TOKEN_USER_1)))
				.andExpect(status().isForbidden())
				.andReturn();

		//only the admin who create can delete that particular faculty details //wrong admin //forbidden
		mockMvc.perform(delete("/faculty/delete/"+getDataFromFileSystem(ID_FACULTY_2))
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization","Bearer "+ getDataFromFileSystem(TOKEN_ADMIN_2)))
				.andExpect(status().is(403))
				.andReturn();


		//invalid faculty id  //bad request
		mockMvc.perform(delete("/faculty/delete/7")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization","Bearer "+ getDataFromFileSystem(TOKEN_ADMIN_1)))
				.andExpect(status().is(400))
				.andReturn();

	}

	@Test
	void j_deleteFacultyWithAccess() throws  Exception {



		//only the admin who create can delete that particular faculty details //correct admin //no content
		mockMvc.perform(delete("/faculty/delete/"+getDataFromFileSystem(ID_FACULTY_1))
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization","Bearer "+ getDataFromFileSystem(TOKEN_ADMIN_1)))
				.andExpect(status().is(204))
				.andReturn();


		//only the admin who create can delete that particular faculty details //correct admin //no content
		mockMvc.perform(delete("/faculty/delete/"+getDataFromFileSystem(ID_FACULTY_2))
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization","Bearer "+ getDataFromFileSystem(TOKEN_ADMIN_1)))
				.andExpect(status().is(204))
				.andReturn();



	}

	@Test
	void z_checkSwagger() throws Exception {
		MvcResult result = mockMvc.perform(get("/v3/api-docs").header("Authorization","Bearer " + getDataFromFileSystem(TOKEN_ADMIN_1))).andExpect(status().isOk()).andReturn();
		assert result.getResponse().getContentAsString().contains("openapi");
	}

	private byte[] toJson(Object r) throws Exception {
		ObjectMapper map = new ObjectMapper();
		return map.writeValueAsString(r).getBytes();
	}


	private void print(String s){
		out.println(s);
	}

	private void saveDataToFileSystem(Object key,Object value) throws Exception {
		try {
			JSONObject jsonObject = new JSONObject();
			StringBuilder builder = new StringBuilder();
			try {
				File myObj = new File("temp.txt");
				Scanner myReader = new Scanner(myObj);
				while (myReader.hasNextLine()) {
					builder.append(myReader.nextLine());
				}
				myReader.close();
				if (!builder.toString().isEmpty())
					jsonObject = new JSONObject(builder.toString());
			} catch (FileNotFoundException | JSONException e) {
				e.printStackTrace();
			}

			BufferedWriter writer = new BufferedWriter(new FileWriter("temp.txt"));
			jsonObject.put((String) key, value);
			writer.write(jsonObject.toString());
			writer.close();
		}catch (JSONException | IOException e){
			throw new Exception("Data not saved.");
		}
	}

	private Object getDataFromFileSystem(String key) throws Exception {
		try {
			File myObj = new File("temp.txt");
			Scanner myReader = new Scanner(myObj);
			StringBuilder builder = new StringBuilder();
			while (myReader.hasNextLine()) {
				builder.append(myReader.nextLine());
			}
			myReader.close();
			JSONObject jsonObject = new JSONObject(builder.toString());
			return jsonObject.get(key);
		} catch (FileNotFoundException | JSONException e) {
			throw new Exception("Data not found. Check authentication and ID generations to make sure data is being produced.");
		}
	}

}
