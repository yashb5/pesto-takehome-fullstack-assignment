package com.pesto.takehomefullstackassignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pesto.takehomefullstackassignment.entity.Task;
import com.pesto.takehomefullstackassignment.entity.TaskStatus;
import com.pesto.takehomefullstackassignment.entity.User;
import com.pesto.takehomefullstackassignment.model.TaskRequest;
import com.pesto.takehomefullstackassignment.model.TaskUpdateRequest;
import com.pesto.takehomefullstackassignment.repository.TaskRepository;
import com.pesto.takehomefullstackassignment.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;
    private User otherUser;

    @BeforeEach
    void setUp() {
        testUser = new User("testuser", "test@example.com", passwordEncoder.encode("password"));
        testUser = userRepository.save(testUser);

        otherUser = new User("otheruser", "other@example.com", passwordEncoder.encode("password"));
        otherUser = userRepository.save(otherUser);
    }

    @Test
    void getTaskList_Unauthenticated_RedirectsToLogin() throws Exception {
        mockMvc.perform(get("/api/v1/todo"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "testuser")
    void getTaskList_Authenticated_ReturnsOnlyUsersTasks() throws Exception {
        Task userTask = new Task();
        userTask.setTitle("User's Task");
        userTask.setStatus(TaskStatus.TODO);
        userTask.setUser(testUser);
        taskRepository.save(userTask);

        Task otherTask = new Task();
        otherTask.setTitle("Other's Task");
        otherTask.setStatus(TaskStatus.TODO);
        otherTask.setUser(otherUser);
        taskRepository.save(otherTask);

        mockMvc.perform(get("/api/v1/todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("User's Task")));
    }

    @Test
    @WithMockUser(username = "testuser")
    void createTask_AssociatesWithCurrentUser() throws Exception {
        TaskRequest request = TaskRequest.builder()
                .title("New Task")
                .description("Description")
                .status(TaskStatus.TODO)
                .build();

        mockMvc.perform(post("/api/v1/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("New Task")));

        var tasks = taskRepository.findTasksByUserAndStatusIsNot(testUser, TaskStatus.DELETED);
        assertEquals(1, tasks.size());
        assertEquals("New Task", tasks.get(0).getTitle());
        assertEquals(testUser.getId(), tasks.get(0).getUser().getId());
    }

    @Test
    @WithMockUser(username = "testuser")
    void updateTask_OwnTask_Success() throws Exception {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setStatus(TaskStatus.TODO);
        task.setUser(testUser);
        task = taskRepository.save(task);

        TaskUpdateRequest updateRequest = new TaskUpdateRequest();
        updateRequest.setStatus(TaskStatus.IN_PROGRESS);

        mockMvc.perform(put("/api/v1/todo/" + task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        Task updatedTask = taskRepository.findById(task.getId()).orElseThrow();
        assertEquals(TaskStatus.IN_PROGRESS, updatedTask.getStatus());
    }

    @Test
    @WithMockUser(username = "testuser")
    void updateTask_OtherUsersTask_ReturnsNotFound() throws Exception {
        Task task = new Task();
        task.setTitle("Other's Task");
        task.setStatus(TaskStatus.TODO);
        task.setUser(otherUser);
        task = taskRepository.save(task);

        TaskUpdateRequest updateRequest = new TaskUpdateRequest();
        updateRequest.setStatus(TaskStatus.IN_PROGRESS);

        mockMvc.perform(put("/api/v1/todo/" + task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound());

        Task unchangedTask = taskRepository.findById(task.getId()).orElseThrow();
        assertEquals(TaskStatus.TODO, unchangedTask.getStatus());
    }

    @Test
    @WithMockUser(username = "testuser")
    void deleteTask_OwnTask_Success() throws Exception {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setStatus(TaskStatus.TODO);
        task.setUser(testUser);
        task = taskRepository.save(task);

        TaskUpdateRequest deleteRequest = new TaskUpdateRequest();
        deleteRequest.setStatus(TaskStatus.DELETED);

        mockMvc.perform(put("/api/v1/todo/" + task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteRequest)))
                .andExpect(status().isOk());

        Task deletedTask = taskRepository.findById(task.getId()).orElseThrow();
        assertEquals(TaskStatus.DELETED, deletedTask.getStatus());
    }

    @Test
    @WithMockUser(username = "testuser")
    void deleteTask_OtherUsersTask_ReturnsNotFound() throws Exception {
        Task task = new Task();
        task.setTitle("Other's Task");
        task.setStatus(TaskStatus.TODO);
        task.setUser(otherUser);
        task = taskRepository.save(task);

        TaskUpdateRequest deleteRequest = new TaskUpdateRequest();
        deleteRequest.setStatus(TaskStatus.DELETED);

        mockMvc.perform(put("/api/v1/todo/" + task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteRequest)))
                .andExpect(status().isNotFound());

        Task unchangedTask = taskRepository.findById(task.getId()).orElseThrow();
        assertEquals(TaskStatus.TODO, unchangedTask.getStatus());
    }

    @Test
    @WithMockUser(username = "testuser")
    void getTaskList_FilterByStatus_ReturnsOnlyMatchingTasks() throws Exception {
        Task todoTask = new Task();
        todoTask.setTitle("Todo Task");
        todoTask.setStatus(TaskStatus.TODO);
        todoTask.setUser(testUser);
        taskRepository.save(todoTask);

        Task doneTask = new Task();
        doneTask.setTitle("Done Task");
        doneTask.setStatus(TaskStatus.DONE);
        doneTask.setUser(testUser);
        taskRepository.save(doneTask);

        mockMvc.perform(get("/api/v1/todo").param("status", "TODO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Todo Task")));
    }

    @Test
    @WithMockUser(username = "testuser")
    void getTaskList_ExcludesDeletedTasks() throws Exception {
        Task activeTask = new Task();
        activeTask.setTitle("Active Task");
        activeTask.setStatus(TaskStatus.TODO);
        activeTask.setUser(testUser);
        taskRepository.save(activeTask);

        Task deletedTask = new Task();
        deletedTask.setTitle("Deleted Task");
        deletedTask.setStatus(TaskStatus.DELETED);
        deletedTask.setUser(testUser);
        taskRepository.save(deletedTask);

        mockMvc.perform(get("/api/v1/todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Active Task")));
    }
}

