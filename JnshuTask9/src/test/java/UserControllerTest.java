import com.gerry.jnshu.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @MockBean
//    private UserService userService;

    @Test
    public void testList() throws Exception{
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/users"));
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().json("[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"username\": \"yudaoyuanma\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"username\": \"woshiyutou\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 3,\n" +
                "        \"username\": \"chifanshuijiao\"\n" +
                "    }\n" +
                "]")); // 响应结果

    }
    @Test
    public void testGet() throws Exception {
        // 获得指定用户编号的用户
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users/1"));
        // 校验结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()); // 响应状态码 200
        resultActions.andExpect(MockMvcResultMatchers.content().json("{\n" +
                "\"id\": 1,\n" +
                "\"username\": \"username:1\"\n" +
                "}")); // 响应结果
    }

    @Test
    public void testGet2(){
//        Mockito.when(userService.get(1)).thenReturn(new UserVO().setId(1).setUsername("username:1"));

    }
    @Test
    public void testAdd() throws Exception {
        // 获得指定用户编号的用户
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .param("username", "yudaoyuanma")
                .param("passowrd", "nicai"));
        // 校验结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()); // 响应状态码 200
        resultActions.andExpect(MockMvcResultMatchers.content().string("1")); // 响应结果
    }

    @Test
    public void testUpdate() throws Exception {
        // 获得指定用户编号的用户
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                .param("username", "yudaoyuanma")
                .param("passowrd", "nicai"));
        // 校验结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()); // 响应状态码 200
        resultActions.andExpect(MockMvcResultMatchers.content().string("true")); // 响应结果
    }

    @Test
    public void testDelete() throws Exception {
        // 获得指定用户编号的用户
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/users/1"));
        // 校验结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()); // 响应状态码 200
        resultActions.andExpect(MockMvcResultMatchers.content().string("false")); // 响应结果
    }

}
