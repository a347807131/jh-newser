package fun.gatsby;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("fun.gatsby");

        noClasses()
            .that()
                .resideInAnyPackage("fun.gatsby.service..")
            .or()
                .resideInAnyPackage("fun.gatsby.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..fun.gatsby.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
