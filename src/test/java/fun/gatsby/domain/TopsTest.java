package fun.gatsby.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fun.gatsby.web.rest.TestUtil;

public class TopsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tops.class);
        Tops tops1 = new Tops();
        tops1.setId(1L);
        Tops tops2 = new Tops();
        tops2.setId(tops1.getId());
        assertThat(tops1).isEqualTo(tops2);
        tops2.setId(2L);
        assertThat(tops1).isNotEqualTo(tops2);
        tops1.setId(null);
        assertThat(tops1).isNotEqualTo(tops2);
    }
}
