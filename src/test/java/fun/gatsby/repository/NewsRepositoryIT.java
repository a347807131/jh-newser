package fun.gatsby.repository;

import fun.gatsby.JhdApp;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = JhdApp.class)
@Transactional
public class NewsRepositoryIT {
    @Autowired
    private NewsRepository newsRepository;
    @Test
    public void testFindWithout(){
    }
}
