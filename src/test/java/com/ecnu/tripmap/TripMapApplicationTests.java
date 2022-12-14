package com.ecnu.tripmap;

import com.ecnu.tripmap.model.vo.PlaceBiref;
import com.ecnu.tripmap.model.vo.PostBrief;
import com.ecnu.tripmap.mysql.entity.Post;
import com.ecnu.tripmap.neo4j.dao.PlaceRepository;
import com.ecnu.tripmap.neo4j.dao.PostRepository;
import com.ecnu.tripmap.neo4j.dao.UserRepository;
import com.ecnu.tripmap.service.Impl.UserServiceImpl;
import com.ecnu.tripmap.service.PlaceService;
import com.ecnu.tripmap.service.SearchService;
import com.ecnu.tripmap.service.UserService;
import com.ecnu.tripmap.utils.CopyUtil;
import com.ecnu.tripmap.utils.RedisUtil;
import com.ecnu.tripmap.utils.SimilarityUtil;
import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class TripMapApplicationTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SearchService searchService;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void testSave() {
//int i = 0;
//int greater = 0;
//int equal = 0;
//int le = 0;
//        for (; i < 1000; i++) {
//            UserVo userInfo = userService.findUserInfo(i + 1);
//            List<PlaceNode> userStoredPlace = placeRepository.findUserStoredPlace(i + 1);
//            if (userInfo.getUserCollectLocationCount() > userStoredPlace.size()) {
//               le++;
//            }else if(userInfo.getUserCollectLocationCount() == userStoredPlace.size()){
//                equal++;
//            }else greater++;
//
//        }
//        System.out.println(le);
//        System.out.println(equal);
//        System.out.println(greater);
        Integer followRelationship = userRepository.createFollowRelationship(1, 1111);
        System.out.println(followRelationship);
    }


    @Test
    void copyUtilTest() {
        Post post = new Post(1, new Date(), "ilist", "desc", 0, 0, "title");
        PostBrief copy = CopyUtil.copy(post, PostBrief.class);
        System.out.println(copy);
    }

    @Test
    void password() {
        String pass = "1";
        boolean matches = passwordEncoder.matches(pass, "$2a$10$MJ.vAWxoIR712GCjl9qZpO9KHvTU52Pbd2KOlEYEq/cegYm0eXNUW");
        if (matches)
            System.out.println("matches");
    }

//    @Test
//    void t(){
//        List<Integer> recommend = similarityUtil.recommend(3);
//        for (Integer integer : recommend) {
//            System.out.println(integer);
//        }
//    }

    @Test
    void recommendPlaces(){
//        List<PlaceBiref> places = placeService.recommendPlaces(1);
//        for (PlaceBiref place:places){
//            System.out.println(place.getPlaceId().toString());
//            System.out.println(place.getPlaceAddress());
//        }
        HashMap<String, Object> sd = searchService.search("???", 0, 1);
        Object user = sd.get("post");
        System.out.println(user);
    }

    @Test
    public void test() {
        // ??????????????????????????????????????????
        System.setProperty("jasypt.encryptor.password", "tripmap");
        StringEncryptor stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());
        System.out.println("??????mysql.username??? " + stringEncryptor.encrypt("root"));
        System.out.println("??????mysql.password??? " + stringEncryptor.encrypt("ZHAOjiaJUN2022!"));
        System.out.println("??????mysq.url??? " + stringEncryptor.encrypt("jdbc:mysql://127.0.0.1:3306/trip_map?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false"));
        System.out.println("??????neo4j.username??? " + stringEncryptor.encrypt("neo4j"));
        System.out.println("??????neo4j.password??? " + stringEncryptor.encrypt("123456"));
        System.out.println("??????redis.host??? " + stringEncryptor.encrypt("127.0.0.1"));
        System.out.println("??????cos.url??? " + stringEncryptor.encrypt("https://juzimang-image-1307651200.cos.ap-shanghai.myqcloud.com"));
        System.out.println("??????cos.appId??? " + stringEncryptor.encrypt("1307651200"));
        System.out.println("??????cos.secretId??? " + stringEncryptor.encrypt("AKIDsWu2tTmoa5JFvJQGp78sPfeMnxqRlpc8"));
        System.out.println("??????cos.secretKey??? " + stringEncryptor.encrypt("GH2WjfKiqhIbdOs50e2A3kESP5UdU4NP"));
    }

    @Test
    public void redisTest(){
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        integers.add(5);
        redisUtil.lSet("integers", integers);
        List<Object> integers1 = redisUtil.lGet("integers", 0, -1);
        List<Integer> fromList = SimilarityUtil.getFromList(integers1);

        fromList.forEach(System.out::println);
//        redisUtil.del("integers");
    }

}
